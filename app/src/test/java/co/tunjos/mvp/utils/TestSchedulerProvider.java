package co.tunjos.mvp.utils;

import co.tunjos.mvp.util.SchedulerProvider;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;

/**
 * A {@link SchedulerProvider} for testing backed by a {@link TestScheduler} instance.
 */

public class TestSchedulerProvider implements SchedulerProvider {

    private final TestScheduler testScheduler;

    public TestSchedulerProvider(final TestScheduler testScheduler) {
        this.testScheduler = testScheduler;
    }

    @Override
    public Scheduler ui() {
        return testScheduler;
    }

    @Override
    public Scheduler computation() {
        return testScheduler;
    }

    @Override
    public Scheduler io() {
        return testScheduler;
    }
}