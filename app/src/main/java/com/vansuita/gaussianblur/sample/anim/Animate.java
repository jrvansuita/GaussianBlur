package com.vansuita.gaussianblur.sample.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

/**
 * Created by jrvansuita on 19/02/17.
 */

public class Animate {

    private static final int DURATION = 200;
    private View view;

    private Animate(View view) {
        this.view = view;
    }

    public static Animate with(View v) {
        return new Animate(v);
    }

    public boolean isVisible() {
        return view.getVisibility() == View.VISIBLE;
    }

    private int getAlphaBy() {
        return isVisible() ? 1 : 0;
    }

    private int getAlpha() {
        return isVisible() ? 0 : 1;
    }

    private AnimatorListenerAdapter getAnimatorListenerAdapter() {
        if (isVisible()) {
            return new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animator) {
                    view.setVisibility(View.INVISIBLE);
                }
            };
        } else {
            return new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animator) {
                    view.setVisibility(View.VISIBLE);
                }
            };
        }
    }

    public void toggleVisibility() {
        view.animate().alphaBy(getAlphaBy()).alpha(getAlpha()).setDuration(DURATION).setListener(getAnimatorListenerAdapter());
    }

}
