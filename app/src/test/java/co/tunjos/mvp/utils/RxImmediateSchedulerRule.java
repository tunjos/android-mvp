package co.tunjos.mvp.utils;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.concurrent.Callable;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;


public class RxImmediateSchedulerRule implements TestRule {

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxJavaPlugins.setInitIoSchedulerHandler(getImmediateSchedulerFunction());
                RxJavaPlugins.setInitComputationSchedulerHandler(getImmediateSchedulerFunction());
                RxJavaPlugins.setInitNewThreadSchedulerHandler(getImmediateSchedulerFunction());
                RxJavaPlugins.setInitSingleSchedulerHandler(getImmediateSchedulerFunction());
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(getImmediateSchedulerFunction());

                try {
                    base.evaluate();
                } finally {
                    RxJavaPlugins.reset();
                    RxAndroidPlugins.reset();
                }
            }
        };
    }

    private Function<Callable<Scheduler>, Scheduler> getImmediateSchedulerFunction() {
        return schedulerCallable -> getImmediateScheduler();
    }

    private Scheduler getImmediateScheduler() {
        return new Scheduler() {
            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };
    }
}