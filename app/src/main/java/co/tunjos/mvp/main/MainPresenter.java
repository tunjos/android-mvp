package co.tunjos.mvp.main;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import co.tunjos.mvp.R;
import co.tunjos.mvp.api.managers.DataManager;
import co.tunjos.mvp.api.model.Repo;
import co.tunjos.mvp.base.BasePresenter;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Presenter for the {@link MainActivity}.
 */
class MainPresenter extends BasePresenter<MainMVPView> {

    @Inject
    MainPresenter(@NonNull DataManager dataManager,
                  @NonNull CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    void getRepos(@NonNull String username) {
        checkViewAttached();
        getMvpView().showMessageView(false);
        getMvpView().showProgress(true);

        getDataManager().getRepos(username)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<Repo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onSuccess(List<Repo> repos) {
                        getMvpView().showProgress(false);
                        if (!repos.isEmpty()) {
                            getMvpView().showRepos(repos);

                        } else {
                            getMvpView().showMessageView(true);
                            getMvpView().showEmpty();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "Error retrieving repos");
                        getMvpView().showProgress(false);
                        getMvpView().showMessageView(true);
                        getMvpView().showError(R.string.err_repos);
                    }
                });
    }
}
