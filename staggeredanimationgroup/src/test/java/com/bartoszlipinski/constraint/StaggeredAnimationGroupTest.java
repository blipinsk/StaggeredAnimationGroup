package com.bartoszlipinski.constraint;

import android.animation.TimeInterpolator;
import android.support.transition.AutoTransition;
import android.support.transition.Fade;
import android.support.transition.Transition;
import android.support.transition.TransitionSet;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.animation.Interpolator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static com.bartoszlipinski.constraint.internal.Utilities.prepareSpiedGroup;
import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class StaggeredAnimationGroupTest {


    // filterNonZeroIds
    @Test
    public void filterNonZeroIds_returns_shorterArray_whenThereAre_zeros_inPassedArray() {
        //given
        StaggeredAnimationGroup spiedGroup = prepareSpiedGroup();
        int[] testIds = new int[]{1, 2, 3, 4, 0, 0, 0, 0};

        //when
        int[] filtered = spiedGroup.filterNonZeroIds(testIds);

        //then
        assertThat(filtered).asList().containsExactly(1, 2, 3, 4).inOrder();
    }

    @Test
    public void filterNonZeroIds_returns_sameArray_whenThereAre_noZeros_inPassedArray() {
        //given
        StaggeredAnimationGroup spiedGroup = prepareSpiedGroup();
        int[] testIds = new int[]{1, 2, 3, 4};

        //when
        int[] filtered = spiedGroup.filterNonZeroIds(testIds);

        //then
        assertThat(filtered).asList().containsExactly(1, 2, 3, 4).inOrder();
    }

    /**
     * We don't want the filterNonZeroIds to iterate through the array if there are no zeros in it.
     */
    @Test
    public void filterNonZeroIds_returns_theSameInstanceOfArray_whenThereAre_noZeros_inPassedArray() {
        //given
        StaggeredAnimationGroup spiedGroup = prepareSpiedGroup();
        int[] testIds = new int[]{1, 2, 3, 4};

        //when
        int[] filtered = spiedGroup.filterNonZeroIds(testIds);

        //then
        assertThat(filtered).isEqualTo(filtered);
    }

    // show
    @Test
    public void show_withoutArgs_calls_show_withFalse() {
        //given
        StaggeredAnimationGroup spiedGroup = prepareSpiedGroup();

        //when
        spiedGroup.show();

        //then
        verify(spiedGroup, times(1)).show();
        verify(spiedGroup, times(1)).show(false);
    }

    // hide
    @Test
    public void hide_withoutArgs_calls_hide_withFalse() {
        //given
        StaggeredAnimationGroup spiedGroup = prepareSpiedGroup();

        //when
        spiedGroup.hide();

        //then
        verify(spiedGroup, times(1)).hide();
        verify(spiedGroup, times(1)).hide(false);
    }

    // applyStaggeredTransitionParams
    @Test
    public void applyStaggeredTransitionParams_setsPassedViewId_asTarget() {
        //given
        StaggeredAnimationGroup spiedGroup = prepareSpiedGroup();
        Transition testTransition = new Fade(); //exemplary value
        int testIndexInStaggeredTransition = 0; //exemplary value
        int viewId = 12345; //exemplary value

        //when
        Transition resultTransition = spiedGroup.applyStaggeredTransitionParams(testTransition, viewId, testIndexInStaggeredTransition);

        //then
        List<Integer> targetIds = resultTransition.getTargetIds();
        assertThat(targetIds).hasSize(1);
        assertThat(targetIds.get(0)).isEqualTo(viewId);
    }

    @Test
    public void applyStaggeredTransitionParams_setsNoDelay_forFirstView() {
        applyStaggeredTransitionParams_setsDelay(0, 0);
    }

    @Test
    public void applyStaggeredTransitionParams_setsSingleDelay_forSecondView() {
        applyStaggeredTransitionParams_setsDelay(1, StaggeredAnimationGroup.DEFAULT_PARTIAL_TRANSITION_DELAY);
    }

    @Test
    public void applyStaggeredTransitionParams_setsDoubleDelay_forThirdView() {
        applyStaggeredTransitionParams_setsDelay(2, 2 * StaggeredAnimationGroup.DEFAULT_PARTIAL_TRANSITION_DELAY);
    }

    @Test
    public void applyStaggeredTransitionParams_setsTripleDelay_forFourthView() {
        applyStaggeredTransitionParams_setsDelay(3, 3 * StaggeredAnimationGroup.DEFAULT_PARTIAL_TRANSITION_DELAY);
    }

    public void applyStaggeredTransitionParams_setsDelay(int indexInStaggeredTransition, int expectedDelay) {
        //given
        StaggeredAnimationGroup spiedGroup = prepareSpiedGroup();
        Transition testTransition = new Fade(); //exemplary value
        int testViewId = 0; //exemplary value

        //when
        Transition resultTransition = spiedGroup.applyStaggeredTransitionParams(testTransition, testViewId, indexInStaggeredTransition);

        //then
        assertThat(resultTransition.getStartDelay()).isEqualTo(expectedDelay);
    }

    // partialTransitionFactory
    @Test
    public void partialTransitionFactory_isTheDefaultInstance_whenGroupIsCreated() {
        //given

        //when
        StaggeredAnimationGroup spiedGroup = prepareSpiedGroup();

        //then
        assertThat(spiedGroup.partialTransitionFactory)
                .isEqualTo(StaggeredAnimationGroup.defaultPartialTransitionFactory);
    }

    @Test(expected = NullPointerException.class)
    public void setPartialTransitionFactory_throwsNpe_whenPassed_null_listener() {
        //given
        StaggeredAnimationGroup spiedGroup = prepareSpiedGroup();
        StaggeredAnimationGroup.PartialTransitionFactory factory = null;

        //when
        spiedGroup.setPartialTransitionFactory(factory);

        //then
        // -> handled by `expected`
    }

    @Test
    public void setPartialTransitionFactory_setsField_whenPassed_nonNull_listener() {
        //given
        StaggeredAnimationGroup spiedGroup = prepareSpiedGroup();
        StaggeredAnimationGroup.PartialTransitionFactory testFactory =
                new StaggeredAnimationGroup.PartialTransitionFactory() {
                    @Override
                    public Transition createPartialTransition(boolean show, int viewId, int indexInTransition) {
                        return new AutoTransition();
                    }
                };

        //when
        spiedGroup.setPartialTransitionFactory(testFactory);

        //then
        assertThat(spiedGroup.partialTransitionFactory).isEqualTo(testFactory);
    }

    @Test
    public void clearPartialTransitionFactory_setsDefaultInstance() {
        //given
        StaggeredAnimationGroup spiedGroup = prepareSpiedGroup();

        //when
        spiedGroup.clearPartialTransitionFactory();

        //then
        assertThat(spiedGroup.partialTransitionFactory)
                .isEqualTo(StaggeredAnimationGroup.defaultPartialTransitionFactory);
    }

    @Test
    public void preparePartialTransition_usesFactory() {
        //given
        StaggeredAnimationGroup spiedGroup = prepareSpiedGroup();
        StaggeredAnimationGroup.PartialTransitionFactory spiedFactory =
                spy(new StaggeredAnimationGroup.PartialTransitionFactory() {
                    @Override
                    public Transition createPartialTransition(boolean show, int viewId, int indexInTransition) {
                        return new AutoTransition();
                    }
                });
        spiedGroup.setPartialTransitionFactory(spiedFactory);

        //when
        spiedGroup.preparePartialTransition(true, 0, 0);

        //then
        verify(spiedFactory, times(1))
                .createPartialTransition(anyBoolean(), anyInt(), anyInt());
        verifyNoMoreInteractions(spiedFactory);
    }

    @Test
    public void preparePartialTransition_setsPartialDuration() {
        //given
        final StaggeredAnimationGroup spiedGroup = prepareSpiedGroup();
        final int testPartialDuration = 12345;
        final Transition spiedTransition = spy(new AutoTransition());
        final StaggeredAnimationGroup.PartialTransitionFactory factory =
                new StaggeredAnimationGroup.PartialTransitionFactory() {
                    @Override
                    public Transition createPartialTransition(boolean show, int viewId, int indexInTransition) {
                        return spiedTransition;
                    }
                };
        spiedGroup.setPartialTransitionFactory(factory);
        spiedGroup.setPartialDuration(testPartialDuration);

        //when
        spiedGroup.preparePartialTransition(true, 0, 0);

        //then
        verify(spiedTransition, times(1)).setDuration(testPartialDuration);
    }

    @Test
    public void preparePartialTransition_setsPartialInterpolator() {
        //given
        final StaggeredAnimationGroup spiedGroup = prepareSpiedGroup();
        final TimeInterpolator testInterpolator = new LinearOutSlowInInterpolator();
        final Transition spiedTransition = spy(new AutoTransition());
        final StaggeredAnimationGroup.PartialTransitionFactory factory =
                new StaggeredAnimationGroup.PartialTransitionFactory() {
                    @Override
                    public Transition createPartialTransition(boolean show, int viewId, int indexInTransition) {
                        return spiedTransition;
                    }
                };
        spiedGroup.setPartialTransitionFactory(factory);
        spiedGroup.setPartialInterpolator(testInterpolator);

        //when
        spiedGroup.preparePartialTransition(true, 0, 0);

        //then
        verify(spiedTransition, times(1)).setInterpolator(testInterpolator);
    }

    // onPreparedListener
    @Test
    public void onPreparedListener_isTheDefaultInstance_whenGroupIsCreated() {
        //given

        //when
        StaggeredAnimationGroup spiedGroup = prepareSpiedGroup();

        //then
        assertThat(spiedGroup.onPreparedListener)
                .isEqualTo(StaggeredAnimationGroup.defaultOnPreparedListener);
    }

    @Test(expected = NullPointerException.class)
    public void setOnStaggeredTransitionPreparedListener_throwsNpe_whenPassed_null_listener() {
        //given
        StaggeredAnimationGroup spiedGroup = prepareSpiedGroup();
        StaggeredAnimationGroup.OnTransitionPreparedListener listener = null;

        //when
        spiedGroup.setOnTransitionPreparedListener(listener);

        //then
        // -> handled by `expected`
    }

    @Test
    public void setOnStaggeredTransitionPreparedListener_setsField_whenPassed_nonNull_listener() {
        //given
        StaggeredAnimationGroup spiedGroup = prepareSpiedGroup();
        StaggeredAnimationGroup.OnTransitionPreparedListener testListener =
                new StaggeredAnimationGroup.OnTransitionPreparedListener() {
                    @Override
                    public TransitionSet onStaggeredTransitionPrepared(TransitionSet transition, boolean show, boolean inReversedOrder) {
                        return transition;
                    }
                };

        //when
        spiedGroup.setOnTransitionPreparedListener(testListener);

        //then
        assertThat(spiedGroup.onPreparedListener).isEqualTo(testListener);
    }

    @Test
    public void clearOnStaggeredTransitionPreparedListener_setsDefaultInstance() {
        //given
        StaggeredAnimationGroup spiedGroup = prepareSpiedGroup();

        //when
        spiedGroup.clearOnTransitionPreparedListener();

        //then
        assertThat(spiedGroup.onPreparedListener).isEqualTo(StaggeredAnimationGroup.defaultOnPreparedListener);
    }

    //partialDuration
    @Test
    public void partialDuration_hasDefault_asInitialState() {
        //given
        StaggeredAnimationGroup spiedGroup = prepareSpiedGroup();

        //when
        // nothing to do here (this checks the initial state)

        //then
        assertThat(spiedGroup.partialDuration)
                .isEqualTo(StaggeredAnimationGroup.DEFAULT_PARTIAL_DURATION);
    }

    @Test
    public void partialDuration_isModified_when_setPartialDuration_isCalled() {
        //given
        StaggeredAnimationGroup spiedGroup = prepareSpiedGroup();
        int testDuration = 100;

        //when
        spiedGroup.setPartialDuration(testDuration);

        //then
        assertThat(spiedGroup.partialDuration).isEqualTo(testDuration);
    }

    //partialDelay
    @Test
    public void partialDelay_hasDefault_asInitialState() {
        //given
        StaggeredAnimationGroup spiedGroup = prepareSpiedGroup();

        //when
        // nothing to do here (this checks the initial state)

        //then
        assertThat(spiedGroup.partialDelay)
                .isEqualTo(StaggeredAnimationGroup.DEFAULT_PARTIAL_TRANSITION_DELAY);
    }

    @Test
    public void partialDelay_isModified_when_setPartialDelay_isCalled() {
        //given
        StaggeredAnimationGroup spiedGroup = prepareSpiedGroup();
        int testDelay = 100;

        //when
        spiedGroup.setPartialDelay(testDelay);

        //then
        assertThat(spiedGroup.partialDelay).isEqualTo(testDelay);
    }

    //partialInterpolator
    @Test
    public void partialInterpolator_hasDefault_asInitialState() {
        //given
        StaggeredAnimationGroup spiedGroup = prepareSpiedGroup();

        //when
        // nothing to do here (this checks the initial state)

        //then
        assertThat(spiedGroup.partialInterpolator)
                .isEqualTo(StaggeredAnimationGroup.DEFAULT_PARTIAL_INTERPOLATOR);
    }

    @Test
    public void partialInterpolator_isModified_when_setPartialInterpolator_isCalled() {
        //given
        StaggeredAnimationGroup spiedGroup = prepareSpiedGroup();
        Interpolator testInterpolator = new LinearOutSlowInInterpolator();

        //when
        spiedGroup.setPartialInterpolator(testInterpolator);

        //then
        assertThat(spiedGroup.partialInterpolator).isEqualTo(testInterpolator);
    }
}