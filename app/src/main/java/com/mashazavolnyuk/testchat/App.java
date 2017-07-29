package com.mashazavolnyuk.testchat;

import com.mashazavolnyuk.testchat.di.component.AppComponent;
import com.mashazavolnyuk.testchat.di.component.DaggerAppComponent;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by mashka on 27.07.17.
 */

public class Application extends android.app.Application{


    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.create();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")//RobotoRegular
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
    public static AppComponent getComponent() {
        return component;
    }
}
