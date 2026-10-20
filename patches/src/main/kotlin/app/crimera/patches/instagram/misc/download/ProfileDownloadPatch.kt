/* Copyright (C) 2026 piko <https://github.com/crimera/piko>
 * See NOTICE for GPLv3 §7(b) terms. */
package app.crimera.patches.instagram.misc.download

import app.crimera.bytecode.Block
import app.crimera.bytecode.RegisterLimit
import app.crimera.bytecode.Target
import app.crimera.bytecode.insertHook
import com.android.tools.smali.dexlib2.immutable.reference.ImmutableMethodReference
import app.crimera.patches.common.requireExactlyOne
import app.crimera.patches.instagram.misc.userProfile.profileMoreOptionsPatch
import app.crimera.patches.instagram.utils.Constants.COMPATIBILITY_INSTAGRAM
import app.crimera.patches.instagram.utils.enableSettings
import app.morphe.patcher.extensions.InstructionExtensions.removeInstruction
import app.morphe.patcher.patch.BytecodePatchContext
import app.morphe.patcher.patch.PatchException
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.util.proxy.mutableTypes.MutableMethod
import app.morphe.util.cloneMutable
import app.morphe.util.getReference
import app.morphe.util.p0Register
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.NarrowLiteralInstruction
import com.android.tools.smali.dexlib2.iface.Method
import com.android.tools.smali.dexlib2.iface.reference.FieldReference
import com.android.tools.smali.dexlib2.iface.reference.MethodReference
import com.android.tools.smali.dexlib2.iface.reference.StringReference

private const val BRIDGE = "Lapp/morphe/extension/instagram/patches/download/NativeProfileBridge;"
private const val WORKER = "Lapp/morphe/extension/instagram/patches/download/ProfileDownloader;"
private const val OBJECT = "Ljava/lang/Object;"
private const val MEDIA = "Lcom/instagram/feed/media/Media;"
private const val SESSION = "Lcom/instagram/common/session/UserSession;"
private const val USER = "Lcom/instagram/user/model/User;"
private const val VIEW_MODEL = "Lcom/instagram/profile/fragment/UserDetailViewModel;"
private const val LIST = "Ljava/util/List;"
private fun Method.code() = implementation?.instructions?.toList().orEmpty()
private fun Method.types() = parameterTypes.map(CharSequence::toString)

