package co.tunjos.mvp.base;

import android.support.annotation.NonNull;

import co.tunjos.mvp.api.managers.DataManager;
import co.tunjos.mvp.util.preferences.SharedPreferencesHelper;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Base class that implements the {@link Presenter} interface and provides a base implementation for
 * {@link Presenter#attachView(MVPView)} and {@link Presenter#detachView()}. It also keeps a reference
 * to any attached view, accessible by calling {@link BasePresenter#getMvpView()}.
 */
public class BasePresenter<T extends MVPView> implements Presenter<T> {
    private final DataManager dataManager;
    private final CompositeDisposable compositeDisposable;

    private T mvpView;

    public BasePresenter(@NonNull DataManager dataManager,
                         @NonNull CompositeDisposable compositeDisposable) {
        this.dataManager = dataManager;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void attachView(@NonNull T mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void detachView() {
        getCompositeDisposable().dispose();
        mvpView = null;
    }

    private boolean isViewAttached() {
        return mvpView != null;
    }

    protected T getMvpView() {
        return mvpView;
    }

    protected void checkViewAttached() {
        if (!isViewAttached()) throw new MVPViewNotAttachedException();
    }

    public static class MVPViewNotAttachedException extends RuntimeException {

        MVPViewNotAttachedException() {
            super("Please call Presenter.attachView(MVPView) before" +
                    " making data requests to the Presenter");
        }
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    protected CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }
}