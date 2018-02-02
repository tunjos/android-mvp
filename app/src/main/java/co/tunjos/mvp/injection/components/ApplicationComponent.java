package co.tunjos.mvp.injection.components;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import javax.inject.Singleton;

import co.tunjos.mvp.AndroidMVPApplication;
import co.tunjos.mvp.api.GithubService;
import co.tunjos.mvp.api.managers.GithubDataManager;
import co.tunjos.mvp.injection.modules.ActivityBindingModule;
import co.tunjos.mvp.injection.modules.ActivityModule;
import co.tunjos.mvp.injection.modules.ApplicationModule;
import co.tunjos.mvp.injection.modules.FragmentBindingModule;
import co.tunjos.mvp.injection.modules.NetworkModule;
import dagger.Component;
import dagger.android.AndroidInjectionModule;


@Singleton
@Component(modules = {AndroidInjectionModule.class, ApplicationModule.class,
        NetworkModule.class, ActivityBindingModule.class,
        FragmentBindingModule.class, ActivityModule.class})
public interface ApplicationComponent {

    //    @ApplicationContext
    Context context();

    Application application();

    GithubDataManager dataManager();

    GithubService githubService();

    Handler handler();

    void inject(AndroidMVPApplication androidMVPApplication);
}