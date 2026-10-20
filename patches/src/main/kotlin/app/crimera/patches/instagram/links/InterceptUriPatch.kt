/*
 * Copyright (C) 2026 piko <https://github.com/crimera/piko>
 *
 * See the included NOTICE file for GPLv3 §7(b) terms that apply to this code.
 */

package app.crimera.patches.instagram.links

import app.crimera.patches.instagram.utils.Constants
import app.crimera.patches.instagram.utils.Constants.COMPATIBILITY_INSTAGRAM
import app.morphe.patcher.Fingerprint
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.extensions.InstructionExtensions.instructions
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.patch.PatchException
import app.morphe.util.getReference
import com.android.tools.smali.dexlib2.iface.reference.FieldReference
import com.android.tools.smali.dexlib2.iface.reference.MethodReference
import app.morphe.util.indexOfFirstInstruction
import app.morphe.util.registersUsed
import com.android.tools.smali.dexlib2.Opcode

object TigonServiceLayerStartRequestFingerprint : Fingerprint(
    definingClass = "Lcom/instagram/api/tigon/TigonServiceLayer;",
    name = "startRequest",
)

@Suppress("unused")
val interceptUriPatch =
    bytecodePatch(
        description = "Intercept uri to block.",
    ) {
        compatibleWith(COMPATIBILITY_INSTAGRAM)

        execute {
            val entry = TigonServiceLayerStartRequestFingerprint.matchAll(1..1).single().method
            val owner = mutableClassDefBy(entry.definingClass)
            val pending = ArrayDeque(listOf(entry))
            val visited = mutableSetOf<String>()
            val reads = mutableListOf<Pair<app.morphe.patcher.util.proxy.mutableTypes.MutableMethod, Int>>()
            while (pending.isNotEmpty()) {
                val method = pending.removeFirst()
                if (!visited.add(method.toString())) continue
                method.instructions.forEachIndexed { index, instruction ->
                    val field = instruction.getReference<FieldReference>()
                    if (instruction.opcode == Opcode.IGET_OBJECT && field?.type == "Ljava/net/URI;" &&
                        field.definingClass == entry.parameterTypes.first()
                    ) reads += method to index
                    val call = instruction.getReference<MethodReference>()
                    if (instruction.opcode in setOf(Opcode.INVOKE_DIRECT, Opcode.INVOKE_DIRECT_RANGE) &&
                        call?.definingClass == entry.definingClass && call.parameterTypes == entry.parameterTypes &&
                        call.returnType == entry.returnType
                    ) {
                        pending += owner.methods.singleOrNull { it.toString() == call.toString() }
                            ?: throw PatchException("Missing delegated Tigon request method")
                    }
                }
            }
            val (method, index) = reads.singleOrNull()
                ?: throw PatchException("Expected one request URI read in Tigon request entry path")
            method.apply {
                val uriRegister = instructions[index].registersUsed[0]
                addInstructions(
                    index + 1,
                    """
                    invoke-static/range { v$uriRegister .. v$uriRegister }, ${Constants.LINKS_DESCRIPTOR}->interceptUri(Ljava/net/URI;)V
                    """.trimIndent(),
                )
            }
        }
    }
