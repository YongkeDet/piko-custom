package app.crimera.patches.instagram.filter.story

// Guards the IG 451 startup VerifyError: key ordering selected an integer equality
// result as a story, and the old external label branched into move-result-object.
import app.morphe.patcher.extensions.InstructionExtensions.instructions
import app.morphe.patcher.patch.PatchException
import app.morphe.patcher.util.proxy.mutableTypes.MutableMethod
import app.morphe.patcher.util.smali.toInstruction
import app.morphe.util.getReference
import app.morphe.util.registersUsed
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.builder.MethodImplementationBuilder
import com.android.tools.smali.dexlib2.builder.instruction.BuilderInstruction21t
import com.android.tools.smali.dexlib2.builder.instruction.BuilderInstruction22t
import com.android.tools.smali.dexlib2.builder.instruction.BuilderInstruction10t
import com.android.tools.smali.dexlib2.iface.instruction.OffsetInstruction
import com.android.tools.smali.dexlib2.iface.reference.MethodReference
import com.android.tools.smali.dexlib2.immutable.ImmutableMethod
import com.android.tools.smali.dexlib2.immutable.ImmutableMethodParameter
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FilterStoriesPatchTest {
    private fun parser(primitiveResult: Boolean = false, duplicate: Boolean = false): MutableMethod {
        val body = MethodImplementationBuilder(7)
        fun add(text: String) = body.addInstruction(text.toInstruction())
        fun jump(label: String) = body.addInstruction(BuilderInstruction10t(Opcode.GOTO, body.getLabel(label)))
        fun guard(register: Int, label: String) = body.addInstruction(BuilderInstruction21t(Opcode.IF_EQZ, register, body.getLabel(label)))
        body.addLabel("field")
        add("const-string v3, \"hallpass_share_info\"")
        add("const/4 v0, 0")
        guard(0, "key") // Misleading integer guard before the semantic tray case.
        body.addLabel("key")
        add("const-string v0, \"tray\"")
        add("invoke-virtual {v0, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z")
        add("move-result v0")
        guard(0, "field") // The real parser returns backwards to its shared field loop.
        add("invoke-virtual {v6}, Lfixture/Reader;->current()Lfixture/Token;")
        add("move-result-object v0")
        add("sget-object v1, Lfixture/Token;->ARRAY:Lfixture/Token;")
        body.addInstruction(BuilderInstruction22t(Opcode.IF_NE, 0, 1, body.getLabel("other")))
        add("new-instance v5, Ljava/util/ArrayList;")
        add("invoke-direct {v5}, Ljava/util/ArrayList;-><init>()V")
        add("const/4 v2, 0")
        for (suffix in if (duplicate) listOf("one", "two") else listOf("one")) {
            body.addLabel("loop$suffix")
            add("invoke-virtual {v6}, Lfixture/Reader;->next()Z")
            add("move-result v0")
            guard(0, "other")
            add("invoke-virtual {v2, v6}, Lfixture/Decoder;->parse(Lfixture/Reader;)Ljava/lang/Object;")
            add(if (primitiveResult) "move-result v1" else "move-result-object v1")
            guard(1, "loop$suffix")
            add("invoke-virtual {v5, v1}, Ljava/util/AbstractCollection;->add(Ljava/lang/Object;)Z")
            jump("loop$suffix")
        }
        body.addLabel("other")
        add("const/4 v0, 0")
        add("return-object v0")
        return MutableMethod(ImmutableMethod("Lfixture/Parser;", "unsafeParseFromJson",
            listOf(ImmutableMethodParameter("Lfixture/Reader;", emptySet(), null)), "Ljava/lang/Object;",
            AccessFlags.PUBLIC.value or AccessFlags.STATIC.value, emptySet(), emptySet(), body.methodImplementation))
    }

    private fun target(method: MutableMethod, index: Int): Int {
        val code = method.instructions
        val address = code.take(index).sumOf { it.codeUnits } + (code[index] as OffsetInstruction).codeOffset
        var current = 0
        return code.indexOfFirst { instruction -> (current == address).also { current += instruction.codeUnits } }
    }

    @Test
    fun `filter parsed tray objects and keep branches outside result instructions`() {
        val method = parser()
        val site = resolveStoryFilterSite(method)
        assertEquals(1, site.itemRegister)
        installStoryFilterHook(method)
        val code = method.instructions
        val start = site.guardIndex
        assertEquals(Opcode.IF_EQZ, code[start].opcode)
        assertEquals(start + 3, target(method, start))
        assertEquals("filter", code[start + 1].getReference<MethodReference>()?.name)
        assertEquals(listOf(1), code[start + 1].registersUsed)
        assertEquals(Opcode.MOVE_RESULT_OBJECT, code[start + 2].opcode)
        assertEquals(Opcode.IF_EQZ, code[start + 3].opcode)
        assertEquals("next", code[target(method, start + 3)].getReference<MethodReference>()?.name)
        assertEquals("add", code[start + 4].getReference<MethodReference>()?.name)
        assertTrue(target(method, start + 3) < start)
        for (index in code.indices) if (code[index] is OffsetInstruction) {
            assertFalse(code[target(method, index)].opcode in setOf(Opcode.MOVE_RESULT, Opcode.MOVE_RESULT_OBJECT, Opcode.MOVE_RESULT_WIDE))
        }
    }

    @Test
    fun `reject primitive results and ambiguous tray insertion loops`() {
        assertFailsWith<PatchException> { resolveStoryFilterSite(parser(primitiveResult = true)) }
        assertFailsWith<PatchException> { resolveStoryFilterSite(parser(duplicate = true)) }
    }
}
