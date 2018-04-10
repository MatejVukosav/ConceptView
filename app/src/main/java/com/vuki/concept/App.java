package com.vuki.concept;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

/**
 * Created by mvukosav
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        this.registerActivityLifecycleCallbacks( new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated( Activity activity, Bundle savedInstanceState ) {
                activity.setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE );
            }

            @Override
            public void onActivityStarted( Activity activity ) {

            }

            @Override
            public void onActivityResumed( Activity activity ) {

            }

            @Override
            public void onActivityPaused( Activity activity ) {

            }

            @Override
            public void onActivityStopped( Activity activity ) {

            }

            @Override
            public void onActivitySaveInstanceState( Activity activity, Bundle outState ) {

            }

            @Override
            public void onActivityDestroyed( Activity activity ) {

            }
        } );
    }
}
