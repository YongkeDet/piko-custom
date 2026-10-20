/*
 * Copyright (C) 2026 piko <https://github.com/crimera/piko>
 *
 * See the included NOTICE file for GPLv3 搂7(b) terms that apply to this code.
 */

package app.crimera.patches.newx.misc.videoscrolling

import app.crimera.patches.newx.settings.Categories
import app.crimera.patches.settings.SettingReadRegisterConstraint
import app.crimera.patches.settings.ToggleSettingDefinition
import app.crimera.patches.settings.injectRead
import app.crimera.patches.newx.settings.newXToggle
import app.crimera.patches.settings.settingStrings
import app.crimera.patches.newx.utils.Constants.COMPATIBILITY_NEW_X
import app.crimera.bytecode.Target
import app.crimera.bytecode.insertHook
import app.crimera.patches.utils.scopedMatchAllOrNull
import app.morphe.patcher.Fingerprint
import app.morphe.patcher.Match
import app.morphe.patcher.extensions.InstructionExtensions.instructions
import app.morphe.patcher.patch.PatchException
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.util.getReference
import app.morphe.util.p0Register
import app.crimera.patches.common.requireExactlyOne
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.builder.instruction.BuilderInstruction23x
import com.android.tools.smali.dexlib2.iface.Method
import com.android.tools.smali.dexlib2.iface.reference.FieldReference

private const val MODIFIER_DESCRIPTOR = "Landroidx/compose/ui/Modifier;"
private const val FUNCTION1_DESCRIPTOR = "Lkotlin/jvm/functions/Function1;"
private const val FUNCTION4_DESCRIPTOR = "Lkotlin/jvm/functions/Function4;"
private const val COMPOSER_DESCRIPTOR = "Landroidx/compose/runtime/Composer;"

private const val PAGER_PARAMETER_PREFIX = "Landroidx/compose/foundation/pager/"

// Resolve the native vertical pager by semantic roles. R8 can specialize the nested-scroll
// parameter to a pager implementation, so a fixed declaring-package list is not its contract.
internal fun isVerticalPagerMethod(method: Method): Boolean {
    val parameters = method.parameterTypes.map(CharSequence::toString)
    if (parameters.firstOrNull()?.startsWith(PAGER_PARAMETER_PREFIX) != true ||
        parameters.getOrNull(1) != MODIFIER_DESCRIPTOR ||
        parameters.count { it == "Z" } != 1 ||
        parameters.count { it == FUNCTION1_DESCRIPTOR } != 1 ||
        parameters.count { it == FUNCTION4_DESCRIPTOR } != 1 ||
        parameters.count { it == COMPOSER_DESCRIPTOR } != 1 ||
        parameters.takeLast(3) != listOf("I", "I", "I") ||
        parameters.any { it == "J" || it == "D" }
    ) return false
    return method.implementation?.instructions?.any { instruction ->
        if (instruction.opcode != Opcode.SGET_OBJECT) return@any false
        val field = instruction.getReference<FieldReference>() ?: return@any false
        field.name == "Vertical" &&
            field.definingClass.startsWith("Landroidx/compose/foundation/gestures/")
    } == true
}

private object VerticalPagerFingerprint : Fingerprint(
    returnType = "V",
    custom = { method, _ -> isVerticalPagerMethod(method) },
)

private val verticalPagerFingerprints = listOf(VerticalPagerFingerprint)

private fun patchVerticalPager(
    match: Match,
    setting: ToggleSettingDefinition,
) {
    val method = match.method
    if (method.instructions.isEmpty()) {
        throw PatchException("NewX VerticalPager target has no instructions: ${match.originalMethod}")
    }
    val p0Register = method.p0Register
    val booleanParamIndex = requireExactlyOne(
        "NewX VerticalPager userScrollEnabled parameter",
        method.parameterTypes.indices.filter { method.parameterTypes[it].toString() == "Z" },
    )
    val userScrollEnabledRegister = p0Register + booleanParamIndex
    val defaultMaskRegister = p0Register + method.parameterTypes.lastIndex
    if (userScrollEnabledRegister > 255 || defaultMaskRegister > 255) {
        throw PatchException(
            "NewX VerticalPager parameter registers exceed bytecode encoding limits: " +
                "userScrollEnabled=v$userScrollEnabledRegister, defaultMask=v$defaultMaskRegister",
        )
    }

    val read =
        setting.injectRead(
            method = method,
            index = 0,
            registerConstraint = SettingReadRegisterConstraint.FOUR_BIT,
        )
    if (read.register == userScrollEnabledRegister || read.register == defaultMaskRegister) {
        throw PatchException(
            "NewX VerticalPager setting register aliases a parameter: v${read.register}",
        )
    }

    // The old external label was anchored on the method's original first instruction, which is
    // exactly the instruction the injected read left behind its own block, so
    // `Target.Original` addresses it. The plain insertion kept incoming labels on that
    // instruction, so the branch policy stays `false`.
    method.insertHook(
        index = read.nextIndex,
        relocateBranchTargets = false,
    ) {
        ifEqz(read.register, Target.Original)
        // Clears `userScrollEnabled` and drops the `UserScrollEnabled` bit (0x100) from the
        // Compose default-parameter mask of the trailing parameters.
        constInt(userScrollEnabledRegister, 0)
        constInt(read.register, -0x101)
        add(
            BuilderInstruction23x(
                Opcode.AND_INT,
                defaultMaskRegister,
                defaultMaskRegister,
                read.register,
            ),
        )
    }
}

@Suppress("unused")
val newXDisableVideoScrollingPatch =
    bytecodePatch(
        name = "NewX: Disable video player scrolling",
        description =
            "Disables vertical swipes in the NewX video player while keeping playback controls and other gestures available.",
    ) {
        compatibleWith(COMPATIBILITY_NEW_X)

        val disableVideoScrolling =
            newXToggle(
                id = "newx.post_actions_media.disable_video_scrolling",
                category = Categories.POST_ACTIONS_MEDIA,
                strings = settingStrings("piko_newx_disable_video_scrolling"),
                order = 510,
                defaultValue = false,
            )

        execute {
            val matches =
                verticalPagerFingerprints
                    .flatMap { fingerprint -> fingerprint.scopedMatchAllOrNull().orEmpty() }
                    .distinctBy { match -> match.originalMethod }
            if (matches.size != 1) {
                throw PatchException(
                    "Expected one NewX VerticalPager implementation, found ${matches.size}: " +
                        matches.joinToString { it.originalMethod.toString() },
                )
            }
            patchVerticalPager(matches.single(), disableVideoScrolling)
        }
    }
