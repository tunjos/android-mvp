package co.tunjos.mvp.base;

import android.support.annotation.NonNull;

/**
 * Every presenter in the MVP (Model View Presenter) pattern must implement this interface or extend {@link BasePresenter}
 */
interface Presenter<V extends MVPView> {

    void attachView(@NonNull V mvpView);

    void detachView();
}
