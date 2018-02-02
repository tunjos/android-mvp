package co.tunjos.mvp.injection.modules;

import co.tunjos.mvp.repo.RepoFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;


/**
 * Binding module for {@link RepoFragment}
 */
@Module
public abstract class FragmentBindingModule {

    @ContributesAndroidInjector(modules = {FragmentModule.class, FragmentAbstractModule.class})
    abstract RepoFragment contributeRepoFragmentInjector();
}
