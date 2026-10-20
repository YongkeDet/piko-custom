# X 12.34 playback quality: HEVC preference experiment

## Withdrawn at the user's request

The user requested removal of the HEVC/2160p experiment before upgrading X to
12.35.0-prod.01. The experimental patch, setting resources, and feature-specific
fixtures have been removed from the upgrade source. The original highest-video-
resolution policy remains. This document preserves the historical failure and
validation evidence; it is not an active feature or a current installation guide.

## User report and frozen input

The user reports that the same post offers 2160p in the iOS player but only 1080p
in the Android player, and requested an experimental patch. This concerns playback,
not downloaded MP4 selection. Device: Xiaomi 13, HyperOS 3.0.309.0; the previously
reported Android runtime is 16 / API 36.

- Package/version: `com.twitter.android`, `12.34.0-prod.01`, `312340001`, arm64-v8a.
- Original APKM: 78,417,059 bytes, SHA-256
  `016a7eb2b6d8e2d6410da7f3c60e1bb46b002f6fedd797e366b068fb2897682b`.
- Extracted base: 128,532,406 bytes, SHA-256
  `132074017a77fc5963965368a88105b6468036c72a1c98fa27e691cb55732341`.
- Baseline source: `2a9502e0f3a88aae12b828168c871023008a4964` (.16).
- Candidate: `3.10.0-custom-newx.17-test.2`, NewX-only test bundle.

The supplied sample's public syndication response is an empty TweetTombstone;
its actual media manifests have not been obtained. No sample video was downloaded
or rendered. The input APK has not been installed or launched on a phone.

## Direct evidence in this exact APK

The native quality menu already contains 2160p. The existing highest-resolution
patch changes PreferredHeight and bitrate policy, but does not change codec preference.

Reconnaissance identities below are evidence only, never resolver constants:

- `Lcom/x/media/playback/fs/b;->u()Z` directly returns the feature repository's
  getBoolean result for `android_media_playback_hevc_preferred_enabled`, default false.
- `Lcom/x/media/playback/exoplayerpool/k;` calls that interface accessor in its
  constructor. True installs the ordered `video/hevc`, `video/avc` MIME preference.
- `Lcom/x/media/playback/mediaprefetcher/i;` uses the same gate and codec order
  for its Media3 selector, preserving consistent prefetch and playback preference.
- `Lcom/x/video/tab/m5;` contains the native quality list, including 2160, and maps
  actual Media3 formats to labels. This experiment does not invent menu entries.

This is a plausible client-side experiment, not proof that codec preference caused
the user's specific discrepancy. A server response may still omit the 2160p rendition.

## Change and safety boundary

A separate, reversible setting prefers native supported HEVC playback and retains
the native AVC fallback. It is limited to the inspected 12.34 target. Older targets
and the existing highest-resolution policy are unchanged. Restart X after changing it.

The resolver uses the exact flag string, scalar feature lookup, returned register,
and both native HEVC/AVC preference consumers. The mutation runs immediately before
the native boolean return. Enabled returns true; disabled preserves the actual native
flag result. Typed emission relocates return labels to the setting check without
splitting invoke/move-result. Device decoder checks and native fallback remain intact.

The existing PreferredVideoResolutionTest fixture suite now guards the real failure
modes: disabled-path preservation, result-register correctness, invoke/result pairing,
and refusal of unrelated keys or a changed return contract.

## Validation status

Pending candidate CI build, tests, resolver lint, descriptor checks and real APK
patch/rebuild through patch-twitter.sh. No claim of successful 2160p device playback
is made before the user tests the candidate against the same post.

## First real-APK failure and correction

