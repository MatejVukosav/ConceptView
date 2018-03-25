package com.vuki.concept.view;

import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.LinearInterpolator;

import com.vuki.concept.view.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_main );

        ValueAnimator valueAnimator = ValueAnimator.ofFloat( 0, 360 );
        valueAnimator.setDuration( 3000 );
//        valueAnimator.setRepeatMode( ValueAnimator.RESTART );
        valueAnimator.setRepeatCount( ValueAnimator.INFINITE );
        valueAnimator.setEvaluator( new FloatEvaluator());
        valueAnimator.setInterpolator( new LinearInterpolator(  ) );

        valueAnimator.addUpdateListener( new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate( ValueAnimator animation ) {
                float angle = (float) animation.getAnimatedValue();
                binding.conceptView.wheelAngleOffset = angle;
                binding.conceptView.postInvalidate();
            }
        } );

        valueAnimator.start();

//        RotateAnimation wheelRotation = new RotateAnimation( 0.0f, 360, binding.conceptView.leftWheelCenterX, binding.conceptView.leftWheelCenterY );
//        wheelRotation.setDuration( 3000 ); // rotate 12 rounds in 3 seconds
//        wheelRotation.setInterpolator( this, android.R.interpolator.accelerate_decelerate );
//        binding.conceptView.startAnimation( wheelRotation );
//
//        wheelRotation.setAnimationListener( new Animation.AnimationListener() {
//            public void onAnimationStart( Animation animation ) {
//            }
//
//            public void onAnimationEnd( Animation animation ) {
//            }
//
//            public void onAnimationRepeat( Animation animation ) {
//
//            }
//        } );

    }
}
