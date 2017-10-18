package com.bartoszlipinski.constraint.internal;

import com.bartoszlipinski.constraint.StaggeredAnimationGroup;

import org.robolectric.RuntimeEnvironment;

import static org.mockito.Mockito.spy;

public final class Utilities {
    public static StaggeredAnimationGroup prepareSpiedGroup() {
        StaggeredAnimationGroup group = new StaggeredAnimationGroup(RuntimeEnvironment.application);
        return spy(group);
    }

    // Suppress default constructor for noninstantiability
    private Utilities() {
        throw new AssertionError();
    }
}
