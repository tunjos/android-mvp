package co.tunjos.mvp.api.managers;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.tunjos.mvp.api.GithubService;
import co.tunjos.mvp.api.model.Repo;
import io.reactivex.Single;

@Singleton
public class DataManager {
    private final GithubService githubService;

    @Inject
    DataManager(@NonNull GithubService githubService) {
        this.githubService = githubService;
    }

    public Single<List<Repo>> getRepos(@NonNull String username) {
        return githubService.getRepos(username);
    }

    public Single<Repo> getRepo(@NonNull String owner, @NonNull String repo) {
        return githubService.getRepo(owner, repo);
    }
}