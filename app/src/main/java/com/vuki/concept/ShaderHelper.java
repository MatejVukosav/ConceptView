package com.vuki.concept;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;

/**
 * Created by mvukosav
 */
public class ShaderHelper {

    public static Shader getLinearShader( float cX, float cY ) {
        /*
        LINEAR GRADIENT
         */
        return new LinearGradient( cX / 2, cY / 2, cX / 2 + cX, cY / 2 + cY,
                new int[]{ Color.RED,  Color.GREEN, Color.BLUE },
                null,
                Shader.TileMode.CLAMP );
    }

    public static Shader getRadialShader( float cX, float cY, float radius ) {
        /*
          RADIAL
         */
        return new RadialGradient( cX, cY, radius,
                new int[]{ Color.RED, Color.GREEN, Color.BLUE,Color.YELLOW },
                null,
                Shader.TileMode.CLAMP );
        //two colors
        /*not with software layer type <- it should be hardware if we want only two colors (and one is transparent)
        https://blog.stylingandroid.com/radialgradient-gradients/
        setLayerType( LAYER_TYPE_HARDWARE,null );
        */
//        return new RadialGradient( cX, cY, radius,
//                Color.GREEN,
//                Color.TRANSPARENT,
//                Shader.TileMode.CLAMP );
    }

    public static Shader getSweepShader( float cX, float cY ) {
        /**
         * SWEEP
         *
         * Position must be equally divided
         * Creating whole circle using colors
         * Example -> coloring letters, progress bars
         */
        return new SweepGradient( cX, cY, new int[]{ Color.RED, Color.GREEN,Color.BLUE ,Color.YELLOW, }, null );
    }

    public static Shader getBitmapShader( Bitmap bitmap ) {
        /**
         * BITMAP GRADIENT
         */
         return new BitmapShader( bitmap,
                Shader.TileMode.REPEAT, //repeat by x
                Shader.TileMode.REPEAT ); //repeat by y
    }

    public static Shader getComposeShader( Shader first, Shader second ) {

        /**
         * COMPOSE GRADIENT
         * doesn't work with hardware acceleration
         * https://developer.android.com/guide/topics/graphics/hardware-accel.html#unsupported
         * Covered structures are not available
         */
        return new ComposeShader( first, second, PorterDuff.Mode.ADD );
    }
}
