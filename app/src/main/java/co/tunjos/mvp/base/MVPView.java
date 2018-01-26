package co.tunjos.mvp.base;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

/**
 * Base interface that any view in the MVP (Model View Presenter) pattern must implement.
 */
public interface MVPView {

    void showProgress(boolean show);

    /**
     *
     * @param message The {@link String} message to be displayed.
     * @param error a boolean flag signifying if the {@code message} should be an error message or not.
     */
    void showMessage(@NonNull String message, boolean error);

    /**
     *
     * @param resId The resource id for the string.
     * @param error a boolean flag signifying if the {@code resId} should be an error message or not.
     */
    void showMessage(@StringRes int resId, boolean error);

    void showEmpty();

    void showMessageView(boolean show);
}
