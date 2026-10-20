package app.crimera.patches.instagram.links.misc

import app.morphe.patcher.patch.bytecodePatch
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.Method
import com.android.tools.smali.dexlib2.iface.instruction.*
import com.android.tools.smali.dexlib2.iface.reference.MethodReference
import com.android.tools.smali.dexlib2.iface.reference.StringReference

private var suggestionStringTables: Map<String, Map<Int, String>> = emptyMap()

// R8 can replace a literal key with a constant-index switch lookup. Only accept a
// complete, side-effect-free switch whose cases directly return a string literal.
internal fun Method.constantStringSwitch(): Map<Int, String> {
    if (!AccessFlags.STATIC.isSet(accessFlags) || parameterTypes != listOf("I") ||
        returnType != "Ljava/lang/String;"
    ) return emptyMap()
    val implementation = implementation ?: return emptyMap()
    val code = implementation.instructions.toList()
    val switch = code.firstOrNull() ?: return emptyMap()
    if (switch.opcode !in setOf(Opcode.PACKED_SWITCH, Opcode.SPARSE_SWITCH) ||
        (switch as? OneRegisterInstruction)?.registerA != implementation.registerCount - 1
    ) return emptyMap()
    val addresses = mutableMapOf<Int, Int>()
    var address = 0
    code.forEachIndexed { index, instruction ->
        addresses[address] = index
        address += instruction.codeUnits
    }
    val payloadIndex = addresses[(switch as OffsetInstruction).codeOffset] ?: return emptyMap()
    val payload = code[payloadIndex] as? SwitchPayload ?: return emptyMap()
    val result = mutableMapOf<Int, String>()
    for (element in payload.switchElements) {
        val index = addresses[element.offset] ?: return emptyMap()
        val literal = code[index]
        val value = ((literal as? ReferenceInstruction)?.reference as? StringReference)?.string
            ?: return emptyMap()
        val returned = code.getOrNull(index + 1)
        if (literal.opcode !in setOf(Opcode.CONST_STRING, Opcode.CONST_STRING_JUMBO) ||
            returned?.opcode != Opcode.RETURN_OBJECT ||
            (literal as? OneRegisterInstruction)?.registerA != (returned as? OneRegisterInstruction)?.registerA
        ) return emptyMap()
        result[element.key] = value
    }
    return result
}

internal val suggestionStringConstantsPatch = bytecodePatch {
    execute {
        val anchors = listOf("mutual_followers", "suggested_users", "show_see_all_followers_button",
            "follow_requests", "recent_follows", "follow_back", "Tracking.ARG_CLICK_SOURCE", "TrackingInfo.ARG_MODULE_NAME",
            "android.permission.WRITE_EXTERNAL_STORAGE")
        suggestionStringTables = anchors.flatMap { classDefByStrings(it) }.distinctBy { it.type }
            .flatMap { it.methods }.mapNotNull { method ->
                method.constantStringSwitch().takeIf { it.isNotEmpty() }?.let { method.toString() to it }
            }.toMap()
    }
}

internal fun Method.hasSuggestionKeys(vararg keys: String, tables: Map<String, Map<Int, String>> = suggestionStringTables): Boolean {
    val required = keys.toMutableSet()
    var previous: Instruction? = null
    for (instruction in implementation?.instructions ?: emptyList()) {
        val reference = (instruction as? ReferenceInstruction)?.reference
        if (reference is StringReference) required.remove(reference.string)
        if (reference is MethodReference && instruction.opcode == Opcode.INVOKE_STATIC &&
            instruction is FiveRegisterInstruction && instruction.registerCount == 1
        ) {
            val literal = previous as? NarrowLiteralInstruction
            val register = previous as? OneRegisterInstruction
            if (previous?.opcode in setOf(Opcode.CONST_4, Opcode.CONST_16, Opcode.CONST, Opcode.CONST_HIGH16) &&
                literal != null && register?.registerA == instruction.registerC
            ) tables[reference.toString()]?.get(literal.narrowLiteral)?.let(required::remove)
        }
        if (required.isEmpty()) return true
        previous = instruction
    }
    return false
}
