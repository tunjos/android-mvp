package co.tunjos.mvp.base;

/**
 * Every presenter in the MVP (Model View Presenter) pattern must implement this interface or extend {@link BasePresenter}
 */
public interface Presenter<V extends MVPView> {

    void attachView(V mvpView);

    void detachView();
}
