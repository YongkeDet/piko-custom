# Full native download information and restored graphical progress

Target: com.twitter.android 12.34.0-prod.01 / 312340001, APKMirror arm64-v8a APKM.
APKM SHA-256 `016a7eb2b6d8e2d6410da7f3c60e1bb46b002f6fedd797e366b068fb2897682b`.
Base APK SHA-256 `132074017a77fc5963965368a88105b6468036c72a1c98fa27e691cb55732341`.

## Report and baseline

Complete user reports (no exception stack was supplied):

> 弹窗应当显示全部信息，不要省略。
> 下载进度不光要有百分比，还要有进度条，速度，你之前明明做了这些，为什么删去了呢？

Baseline .10 source `6b2da69f22a09107597ca4a4f0b2d35465218b79`, MPP SHA-256
`3de1e2ccbd0b38b087ff48ea88aca57c048c0a0ebf669570f5fdd76bfc0a5bef`.
NewX MPE SHA-256 `f3b8fddc466400aa408a989c3b7b9b38ad0626cf0937c91c5e963c12dfbb3216`;
shared MPE SHA-256 `ad486f4bf4537b12e4d0efaff8b809cf550d6f9eea4bff4549d481a9da105396`.
Baseline all APK SHA-256 `17133098b9a34fbf84cbd8808083ba31642c6d125f9aea7aadde7fcc151af29a`;
default `19f411828a9a6bf40f228f26435f4ec65d1ff7f2ec75dd75a612e16568490981`.
Affected patch: NewX: Unified download notifications. Classification: extension/UI integration regression.
This is unrelated to resolver-linter or cardinality-helper behavior.

## Smali evidence and change

The extension explicitly shortened ordinary progress filenames to 60 characters. Native modern
notifications limit their rich headline to three lines and detail text to one line; the fallback
also constrains its headline and detail. Overflow uses native Ellipsis. A speed line existed but could
be hidden by these constraints. The native adapter omitted the earlier graphical progress bar.

The fix removes filename shortening and resolves native text sites by the API model receiver,
Material3 text contract and the ordered overflow/softWrap/maxLines/minLines tuple. Only notification
models registered by this extension get Clip, softWrap=true and unlimited lines. Those are also the
native default values, so default masks and changed flags remain untouched. Other native messages
retain their original operands and flow. Live model aliases and wide-argument offsets are derived
from actual bytecode; overwritten parameters fail closed.

A native Material3 horizontal progress indicator is inserted into the existing text column after
the detail body, once per renderer. Its Float Snapshot state and Function0 callback belong
to the same task/model as the text. A known total draws determinate progress; an unknown total or
pagination wait draws the native animated indeterminate bar. Colors use the existing theme accent
and a translucent track, packed through the APK's native Color function. The bar fills the available
text-column width. It neither creates a second card nor re-presents the notification.

Bulk downloads show current-transfer percentage, the same native bar, and size/speed throughout
pagination. Counts and recovery/context text remain available and wrap without ellipsis. The
percentage describes current transfers, not undiscovered profile media. Result ordering, task IDs,
cancellation isolation and the single bulk summary remain intact. Native UI animations and device
layout still require use on the user's phone; no device was controlled.

Tests extend UnifiedDownloadNotificationPatchTest (wide text operands, ownership branch, lost
model alias, Float/long progress ABI and determinate/indeterminate branches) and existing downloader
queue tests (owned result vs progress state). No local bytecode/linter library changes.


## Initial validation failures and regression fixture

Source `81134d96ea529a14b18f95e050141e72a4e309be`, CI 38935711159 failed only the
new emitted-instruction fixture: `const/4 v16`/`v17` cannot encode high registers.
Using `const/16` fixes that test setup. Source `6178458f3b3c25c308fc2857dfb4521591115c16`,
CI 39001709822 then passed (88 patch and 279 extension tests).

Its actual all-NewX APKM patch test exposed a real resolver error: both modern and fallback
layouts contain a rich headline AND detail, whereas the first adapter expected a single
rich headline globally. The shared cardinality helper correctly rejected two matches; this
was resolver logic, not a helper or linter bug. Both layouts now resolve headline/detail
cardinality within each renderer. The existing emitted fixture contains both layouts so this
same error cannot pass regression unnoticed. Full original error and command output:
[x12.34-full-first-failure.log](x12.34-full-first-failure.log).

Failed artifact: CI bundle artifact 12089491094, ZIP SHA-256
`cc6fd5d3c95b3e72444096b611890c31d6c2f92dcb1bd6b66afd50a6615eacdd`;
MPP `d611be44cba491638a0d7562bcf80e02f24d540303bda0135b34981b8425e694`;
NewX MPE `6f05f37111ae56324f28d9b61cd01fab7aa69e11c619499e520e38d5d532f8be`;
shared MPE `ad486f4bf4537b12e4d0efaff8b809cf550d6f9eea4bff4549d481a9da105396`.
Failed output: `build/newx-full-banner-first.apk`; validation was explicitly rejected by
`patch-twitter.sh` because the result report contains a failed required patch.

