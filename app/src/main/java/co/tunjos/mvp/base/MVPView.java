package co.tunjos.mvp.base;

import android.support.annotation.StringRes;

/**
 * Base interface that any view in the MVP (Model View Presenter) pattern must implement.
 */
public interface MVPView {

    void showProgress(boolean show);

    void showError(@StringRes int resId);

    void showEmpty();

    void showMessageView(boolean show);
}
