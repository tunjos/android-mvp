package co.tunjos.mvp.repo;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import co.tunjos.mvp.R;
import co.tunjos.mvp.api.managers.DataManager;
import co.tunjos.mvp.api.model.Repo;
import co.tunjos.mvp.api.model.error.APIError;
import co.tunjos.mvp.base.BasePresenter;
import co.tunjos.mvp.util.SchedulerProvider;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Presenter for the {@link RepoFragment}.
 */
public class RepoPresenter extends BasePresenter<RepoMVPView> {

    private final SchedulerProvider schedulerProvider;

    @Inject
    public RepoPresenter(@NonNull DataManager dataManager,
                         @NonNull SchedulerProvider schedulerProvider,
                         @NonNull CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);

        this.schedulerProvider = schedulerProvider;
    }

    void getRepo(@NonNull String owner, @NonNull String repo) {
        checkViewAttached();
        getMvpView().showMessageView(false);
        getMvpView().showProgress(true);

        getDataManager().getRepo(owner, repo)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .subscribe(new SingleObserver<Response<Repo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onSuccess(Response<Repo> response) {
                        getMvpView().showProgress(false);

                        if (response.isSuccessful() && response.code() == 200) {
                            final Repo repo = response.body();
                            if (repo != null) {
                                getMvpView().showRepo(repo);
                            } else {
                                getMvpView().showMessageView(true);
                                getMvpView().showEmpty();
                            }
                        } else if (response.code() == 404) {

                            ResponseBody errorBody = response.errorBody();
                            if (errorBody != null) {
                                APIError apiError = getDataManager().convertToError(errorBody);
                                getMvpView().showMessageView(true);
                                getMvpView().showMessage(apiError.message, true);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "Error retrieving repo");
                        getMvpView().showProgress(false);
                        getMvpView().showMessageView(true);
                        getMvpView().showMessage(R.string.err_repo, true);
                    }
                });
    }
}
