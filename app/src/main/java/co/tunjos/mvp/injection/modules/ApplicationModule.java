package co.tunjos.mvp.injection.modules;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import co.tunjos.mvp.util.SharedPreferencesHelper;
import dagger.Module;
import dagger.Provides;

/**
 * Provides application-level dependencies. Mainly singleton object that can be injected from
 * anywhere in the app.
 */
@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(@NonNull Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }

    @Provides
//    @ApplicationContext
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    Handler provideHandler() {
        return new Handler(Looper.getMainLooper());
    }

    @Provides
    @Singleton
    SharedPreferencesHelper SharedPreferencesHelper() {
        return new SharedPreferencesHelper(application);
    }
}
