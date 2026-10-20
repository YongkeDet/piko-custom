/*
 * Copyright (C) 2026 piko <https://github.com/crimera/piko>
 *
 * See the included NOTICE file for GPLv3 §7(b) terms that apply to this code.
 */

package app.crimera.patches.instagram.misc.stories.loopStory

import app.crimera.patches.instagram.utils.Constants.PREF_CALL_DESCRIPTOR
import app.morphe.patcher.extensions.InstructionExtensions.addInstructionsWithLabels
import app.morphe.patcher.extensions.InstructionExtensions.instructions
import app.morphe.patcher.patch.BytecodePatchContext
import app.morphe.patcher.patch.PatchException
import app.morphe.patcher.util.proxy.mutableTypes.MutableMethod
import app.morphe.patcher.util.smali.ExternalLabel
import app.morphe.util.getReference
import app.morphe.util.p0Register
import app.morphe.util.registersUsed
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.reference.FieldReference
import com.android.tools.smali.dexlib2.iface.reference.MethodReference

/** Resolve both native video backends through their shared absolute-seek contract. */
context(context: BytecodePatchContext)
internal fun hookInterfaceStoryLoop(method: MutableMethod, itemType: String, itemRegister: Int) {
    val fragment = context.classDefBy(method.definingClass)
    val playerField = fragment.fields.singleOrNull { it.name == "mVideoPlayer" }
        ?: throw PatchException("Expected one story video player field")
    val playerInterface = context.classDefBy(playerField.type)
    val seekDeclarations = playerInterface.methods.filter {
        it.parameterTypes == listOf("I") && it.returnType == "V"
    }
    if (seekDeclarations.size != 2) throw PatchException("Expected absolute and relative story seek declarations")
    val resume = playerInterface.methods.singleOrNull {
        it.parameterTypes == listOf("Ljava/lang/String;", "Z") && it.returnType == "V"
    } ?: throw PatchException("Expected one story resume declaration")
    val players = buildList {
        context.classDefForEach { cls ->
            if (playerField.type in cls.interfaces && !AccessFlags.ABSTRACT.isSet(cls.accessFlags)) add(cls)
        }
    }
    if (players.size !in 1..2) throw PatchException("Expected one or two native story video backends, found ${players.size}")
    val resolved = players.map { player ->
        val seeks = player.methods.filter { candidate ->
            AccessFlags.PUBLIC.isSet(candidate.accessFlags) &&
                seekDeclarations.any { it.name == candidate.name && it.parameterTypes == candidate.parameterTypes && it.returnType == candidate.returnType }
        }
        if (seeks.size != 2) throw PatchException("Incomplete native seek contract in ${player.type}")
        fun addsPosition(candidate: com.android.tools.smali.dexlib2.iface.Method) =
            candidate.implementation?.instructions?.any {
                it.opcode in setOf(Opcode.ADD_INT, Opcode.ADD_INT_2ADDR, Opcode.ADD_INT_LIT8, Opcode.ADD_INT_LIT16)
            } == true
        val relative = seeks.singleOrNull(::addsPosition)
            ?: throw PatchException("Expected one relative seek in ${player.type}")
        val absolute = seeks.singleOrNull { !addsPosition(it) }
            ?: throw PatchException("Expected one absolute seek in ${player.type}")
        fun leaves(candidate: com.android.tools.smali.dexlib2.iface.Method) =
            candidate.implementation!!.instructions.mapNotNull { instruction ->
                instruction.getReference<MethodReference>()?.takeIf {
                    instruction.opcode == Opcode.INVOKE_DIRECT && it.definingClass == player.type &&
                        it.parameterTypes == listOf("I") && it.returnType == "V"
                }
            }.map { it.toString() }.toSet()
        val leafReference = leaves(relative).intersect(leaves(absolute)).singleOrNull()
            ?: throw PatchException("Absolute and relative story seek do not share one native leaf")
        val leaf = player.methods.single { it.toString() == leafReference }
        // One backend checks the current item in its relative adapter; the other checks it
        // inside the common seek leaf. Both must resolve the same concrete current-item field.
        val item = (relative.implementation!!.instructions + leaf.implementation!!.instructions).mapNotNull { instruction ->
            instruction.getReference<FieldReference>()?.takeIf {
                instruction.opcode == Opcode.IGET_OBJECT && it.definingClass == player.type && it.type == itemType
            }
        }.distinctBy { it.toString() }.singleOrNull()
            ?: throw PatchException("Expected one current story in ${player.type}")
        if (player.fields.none { it.toString() == item.toString() && AccessFlags.PUBLIC.isSet(it.accessFlags) }) {
            throw PatchException("Current story field is not accessible")
        }
        Triple(player.type, item, absolute.name)
    }
    val absoluteName = resolved.map { it.third }.distinct().singleOrNull()
        ?: throw PatchException("Native video backends disagree on absolute seek")
    val absolute = seekDeclarations.single { it.name == absoluteName }
    val code = method.instructions
    val photoIndex = code.indices.singleOrNull { i ->
        i + 6 < code.size && code[i].opcode == Opcode.IGET_OBJECT &&
            code[i].getReference<FieldReference>()?.definingClass == fragment.type &&
            code[i + 1].opcode == Opcode.CONST_4 && code[i + 1].registersUsed == listOf(1) &&
            code[i + 2].opcode == Opcode.IF_EQZ && code[i].registersUsed[0] == 2 &&
            code[i + 2].registersUsed == listOf(2) &&
            code[i + 3].opcode == Opcode.IGET_OBJECT &&
            code[i + 4].getReference<MethodReference>()?.toString() == "Ljava/lang/Object;->equals(Ljava/lang/Object;)Z" &&
            code[i + 6].opcode == Opcode.IF_NE && code[i + 6].registersUsed == listOf(0, 3)
    } ?: throw PatchException("Expected one native story photo restart block")
    if (method.p0Register !in 4..14 || itemRegister != method.p0Register + 1) {
        throw PatchException("Unsupported story restart registers")
    }
    val backends = resolved.mapIndexed { index, (type, item, _) -> """
        instance-of v1, v0, $type
        if-eqz v1, :piko_player_$index
        check-cast v0, $type
        iget-object v1, v0, $item
        invoke-virtual {v$itemRegister, v1}, Ljava/lang/Object;->equals(Ljava/lang/Object;)Z
        move-result v1
        if-eqz v1, :piko_photo
        const/4 v1, 0x0
        invoke-interface {v0, v1}, $absolute
        const-string v1, "resume"
        const/4 v2, 0x0
        invoke-interface {v0, v1, v2}, $resume
        goto :piko_photo
        :piko_player_$index
    """.trimIndent() }.joinToString("\n")
    method.addInstructionsWithLabels(0, """
        ${PREF_CALL_DESCRIPTOR}->loopStory()Z
        move-result v0
        if-eqz v0, :piko_original
        check-cast v$itemRegister, $itemType
        iget-object v0, p0, $playerField
        if-eqz v0, :piko_photo
        $backends
        goto :piko_original
        :piko_photo
        const/4 v3, 0x1
        goto :piko_photo_restart
    """.trimIndent(), ExternalLabel("piko_original", code[0]), ExternalLabel("piko_photo_restart", code[photoIndex]))
}
