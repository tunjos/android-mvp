package co.tunjos.mvp.main;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import co.tunjos.mvp.api.managers.DataManager;
import co.tunjos.mvp.base.BasePresenter;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Presenter for the {@link MainActivity}.
 */
class MainPresenter extends BasePresenter<MainMVPView> {
    private final DataManager dataManager;
    private CompositeDisposable compositeDisposable;

    @Inject
    MainPresenter(@NonNull DataManager dataManager,
                  @NonNull CompositeDisposable compositeDisposable) {
        this.dataManager = dataManager;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void detachView() {
        super.detachView();
        compositeDisposable.clear();
    }

    void getRepos() {
        checkViewAttached();

    }
}
