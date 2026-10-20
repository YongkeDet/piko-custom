package app.crimera.patches.instagram.utils

import app.crimera.bytecode.insertHook
import app.morphe.patcher.patch.BytecodePatchContext
import app.morphe.patcher.patch.PatchException
import app.morphe.util.cloneMutable
import app.morphe.util.p0Register
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.iface.reference.MethodReference

/** Bind an Object-only extension stub to an APK-resolved interface getter or static mapper. */
context(context: BytecodePatchContext)
internal fun bindEntityBridge(ownerType: String, name: String, target: MethodReference, staticMapper: Boolean = false) {
    val inputType = if (staticMapper) target.parameterTypes.singleOrNull()?.toString()
        else target.definingClass.takeIf { target.parameterTypes.isEmpty() }
    if (inputType == null || !inputType.startsWith("L") || !target.returnType.startsWith("L")) {
        throw PatchException("Unsupported entity bridge contract: $target")
    }
    val owner = context.mutableClassDefBy(ownerType)
    val stub = owner.methods.singleOrNull {
        it.name == name && AccessFlags.STATIC.isSet(it.accessFlags) &&
            it.parameterTypes == listOf("Ljava/lang/Object;") && it.returnType == target.returnType
    } ?: throw PatchException("Expected one Object entity bridge $ownerType->$name")
    val method = stub.cloneMutable(additionalRegisters = 1)
    owner.methods.remove(stub)
    owner.methods.add(method)
    method.implementation!!.apply { while (instructions.isNotEmpty()) removeInstruction(instructions.lastIndex) }
    method.insertHook(0, relocateBranchTargets = false) {
        move(0, method.p0Register, "Ljava/lang/Object;")
        checkCast(0, inputType)
        if (staticMapper) invokeStatic(target, 0) else invokeInterface(target, 0)
        moveResult(0, target.returnType)
        returnObject(0)
    }
}