Command (official Desktop 1.18.1, Java 21.0.12.1):
```bash
JAVA_TOOL_OPTIONS='-XX:-UsePerfData -Xshare:off' \
PATCHER_JAVA=/workspace/scratch/558c083b636f/verification/jdk-21.0.12.1+1-jre/bin/java \
MORPHE_CLI_JAR=/workspace/scratch/558c083b636f/verification/morphe-desktop-1.18.1-all.jar \
PATCHER_OUTPUT_APK=/workspace/scratch/558c083b636f/piko/build/newx-full-banner-first.apk \
./patch-twitter.sh /workspace/scratch/558c083b636f/recovered-input/com.twitter.android_12.34.0-prod.01-312340001_1arch_2dpi_24lang_0d5e5dfcd6d1ce2f7899e98625ad1541_apkmirror.com.apkm \
  --all-newx --unsigned --continue-on-error --disable-purge --bytecode-mode FULL
```


## Full register frame found in actual fallback

The next actual all-NewX APKM test (source `d621bdf5b24cbe827f93553d6beb5866ec946327`,
CI 39001998354, bundle artifact 12089192679) failed closed at fallback headline insertion:
`no byte scratch register available (no register is free at this index)`.
MPP SHA-256 `17ff42080b483fe49d7255ace0e01d14f671f7af3d5a7502114bc8e1b430481c`;
NewX MPE `6f05f37111ae56324f28d9b61cd01fab7aa69e11c619499e520e38d5d532f8be`;
shared MPE `ad486f4bf4537b12e4d0efaff8b809cf550d6f9eea4bff4549d481a9da105396`.
The command is the initial all-NewX command above with output `build/newx-full-banner-all.apk`.
The result was 58 applied and one failed required patch; the rebuilt output is rejected and
will not be released. Full error: [x12.34-full-frame-failure.log](x12.34-full-frame-failure.log).
Classification: resolver/emission integration; the bytecode library correctly prevented
clobbering a live value. No library or linter changes are needed.

The boolean ownership result now uses the existing softWrap operand. Its original value is
proven to be a zero literal with no incoming jump or exception handler bypassing that producer.
An unowned notice therefore keeps exactly its original zero; an owned notice takes true and
unlimited-line overrides. No scratch local or register-frame expansion is needed. The regression
fixture keeps all registers live after the native call and also rejects a nonzero original
softWrap operand. Both native layouts remain adapted independently.


## Final verified build and APKs

Source `71b54b57b79f449f0a75fdd000896cadb2fcbe10`, source tree
`90dafb18687da5df1ef29e94a78361151eee55e7`. CI [39070521923](https://github.com/YongkeDet/piko/actions/runs/39070521923)
completed successfully: 88 patch + 279 extension tests = **367**, zero failures or skips;
Android bundle build, resolver linter and extension descriptor checks passed.
Artifact and extension hashes, APK checksums and exact counts are recorded in
[x12.34-full-provenance.json](x12.34-full-provenance.json).

The tested MPP has 9,155,045 bytes, SHA-256
`e28f827d6726f367f2632d6ee3d5326462ddb973d4520d555ef0d645b1c4ac7b`.
No production sources changed after this build. Both exact APKM invocations completed
through `./patch-twitter.sh` with successful resource and DEX rebuilding:

| Selection | Applied / failed | Final APK SHA-256 | Checks | Chinese strings |
| --- | --- | --- | --- | --- |
| All NewX | 59 / 0 | `03aec3683c94b90ec914e3b102bfcda791572fd7b320472f8f7de1337fb41cd6` | 321 | 621 |
| Default | 53 / 0 | `13439f80129b7f221d574ac53b2070c33fdf9cccf0515d01b897ecc7302dfb7c` | 321 | 621 |

Use the same command as above with output `build/newx-full-banner-all.apk`; for default,
omit `--all-newx` and set output `build/newx-full-banner-default.apk`. Outputs are unsigned,
local validation artifacts. Only the exact tested patch bundle is released.
[All log](x12.34-full-all.log), [all result](x12.34-full-all-result.json),
[default log](x12.34-full-default.log), [default result](x12.34-full-default-result.json).

Each APK's 321 assertions comprise 220 native banner checks (including the previous 135),
38 fixed-bar/automatic-refresh checks, 33 glass checks and 30 bulk/resource checks.
[FullBannerStaticReview.java](FullBannerStaticReview.java) verifies all four scoped text
gates across both layouts, original renderer instruction/branch preservation, zero-wrap
argument reuse, one bar per detail column, both native progress ABI branches, native Color
wide operands, theme colors, full width, Float Snapshot ownership and no per-tick re-presentation.
The composition assertion follows the actual concrete Composer implementation's interfaces;
its original interface-only test assumption was corrected, without any production changes.

Reports: [all banner](x12.34-full-all-banner-review.json), [default banner](x12.34-full-default-banner-review.json),
[all fixed](x12.34-full-all-fixed-review.json), [default fixed](x12.34-full-default-fixed-review.json),
[all glass](x12.34-full-all-glass-review.json), [default glass](x12.34-full-default-glass-review.json),
[all bulk/resources](x12.34-full-all-bulk-review.json), [default bulk/resources](x12.34-full-default-bulk-review.json).

No device was operated. Actual banner appearance, animation and foreground/background behavior
still require user trial. No older original APK was available for an additional full-app
regression; existing older adapters remain unchanged.


## Publication

Publisher commit `42f186ae4bfcf71b0b6e904ffbc673b75679a090`, workflow 39071002307 succeeded.
Release [v3.10.0-custom-newx.11](https://github.com/YongkeDet/piko/releases/tag/v3.10.0-custom-newx.11)
was published at `2026-10-17T21:27:26Z`. The public MPP, SHA256SUMS and source-provenance.json
were downloaded again; the released bytes exactly match the tested MPP and source above.
The stable main Morphe feed uses that release's version, URL and publication time.
