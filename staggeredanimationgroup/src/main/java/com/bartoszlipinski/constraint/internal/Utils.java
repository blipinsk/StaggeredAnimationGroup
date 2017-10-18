package com.bartoszlipinski.constraint.internal;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;

public final class Utils {

    @Nullable
    public static ConstraintLayout getConstraintLayoutParent(View view) {
        ViewParent parent = view.getParent();
        return parent instanceof ConstraintLayout ? (ConstraintLayout) parent : null;
    }

    public static boolean notNull(ConstraintLayout parent) {
        if (parent == null) {
            warning("Trying to run show() on a StaggeredAnimationGroup without (proper) parent.");
            return false;
        }
        return true;
    }

    public static void warning(String msg) {
        Log.w("StaggeredAnimationGroup", msg);
    }

    // Suppress default constructor for noninstantiability
    private Utils() {
        throw new AssertionError();
    }
}
