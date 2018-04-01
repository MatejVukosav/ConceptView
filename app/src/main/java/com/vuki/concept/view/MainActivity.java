package com.vuki.concept.view;

import android.animation.FloatEvaluator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.LinearInterpolator;

import com.vuki.concept.view.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private int WHEEL_ANIMATION_DURATION = 400;
    private int BACKGROUND_ANIMATION_DURATION = 3000;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_main );

        PropertyValuesHolder wheelAngleOffset = PropertyValuesHolder.ofFloat( "wheelAngleOffset", 0, 360 );

        ValueAnimator conceptViewValueAnimator = new ValueAnimator();
        conceptViewValueAnimator.setValues( wheelAngleOffset );
        conceptViewValueAnimator.setDuration( WHEEL_ANIMATION_DURATION );
        conceptViewValueAnimator.setRepeatCount( ValueAnimator.INFINITE );
        conceptViewValueAnimator.setEvaluator( new FloatEvaluator() );
        conceptViewValueAnimator.setInterpolator( new LinearInterpolator() );

        conceptViewValueAnimator.addUpdateListener( new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate( ValueAnimator animation ) {
                binding.conceptView.wheelAngleOffset = (float) animation.getAnimatedValue( "wheelAngleOffset" );
                binding.conceptView.postInvalidate();
            }
        } );

        conceptViewValueAnimator.start();


        PropertyValuesHolder mountainOffset = PropertyValuesHolder.ofFloat( "mountainOffset", 0, 2 * 1776 );
        ValueAnimator mountainViewValueAnimator = new ValueAnimator();
        mountainViewValueAnimator.setValues( mountainOffset );
        mountainViewValueAnimator.setDuration( BACKGROUND_ANIMATION_DURATION );
        mountainViewValueAnimator.setRepeatCount( ValueAnimator.INFINITE );
        mountainViewValueAnimator.setRepeatMode( ValueAnimator.RESTART );
        mountainViewValueAnimator.setEvaluator( new FloatEvaluator() );
        mountainViewValueAnimator.setInterpolator( new LinearInterpolator() );

        mountainViewValueAnimator.addUpdateListener( new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate( ValueAnimator animation ) {
                binding.countrysideView.offset = (float) animation.getAnimatedValue( "mountainOffset" );
                binding.countrysideView.postInvalidate();
            }
        } );
        mountainViewValueAnimator.start();
    }

}
