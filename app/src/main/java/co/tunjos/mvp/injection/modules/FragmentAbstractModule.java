package co.tunjos.mvp.injection.modules;

import android.support.annotation.NonNull;

import co.tunjos.mvp.repo.RepoMVPPresenter;
import co.tunjos.mvp.repo.RepoPresenter;
import dagger.Binds;
import dagger.Module;


@Module
public abstract class FragmentAbstractModule {

    @Binds
    public abstract RepoPresenter provideRepoPresenter(@NonNull RepoMVPPresenter repoMVPPresenter);
}
