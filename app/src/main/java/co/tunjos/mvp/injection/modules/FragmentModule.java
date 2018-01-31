package co.tunjos.mvp.injection.modules;

import co.tunjos.mvp.util.AppSchedulerProvider;
import co.tunjos.mvp.util.SchedulerProvider;
import dagger.Module;
import dagger.Provides;


@Module
public class FragmentModule {

    @Provides
    SchedulerProvider providesSchedulerProvider() {
        return new AppSchedulerProvider();
    }
}
