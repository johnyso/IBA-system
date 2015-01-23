package de.ibs.app.utils;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by johnyso on 22.01.15.
 */

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault("fonts/BebasNeue.ttf");
    }
}