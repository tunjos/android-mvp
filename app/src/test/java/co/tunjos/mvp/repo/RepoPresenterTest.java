package co.tunjos.mvp.repo;

import android.support.annotation.NonNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import co.tunjos.mvp.R;
import co.tunjos.mvp.api.managers.DataManager;
import co.tunjos.mvp.api.model.Repo;
import co.tunjos.mvp.api.model.error.APIError;
import co.tunjos.mvp.util.datafactories.TestDataFactory;
import co.tunjos.mvp.utils.RxImmediateSchedulerRule;
import co.tunjos.mvp.utils.TestSchedulerProvider;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.TestScheduler;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class RepoPresenterTest {

    @Mock private DataManager mockDataManager;
    @Mock private RepoMVPView mockRepoMVPView;

    @Rule public final RxImmediateSchedulerRule rxImmediateSchedulerRule = new RxImmediateSchedulerRule();

    private TestScheduler testScheduler;
    private RepoPresenter repoPresenter;

    @Before
    public void setUp() throws Exception {
        testScheduler = new TestScheduler();
        repoPresenter = new RepoPresenter(mockDataManager, new TestSchedulerProvider(testScheduler), new CompositeDisposable());
        repoPresenter.attachView(mockRepoMVPView);
    }

    @After
    public void tearDown() throws Exception {
        repoPresenter.detachView();
    }

    @Test
    public void getRepoSuccess() throws Exception {
        String owner = TestDataFactory.repoDataFactory.createOwner().login;
        String repoName = TestDataFactory.repoDataFactory.createRepoName();
        Repo repo = TestDataFactory.repoDataFactory.createRepo();
        stubDataManagerGetRepo(owner, repoName, Single.just(Response.success(repo)));

        repoPresenter.getRepo(owner, repoName);
        testScheduler.triggerActions();

        verify(mockRepoMVPView).showMessageView(false);
        verify(mockRepoMVPView).showProgress(true);
        verify(mockRepoMVPView).showProgress(false);
        verify(mockRepoMVPView).showRepo(repo);
    }

    @Test
    public void getRepoFailure() throws Exception {
        String owner = TestDataFactory.repoDataFactory.createOwner().login;
        String repoName = TestDataFactory.repoDataFactory.createRepoName();
        ResponseBody errorbody = ResponseBody.create(null, TestDataFactory.getRandomUUID());
        APIError apiError = TestDataFactory.createApiError();
        stubDataManagerGetRepo(owner, repoName, Single.just(Response.error(404, errorbody)));
        stubDataManagerConvertToError(errorbody, apiError);

        repoPresenter.getRepo(owner, repoName);
        testScheduler.triggerActions();

        verify(mockRepoMVPView).showMessageView(false);
        verify(mockRepoMVPView).showProgress(true);
        verify(mockRepoMVPView).showProgress(false);
        verify(mockRepoMVPView).showMessageView(true);
        verify(mockRepoMVPView).showMessage(apiError.message, true);
    }

    @Test
    public void getRepoError() throws Exception {
        String owner = TestDataFactory.repoDataFactory.createOwner().login;
        String repoName = TestDataFactory.repoDataFactory.createRepoName();
        stubDataManagerGetRepo(owner, repoName, Single.error(new RuntimeException("")));

        repoPresenter.getRepo(owner, repoName);
        testScheduler.triggerActions();

        verify(mockRepoMVPView).showMessageView(false);
        verify(mockRepoMVPView).showProgress(true);
        verify(mockRepoMVPView).showProgress(false);
        verify(mockRepoMVPView).showMessageView(false);
        verify(mockRepoMVPView).showMessage(R.string.err_repo, true);
    }

    private void stubDataManagerGetRepo(@NonNull String owner, @NonNull String repo, @NonNull Single<Response<Repo>> single) {
        when(mockDataManager.getRepo(owner, repo)).thenReturn(single);
    }

    private void stubDataManagerConvertToError(@NonNull ResponseBody errorBody, @NonNull APIError apiError) {
        when(mockDataManager.convertToError(errorBody)).thenReturn(apiError);
    }
}