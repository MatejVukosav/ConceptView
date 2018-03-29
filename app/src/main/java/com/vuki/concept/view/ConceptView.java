package com.vuki.concept.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by mvukosav
 */
public class ConceptView extends View {

    float width;
    float height;
    float wheelRadius;
    Paint wheelPaint;
    Paint chassisPaint;
    Paint background;
    Path carPath = new Path();
    PointF wheelCirclePoint = new PointF();

    float firstWheelCx;
    float firstWheelCy;

    float leftWheelCenterX;
    float leftWheelCenterY;

    float secondWheelCx;
    float secondWheelCy;

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

        setBackgroundShader();
        canvas.drawPaint( background );
        //setLayerType( LAYER_TYPE_NONE, null );

//        drawUglyCar( canvas );
        drawPrettyCar( canvas );
        //  chassisPaint.setShader( getShader( getWidth() / 2, getHeight() / 2, wheelRadius ) );
        //wheelPaint.setShader( getShader( firstWheelCx, firstWheelCy, wheelRadius ) );
    }

    private void setBackgroundShader() {
        //setLayerType( LAYER_TYPE_HARDWARE, null );
    }

    private Shader getLinearShader( float cX, float cY ) {
        /**
         * LINEAR GRADIENT
         */
        return new LinearGradient( cX / 2, cY / 2, cX / 2 + cX, cY / 2 + cY,
                new int[]{ Color.BLUE, Color.GREEN, Color.RED },
                null,
                Shader.TileMode.REPEAT );
    }

    private Shader getRadialShader( float cX, float cY, float radius ) {
        /**
         * RADIAL
         */
//        Shader shader = new RadialGradient( cX, cY, radius,
//                new int[]{ Color.RED, Color.GREEN, Color.BLUE },
//                new float[]{ 0.3f, 0.4f, 0.5f },
//                Shader.TileMode.CLAMP );
        /*sa softverskim iscrtavanjem <- TREBA PAZIT DA JE HARDWARE
        ako zelimo s dvije boje samo
        https://blog.stylingandroid.com/radialgradient-gradients/
        setLayerType( LAYER_TYPE_SOFTWARE,wheelPaint );
        */
        return new RadialGradient( cX, cY, radius,
                Color.GREEN,
                Color.TRANSPARENT,
                Shader.TileMode.CLAMP );
    }

    private Shader getSweepShader( float cX, float cY ) {
        /**
         * SWEEP
         *
         * upozorit da pozicije moraju biti jednako rasporedene.
         * Radi cijeli krug koristeci boje.
         * Primjer -> farbanje slova, progress barova
         */
        return new SweepGradient( cX, cY, new int[]{ Color.RED, Color.GREEN, Color.BLUE }, null );
    }

    private Shader getBitmapShader( float cX, float cY, float radius ) {
        /**
         * BITMAP GRADIENT
         */

        // Bitmap bitmap = Bitmap.createBitmap( getWidth() / 2, getHeight() / 2, Bitmap.Config.ARGB_8888 );
        Bitmap bitmap = BitmapFactory.decodeResource( getResources(), android.R.drawable.star_big_on );
        return new BitmapShader( bitmap,
                Shader.TileMode.CLAMP, //ponavlja po x
                Shader.TileMode.REPEAT ); //ponavlja po y
    }

    private Shader getComposeShader() {

        /**
         * COMPOSE GRADIENT
         * ne radi s hardverskom akceleracijom
         * https://developer.android.com/guide/topics/graphics/hardware-accel.html#unsupported
         * prekrivene strukture nisu moguce
         */
        return new ComposeShader( linearShader, sweepGradient, PorterDuff.Mode.ADD );
    }

    boolean isInitialized = false;

    private void init() {
        height = getHeight();
        width = getWidth();

        wheelPaint = new Paint();
        wheelPaint.setAntiAlias( true );
        wheelPaint.setColor( Color.RED );
        wheelPaint.setStyle( Paint.Style.STROKE );
        wheelPaint.setStrokeWidth( 10 );

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

        background = new Paint();
        background.setAntiAlias( true );
        background.setColor( Color.YELLOW );

        linearShader = getLinearShader( getWidth() / 2, getHeight() / 2 );
        sweepGradient = getSweepShader( getWidth() / 2, getHeight() / 2 );
        radialShader = getRadialShader( getWidth() / 2, getHeight() / 2, wheelRadius );
        bitmapShader = getBitmapShader( getWidth() / 2, getHeight() / 2, wheelRadius );
        composeShader = getComposeShader();

        background.setShader( sweepGradient );

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
