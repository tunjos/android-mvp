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
public class DataManager {
    private final GithubService githubService;
    @Inject Lazy<Converter<ResponseBody, APIError>> converter;

    @Inject
    DataManager(@NonNull GithubService githubService) {
        this.githubService = githubService;
    }

    public Single<Response<List<Repo>>> getRepos(@NonNull String username) {
        return githubService.getRepos(username);
    }

    public Single<Response<Repo>> getRepo(@NonNull String owner, @NonNull String repo) {
        return githubService.getRepo(owner, repo);
    }

    /**
     * @param errorBody The error {@link ResponseBody} to convert.
     * @return The converted {@link APIError}.
     */
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