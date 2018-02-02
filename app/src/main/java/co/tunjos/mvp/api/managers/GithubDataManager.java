package co.tunjos.mvp.api.managers;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.tunjos.mvp.api.GithubService;
import co.tunjos.mvp.api.model.Repo;
import co.tunjos.mvp.api.model.error.APIError;
import dagger.Lazy;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

@Singleton
public class GithubDataManager implements DataManager {
    private final GithubService githubService;
    @Inject Lazy<Converter<ResponseBody, APIError>> converter;

    @Inject
    public GithubDataManager(@NonNull GithubService githubService) {
        this.githubService = githubService;
    }

    @Override
    public Single<Response<List<Repo>>> getRepos(@NonNull String username) {
        return githubService.getRepos(username);
    }

    @Override
    public Single<Response<Repo>> getRepo(@NonNull String owner, @NonNull String repo) {
        return githubService.getRepo(owner, repo);
    }

    @Override
    public APIError convertToError(@NonNull ResponseBody errorBody) {
        APIError error;

        try {
            error = converter.get().convert(errorBody);
        } catch (IOException e) {
            return new APIError();
        }

        return error;
    }
}