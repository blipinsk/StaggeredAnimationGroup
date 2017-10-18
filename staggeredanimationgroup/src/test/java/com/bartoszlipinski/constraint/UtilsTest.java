package com.bartoszlipinski.constraint;

import android.support.constraint.ConstraintLayout;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.bartoszlipinski.constraint.internal.Utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static com.bartoszlipinski.constraint.internal.Utilities.prepareSpiedGroup;
import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class UtilsTest {

    // getConstraintLayoutParent
    @Test
    public void getConstraintLayoutParent_returnsParent_ifParent_isConstraintLayout() {
        //given
        StaggeredAnimationGroup spiedGroup = prepareSpiedGroup();
        ViewParent parent = new ConstraintLayout(RuntimeEnvironment.application);
        when(spiedGroup.getParent()).thenReturn(parent);

        //when
        ConstraintLayout result = Utils.getConstraintLayoutParent(spiedGroup);

        //then
        assertThat(result).isEqualTo(parent);
    }

    @Test
    public void getConstraintLayoutParent_returnsNull_ifParent_isNotConstraintLayout() {
        //given
        StaggeredAnimationGroup spiedGroup = prepareSpiedGroup();
        ViewParent parent = new FrameLayout(RuntimeEnvironment.application);
        when(spiedGroup.getParent()).thenReturn(parent);

        //when
        ConstraintLayout result = Utils.getConstraintLayoutParent(spiedGroup);

        //then
        assertThat(result).isNull();
    }

    @Test
    public void getConstraintLayoutParent_returnsNull_ifParent_isNull() {
        //given
        StaggeredAnimationGroup spiedGroup = prepareSpiedGroup();
        when(spiedGroup.getParent()).thenReturn(null);

        //when
        ConstraintLayout result = Utils.getConstraintLayoutParent(spiedGroup);

        //then
        assertThat(result).isNull();
    }
}