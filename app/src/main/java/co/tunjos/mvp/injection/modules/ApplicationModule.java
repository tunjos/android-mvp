package co.tunjos.mvp.injection.modules;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import co.tunjos.mvp.api.GithubService;
import co.tunjos.mvp.api.managers.DataManager;
import co.tunjos.mvp.api.managers.GithubDataManager;
import co.tunjos.mvp.util.preferences.AppSharedPreferencesHelper;
import co.tunjos.mvp.util.preferences.SharedPreferencesHelper;
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
    SharedPreferencesHelper provideSharedPreferencesHelper() {
        return new AppSharedPreferencesHelper(application);
    }

    @Provides
    @Singleton
    DataManager provideDataManager(@NonNull GithubService githubService, @NonNull SharedPreferencesHelper sharedPreferencesHelper) {
        return new GithubDataManager(githubService, sharedPreferencesHelper);
    }
}
