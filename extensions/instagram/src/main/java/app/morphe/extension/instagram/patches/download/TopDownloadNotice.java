/* Copyright (C) 2026 piko <https://github.com/crimera/piko>
 * See NOTICE for GPLv3 §7(b) terms. */
package app.morphe.extension.instagram.patches.download;

import static app.morphe.extension.instagram.utils.IgStr.str;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BooleanSupplier;
import app.morphe.extension.shared.Utils;

/** Activity content only: no overlay permission, Toast gravity, or background windows. */
public final class TopDownloadNotice {
    private static final Handler MAIN = new Handler(Looper.getMainLooper());
    private static final AtomicLong IDS = new AtomicLong();
    private static final Map<Long, Entry> ACTIVE = new LinkedHashMap<>();
    private static final ArrayDeque<Entry> RESULTS = new ArrayDeque<>();
    private static final Map<Long, Card> CARDS = new LinkedHashMap<>();
    private static WeakReference<Activity> foreground = new WeakReference<>(null);
    private static ScrollView surface;
    private static LinearLayout stack;
    private static boolean installed;
    private static Entry displayedResult;
    private static final Runnable DISMISS = () -> {
        if (displayedResult != null) RESULTS.remove(displayedResult);
        displayedResult = null;
        render();
    };

    private TopDownloadNotice() {}
    public static long nextId() { return IDS.incrementAndGet(); }

