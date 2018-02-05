package co.tunjos.mvp.main;

import android.content.Context;
import android.support.annotation.NonNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import co.tunjos.mvp.R;
import co.tunjos.mvp.api.managers.GithubDataManager;
import co.tunjos.mvp.api.model.Repo;
import co.tunjos.mvp.api.model.error.APIError;
import co.tunjos.mvp.util.datafactories.TestDataFactory;
import co.tunjos.mvp.util.preferences.AppSharedPreferencesHelper;
import co.tunjos.mvp.utils.RxImmediateSchedulerRule;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainMVPPresenterTest {

    @Mock private GithubDataManager mockDataManager;
    @Mock private MainMVPView mockMainMVPView;
    @Mock private Context mockContext;

    @Rule public final RxImmediateSchedulerRule rxImmediateSchedulerRule = new RxImmediateSchedulerRule();

    private MainPresenter mainPresenter;

    @Before
    public void setUp() throws Exception {
        mainPresenter = new MainMVPPresenter(mockDataManager, new AppSharedPreferencesHelper(mockContext), new CompositeDisposable());
        mainPresenter.attachView(mockMainMVPView);
    }

    @After
    public void tearDown() throws Exception {
        mainPresenter.detachView();
    }

    @Test
    public void getReposSuccess() throws Exception {
        String username = TestDataFactory.repoDataFactory.createUsername();
        List<Repo> repos = TestDataFactory.repoDataFactory.createRepoList(3);
        stubDataManagerGetRepos(username, Single.just(Response.success(repos)));

        mainPresenter.getRepos(username);

        verify(mockMainMVPView).showMessageView(false);
        verify(mockMainMVPView).showProgress(true);
        verify(mockMainMVPView).showProgress(false);
        verify(mockMainMVPView).showRepos(repos);
    }

    @Test
    public void getReposEmpty() throws Exception {
        String username = TestDataFactory.repoDataFactory.createUsername();
        List<Repo> repos = TestDataFactory.repoDataFactory.createRepoList(0);
        stubDataManagerGetRepos(username, Single.just(Response.success(repos)));

        mainPresenter.getRepos(username);

        verify(mockMainMVPView).showMessageView(false);
        verify(mockMainMVPView).showProgress(true);
        verify(mockMainMVPView).showProgress(false);
        verify(mockMainMVPView).showMessageView(true);
        verify(mockMainMVPView).showEmpty();
    }

    @Test
    public void getReposFailure() throws Exception {
        String username = TestDataFactory.repoDataFactory.createUsername();
        ResponseBody errorbody = ResponseBody.create(null, TestDataFactory.getRandomUUID());
        APIError apiError = TestDataFactory.createApiError();
        stubDataManagerGetRepos(username, Single.just(Response.error(404, errorbody)));
        stubDataManagerConvertToError(errorbody, apiError);

        mainPresenter.getRepos(username);

        verify(mockMainMVPView).showMessageView(false);
        verify(mockMainMVPView).showProgress(true);
        verify(mockMainMVPView).showProgress(false);
        verify(mockMainMVPView).showMessageView(true);
        verify(mockMainMVPView).showMessage(apiError.message, true);
    }

    @Test
    public void getReposError() throws Exception {
        String username = TestDataFactory.repoDataFactory.createUsername();
        stubDataManagerGetRepos(username, Single.error(new RuntimeException("")));

        mainPresenter.getRepos(username);

        verify(mockMainMVPView).showMessageView(false);
        verify(mockMainMVPView).showProgress(true);
        verify(mockMainMVPView).showProgress(false);
        verify(mockMainMVPView).showMessageView(false);
        verify(mockMainMVPView).showMessage(R.string.err_repos, true);
    }

    private void stubDataManagerGetRepos(@NonNull String username, @NonNull Single<Response<List<Repo>>> single) {
        when(mockDataManager.getRepos(username)).thenReturn(single);
    }

    private void stubDataManagerConvertToError(@NonNull ResponseBody errorBody, @NonNull APIError apiError) {
        when(mockDataManager.convertToError(errorBody)).thenReturn(apiError);
    }
}