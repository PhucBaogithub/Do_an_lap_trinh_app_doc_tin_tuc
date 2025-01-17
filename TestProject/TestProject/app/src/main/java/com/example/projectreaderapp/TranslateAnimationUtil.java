package com.example.projectreaderapp;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TranslateAnimationUtil implements View.OnTouchListener{

    private GestureDetector gestureDetector;

    public TranslateAnimationUtil(Context context, View viewAnimation){
        gestureDetector = new GestureDetector(context, new SimpleGestureDetector(viewAnimation));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public class SimpleGestureDetector extends GestureDetector.SimpleOnGestureListener{
        private View viewAnimation;
        private boolean isFinishAnimation = true;

        public SimpleGestureDetector(View viewAnimation) {
            this.viewAnimation = viewAnimation;
        }

        @Override
        public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
            if(distanceY>0){
                hiddenView();
            }else{
                showView();
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        private void hiddenView(){
            if(viewAnimation==null || viewAnimation.getVisibility() == View.GONE){
                return;
            }else {
                Animation animationDown = AnimationUtils.loadAnimation(viewAnimation.getContext(), R.anim.move_down);
                animationDown.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        viewAnimation.setVisibility(View.VISIBLE);
                        isFinishAnimation = false;
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        viewAnimation.setVisibility(View.GONE);
                        isFinishAnimation = true;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                if(isFinishAnimation){
                    viewAnimation.startAnimation(animationDown);
                }
            }
        }

        private void  showView(){
            if(viewAnimation==null || viewAnimation.getVisibility() == View.VISIBLE){
                return;
            }else {
                Animation animationUp = AnimationUtils.loadAnimation(viewAnimation.getContext(), R.anim.move_up);
                animationUp.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        viewAnimation.setVisibility(View.VISIBLE);
                        isFinishAnimation = false;
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        isFinishAnimation = true;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                if(isFinishAnimation){
                    viewAnimation.startAnimation(animationUp);
                }
            }
        }
    }
}