    public static void attach(Context context) {
        MAIN.post(() -> {
            Context app = context.getApplicationContext();
            if (!installed && app instanceof Application) {
                installed = true;
                ((Application) app).registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                    public void onActivityCreated(Activity a, Bundle b) {}
                    public void onActivityStarted(Activity a) {}
                    public void onActivityResumed(Activity a) { foreground = new WeakReference<>(a); detach(); render(); }
                    public void onActivityPaused(Activity a) {
                        if (foreground.get() == a) { foreground.clear(); detach(); }
                    }
                    public void onActivityStopped(Activity a) {}
                    public void onActivitySaveInstanceState(Activity a, Bundle b) {}
                    public void onActivityDestroyed(Activity a) {
                        if (foreground.get() == a) { foreground.clear(); detach(); }
                    }
                });
            }
            Context current = context;
            while (current instanceof ContextWrapper && !(current instanceof Activity)) {
                Context base = ((ContextWrapper) current).getBaseContext();
                if (base == current) break;
                current = base;
            }
            Activity activity = current instanceof Activity ? (Activity) current : Utils.getActivity();
            if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
                if (foreground.get() != activity) detach();
                foreground = new WeakReference<>(activity);
            }
            render();
        });
    }

    public static void progress(long id, String title, String detail, int percent, Runnable pause,
                                BooleanSupplier isActive) {
        MAIN.post(() -> {
            // A worker update can be queued after finish while the user pauses on the UI thread.
            // Recheck the task's own terminal state here; retaining finished IDs would grow forever.
            if (!isActive.getAsBoolean()) return;
            Entry e = ACTIVE.get(id);
            if (e == null) { e = new Entry(id); ACTIVE.put(id, e); }
            e.title = title; e.detail = detail; e.percent = percent; e.action = pause;
            render();
        });
    }

    public static void finish(long id, String title, String detail, boolean failed) {
        MAIN.post(() -> {
            ACTIVE.remove(id);
            Entry e = new Entry(id);
            e.title = title; e.detail = detail; e.failed = failed;
            RESULTS.add(e);
            render();
        });
    }

    public static void message(Context context, String title, String detail) {
        attach(context);
        finish(nextId(), title, detail, true);
    }

    private static void detach() {
        MAIN.removeCallbacks(DISMISS);
        displayedResult = null;
        if (surface != null && surface.getParent() instanceof ViewGroup) {
            ((ViewGroup) surface.getParent()).removeView(surface);
        }
        surface = null; stack = null; CARDS.clear();
    }

    private static void render() {
        Activity a = foreground.get();
        if (a == null || a.isFinishing() || a.isDestroyed()) return;
        if (ACTIVE.isEmpty() && RESULTS.isEmpty()) { detach(); return; }
        if (surface == null) {
            View decor = a.getWindow().getDecorView();
            if (!(decor instanceof FrameLayout)) return;
            surface = new ScrollView(a) {
                protected void onMeasure(int w, int h) {
                    int max = (int) (getResources().getDisplayMetrics().heightPixels * .45f);
                    super.onMeasure(w, MeasureSpec.makeMeasureSpec(max, MeasureSpec.AT_MOST));
                }
            };
            surface.setFillViewport(false);
            surface.setClipToPadding(false);
            stack = new LinearLayout(a); stack.setOrientation(LinearLayout.VERTICAL);
            surface.addView(stack, new ViewGroup.LayoutParams(-1, -2));
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(-1, -2, Gravity.TOP);
            int inset = decor.getRootWindowInsets() == null ? 0
                    : decor.getRootWindowInsets().getSystemWindowInsetTop();
            lp.setMargins(dp(a, 12), inset + dp(a, 8), dp(a, 12), 0);
            ((FrameLayout) decor).addView(surface, lp);
            surface.setElevation(dp(a, 24));
        }
        Map<Long, Entry> visible = new LinkedHashMap<>(ACTIVE);
        Entry result = RESULTS.peek();
        if (result != null) visible.put(result.id, result);
        for (Long id : new java.util.ArrayList<>(CARDS.keySet())) {
            if (!visible.containsKey(id)) stack.removeView(CARDS.remove(id).root);
        }
        for (Entry e : visible.values()) {
            Card c = CARDS.get(e.id);
            if (c == null) { c = new Card(a); CARDS.put(e.id, c); stack.addView(c.root); }
            boolean ongoing = ACTIVE.containsKey(e.id);
            c.title.setText(e.title); c.detail.setText(e.detail);
            c.bar.setVisibility(ongoing ? View.VISIBLE : View.GONE);
            c.bar.setIndeterminate(e.percent < 0);
            if (e.percent >= 0) c.bar.setProgress(e.percent);
            c.action.setText(ongoing ? str("piko_profile_download_pause") : str("piko_close"));
            c.action.setVisibility(!ongoing || e.action != null ? View.VISIBLE : View.GONE);
            c.action.setOnClickListener(v -> {
                if (ongoing && e.action != null) e.action.run();
                else { MAIN.removeCallbacks(DISMISS); DISMISS.run(); }
            });
            c.title.setTextColor(e.failed ? 0xffd75a52 : c.foreground);
        }
        if (result != null && displayedResult != result) {
            MAIN.removeCallbacks(DISMISS);
            displayedResult = result;
            MAIN.postDelayed(DISMISS, 8000);
            surface.announceForAccessibility(result.title + "\n" + result.detail);
        }
    }

    private static int dp(Context c, int value) {
        return Math.round(value * c.getResources().getDisplayMetrics().density);
    }
    private static final class Entry {
        final long id; String title, detail; int percent = -1; boolean failed; Runnable action;
        Entry(long id) { this.id = id; }
    }
    private static final class Card {
        final LinearLayout root;
        final TextView title, detail, action;
        final ProgressBar bar;
        final int foreground;
        Card(Activity a) {
            boolean dark = (a.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                    == Configuration.UI_MODE_NIGHT_YES;
            foreground = dark ? 0xfffafafa : 0xff171717;
            root = new LinearLayout(a); root.setOrientation(LinearLayout.VERTICAL);
            root.setPadding(dp(a, 16), dp(a, 12), dp(a, 16), dp(a, 8));
            GradientDrawable bg = new GradientDrawable();
            bg.setColor(dark ? 0xff252525 : 0xfffafafa); bg.setCornerRadius(dp(a, 20));
            bg.setStroke(dp(a, 1), dark ? 0xff454545 : 0xffdddddd);
            root.setBackground(bg); root.setElevation(dp(a, 4));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
            lp.bottomMargin = dp(a, 8); root.setLayoutParams(lp);
            title = text(a, 15, foreground); title.setTypeface(null, Typeface.BOLD);
            detail = text(a, 12, dark ? 0xffdddddd : 0xff555555);
            detail.setTextIsSelectable(true);
            bar = new ProgressBar(a, null, android.R.attr.progressBarStyleHorizontal); bar.setMax(100);
            action = text(a, 12, dark ? 0xffb5ccff : 0xff315d9b);
            action.setPadding(0, dp(a, 8), 0, dp(a, 4)); action.setGravity(Gravity.END);
            root.addView(title); root.addView(detail);
            root.addView(bar, new LinearLayout.LayoutParams(-1, dp(a, 8))); root.addView(action);
        }
        TextView text(Activity a, int size, int color) {
            TextView view = new TextView(a); view.setTextSize(size); view.setTextColor(color);
            view.setSingleLine(false); view.setEllipsize(null); view.setMaxLines(Integer.MAX_VALUE);
            return view;
        }
    }
}