Source e1dd824f3363a427ce1675215fcd7112ce7fab95, MPP SHA-256 fdcd323cbcbbdc23e7d41791274c493a39d2787fbdba0193af5435d2b383106a.
CI passed 279 extension and 93 patch tests, plus resolver lint and descriptor checks.
The exact APKM above was patched through patch-twitter.sh with --unsigned.
The HEVC patch failed while scanning an abstract playback method with no implementation.
Cause: new resolver logic, not linter or cardinality helper behavior. No APK was emitted.
The consumer scan now rejects body-less declarations; the existing emission fixture suite
includes the abstract declaration that previously threw. Candidate .17-test.2 requires a new build.

Complete failure:

~~~text
app.morphe.patcher.patch.PatchException
	at app.morphe.patcher.Patcher$invoke$1.invokeSuspend$execute(Patcher.kt:102)
	at app.morphe.patcher.Patcher$invoke$1.invokeSuspend(Patcher.kt:120)
	at app.morphe.patcher.Patcher$invoke$1.invoke(Patcher.kt)
	at app.morphe.patcher.Patcher$invoke$1.invoke(Patcher.kt)
	at kotlinx.coroutines.flow.SafeFlow.collectSafely(Builders.kt:57)
	at kotlinx.coroutines.flow.AbstractFlow.collect(Flow.kt:226)
	at app.morphe.desktop.command.PatchCommand$call$3$3$1.invokeSuspend(PatchCommand.kt:787)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:34)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:100)
	at kotlinx.coroutines.EventLoopImplBase.processNextEvent(EventLoop.common.kt:256)
	at kotlinx.coroutines.BlockingCoroutine.joinBlocking(Builders.kt:54)
	at kotlinx.coroutines.BuildersKt__BuildersKt.runBlockingImpl(Builders.kt:30)
	at kotlinx.coroutines.BuildersKt.runBlockingImpl(Unknown Source)
	at kotlinx.coroutines.BuildersKt__Builders_concurrentKt.runBlockingK(Builders.concurrent.kt:172)
	at kotlinx.coroutines.BuildersKt.runBlockingK(Unknown Source)
	at kotlinx.coroutines.BuildersKt__Builders_concurrentKt.runBlockingK$default(Builders.concurrent.kt:157)
	at kotlinx.coroutines.BuildersKt.runBlockingK$default(Unknown Source)
	at app.morphe.desktop.command.PatchCommand.call$lambda$13$2(PatchCommand.kt:786)
	at app.morphe.desktop.command.model.PatchingResultKt.addStepResult(PatchingResult.kt:29)
	at app.morphe.desktop.command.PatchCommand.call(PatchCommand.kt:785)
	at app.morphe.desktop.command.PatchCommand.call(PatchCommand.kt:55)
	at picocli.CommandLine.executeUserObject(CommandLine.java:2031)
	at picocli.CommandLine.access$1500(CommandLine.java:148)
	at picocli.CommandLine$RunLast.executeUserObjectOfLastSubcommandWithSameParent(CommandLine.java:2469)
	at picocli.CommandLine$RunLast.handle(CommandLine.java:2461)
	at picocli.CommandLine$RunLast.handle(CommandLine.java:2423)
	at picocli.CommandLine$AbstractParseResultHandler.execute(CommandLine.java:2277)
	at picocli.CommandLine$RunLast.execute(CommandLine.java:2425)
	at picocli.CommandLine.execute(CommandLine.java:2174)
	at app.morphe.MorpheLauncherKt.main(MorpheLauncher.kt:85)
Caused by: java.lang.NullPointerException
	at app.morphe.patcher.extensions.InstructionExtensions.getInstructions(InstructionExtensions.kt:440)
	at app.crimera.patches.newx.misc.mediaquality.PreferHevcPlaybackPatchKt.preferHevcPlaybackPatch$lambda$0$0$0(PreferHevcPlaybackPatch.kt:104)
	at app.crimera.patches.common.ParallelClassScanKt.flatMapParallel$lambda$2$0(ParallelClassScan.kt:54)
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:317)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642)
	at java.base/java.lang.Thread.run(Thread.java:1583)

~~~
