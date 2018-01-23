package co.tunjos.mvp;

import android.app.Activity;
import android.app.Application;

import javax.inject.Inject;

import co.tunjos.mvp.injection.components.DaggerApplicationComponent;
import co.tunjos.mvp.injection.modules.ApplicationModule;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 *
 */

public class AndroidMVPApplication extends Application implements HasActivityInjector {
    @Inject DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build()
                .inject(this);

    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
