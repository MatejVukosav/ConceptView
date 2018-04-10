package com.vuki.concept;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.annotation.ColorInt;

/**
 * Created by mvukosav
 */
public class ColorFilterHelper {

//    private static float[] matrix = {
//            1, 1 ,0, 0, -255, //red
//            0, 0, 0, 0, 0, //green
//            0, 0, 1, 0, 0, //blue
//            0, 0, 0, 1, 0 //alpha
//    };

    private static float[] matrix = {
            -1, 0, 0, 0, 255,
            0, -1, 0, 0, 255,
            0, 0, -1, 0, 255,
            0, 0, 0, 1, 0
    };

    public static ColorFilter getColorMatrixColorFilter() {
        ColorMatrix colorMatrix = new ColorMatrix();
//        colorMatrix.set( matrix );
        colorMatrix.setSaturation( 0 ); //grayscale

        ColorMatrix colorScale=new ColorMatrix(  );
        colorScale.setScale( 1, 1, 0.8f, 1 );
        colorMatrix.postConcat(colorScale );

        return new ColorMatrixColorFilter( colorMatrix );
    }

    //transform every pixel to red
//    float[] matrix = {
//            1, 1, 1, 1, 1, //red
//            0, 0, 0, 0, 0, //green
//            0, 0, 0, 0, 0, //blue
//            1, 1, 1, 1, 1 //alpha
//    };

    public static ColorFilter getPorterDuffColorFilter() {
        return new PorterDuffColorFilter( Color.RED, PorterDuff.Mode.XOR );
    }

    public static ColorFilter getLightningColorFilter() {
        /**
         * Integer values are colors
         * 4 bytes are used, one for alpha, one for red, one for green, one for blue
         * 8 bits
         * range from 0 to 255 (hex 0 to FF)
         * 0x00     00  00  00
         * alpha, red,green,blue
         */
//        @ColorInt int mul = 0xFFFF00FF; //set green to zero
        int mul = 0xFF00FF; //show only blue
        @ColorInt int add = 0x00000000;

        //to lighten
//        @ColorInt int mul = 0xFFFFFF;
//        @ColorInt int add = 0x222222;

//        @ColorInt int add = 0x333333; //20% transparent
//        @ColorInt int add = 0x808080; //50%
//        @ColorInt int add = 0x999999; //60%
//        @ColorInt int add = 0xCCCCCC; //80%

//        @ColorInt int add = 0x000000; //original colors
//        @ColorInt int add = 0xFFFFFF; //everything is white

        //to darken
//        @ColorInt int mul = 0x808080;  // multiply colors by 50% black overlay
//        @ColorInt int add = 0x000000;
        return new LightingColorFilter( mul, add );
    }

}
