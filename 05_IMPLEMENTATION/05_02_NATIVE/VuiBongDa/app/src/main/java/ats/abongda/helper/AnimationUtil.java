package ats.abongda.helper;

import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

/**
 * Created by NienLe on 15-Aug-16.
 */
public class AnimationUtil {
    public static void switchView(final View viewToShow,final  View viewToHide, boolean slideToRight){
        if (slideToRight){
            YoYo.with(Techniques.SlideOutRight).duration(200).playOn(viewToHide);
            YoYo.with(Techniques.SlideInLeft).withListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    viewToShow.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).duration(200).playOn(viewToShow);
        }else{
            YoYo.with(Techniques.SlideOutLeft).duration(200).playOn(viewToHide);
            YoYo.with(Techniques.SlideInRight).withListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    viewToShow.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).duration(200).playOn(viewToShow);
        }
    }
}
