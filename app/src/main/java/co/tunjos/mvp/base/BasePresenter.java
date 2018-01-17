package co.tunjos.mvp.base;

/**
 * Base class that implements the {@link Presenter} interface and provides a base implementation for
 * {@link Presenter#attachView(MVPView)} and {@link Presenter#detachView()}. It also keeps a reference
 * to any attached view, accessible by calling {@link BasePresenter#getMvpView()}.
 */
public class BasePresenter<T extends MVPView> implements Presenter<T> {
    private T mvpView;

    @Override
    public void attachView(T mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void detachView() {
        mvpView = null;
    }

    public boolean isViewAttached() {
        return mvpView != null;
    }

    public T getMvpView() {
        return mvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MVPViewNotAttachedException();
    }

    public static class MVPViewNotAttachedException extends RuntimeException {
        public MVPViewNotAttachedException() {
            super("Please call Presenter.attachView(MVPView) before" +
                    " making data requests to the Presenter");
        }
    }
}
