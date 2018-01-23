package co.tunjos.mvp.injection.modules;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import javax.inject.Singleton;

import co.tunjos.mvp.api.GithubService;
import co.tunjos.mvp.api.GithubServiceFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Provides application-level dependencies. Mainly singleton object that can be injected from
 * anywhere in the app.
 */
@Module
public class ApplicationModule {
    protected final Application application;

    public ApplicationModule(Application application) {
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
    GithubService provideGithubService() {
        return GithubServiceFactory.createGithubService(application);
    }

    @Provides
    @Singleton
    Handler provideHandler() {
        return new Handler(Looper.getMainLooper());
    }
}
