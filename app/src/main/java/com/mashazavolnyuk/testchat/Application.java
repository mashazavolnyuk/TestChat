package com.mashazavolnyuk.testchat;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by mashka on 27.07.17.
 */

public class Application extends android.app.Application{

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")//RobotoRegular
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
