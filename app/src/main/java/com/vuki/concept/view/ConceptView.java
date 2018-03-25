package com.vuki.concept.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by mvukosav
 */
public class ConceptView extends View {

    float width;
    float height;
    Paint wheelPaint;
    float wheelRadius;
    Paint chassisPaint;
    Path carPath = new Path();

    float leftWheelCenterX;
    float leftWheelCenterY;

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

//        drawUglyCar( canvas );
        drawPrettyCar( canvas );
    }

    boolean isInitialized = false;

    private void init() {
        height = getHeight();
        width = getWidth();

        wheelPaint = new Paint();
        wheelPaint.setColor( Color.RED );
        wheelPaint.setStyle( Paint.Style.STROKE );
        wheelPaint.setStrokeWidth( 10 );

        chassisPaint = new Paint();
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
        float x3 = 7 * width / 8;
        float y3 = 4.5f * height / 8;
        carPath.lineTo( x3, y3 );

        //hauba desno
        float x4 = 3 * width / 4;
        float y4 = 4 * height / 8;
        carPath.lineTo( x4, y4 );

        //hauba lijevo
        float x5 = 5 * width / 8;
        float y5 = height / 2;
        carPath.lineTo( x5, y5 );

        //krov desno
        float x6 = width / 2;
        float y6 = 3 * height / 8;
        carPath.lineTo( x6, y6 );

        //krov lijevo
        float x7 = 3 * width / 8;
        float y7 = 3 * height / 8;
        carPath.lineTo( x7, y7 );

        //lijevo kraj
        float x8 = width / 8;
        float y8 = height / 2;
        carPath.lineTo( x8, y8 );

        carPath.close();
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

        PointF endPoint = getPoint( initAngle + wheelAngleOffset, wheelRadius, cX, cY );
        canvas.drawLine( cX, cY, endPoint.x, endPoint.y, wheelPaint );

        endPoint = getPoint( 2 * initAngle + wheelAngleOffset, wheelRadius, cX, cY );
        canvas.drawLine( cX, cY, endPoint.x, endPoint.y, wheelPaint );

        endPoint = getPoint( 3 * initAngle + wheelAngleOffset, wheelRadius, cX, cY );
        canvas.drawLine( cX, cY, endPoint.x, endPoint.y, wheelPaint );

        endPoint = getPoint( 4 * initAngle + wheelAngleOffset, wheelRadius, cX, cY );
        canvas.drawLine( cX, cY, endPoint.x, endPoint.y, wheelPaint );

        endPoint = getPoint( 5 * initAngle + wheelAngleOffset, wheelRadius, cX, cY );
        canvas.drawLine( cX, cY, endPoint.x, endPoint.y, wheelPaint );
    }

    private PointF getPoint( float angle, float radius, float cX, float cY ) {
        float x = (float) ( Math.cos( Math.toRadians( angle ) ) * radius ) + cX;
        float y = (float) ( Math.sin( Math.toRadians( angle ) ) * radius ) + cY;
        return new PointF( x, y );
    }

    private void drawPrettyCar( Canvas canvas ) {
        float firstWheelCx = width / 4;
        float firstWheelCy = 3 * height / 4;
        draWheel( canvas, firstWheelCx, firstWheelCy, wheelRadius, wheelPaint );

        float secondWheelCx = 3 * width / 4;
        float secondWheelCy = 3 * height / 4;
        draWheel( canvas, secondWheelCx, secondWheelCy, wheelRadius, wheelPaint );

        drawChassis( canvas );
    }

    private void drawChassis( Canvas canvas ) {

        canvas.drawPath( carPath, chassisPaint );
    }
}
