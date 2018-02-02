package co.tunjos.mvp.injection.modules;

import android.support.annotation.NonNull;

import co.tunjos.mvp.main.MainMVPPresenter;
import co.tunjos.mvp.main.MainPresenter;
import dagger.Binds;
import dagger.Module;


@Module
public abstract class ActivityAbstractModule {

    @Binds
    public abstract MainPresenter provideMainPresenter(@NonNull MainMVPPresenter mainMVPPresenter);
}
