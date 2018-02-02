package co.tunjos.mvp.repo;

import android.support.annotation.NonNull;

import co.tunjos.mvp.base.Presenter;

/**
 *
 */
public interface RepoPresenter extends Presenter<RepoMVPView> {
    void getRepo(@NonNull String owner, @NonNull String repo);
}