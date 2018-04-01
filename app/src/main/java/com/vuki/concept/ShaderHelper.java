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
        /**
         * LINEAR GRADIENT
         */
        return new LinearGradient( cX / 2, cY / 2, cX / 2 + cX, cY / 2 + cY,
                new int[]{ Color.BLUE, Color.GREEN, Color.RED },
                null,
                Shader.TileMode.REPEAT );
    }

    public static Shader getRadialShader( float cX, float cY, float radius ) {
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

    public static Shader getSweepShader( float cX, float cY ) {
        /**
         * SWEEP
         *
         * upozorit da pozicije moraju biti jednako rasporedene.
         * Radi cijeli krug koristeci boje.
         * Primjer -> farbanje slova, progress barova
         */
        return new SweepGradient( cX, cY, new int[]{ Color.RED, Color.GREEN, Color.BLUE }, null );
    }

    public static Shader getBitmapShader( Bitmap bitmap, float cX, float cY, float radius ) {
        /**
         * BITMAP GRADIENT
         */

         return new BitmapShader( bitmap,
                Shader.TileMode.CLAMP, //ponavlja po x
                Shader.TileMode.REPEAT ); //ponavlja po y
    }

    public static Shader getComposeShader( Shader first, Shader second ) {

        /**
         * COMPOSE GRADIENT
         * ne radi s hardverskom akceleracijom
         * https://developer.android.com/guide/topics/graphics/hardware-accel.html#unsupported
         * prekrivene strukture nisu moguce
         */
        return new ComposeShader( first, second, PorterDuff.Mode.ADD );
    }
}
