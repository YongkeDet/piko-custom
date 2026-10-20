/* Copyright (C) 2026 piko <https://github.com/crimera/piko>
 * See NOTICE for GPLv3 §7(b) terms. */
package app.crimera.patches.instagram.misc.download

import app.crimera.bytecode.Block
import app.crimera.bytecode.Target
import app.crimera.bytecode.insertHook
import app.crimera.bytecode.methodReference
import app.crimera.patches.common.requireExactlyOne
import app.crimera.patches.instagram.entity.decoder.CURRENT_MEDIA_FIELD
import app.crimera.patches.instagram.entity.decoder.MEDIA_ADD_INFO_CLASS_NAME
import app.morphe.patcher.Fingerprint
import app.morphe.patcher.literal
import app.morphe.patcher.patch.BytecodePatchContext
import app.morphe.patcher.patch.PatchException
import app.morphe.patcher.util.proxy.mutableTypes.MutableMethod
import app.morphe.patches.all.misc.resources.ResourceType
import app.morphe.patches.all.misc.resources.getResourceId
import app.morphe.util.cloneMutable
import app.morphe.util.getReference
import app.morphe.util.p0Register
import app.morphe.util.registersUsed
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.Method
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import com.android.tools.smali.dexlib2.iface.instruction.WideLiteralInstruction
import com.android.tools.smali.dexlib2.iface.reference.FieldReference
import com.android.tools.smali.dexlib2.iface.reference.MethodReference
import com.android.tools.smali.dexlib2.iface.reference.StringReference
import com.android.tools.smali.dexlib2.iface.reference.TypeReference

private const val BUTTON = "Lapp/morphe/extension/instagram/patches/download/FeedDownloadButton;"
private const val OBJECT = "Ljava/lang/Object;"
private const val FUNCTION = "Lkotlin/jvm/functions/Function1;"
private const val MEDIA = "Lcom/instagram/feed/media/Media;"
private const val SESSION = "Lcom/instagram/common/session/UserSession;"
private const val SCALE = "Landroid/widget/ImageView\$ScaleType;"
private const val INTEGER = "Ljava/lang/Integer;"
private fun Method.types() = parameterTypes.map(CharSequence::toString)
private fun MethodReference.types() = parameterTypes.map(CharSequence::toString)

