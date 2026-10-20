/*
 * Copyright (C) 2026 piko <https://github.com/crimera/piko>
 *
 * See the included NOTICE file for GPLv3 §7(b) terms that apply to this code.
 */

package app.crimera.patches.instagram.links.misc

import app.morphe.patcher.extensions.InstructionExtensions.instructions
import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.patch.BytecodePatchContext
import app.morphe.patcher.patch.PatchException
import app.morphe.patcher.util.proxy.mutableTypes.MutableMethod
import app.morphe.util.getReference
import app.morphe.util.p0Register
import app.morphe.util.registersUsed
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.reference.FieldReference
import com.android.tools.smali.dexlib2.iface.reference.StringReference
import com.android.tools.smali.dexlib2.immutable.ImmutableMethod
import com.android.tools.smali.dexlib2.immutable.ImmutableMethodParameter

private const val SESSION = "Lcom/instagram/common/session/UserSession;"

/** Keep the native classifier and its account-specific gates when its receiver only supplies a session. */
context(context: BytecodePatchContext)
internal fun bindSessionReelsClassifier(
    filter: MutableMethod,
    lifecycle: MutableMethod,
    itemType: String,
    itemRegister: Int,
    scratch: Int,
): String {
    val marker = lifecycle.instructions.withIndex().singleOrNull { (_, instruction) ->
        instruction.getReference<StringReference>()?.string ==
            "android_purge_26_q3_ClipsSuggestedUsersItemDelegateImpl_onExitClipsViewer"
    }?.index ?: throw PatchException("Expected one suggested-users lifecycle marker")
    val delegateField = lifecycle.instructions.take(marker).lastOrNull { it.opcode == Opcode.IGET_OBJECT }
        ?.getReference<FieldReference>() ?: throw PatchException("Missing suggested-users delegate receiver")
    val delegate = context.mutableClassDefBy(delegateField.definingClass)
    val native = delegate.methods.singleOrNull {
        AccessFlags.PUBLIC.isSet(it.accessFlags) && !AccessFlags.STATIC.isSet(it.accessFlags) &&
            it.parameterTypes == listOf(itemType) && it.returnType == "Z"
    } ?: throw PatchException("Expected one session-backed suggested-users classifier")
    val receiver = native.p0Register
    val receiverUses = native.instructions.withIndex().filter { receiver in it.value.registersUsed }
    val use = receiverUses.singleOrNull() ?: throw PatchException("Classifier receiver has non-session uses")
    val sessionField = use.value.getReference<FieldReference>()
    if (use.value.opcode != Opcode.IGET_OBJECT || sessionField?.type != SESSION ||
        sessionField.definingClass != delegate.type || use.value.registersUsed[1] != receiver
    ) throw PatchException("Classifier receiver is not a single session-field read")
    val name = "pikoSuggestedUsersForSession"
    if (delegate.methods.any { it.name == name }) throw PatchException("Duplicate session-backed classifier")
    // Two static parameters occupy the same registers as the original receiver and item.
    // Copy its control flow verbatim, replacing only the receiver's single session read.
    val bridge = MutableMethod(ImmutableMethod(
        delegate.type, name,
        listOf(ImmutableMethodParameter(SESSION, null, null), ImmutableMethodParameter(itemType, null, null)),
        "Z", AccessFlags.PUBLIC.value or AccessFlags.STATIC.value or AccessFlags.SYNTHETIC.value,
        emptySet(), emptySet(), native.implementation,
    ))
    bridge.replaceInstruction(use.index, "move-object/from16 v${use.value.registersUsed[0]}, v$receiver")
    delegate.methods.add(bridge)

    val stores = filter.instructions.mapNotNull { instruction ->
        instruction.getReference<FieldReference>()?.takeIf { field ->
            instruction.opcode == Opcode.IGET_OBJECT && field.definingClass == filter.definingClass &&
                context.classDefByOrNull(field.type)?.fields?.any { it.type == SESSION } == true
        }
    }.distinctBy { it.toString() }
    val store = stores.singleOrNull() ?: throw PatchException("Expected one account-backed hidden-reels store")
    val storeSession = context.classDefBy(store.type).fields.singleOrNull {
        it.type == SESSION && AccessFlags.PUBLIC.isSet(it.accessFlags) && !AccessFlags.STATIC.isSet(it.accessFlags)
    } ?: throw PatchException("Expected one accessible hidden-reels store session")
    if (AccessFlags.STATIC.isSet(filter.accessFlags) || itemRegister !in 0..15 || scratch !in 0..15 || itemRegister == scratch) {
        throw PatchException("Unsupported session-backed reels filter registers")
    }
    return """
        move-object/from16 v$scratch, v${filter.p0Register}
        iget-object v$scratch, v$scratch, $store
        iget-object v$scratch, v$scratch, $storeSession
        invoke-static {v$scratch, v$itemRegister}, $bridge
    """.trimIndent()
}
