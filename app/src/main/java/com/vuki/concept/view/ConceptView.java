package com.vuki.concept.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.vuki.concept.ShaderHelper;

/**
 * Created by mvukosav
 */
public class ConceptView extends View {

    float width;
    float height;
    float wheelRadius;
    Paint wheelPaint;
    Paint chassisPaint;

    Path carPath = new Path();
    PointF wheelCirclePoint = new PointF();
    boolean isInitialized = false;

    float firstWheelCx;
    float firstWheelCy;

    float leftWheelCenterX;
    float leftWheelCenterY;

    float secondWheelCx;
    float secondWheelCy;

    RectF exhaustBoundaries;

    Shader sweepGradient;
    Shader linearShader;
    Shader composeShader;
    Shader radialShader;
    Shader bitmapShader;

    public float wheelAngleOffset = 0;

    public ConceptView( Context context ) {
        super( context );
    }

    public ConceptView( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
    }

    public ConceptView( Context context, @Nullable AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
    }

    public ConceptView( Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes ) {
        super( context, attrs, defStyleAttr, defStyleRes );
    }

    @Override
    protected void onDraw( Canvas canvas ) {
        super.onDraw( canvas );
        if ( !isInitialized ) {
            isInitialized = true;
            init();
        }
        // bez postavljanja tipa 11mb ali ne radi ComposeShader
        //s postavljanjem 17-18mb

//        canvas.drawPaint( background );
        //setLayerType( LAYER_TYPE_NONE, null );
//        background.setColorFilter( ColorFilterHelper.getColorMatrixColorFilter() );
//        drawUglyCar( canvas );
        drawPrettyCar( canvas );
        //  chassisPaint.setShader( getShader( getWidth() / 2, getHeight() / 2, wheelRadius ) );
        //wheelPaint.setShader( getShader( firstWheelCx, firstWheelCy, wheelRadius ) );

        drawExhaust( canvas );
    }

    private void drawExhaust( Canvas canvas ) {
        canvas.drawRect( exhaustBoundaries, chassisPaint );
    }

    private void init() {
        height = getHeight();
        width = getWidth();

        wheelPaint = new Paint();
        wheelPaint.setAntiAlias( true );
        wheelPaint.setColor( Color.WHITE );
        wheelPaint.setStyle( Paint.Style.STROKE );
        wheelPaint.setStrokeWidth( getResources().getDimensionPixelSize( R.dimen.wheel_paint_stroke_width ) );

        chassisPaint = new Paint();
        chassisPaint.setAntiAlias( true );
        chassisPaint.setColor( Color.BLACK );

        wheelRadius = getContext().getResources().getInteger( R.integer.wheelRadius );

        float wheelSpacing = 20;
        float chassisWheelRadius = wheelRadius + wheelSpacing;

        //dole lijevo
        float x1 = width / 8;
        float y1 = 3 * height / 4;
        carPath.moveTo( x1, y1 );
        float x11 = width / 4 - wheelRadius;
        float y11 = 3 * height / 4;
        carPath.lineTo( x11, y11 );

        carPath.arcTo( width / 4 - chassisWheelRadius, 3 * height / 4 - chassisWheelRadius, width / 4 + chassisWheelRadius, 3 * height / 4 + chassisWheelRadius,
                180f, 180f, false );

        carPath.arcTo( 3 * width / 4 - chassisWheelRadius, 3 * height / 4 - chassisWheelRadius, 3 * width / 4 + chassisWheelRadius, 3 * height / 4 + chassisWheelRadius,
                180f, 180f, false );

        //naprijed dole
        float x2 = 7 * width / 8;
        float y2 = 3 * height / 4;
        carPath.lineTo( x2, y2 );

        //naprijed gore
        float x3 = 7.1f * width / 8;
        float y3 = 2.5f * height / 4;
        carPath.lineTo( x3, y3 );

        //hauba desno
        float x4 = 6.5f * width / 8;
        float y4 = 2.2f * height / 4;
        //hauba lijevo
        float x5 = 5.2f * width / 8;
        float y5 = 2.1f * height / 4;
        carPath.quadTo( x4, y4, x5, y5 );

        //Roof right
        float roofXRight = width / 2;
        float roofYRight = 1.7f * height / 4;
        //Roof middle
        float roofXMiddle = 3.9f * width / 8;
        float roofYMiddle = 1.7f * height / 4;
        //Roof left
        float roofXLeft = 3 * width / 8;
        float roofYLeft = 1.7f * height / 4;
        carPath.cubicTo( roofXRight, roofYRight, roofXMiddle, roofYMiddle, roofXLeft, roofYLeft );

        //lijevo kraj
        float x8 = 0.9f * width / 8;
        float y8 = height / 2;
        carPath.lineTo( x8, y8 );
        carPath.close();

        float endExhaustX = 0.125f * width;
        float endExhaustY = 0.73f * height;
        exhaustBoundaries = new RectF( 0.110f * width, 0.69f * height, endExhaustX, endExhaustY );

        // Bitmap bitmap = Bitmap.createBitmap( getWidth() / 2, getHeight() / 2, Bitmap.Config.ARGB_8888 );
        Bitmap bitmap = BitmapFactory.decodeResource( getResources(), android.R.drawable.star_big_on );

        linearShader = ShaderHelper.getLinearShader( getWidth() / 2, getHeight() / 2 );
        sweepGradient = ShaderHelper.getSweepShader( getWidth() / 2, getHeight() / 2 );
        radialShader = ShaderHelper.getRadialShader( getWidth() / 2, getHeight() / 2, wheelRadius );
        bitmapShader = ShaderHelper.getBitmapShader( bitmap, getWidth() / 2, getHeight() / 2, wheelRadius );
        composeShader = ShaderHelper.getComposeShader( linearShader, sweepGradient );

    }

