/*
 * Copyright (C) 2026 piko <https://github.com/crimera/piko>
 *
 * See the included NOTICE file for GPLv3 §7(b) terms that apply to this code.
 */

package app.crimera.patches.instagram.filter.story

import app.crimera.bytecode.Target
import app.crimera.bytecode.insertHook
import app.crimera.bytecode.methodReference
import app.crimera.patches.instagram.entity.reelResponseItem.reelResponseItemEntity
import app.crimera.patches.instagram.entity.userdata.userDataEntity
import app.crimera.patches.instagram.misc.settings.settingsPatch
import app.crimera.patches.instagram.utils.Constants.COMPATIBILITY_INSTAGRAM
import app.crimera.patches.instagram.utils.Constants.PATCHES_DESCRIPTOR
import app.crimera.patches.instagram.utils.enableSettings
import app.morphe.patcher.extensions.InstructionExtensions.instructions
import app.morphe.patcher.patch.PatchException
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.util.proxy.mutableTypes.MutableMethod
import app.morphe.util.getReference
import app.morphe.util.registersUsed
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.OffsetInstruction
import com.android.tools.smali.dexlib2.iface.reference.MethodReference
import com.android.tools.smali.dexlib2.iface.reference.FieldReference
import com.android.tools.smali.dexlib2.iface.reference.TypeReference
import com.android.tools.smali.dexlib2.iface.reference.StringReference

internal data class StoryFilterSite(val guardIndex: Int, val itemRegister: Int)

