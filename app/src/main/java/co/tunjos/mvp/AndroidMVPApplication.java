package co.tunjos.mvp;

import android.app.Application;
import android.content.Context;

/**
 *
 */

public class AndroidMVPApplication extends Application{
    private static Context context;

    public static Context getAppContext() {
        return AndroidMVPApplication.context;
    }
}