/** Append a native icon to the save icon's component list, immediately before save. */
context(context: BytecodePatchContext)
internal fun injectLithoDownloadButton() {
    val saveId = getResourceId(ResourceType.ID, "row_feed_button_save")
    val drawableId = getResourceId(ResourceType.DRAWABLE, "instagram_download_outline_24").toInt()
    val state = Fingerprint(strings = listOf("MediaUfiUiState(mediaForActionHandler=")).matchAll(1..1).single().classDef
    val mediaField = requireExactlyOne("UFI media field", state.fields.filter { it.type == MEDIA })
    val positionState = requireExactlyOne("UFI carousel state", state.fields.filter { it.type == MEDIA_ADD_INFO_CLASS_NAME })
    val constructors = buildList {
        context.classDefForEach { clazz ->
            clazz.methods.filterTo(this) {
                val types = it.types()
                it.name == "<init>" && types.size in 5..6 && types.take(1) == listOf(SCALE) &&
                    types[1].startsWith("L") && types.subList(2, 5) == listOf(INTEGER, "I", "I") &&
                    (types.size == 5 || types.last() == "Z")
            }
        }
    }
    val constructor = requireExactlyOne("UFI icon constructor", constructors)
    val wrapper = constructor.definingClass
    val nodeType = constructor.types()[1]
    val componentType = requireExactlyOne("UFI component superclass", constructor.implementation!!.instructions.mapNotNull {
        it.getReference<MethodReference>()?.takeIf { ref ->
            it.opcode == Opcode.INVOKE_DIRECT && ref.name == "<init>" && ref.parameterTypes.isEmpty() && ref.definingClass != wrapper
        }?.definingClass
    }.distinct())
    val builder = requireExactlyOne("UFI save component builder",
        Fingerprint(filters = listOf(literal(saveId))).matchAll().filter { match ->
            match.method.implementation!!.instructions.any { it.getReference<MethodReference>()?.toString() == constructor.toString() }
        }).method
    val code = builder.implementation!!.instructions.toList()
    val saveIndex = requireExactlyOne("UFI save identity", code.withIndex().filter { (_, instruction) ->
        (instruction as? WideLiteralInstruction)?.wideLiteral == saveId
    }).index
    fun setter(index: Int, value: String): MethodReference? = code[index].getReference<MethodReference>()?.takeIf {
        code[index].opcode == Opcode.INVOKE_STATIC && it.returnType == nodeType && it.types() == listOf(nodeType, value)
    }
    val idSetter = (saveIndex + 1 until code.size).firstNotNullOfOrNull { setter(it, "I") }
        ?: throw PatchException("No UFI identity setter")
    val wrapperIndex = (saveIndex + 1 until code.size).firstOrNull { code[it].getReference<MethodReference>()?.toString() == constructor.toString() }
        ?: throw PatchException("No UFI save wrapper")
    val clicks = (saveIndex + 1 until wrapperIndex).mapNotNull { setter(it, FUNCTION) }
    if (clicks.size != 2 || clicks[0].toString() == clicks[1].toString()) throw PatchException("Expected distinct UFI click and long-click setters")
    val buttonClassIndex = (saveIndex - 1 downTo 0).firstOrNull { code[it].getReference<StringReference>()?.string == "android.widget.Button" }
        ?: throw PatchException("No UFI button accessibility role")
    val descriptionSetter = (buttonClassIndex - 1 downTo 0).firstNotNullOfOrNull { setter(it, "Ljava/lang/CharSequence;") }
        ?: throw PatchException("No UFI accessibility label setter")
    val selectedSetter = requireExactlyOne("UFI selected state setter", (buttonClassIndex + 1 until saveIndex).mapNotNull { setter(it, "Z") })
    val factoryIndex = (wrapperIndex + 1 until code.size).firstOrNull {
        code[it].opcode == Opcode.INVOKE_STATIC_RANGE && code[it].getReference<MethodReference>()?.returnType == componentType
    } ?: throw PatchException("No UFI wrapper factory")
    val factory = code[factoryIndex].getReference<MethodReference>()!!
    val factoryTypes = factory.types()
    val factoryArgs = code[factoryIndex].registersUsed
    if (factoryTypes.size != 7 || factoryTypes[1] != componentType || factoryTypes[4] != SESSION || factoryTypes.takeLast(2) != listOf("Z", "Z")) {
        throw PatchException("Unexpected UFI factory: $factory")
    }
    val androidContext = requireExactlyOne("UFI Android context field", context.classDefBy(factoryTypes[2]).fields.filter {
        it.type == "Landroid/content/Context;"
    })
    val addIndex = (factoryIndex + 1 until code.size).firstOrNull {
        code[it].getReference<MethodReference>()?.let { ref -> ref.name == "add" && ref.types() == listOf(OBJECT) && ref.returnType == "Z" } == true
    } ?: throw PatchException("No UFI component list append")
    val listRegister = code[addIndex].registersUsed[0]
    val stateRegister = requireExactlyOne("UFI state cast", code.filter {
        it.opcode == Opcode.CHECK_CAST && it.getReference<TypeReference>()?.type == state.type
    }.map { (it as OneRegisterInstruction).registerA })
    // Constructor stores prove field roles without embedding obfuscated identities.
    val constructorCode = constructor.implementation!!.instructions.toList()
    val constructorStart = constructor.implementation!!.registerCount - constructor.types().size
    fun field(parameter: Int): FieldReference = requireExactlyOne("UFI icon parameter $parameter field", constructorCode.mapNotNull {
        it.getReference<FieldReference>()?.takeIf { ref ->
            it.opcode in setOf(Opcode.IPUT_OBJECT, Opcode.IPUT, Opcode.IPUT_BOOLEAN) && ref.definingClass == wrapper &&
                it.registersUsed[0] == constructorStart + parameter
        }
    })
    bindFeedBridge("create", 8) { method ->
        move(7, method.p0Register, OBJECT); checkCast(7, wrapper)
        iget(2, 7, field(1))
        invokeStatic(methodReference("$BUTTON->buttonId()I")); moveResult(4, "I")
        invokeStatic(idSetter, 2, 4); moveResult(2, nodeType)
        constString(3, "piko_category_download_media")
        invokeStatic(methodReference("Lapp/morphe/extension/instagram/utils/IgStr;->str(Ljava/lang/String;)Ljava/lang/String;"), 3)
        moveResult(3, "Ljava/lang/String;")
        invokeStatic(descriptionSetter, 2, 3); moveResult(2, nodeType)
        constInt(4, 0); invokeStatic(selectedSetter, 2, 4); moveResult(2, nodeType)
        move(3, method.p0Register + 1, FUNCTION)
        invokeStatic(clicks[0], 2, 3); moveResult(2, nodeType)
        invokeStatic(clicks[1], 2, 3); moveResult(2, nodeType)
        iget(1, 7, field(0)); iget(3, 7, field(2)); constInt(4, drawableId); iget(5, 7, field(4))
        if (constructor.types().size == 6) iget(6, 7, field(5))
        newInstance(0, wrapper)
        invokeDirect(constructor, *(0..constructor.types().size).toList().toIntArray())
        returnObject(0)
    }
    bindFeedBridge("media", 1) { method ->
        move(0, method.p0Register, OBJECT); checkCast(0, state.type); iget(0, 0, mediaField); returnObject(0)
    }
    bindFeedBridge("context", 1) { method ->
        move(0, method.p0Register, OBJECT); checkCast(0, factoryTypes[2]); iget(0, 0, androidContext); returnObject(0)
    }
    bindFeedBridge("currentIndex", 1) { method ->
        move(0, method.p0Register, OBJECT); checkCast(0, state.type); iget(0, 0, positionState)
        ifEqz(0, Target.Local("first")); iget(0, 0, CURRENT_MEDIA_FIELD); returnValue(0)
        label("first"); constInt(0, 0); returnValue(0)
    }
    builder.insertHook(factoryIndex, relocateBranchTargets = true) {
        invokeStatic(methodReference("$BUTTON->add(${OBJECT}Ljava/util/List;$SESSION$OBJECT$OBJECT)V"),
            factoryArgs[1], listRegister, factoryArgs[4], stateRegister, factoryArgs[2])
    }
}

context(context: BytecodePatchContext)
private fun bindFeedBridge(name: String, locals: Int, body: Block.(MutableMethod) -> Unit) {
    val owner = context.mutableClassDefBy(BUTTON)
    val stub = requireExactlyOne("Feed download $name bridge", owner.methods.filter { it.name == name })
    val method = stub.cloneMutable(additionalRegisters = locals)
    owner.methods.remove(stub); owner.methods.add(method)
    method.implementation!!.apply { while (instructions.isNotEmpty()) removeInstruction(instructions.lastIndex) }
    method.insertHook(0, relocateBranchTargets = false) { body(method) }
}
