package co.tunjos.mvp.injection.modules;

import co.tunjos.mvp.main.MainActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;


/**
 * Binding module for {@link MainActivity}
 */
@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = ActivityModule.class)
    abstract MainActivity contributeMainActivityInjector();
}
