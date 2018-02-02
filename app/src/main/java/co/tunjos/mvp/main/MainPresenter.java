package co.tunjos.mvp.main;

import android.support.annotation.NonNull;

import co.tunjos.mvp.base.Presenter;

/**
 *
 */
public interface MainPresenter extends Presenter<MainMVPView> {

    void getRepos(@NonNull String username);

    void setFirstRun(boolean value);

    boolean getFirstRun();

    void setLastUsername(@NonNull String username);

    String getLastUsername();
}