/** Trace strings identify the native callbacks; every obfuscated reference is derived from them. */
@Suppress("unused")
val downloadProfilePatch = bytecodePatch(
    name = "Download all profile media",
    description = "Downloads accessible profile grid photos and videos sequentially, with saved checkpoints and manual pause after errors.",
) {
    compatibleWith(COMPATIBILITY_INSTAGRAM)
    dependsOn(downloadMediaPatch, profileMoreOptionsPatch)
    execute {
        val classes = buildList { classDefForEach { add(it) } }
        val methods = classes.flatMap { it.methods }
        fun anchored(anchor: String): Method = requireExactlyOne("Instagram profile $anchor", methods.filter { method ->
            method.code().any { it.getReference<StringReference>()?.string == anchor }
        })
        val load = anchored("UserDetailFragment.loadMore")
        val success = anchored("FeedRequestCallback.onMediaFeedRequestSuccess")
        val fail = anchored("FeedRequestCallback.onMediaFeedRequestFail")
        val start = anchored("FeedRequestCallback.onMediaFeedRequestStart")
        val finish = anchored("FeedRequestCallback.onMediaFeedRequestFinish")
        if (load.types().size != 1 || load.returnType != "V" || success.types().size != 5 ||
            success.types().takeLast(2) != listOf("Z", "Z") || fail.types().size != 2 ||
            start.types() != listOf(success.types()[2], "Z") || finish.types() != listOf(success.types()[2]) ||
            listOf(success, fail, start, finish).any { it.definingClass != load.definingClass }) {
            throw PatchException("Instagram profile paging callback contract changed")
        }
        val host = classDefBy(load.definingClass)
        val response = success.types()[1]
        val source = success.types()[2]
        val grid = load.types().single()
        val gridClass = classDefBy(grid)
        if (gridClass.superclass != "Ljava/lang/Enum;" || !gridClass.methods.any { method ->
                method.name == "<clinit>" && method.code().any {
                    it.getReference<StringReference>()?.string == "FULL_AUDIENCE_MEDIA_GRID"
                }
            }) throw PatchException("Instagram full-audience grid enum is unavailable")
        val mainSource = requireExactlyOne("Instagram main grid source", success.code().mapNotNull {
            it.getReference<FieldReference>()?.takeIf { field -> it.opcode == Opcode.SGET_OBJECT && field.type == source }
        }.distinctBy(FieldReference::toString))
        val code = success.code()
        fun anchorIndex(text: String) = requireExactlyOne("Instagram callback anchor $text", code.indices.filter {
            code[it].getReference<StringReference>()?.string == text
        })
        val processIndex = anchorIndex("FeedRequestCallback.processGridItems")
        val plain = requireExactlyOne("Instagram plain profile media list", code.take(processIndex).takeLast(8).mapNotNull {
            it.getReference<FieldReference>()?.takeIf { field -> it.opcode == Opcode.IGET_OBJECT && field.definingClass == response && field.type == LIST }
        }.distinctBy(FieldReference::toString))
        val mixed = requireExactlyOne("Instagram mixed profile items list", code.drop(processIndex).mapNotNull {
            it.getReference<FieldReference>()?.takeIf { field -> it.opcode == Opcode.IGET_OBJECT && field.definingClass == response && field.type == LIST && field.toString() != plain.toString() }
        }.distinctBy(FieldReference::toString))
        val more = requireExactlyOne("Instagram has more grid items", code.drop(anchorIndex("has_more_grid_items") + 1).take(5).mapNotNull {
            it.getReference<FieldReference>()?.takeIf { field -> field.definingClass == response && field.type == "Ljava/lang/Boolean;" }
        }.distinctBy(FieldReference::toString))
        val mapper = anchored("ProfileGQLResponse.fromProfileFeedDict")
        if (mapper.returnType != response) throw PatchException("Instagram profile response mapper changed")
        val cursorAnchor = requireExactlyOne("Instagram profile next_max_id parser", mapper.code().indices.filter {
            (mapper.code()[it] as? NarrowLiteralInstruction)?.narrowLiteral == "next_max_id".hashCode()
        })
        val cursor = requireExactlyOne("Instagram profile next_max_id field", mapper.code().drop(cursorAnchor + 1).take(8).mapNotNull {
            it.getReference<FieldReference>()?.takeIf { field -> it.opcode == Opcode.IPUT_OBJECT && field.definingClass == response && field.type == "Ljava/lang/String;" }
        }.distinctBy(FieldReference::toString))
        val mixedGetter = requireExactlyOne("Instagram mixed item media getter", mapper.code().mapNotNull {
            it.getReference<MethodReference>()?.takeIf { call -> it.opcode == Opcode.INVOKE_INTERFACE && call.returnType == MEDIA && call.parameterTypes.isEmpty() }
        }.distinctBy(MethodReference::toString))
        val session = requireExactlyOne("Instagram native profile session", load.code().mapNotNull {
            it.getReference<MethodReference>()?.takeIf { call -> call.returnType == SESSION && call.parameterTypes.isEmpty() }
        }.distinctBy(MethodReference::toString))
        val vmField = requireExactlyOne("Instagram profile view model field", host.fields.filter { it.type == VIEW_MODEL && !AccessFlags.STATIC.isSet(it.accessFlags) })
        val userField = requireExactlyOne("Instagram profile user field", classDefBy(VIEW_MODEL).fields.filter { it.type == USER && !AccessFlags.STATIC.isSet(it.accessFlags) })
        val cached = requireExactlyOne("Instagram response cached getter", code.mapNotNull {
            it.getReference<MethodReference>()?.takeIf { call -> call.definingClass == response && call.returnType == "Z" && call.parameterTypes.isEmpty() }
        }.distinctBy(MethodReference::toString))
        listOf(vmField, userField, mainSource, plain, mixed, more, cursor).forEach {
            val field = classDefBy(it.definingClass).fields.single { field -> field.name == it.name && field.type == it.type }
            if (!AccessFlags.PUBLIC.isSet(field.accessFlags)) throw PatchException("Instagram profile field is not public: $it")
        }
        val failureValue = requireExactlyOne("Instagram failure response accessor", fail.code().mapNotNull {
            it.getReference<MethodReference>()?.takeIf { call -> call.definingClass == fail.types()[0] && call.parameterTypes.isEmpty() && call.returnType == OBJECT }
        }.distinctBy(MethodReference::toString))
        val rateLimitIndex = requireExactlyOne("Instagram native HTTP 429 check", fail.code().indices.filter {
            (fail.code()[it] as? NarrowLiteralInstruction)?.narrowLiteral == 429
        })
        val failureStatus = requireExactlyOne("Instagram response HTTP status field", fail.code().take(rateLimitIndex).takeLast(5).mapNotNull {
            it.getReference<FieldReference>()?.takeIf { field -> it.opcode == Opcode.IGET && field.type == "I" && field.definingClass != host.type }
        }.distinctBy(FieldReference::toString))
        replaceBridge("failureStatus") { method ->
            move(0, method.p0Register, OBJECT); checkCast(0, failureValue.definingClass)
            invokeVirtual(failureValue, 0); moveResult(0, OBJECT)
            instanceOf(1, 0, failureStatus.definingClass)
            ifEqz(1, Target.Local("unknown"))
            checkCast(0, failureStatus.definingClass); iget(0, 0, failureStatus); returnValue(0)
            label("unknown"); constInt(0, 0); returnValue(0)
        }
        replaceBridge("session") { method ->
            move(0, method.p0Register, OBJECT); checkCast(0, host.type)
            invokeVirtual(session, 0); moveResult(0, SESSION); returnObject(0)
        }
        replaceBridge("user") { method ->
            move(0, method.p0Register, OBJECT); checkCast(0, host.type)
            iget(0, 0, vmField); iget(0, 0, userField); returnObject(0)
        }
        replaceBridge("isMainGrid") { method ->
            sget(0, mainSource)
            ifEq(method.p0Register, 0, Target.Local("yes"))
            constInt(0, 0); returnValue(0)
            label("yes"); constInt(0, 1); returnValue(0)
        }
        for ((name, field) in listOf("plainMedia" to plain, "mixedItems" to mixed, "hasMore" to more, "cursor" to cursor)) {
            replaceBridge(name) { method ->
                move(0, method.p0Register, OBJECT); checkCast(0, response)
                iget(0, 0, field); returnObject(0)
            }
        }
        replaceBridge("mixedMedia") { method ->
            move(0, method.p0Register, OBJECT); checkCast(0, mixedGetter.definingClass)
            invokeInterface(mixedGetter, 0); moveResult(0, MEDIA); returnObject(0)
        }
        replaceBridge("isCached") { method ->
            move(0, method.p0Register, OBJECT); checkCast(0, response)
            invokeVirtual(cached, 0); moveResult(0, "Z"); returnValue(0)
        }
        replaceBridge("requestNext") { method ->
            move(0, method.p0Register, OBJECT); checkCast(0, host.type)
            constClass(1, grid); constString(2, "FULL_AUDIENCE_MEDIA_GRID")
            invokeStatic(ImmutableMethodReference("Ljava/lang/Enum;", "valueOf", listOf("Ljava/lang/Class;", "Ljava/lang/String;"), "Ljava/lang/Enum;"), 1, 2)
            moveResult(1, OBJECT); checkCast(1, grid)
            invokeVirtual(load, 0, 1); returnVoid()
        }
        fun mutable(method: Method) = requireExactlyOne("Instagram mutable ${method.name}", mutableClassDefBy(method.definingClass).methods.filter { it.toString() == method.toString() })
        fun hook(method: Method, name: String, offsets: List<Int>, types: List<String>) {
            val target = mutable(method)
            target.insertHook(0, relocateBranchTargets = false) {
                val args = offsets.zip(types).map { (offset, type) ->
                    scratchRegister(RegisterLimit.FOUR_BIT).also { move(it, target.p0Register + offset, type) }
                }
                invokeStatic(ImmutableMethodReference(WORKER, name, types, "V"), *args.toIntArray())
            }
        }
        // Keep Instagram's normal feed error toast, suppress only the duplicate during our task.
        val failureMethod = mutable(fail)
        val failureCode = failureMethod.code()
        val toastAnchor = requireExactlyOne("Instagram feed failure toast anchor", failureCode.indices.filter {
            failureCode[it].getReference<StringReference>()?.string == "media_feed_request_failed"
        })
        val toastIndex = requireExactlyOne("Instagram feed failure toast call", failureCode.indices.filter { index ->
            index in (toastAnchor + 1)..(toastAnchor + 4) &&
                failureCode[index].getReference<MethodReference>()?.returnType == "Landroid/widget/Toast;"
        })
        if (failureCode.getOrNull(toastIndex + 1)?.opcode?.name?.startsWith("MOVE_RESULT") == true)
            throw PatchException("Instagram feed failure Toast result is now consumed")
        failureMethod.insertHook(toastIndex, relocateBranchTargets = true) {
            val receiver = scratchRegister(RegisterLimit.FOUR_BIT)
            move(receiver, failureMethod.p0Register, OBJECT)
            invokeStatic(ImmutableMethodReference(WORKER, "hasTask", listOf(OBJECT), "Z"), receiver)
            moveResult(receiver, "Z")
            ifNez(receiver, Target.AfterOriginal(1))
        }
        hook(success, "onPage", listOf(0, 2, 3, 4), listOf(OBJECT, OBJECT, OBJECT, "Z"))
        hook(fail, "onFailure", listOf(0, 1, 2), listOf(OBJECT, OBJECT, OBJECT))
        hook(start, "onStart", listOf(0, 1), listOf(OBJECT, OBJECT))
        hook(finish, "onFinish", listOf(0, 1), listOf(OBJECT, OBJECT))
        for (name in listOf("onResume", "onPause", "onDestroy")) {
            val method = requireExactlyOne("Instagram profile $name lifecycle", host.methods.filter { it.name == name && it.types().isEmpty() && it.returnType == "V" })
            hook(method, name, listOf(0), listOf(OBJECT))
        }
        enableSettings("downloadProfile")
    }
}

context(context: BytecodePatchContext)
private fun replaceBridge(name: String, body: Block.(MutableMethod) -> Unit) {
    val owner = context.mutableClassDefBy(BRIDGE)
    val stub = requireExactlyOne("Instagram profile bridge $name", owner.methods.filter { it.name == name })
    val method = stub.cloneMutable(additionalRegisters = 4)
    owner.methods.remove(stub); owner.methods.add(method)
    method.implementation!!.apply { while (instructions.isNotEmpty()) removeInstruction(instructions.lastIndex) }
    method.insertHook(0, relocateBranchTargets = false) { body(method) }
}
