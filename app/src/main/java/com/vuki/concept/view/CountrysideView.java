package com.vuki.concept.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by mvukosav
 */
public class CountrysideView extends View {

    private Paint grassPaint;
    private Paint roadPaint;
    private Paint mountainPaint;
    private Paint skyPaint;

    private Path roadPath;
    private Path grassPath;
    public Path mountainPath = new Path();
    public Path skyPath = new Path();
    Path repeatingPart = new Path();

    private float grassStartUpY;
    private float grassEndUpY;

    public float offset = 0;//1776;

    private boolean initialized;

    public CountrysideView( Context context ) {
        super( context );
    }

    public CountrysideView( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
    }

    public CountrysideView( Context context, @Nullable AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
    }

    public CountrysideView( Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes ) {
        super( context, attrs, defStyleAttr, defStyleRes );
    }

    @Override
    protected void onDraw( Canvas canvas ) {
        super.onDraw( canvas );
        if ( !initialized ) {
            initialized = true;
            init();
        }

        setMountainPath( offset, grassStartUpY, grassEndUpY );

        canvas.drawPath( roadPath, roadPaint );
        canvas.drawPath( grassPath, grassPaint );
        canvas.drawPath( skyPath, skyPaint );
        canvas.drawPath( mountainPath, mountainPaint );
    }

    private void setMountainPath( float offset, float leftDownStartY, float rightDownStartY ) {

        float tripleWidth = 3 * getWidth();

        float bottom = Math.min( leftDownStartY, rightDownStartY );

        float startHeight = 0.25f * getHeight();
        float endHeight = 0.25f * getHeight();

        mountainPath.reset();  //less memory consumption than rewind because points coordinates are changing
        //from down to right to top, to left
        mountainPath.moveTo( -offset, bottom );
        mountainPath.lineTo( tripleWidth - offset, bottom );
        //top right
        mountainPath.lineTo( tripleWidth - offset, endHeight );

        /*
        FIRST PART
         */
        mountainPath.cubicTo( ( 2.8f * getWidth() ) - offset, 0.3f * getHeight(),
                ( 2.7f * getWidth() ) - offset, 0.4f * getHeight(),
                ( 2.6f * getWidth() ) - offset, 0.3f * getHeight() );

        //Add a cubic bezier from the last point, approaching control points (x1,y1) and (x2,y2), and ending at (x3,y3).
        mountainPath.cubicTo( ( 2.5f * getWidth() ) - offset, 0.25f * getHeight(),
                ( 2.3f * getWidth() ) - offset, 0.045f * getHeight(),
                2 * getWidth() - offset, startHeight );

        /*
        middle part
         */
        mountainPath.cubicTo( ( 1.9f * getWidth() ) - offset, 0.1f * getHeight(),
                ( 1.7f * getWidth() ) - offset, 0.5f * getHeight(),
                ( 1.5f * getWidth() ) - offset, 0.25f * getHeight() );

        //Add a cubic bezier from the last point, approaching control points (x1,y1) and (x2,y2), and ending at (x3,y3).
        mountainPath.cubicTo( ( 1.4f * getWidth() ) - offset, 0.25f * getHeight(),
                ( 1.2f * getWidth() ) - offset, 0.15f * getHeight(),
                getWidth() - offset, startHeight );

//        //first part
        mountainPath.cubicTo( ( 0.8f * getWidth() ) - offset, 0.3f * getHeight(),
                ( 0.7f * getWidth() ) - offset, 0.4f * getHeight(),
                ( 0.6f * getWidth() ) - offset, 0.3f * getHeight() );

        //Add a cubic bezier from the last point, approaching control points (x1,y1) and (x2,y2), and ending at (x3,y3).
        mountainPath.cubicTo( ( 0.5f * getWidth() ) - offset, 0.25f * getHeight(),
                ( 0.3f * getWidth() ) - offset, 0.05f * getHeight(),
                -offset, startHeight );
        mountainPath.close();
    }

    private void init() {
        grassPaint = new Paint();
        grassPaint.setAntiAlias( true );
        grassPaint.setColor( Color.GREEN );

        roadPaint = new Paint();
        roadPaint.setAntiAlias( true );
        roadPaint.setColor( Color.GRAY );

        skyPaint = new Paint();
        skyPaint.setAntiAlias( true );
        skyPaint.setColor( Color.BLUE );

        float roadUpStartY = ( 2 / 3f ) * getHeight();
        float roadUpEndY = 0.90f * ( 2 / 3f ) * getHeight();
        float roadDownStartY = getHeight();
        float roadDownEndY = 0.96f * getHeight();

        roadPath = new Path();
        roadPath.moveTo( 0, roadUpStartY );
        roadPath.lineTo( getWidth(), roadUpEndY );
        roadPath.lineTo( getWidth(), roadDownEndY );
        roadPath.lineTo( 0, roadDownStartY );
        roadPath.close();

        grassStartUpY = getHeight() / 2;
        grassEndUpY = getHeight() / 2;

        grassPath = new Path();
        grassPath.moveTo( 0, grassStartUpY );
        grassPath.lineTo( getWidth(), grassEndUpY );
        grassPath.lineTo( getWidth(), getHeight() );
        grassPath.lineTo( 0, getHeight() );
        grassPath.close();
        grassPath.op( roadPath, Path.Op.DIFFERENCE );

        mountainPaint = new Paint();
        mountainPaint.setAntiAlias( true );
        mountainPaint.setColor( Color.DKGRAY );

        repeatingPart.cubicTo( ( 0.8f * getWidth() ) - offset, 0.3f * getHeight(),
                ( 0.7f * getWidth() ) - offset, 0.5f * getHeight(),
                ( 0.5f * getWidth() ) - offset, 0.25f * getHeight() );

        //Add a cubic bezier from the last point, approaching control points (x1,y1) and (x2,y2), and ending at (x3,y3).
        repeatingPart.cubicTo( ( 0.45f * getWidth() ) - offset, 0.25f * getHeight(),
                ( 0.35f * getWidth() ) - offset, 0.05f * getHeight(),
                -offset, 0.25f * getHeight() );

        //setMountainPath( 0, grassStartUpY, grassEndUpY );

        skyPaint = new Paint();
        skyPaint.setAntiAlias( true );
        skyPaint.setColor( Color.BLUE );

        skyPath.moveTo( 0, 0 );
        skyPath.lineTo( getWidth(), 0 );
        skyPath.lineTo( getWidth(), getHeight() );
        skyPath.lineTo( 0, getHeight() );
        skyPath.close();
        skyPath.op( roadPath, Path.Op.DIFFERENCE );
        skyPath.op( grassPath, Path.Op.DIFFERENCE );
        skyPath.op( mountainPath, Path.Op.DIFFERENCE );

    }
}
