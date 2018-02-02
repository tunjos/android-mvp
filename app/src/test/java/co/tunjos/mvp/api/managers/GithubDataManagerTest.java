package co.tunjos.mvp.api.managers;

import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import co.tunjos.mvp.api.GithubService;
import co.tunjos.mvp.api.model.Repo;
import co.tunjos.mvp.util.datafactories.TestDataFactory;
import co.tunjos.mvp.utils.RxImmediateSchedulerRule;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import retrofit2.Response;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GithubDataManagerTest {

    @Mock private GithubService mockGithubService;

    @Rule public final RxImmediateSchedulerRule rxImmediateSchedulerRule = new RxImmediateSchedulerRule();

    private DataManager dataManager;

    @Before
    public void setUp() throws Exception {
        dataManager = new GithubDataManager(mockGithubService);
    }

    @Test
    public void getReposCompletesAndEmits() throws Exception {
        String username = TestDataFactory.repoDataFactory.createUsername();
        List<Repo> repos = TestDataFactory.repoDataFactory.createRepoList(3);
        Response<List<Repo>> response = Response.success(repos);
        stubGithubServiceGetRepos(username, Single.just(response));

        TestObserver<Response<List<Repo>>> testSubscriber = dataManager.getRepos(username).test();

        testSubscriber.assertComplete();
        testSubscriber.assertValue(response);
    }

    @Test
    public void getRepoCompletesAndEmits() throws Exception {
        String owner = TestDataFactory.repoDataFactory.createOwner().login;
        String repoName = TestDataFactory.repoDataFactory.createRepoName();
        Repo repo = TestDataFactory.repoDataFactory.createRepo();
        Response<Repo> response = Response.success(repo);
        stubGithubServiceGetRepo(owner, repoName, Single.just(response));

        TestObserver<Response<Repo>> testSubscriber = dataManager.getRepo(owner, repoName).test();

        testSubscriber.assertComplete();
        testSubscriber.assertValue(response);
    }

    @Test
    public void convertToError() throws Exception {
    }

    private void stubGithubServiceGetRepos(@NonNull String username, @NonNull Single<Response<List<Repo>>> single) {
        when(mockGithubService.getRepos(username)).thenReturn(single);
    }

    private void stubGithubServiceGetRepo(@NonNull String owner, @NonNull String repo, @NonNull Single<Response<Repo>> single) {
        when(mockGithubService.getRepo(owner, repo)).thenReturn(single);
    }
}