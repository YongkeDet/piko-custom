/*
 * Copyright (C) 2026 piko <https://github.com/crimera/piko>
 *
 * See the included NOTICE file for GPLv3 §7(b) terms that apply to this code.
 */

package app.crimera.patches.instagram.links.privacy

import app.crimera.patches.instagram.utils.Constants.USER_SESSION_CLASS
import app.crimera.patches.common.isAssignableTo
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.extensions.InstructionExtensions.instructions
import app.morphe.patcher.patch.BytecodePatchContext
import app.morphe.patcher.patch.PatchException
import app.morphe.patcher.util.proxy.mutableTypes.MutableMethod
import app.morphe.util.getReference
import app.morphe.util.registersUsed
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.WideLiteralInstruction
import com.android.tools.smali.dexlib2.iface.reference.FieldReference
import com.android.tools.smali.dexlib2.iface.reference.MethodReference
import com.android.tools.smali.dexlib2.iface.reference.StringReference
import com.android.tools.smali.dexlib2.iface.reference.TypeReference
import com.android.tools.smali.dexlib2.immutable.ImmutableMethod
import com.android.tools.smali.dexlib2.immutable.ImmutableMethodImplementation
import com.android.tools.smali.dexlib2.immutable.ImmutableMethodParameter

/** Adapt the native typed-media wrapper without exposing its release descriptor to the extension. */
context(context: BytecodePatchContext)
internal fun deriveWrappedStoryAggregate(
    method: MutableMethod,
    startIndex: Int,
    pendingType: String,
    mediaType: String,
): AggregateInvocation {
    val code = method.instructions
    val entry = code.withIndex().filter { (index, instruction) ->
        val call = instruction.getReference<MethodReference>()
        index >= startIndex && instruction.opcode in setOf(Opcode.INVOKE_VIRTUAL, Opcode.INVOKE_VIRTUAL_RANGE) &&
            instruction.registersUsed.size == 4 && call?.definingClass == pendingType && call.returnType == "V" &&
            call.parameterTypes.size == 3 && call.parameterTypes[0] == USER_SESSION_CLASS &&
            call.parameterTypes[1].startsWith("L") && call.parameterTypes[2] == STRING_CLASS
    }.singleOrNull() ?: throw PatchException("Expected one typed pending-story aggregation call")
    val call = entry.value.getReference<MethodReference>()!!
    val arguments = entry.value.registersUsed
    val wrapperType = call.parameterTypes[1].toString()
    val wrapperRegister = arguments[2]
    val wrapperCtor = code.withIndex().take(entry.index).lastOrNull { (_, instruction) ->
        val ref = instruction.getReference<MethodReference>()
        instruction.opcode in setOf(Opcode.INVOKE_DIRECT, Opcode.INVOKE_DIRECT_RANGE) &&
            ref?.name == "<init>" && ref.parameterTypes.size == 2 && ref.parameterTypes[1] == mediaType &&
            instruction.registersUsed.firstOrNull() == wrapperRegister
    } ?: throw PatchException("Missing native typed-story wrapper constructor")
    val wrapperReference = wrapperCtor.value.getReference<MethodReference>()!!
    val wrapperClass = context.classDefBy(wrapperType)
    if (wrapperReference.definingClass != wrapperType && wrapperReference.definingClass != wrapperClass.superclass) {
        throw PatchException("Story wrapper constructor is outside its native hierarchy")
    }
    fun lastWrite(before: Int, register: Int) = code.withIndex().take(before).lastOrNull {
        it.value.opcode.setsRegister() && it.value.registersUsed.firstOrNull() == register
    } ?: throw PatchException("Missing story wrapper argument assignment")
    val wrapperAllocation = lastWrite(wrapperCtor.index, wrapperRegister)
    if (wrapperAllocation.value.opcode != Opcode.NEW_INSTANCE ||
        wrapperAllocation.value.getReference<TypeReference>()?.type != wrapperType
    ) throw PatchException("Story wrapper receiver is not its native allocation")
    val mediaResult = code[startIndex]
    if (mediaResult.opcode != Opcode.MOVE_RESULT_OBJECT ||
        wrapperCtor.value.registersUsed[2] != mediaResult.registersUsed.single()
    ) throw PatchException("Story wrapper does not consume the selected media conversion")
    val metadataRegister = wrapperCtor.value.registersUsed[1]
    val metadataType = wrapperReference.parameterTypes[0].toString()
    val metadataCtor = code.withIndex().take(wrapperCtor.index).lastOrNull { (_, instruction) ->
        val ref = instruction.getReference<MethodReference>()
        instruction.opcode in setOf(Opcode.INVOKE_DIRECT, Opcode.INVOKE_DIRECT_RANGE) &&
            ref?.definingClass == metadataType && ref.name == "<init>" &&
            ref.parameterTypes == listOf("Ljava/util/List;", "I", STRING_CLASS) &&
            instruction.registersUsed.firstOrNull() == metadataRegister
    } ?: throw PatchException("Missing native story schema descriptor constructor")
    val metadataReference = metadataCtor.value.getReference<MethodReference>()!!
    val metadataAllocation = lastWrite(metadataCtor.index, metadataRegister)
    if (metadataAllocation.value.opcode != Opcode.NEW_INSTANCE ||
        metadataAllocation.value.getReference<TypeReference>()?.type != metadataType
    ) throw PatchException("Story metadata receiver is not its native allocation")
    val conversion = code[startIndex - 1].registersUsed
    val metadataArguments = metadataCtor.value.registersUsed
    if (conversion.size != 3 || metadataArguments.size != 4 ||
        metadataArguments[2] != conversion[2] || metadataArguments[3] != conversion[1]
    ) throw PatchException("Story metadata does not retain the media conversion schema")
    val listRead = lastWrite(metadataCtor.index, metadataArguments[1]).value
    val listField = listRead.getReference<FieldReference>()
    if (listRead.opcode != Opcode.SGET_OBJECT || listField == null ||
        !context.isAssignableTo(listField.type, "Ljava/util/List;")
    ) {
        throw PatchException("Missing native story schema field list")
    }
    val key = lastWrite(startIndex - 1, conversion[1]).value.getReference<StringReference>()?.string
        ?: throw PatchException("Missing native story schema key")
    val schema = (lastWrite(startIndex - 1, conversion[2]).value as? WideLiteralInstruction)?.wideLiteral
        ?.takeIf { it in Int.MIN_VALUE.toLong()..Int.MAX_VALUE.toLong() }
        ?: throw PatchException("Missing native story schema number")
    val owner = context.mutableClassDefBy(pendingType)
    val name = "pikoAggregateStoryMedia"
    if (owner.methods.any { it.name == name }) throw PatchException("Duplicate typed story aggregation bridge")
    val bridge = MutableMethod(ImmutableMethod(
        pendingType, name,
        listOf(mediaType, USER_SESSION_CLASS, pendingType, STRING_CLASS).map { ImmutableMethodParameter(it, null, null) },
        "V", AccessFlags.PUBLIC.value or AccessFlags.STATIC.value or AccessFlags.SYNTHETIC.value,
        emptySet(), emptySet(), ImmutableMethodImplementation(8, emptyList(), emptyList(), emptyList()),
    ))
    val escapedKey = key.replace("\\", "\\\\").replace("\"", "\\\"")
    bridge.addInstructions(0, """
        sget-object v1, $listField
        const-string v2, "$escapedKey"
        const v3, $schema
        new-instance v0, $metadataType
        invoke-direct {v0, v1, v3, v2}, $metadataReference
        new-instance v1, $wrapperType
        invoke-direct {v1, v0, p0}, $wrapperReference
        invoke-virtual {p2, p1, v1, p3}, $call
        return-void
    """.trimIndent())
    owner.methods.add(bridge)
    return AggregateInvocation(entry.index, bridge, arguments.last())
}