internal fun resolveStoryFilterSite(method: MutableMethod): StoryFilterSite {
    val code = method.instructions
    val parserParameters = method.parameterTypes.map(CharSequence::toString)
    val trayIndex = code.indices.singleOrNull {
        code[it].getReference<StringReference>()?.string == "tray"
    } ?: throw PatchException("Expected one story tray JSON key")
    val addresses = mutableMapOf<Int, Int>()
    var address = 0
    code.forEachIndexed { index, instruction ->
        addresses[address] = index
        address += instruction.codeUnits
    }
    fun target(index: Int): Int? {
        val branch = code.getOrNull(index) as? OffsetInstruction ?: return null
        val from = code.take(index).sumOf { it.codeUnits }
        return addresses[from + branch.codeOffset]
    }
    val keyGuardIndex = trayIndex + 3
    if (code.getOrNull(trayIndex + 1)?.getReference<MethodReference>()?.toString() !=
        "Ljava/lang/String;->equals(Ljava/lang/Object;)Z" ||
        code.getOrNull(trayIndex + 2)?.opcode != Opcode.MOVE_RESULT ||
        code.getOrNull(keyGuardIndex)?.opcode != Opcode.IF_EQZ
    ) throw PatchException("Story tray key has no native equality guard")
    // The key mismatch can jump backwards to the shared field loop. Bound the tray
    // array by its own native token guard and ArrayList allocation instead.
    val arrayGuardIndex = keyGuardIndex + 4
    val tokenCall = code.getOrNull(arrayGuardIndex - 3)?.getReference<MethodReference>()
        ?: throw PatchException("Story tray has no native current-token call")
    val tokenResult = code.getOrNull(arrayGuardIndex - 2)
        ?: throw PatchException("Story tray has no current-token result")
    val arrayToken = code.getOrNull(arrayGuardIndex - 1)
        ?: throw PatchException("Story tray has no array token")
    val tokenField = arrayToken.getReference<FieldReference>()
        ?: throw PatchException("Story tray has no array token field")
    if (tokenCall.definingClass != parserParameters.singleOrNull() ||
        tokenCall.parameterTypes.isNotEmpty() || tokenField.type != tokenCall.returnType ||
        tokenResult.opcode != Opcode.MOVE_RESULT_OBJECT || arrayToken.opcode != Opcode.SGET_OBJECT ||
        code.getOrNull(arrayGuardIndex)?.opcode != Opcode.IF_NE ||
        code[arrayGuardIndex].registersUsed.toSet() != (tokenResult.registersUsed + arrayToken.registersUsed).toSet() ||
        code.getOrNull(arrayGuardIndex + 1)?.opcode != Opcode.NEW_INSTANCE ||
        code[arrayGuardIndex + 1].getReference<TypeReference>()?.type != "Ljava/util/ArrayList;"
    ) throw PatchException("Story tray has no native array token guard/allocation: " +
        "call=$tokenCall parameters=$parserParameters result=${tokenResult.opcode}/${tokenResult.registersUsed} " +
        "field=$tokenField token=${arrayToken.opcode}/${arrayToken.registersUsed} " +
        "guard=${code[arrayGuardIndex].opcode}/${code[arrayGuardIndex].registersUsed} " +
        "allocation=${code.getOrNull(arrayGuardIndex + 1)?.opcode}/${code.getOrNull(arrayGuardIndex + 1)?.getReference<TypeReference>()}")
    val end = target(arrayGuardIndex)
        ?.takeIf { it > arrayGuardIndex } ?: throw PatchException("Story tray has no bounded array case")
    val rejected = mutableListOf<String>()
    val sites = (arrayGuardIndex + 1 until end).mapNotNull { addIndex ->
        val add = code[addIndex]
        val reference = add.getReference<MethodReference>() ?: return@mapNotNull null
        if (reference.definingClass !in setOf("Ljava/util/AbstractCollection;", "Ljava/util/ArrayList;",
                "Ljava/util/Collection;", "Ljava/util/List;") ||
            reference.name != "add" || reference.parameterTypes.map(CharSequence::toString) != listOf("Ljava/lang/Object;") ||
            reference.returnType != "Z" || add.registersUsed.size != 2
        ) return@mapNotNull null
        val item = add.registersUsed[1]
        val guardIndex = addIndex - 1
        val result = code.getOrNull(guardIndex - 1)
        val parse = code.getOrNull(guardIndex - 2)
        val parseReference = parse?.getReference<MethodReference>() ?: return@mapNotNull null
        val skip = target(guardIndex)
        if (code[guardIndex].opcode != Opcode.IF_EQZ || code[guardIndex].registersUsed != listOf(item) ||
            result?.opcode != Opcode.MOVE_RESULT_OBJECT || result.registersUsed != listOf(item) ||
            parse?.opcode !in setOf(Opcode.INVOKE_VIRTUAL, Opcode.INVOKE_VIRTUAL_RANGE) ||
            parseReference.parameterTypes.map(CharSequence::toString) != parserParameters ||
            parseReference.returnType != "Ljava/lang/Object;" ||
            skip == null || skip <= keyGuardIndex || skip >= guardIndex - 2 ||
            code.getOrNull(addIndex + 1)?.opcode !in setOf(Opcode.GOTO, Opcode.GOTO_16, Opcode.GOTO_32) ||
            target(addIndex + 1) != skip
        ) {
            rejected += "add=$addIndex args=${add.registersUsed} guard=${code[guardIndex].opcode}/${code[guardIndex].registersUsed} " +
                "result=${result?.opcode}/${result?.registersUsed} parse=$parseReference parameters=${method.parameterTypes} " +
                "skip=$skip continuation=${target(addIndex + 1)}"
            return@mapNotNull null
        }
        StoryFilterSite(guardIndex, item)
    }
    return sites.singleOrNull()
        ?: throw PatchException("Expected one parsed story tray item/null guard/add loop, found ${sites.size}: ${rejected.joinToString()}")
}

internal fun installStoryFilterHook(method: MutableMethod) {
    val site = resolveStoryFilterSite(method)
    // Keep the original null guard and its loop continuation. Existing incoming labels
    // enter the complete hook; the local bypass returns to that guard, never move-result.
    method.insertHook(site.guardIndex, relocateBranchTargets = true) {
        ifEqz(site.itemRegister, Target.Original)
        invokeStatic(methodReference("$PATCHES_DESCRIPTOR/filter/story/FilterStory;->filter(Ljava/lang/Object;)Ljava/lang/Object;"), site.itemRegister)
        moveResult(site.itemRegister, "Ljava/lang/Object;")
    }
}

// Heavily based on @brosssh work.
// https://github.com/brosssh/instagram-morphe-patches-library/blob/dev/patch-library/src/main/kotlin/app/morphe/library/instagram/patches/FilterStoriesListPatch.kt

@Suppress("unused")
val filterStoriesPatch =
    bytecodePatch(
        name = "Filter stories",
        description = "Filter stories to hide based on different categories",
        default = true,
    ) {
        compatibleWith(COMPATIBILITY_INSTAGRAM)
        dependsOn(settingsPatch, reelResponseItemEntity, userDataEntity)
        execute {

            installStoryFilterHook(StoryResponseJsonParserFingerprint.matchAll(1..1).single().method)
            enableSettings("storyFilters")
        }
    }
