/*
 * Copyright (C) 2026 piko <https://github.com/crimera/piko>
 *
 * See the included NOTICE file for GPLv3 §7(b) terms that apply to this code.
 */

package app.crimera.patches.instagram.links

import app.crimera.patches.instagram.misc.settings.settingsPatch
import app.crimera.patches.instagram.utils.Constants
import app.crimera.patches.instagram.utils.Constants.COMPATIBILITY_INSTAGRAM
import app.crimera.patches.instagram.utils.enableSettings
import app.crimera.patches.instagram.links.misc.hasSuggestionKeys
import app.crimera.patches.instagram.links.misc.suggestionStringConstantsPatch
import app.morphe.patcher.Fingerprint
import app.morphe.patcher.extensions.InstructionExtensions.addInstructionsWithLabels
import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.extensions.InstructionExtensions.instructions
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.patch.PatchException
import app.morphe.patcher.util.smali.ExternalLabel
import app.morphe.util.findFreeRegister
import app.morphe.util.getFreeRegisterProvider
import app.morphe.util.getReference
import app.morphe.util.indexOfFirstInstructionOrThrow
import app.morphe.util.registersUsed
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import com.android.tools.smali.dexlib2.iface.instruction.TwoRegisterInstruction
import com.android.tools.smali.dexlib2.iface.reference.MethodReference
import com.android.tools.smali.dexlib2.iface.reference.FieldReference

internal object InAppBrowserFunctionFingerprint : Fingerprint(
    returnType = "Z",
    custom = { method, _ -> method.hasSuggestionKeys("Tracking.ARG_CLICK_SOURCE", "TrackingInfo.ARG_MODULE_NAME") },
)

@Suppress("unused")
val openLinksExternallyPatch =
    bytecodePatch(
        name = "Open links externally",
        description = "Changes links to always open in your external browser, instead of the in-app browser.",
        default = true,
    ) {

        dependsOn(settingsPatch, suggestionStringConstantsPatch)

        compatibleWith(COMPATIBILITY_INSTAGRAM)

        execute {

            InAppBrowserFunctionFingerprint.matchAll(1..1).single().method.apply {
                    val code = instructions
                    val parserIndex = code.indices.firstOrNull { index ->
                        val call = code[index].getReference<MethodReference>()
                        code[index].opcode in setOf(Opcode.INVOKE_STATIC, Opcode.INVOKE_STATIC_RANGE) &&
                            call?.parameterTypes == listOf("Ljava/lang/String;") && call.returnType == "Landroid/net/Uri;"
                    } ?: throw PatchException("Missing browser URL parser")
                    var sourceRegister = code[parserIndex].registersUsed.single()
                    var fieldIndex: Int? = null
                    for (index in parserIndex - 1 downTo 0) {
                        val instruction = code[index]
                        if (!instruction.opcode.setsRegister() ||
                            (instruction as? OneRegisterInstruction)?.registerA != sourceRegister
                        ) continue
                        if (instruction.opcode in setOf(Opcode.MOVE_OBJECT, Opcode.MOVE_OBJECT_FROM16, Opcode.MOVE_OBJECT_16)) {
                            sourceRegister = (instruction as TwoRegisterInstruction).registerB
                            continue
                        }
                        val field = instruction.getReference<FieldReference>()
                        if (instruction.opcode == Opcode.IGET_OBJECT && field?.type == "Ljava/lang/String;") fieldIndex = index
                        break
                    }
                    val urlResultObjIndex = fieldIndex ?: throw PatchException("Browser URL does not originate from a String field")
                    val urlRegister = code[urlResultObjIndex].registersUsed[0]
                    val freeRegister = getFreeRegisterProvider(urlResultObjIndex + 1, 1, listOf(urlRegister)).getFreeRegister4Bit()

                    addInstructionsWithLabels(
                        urlResultObjIndex + 1,
                        """
                        invoke-static/range { v$urlRegister .. v$urlRegister }, ${Constants.LINKS_DESCRIPTOR}->openExternally(Ljava/lang/String;)Z
                        move-result v$freeRegister
                        if-eqz v$freeRegister, :piko
                        return v$freeRegister
                    """,
                        ExternalLabel("piko", instructions[urlResultObjIndex + 1]),
                    )

                    enableSettings("openLinksExternally")
            }
        }
    }
