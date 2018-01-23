package co.tunjos.mvp.injection.components;

import android.app.Application;
import android.content.Context;
import android.os.Handler;


import javax.inject.Singleton;

import co.tunjos.mvp.AndroidMVPApplication;
import co.tunjos.mvp.api.GithubService;
import co.tunjos.mvp.api.managers.DataManager;
import co.tunjos.mvp.injection.modules.ActivityBindingModule;
import co.tunjos.mvp.injection.modules.ActivityModule;
import co.tunjos.mvp.injection.modules.ApplicationModule;
import dagger.Component;


@Singleton
@Component(modules = {ApplicationModule.class, ActivityModule.class, ActivityBindingModule.class})
public interface ApplicationComponent {

//    @ApplicationContext
    Context context();
    Application application();
    DataManager dataManager();
    GithubService githubService();
    Handler handler();

    void inject(AndroidMVPApplication androidMVPApplication);
}