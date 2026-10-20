package app.crimera.patches.instagram.links.misc

// Guards the IG 451 failure: R8 moved parser keys behind constant-index string switches.
import app.morphe.patcher.util.smali.toInstruction
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.builder.MethodImplementationBuilder
import com.android.tools.smali.dexlib2.builder.instruction.BuilderInstruction31t
import com.android.tools.smali.dexlib2.builder.instruction.BuilderPackedSwitchPayload
import com.android.tools.smali.dexlib2.immutable.ImmutableMethod
import com.android.tools.smali.dexlib2.immutable.ImmutableMethodParameter
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SuggestionStringConstantsTest {
    private fun method(body: MethodImplementationBuilder, parameters: List<String> = listOf("I")) =
        ImmutableMethod("Lfixture/Strings;", "lookup",
            parameters.map { ImmutableMethodParameter(it, emptySet(), null) }, "Ljava/lang/String;",
            AccessFlags.PUBLIC.value or AccessFlags.STATIC.value, emptySet(), emptySet(), body.methodImplementation)

    private fun table(sideEffect: Boolean = false): ImmutableMethod {
        val body = MethodImplementationBuilder(1)
        body.addInstruction(BuilderInstruction31t(Opcode.PACKED_SWITCH, 0, body.getLabel("payload")))
        body.addInstruction("const-string v0, \"other\"".toInstruction())
        body.addInstruction("return-object v0".toInstruction())
        body.addLabel("mutual")
        if (sideEffect) body.addInstruction("invoke-static {}, Lfixture/Log;->write()V".toInstruction())
        body.addInstruction("const-string v0, \"mutual_followers\"".toInstruction())
        body.addInstruction("return-object v0".toInstruction())
        body.addLabel("suggested")
        body.addInstruction("const-string v0, \"suggested_users\"".toInstruction())
        body.addInstruction("return-object v0".toInstruction())
        body.addLabel("payload")
        body.addInstruction(BuilderPackedSwitchPayload(7, listOf(body.getLabel("mutual"), body.getLabel("suggested"))))
        return method(body)
    }

    @Test
    fun `decode the complete constant switch and reject a case with side effects`() {
        assertEquals(mapOf(7 to "mutual_followers", 8 to "suggested_users"), table().constantStringSwitch())
        assertTrue(table(sideEffect = true).constantStringSwitch().isEmpty())
    }

    @Test
    fun `parser keys include resolved constant calls but require the actual argument register`() {
        val target = table()
        val tables = mapOf(target.toString() to target.constantStringSwitch())
        fun parser(constantRegister: Int): ImmutableMethod {
            val body = MethodImplementationBuilder(2)
            body.addInstruction("const-string v0, \"mutual_followers\"".toInstruction())
            body.addInstruction("const/16 v$constantRegister, 8".toInstruction())
            body.addInstruction("invoke-static {v1}, $target".toInstruction())
            body.addInstruction("move-result-object v0".toInstruction())
            body.addInstruction("return-object v0".toInstruction())
            return method(body, emptyList())
        }
        assertTrue(parser(1).hasSuggestionKeys("mutual_followers", "suggested_users", tables = tables))
        assertFalse(parser(0).hasSuggestionKeys("mutual_followers", "suggested_users", tables = tables))
        assertFalse(parser(1).hasSuggestionKeys("missing", tables = tables))
    }
}
