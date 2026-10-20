/*
 * Copyright (C) 2026 piko <https://github.com/crimera/piko>
 *
 * See the included NOTICE file for GPLv3 §7(b) terms that apply to this code.
 */

package app.crimera.patches.instagram.misc.settings

import app.crimera.patches.instagram.misc.extension.sharedExtensionPatch
import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.extensions.InstructionExtensions.instructions
import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.patch.PatchException
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.util.proxy.mutableTypes.MutableMethod
import app.morphe.patcher.util.smali.toInstruction
import app.morphe.util.registersUsed
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.builder.MethodImplementationBuilder
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.instruction.TwoRegisterInstruction
import com.android.tools.smali.dexlib2.iface.reference.FieldReference
import com.android.tools.smali.dexlib2.immutable.ImmutableMethod

private const val IGDS_SWITCH = "Lcom/instagram/igds/components/switchbutton/IgdsSwitch;"
private const val STYLE_METHOD = "pikoUseMaterialSwitchStyle"
private const val PREFERENCE_STYLE =
    "Lapp/morphe/extension/instagram/settings/preference/widgets/InstagramPreferenceStyle;"

internal val nativeSettingsSwitchStylePatch = bytecodePatch {
    dependsOn(sharedExtensionPatch)
    execute {
        val switchClass = mutableClassDefBy(IGDS_SWITCH)
        fun styleReads(method: MutableMethod) = method.instructions.mapNotNull { instruction ->
            val field = (instruction as? ReferenceInstruction)?.reference as? FieldReference
            field?.takeIf {
                instruction.opcode in setOf(Opcode.SGET_BOOLEAN, Opcode.IGET_BOOLEAN) &&
                    it.definingClass == IGDS_SWITCH && it.type == "Z"
            }
        }
        val drawMethod = switchClass.methods.singleOrNull {
            it.name == "onDraw" && it.parameterTypes == listOf("Landroid/graphics/Canvas;") &&
                it.returnType == "V"
        } ?: throw PatchException("Expected one native switch drawing method")
        val checkedMethod = switchClass.methods.singleOrNull {
            it.name == "setChecked" && it.parameterTypes == listOf("Z") && it.returnType == "V"
        } ?: throw PatchException("Expected one native switch checked-state method")
        // The style gate moved from a static flag to an instance flag. In both shapes it
        // controls construction, drawing and checked-state animation; the other boolean
        // only controls animation and is deliberately excluded by this intersection.
        val constructorFields = switchClass.methods.filter { it.name == "<init>" }
            .flatMap(::styleReads).map { it.toString() }.toSet()
        val checkedFields = styleReads(checkedMethod).map { it.toString() }.toSet()
        val sharedFlags = styleReads(drawMethod).distinctBy { it.toString() }.filter {
            it.toString() in constructorFields && it.toString() in checkedFields
        }
        // The legacy widget also reads its instance animation flag in all three
        // paths. Its style policy is the class-wide flag initialized in <clinit>.
        // The refactored widget has no class-wide policy and one shared instance gate.
        val staticFlags = sharedFlags.filter { candidate ->
            switchClass.fields.any { it.toString() == candidate.toString() && AccessFlags.STATIC.isSet(it.accessFlags) }
        }
        val styleField = (if (staticFlags.isNotEmpty()) staticFlags else sharedFlags).singleOrNull()
            ?: throw PatchException("Expected one native switch style flag across creation, drawing and animation")
        val fieldDefinition = switchClass.fields.singleOrNull { it.toString() == styleField.toString() }
            ?: throw PatchException("Native switch style flag has no field definition")
        val staticStyle = AccessFlags.STATIC.isSet(fieldDefinition.accessFlags)
        val readOpcode = if (staticStyle) Opcode.SGET_BOOLEAN else Opcode.IGET_BOOLEAN
        if (switchClass.methods.any { it.name == STYLE_METHOD }) {
            throw PatchException("Native switch style bridge already exists")
        }

        // A shortcut can load IgdsSwitch before Instagram initializes its style provider.
        val patchedMethods = mutableSetOf<String>()
        for (method in switchClass.methods) {
            val reads = method.instructions.mapIndexedNotNull { index, instruction ->
                if (instruction.opcode == readOpcode &&
                    (instruction as? ReferenceInstruction)?.reference.toString() == styleField.toString()
                ) index else null
            }
            if (reads.isEmpty()) continue
            if (AccessFlags.STATIC.isSet(method.accessFlags)) {
                throw PatchException("Native switch style read has no view instance")
            }
            for (index in reads.asReversed()) {
                val instruction = method.instructions[index]
                val register = if (staticStyle) {
                    instruction.registersUsed.singleOrNull()
                        ?: throw PatchException("Native switch style read has no destination register")
                } else {
                    val read = instruction as? TwoRegisterInstruction
                        ?: throw PatchException("Native switch style instance read has an unexpected shape")
                    val parameterWords = method.parameterTypes.sumOf {
                        if (it == "J" || it == "D") 2 else 1
                    }
                    val thisRegister = method.implementation!!.registerCount - parameterWords - 1
                    if (read.registerB != thisRegister) {
                        throw PatchException("Native switch style is read from a different view instance")
                    }
                    read.registerA
                }
                method.replaceInstruction(index,
                    "invoke-direct/range {p0 .. p0}, $IGDS_SWITCH->$STYLE_METHOD()Z")
                method.addInstruction(index + 1, "move-result v$register")
            }
            patchedMethods += method.name
        }
        if (!patchedMethods.containsAll(listOf("<init>", "onDraw", "setChecked"))) {
            throw PatchException("Native switch style does not cover creation, drawing and animation")
        }

        val implementation = MethodImplementationBuilder(2).apply {
            addInstruction((if (staticStyle) "sget-boolean v0, $styleField"
                else "iget-boolean v0, v1, $styleField").toInstruction())
            addInstruction("invoke-static {v1, v0}, $PREFERENCE_STYLE->useMaterialSwitchStyle(Landroid/view/View;Z)Z".toInstruction())
            addInstruction("move-result v0".toInstruction())
            addInstruction("return v0".toInstruction())
        }.methodImplementation
        switchClass.methods.add(MutableMethod(ImmutableMethod(
            IGDS_SWITCH, STYLE_METHOD, emptyList(), "Z", AccessFlags.PRIVATE.value,
            emptySet(), emptySet(), implementation,
        )))
    }
}