    private void drawUglyCar( Canvas canvas ) {

        leftWheelCenterX = width / 4;
        leftWheelCenterY = 3 * height / 4;

        canvas.drawCircle( leftWheelCenterX, leftWheelCenterY, wheelRadius, wheelPaint );

        float secondWheelCx = 3 * width / 4;
        float secondWheelCy = 3 * height / 4;
        canvas.drawCircle( secondWheelCx, secondWheelCy, wheelRadius, wheelPaint );

        RectF lowerChassisRect = new RectF( width / 8, height / 2, 7 * width / 8, 3 * height / 4 );
        canvas.drawRect( lowerChassisRect, wheelPaint );

        RectF upperChassisRect = new RectF( width / 4, height / 4, 5 * width / 8, height / 2 );
        canvas.drawRect( upperChassisRect, wheelPaint );
    }

    private void draWheel( Canvas canvas, float cX, float cY, float radius, Paint paint ) {
        canvas.drawCircle( cX, cY, radius, paint );

        float initAngle = 72;

        calculatePoint( wheelCirclePoint, initAngle + wheelAngleOffset, radius, cX, cY );
        canvas.drawLine( cX, cY, wheelCirclePoint.x, wheelCirclePoint.y, wheelPaint );

        calculatePoint( wheelCirclePoint, 2 * initAngle + wheelAngleOffset, radius, cX, cY );
        canvas.drawLine( cX, cY, wheelCirclePoint.x, wheelCirclePoint.y, wheelPaint );

        calculatePoint( wheelCirclePoint, 3 * initAngle + wheelAngleOffset, radius, cX, cY );
        canvas.drawLine( cX, cY, wheelCirclePoint.x, wheelCirclePoint.y, wheelPaint );

        calculatePoint( wheelCirclePoint, 4 * initAngle + wheelAngleOffset, radius, cX, cY );
        canvas.drawLine( cX, cY, wheelCirclePoint.x, wheelCirclePoint.y, wheelPaint );

        calculatePoint( wheelCirclePoint, 5 * initAngle + wheelAngleOffset, radius, cX, cY );
        canvas.drawLine( cX, cY, wheelCirclePoint.x, wheelCirclePoint.y, wheelPaint );
    }

    private void calculatePoint( PointF point, float angle, float radius, float cX, float cY ) {
        float x = (float) ( Math.cos( Math.toRadians( angle ) ) * radius ) + cX;
        float y = (float) ( Math.sin( Math.toRadians( angle ) ) * radius ) + cY;
        point.set( x, y );
    }

    private void drawPrettyCar( Canvas canvas ) {
        firstWheelCx = width / 4;
        firstWheelCy = 3 * height / 4;
        draWheel( canvas, firstWheelCx, firstWheelCy, wheelRadius, wheelPaint );

        secondWheelCx = 3 * width / 4;
        secondWheelCy = 3 * height / 4;
        draWheel( canvas, secondWheelCx, secondWheelCy, wheelRadius, wheelPaint );

        drawChassis( canvas );
    }

    private void drawChassis( Canvas canvas ) {
        canvas.drawPath( carPath, chassisPaint );
    }
}
