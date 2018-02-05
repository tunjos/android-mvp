package co.tunjos.mvp.api.managers;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.tunjos.mvp.api.GithubService;
import co.tunjos.mvp.api.model.Repo;
import co.tunjos.mvp.api.model.error.APIError;
import co.tunjos.mvp.util.preferences.AppSharedPreferencesHelper;
import co.tunjos.mvp.util.preferences.SharedPreferencesHelper;
import dagger.Lazy;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

@Singleton
public class GithubDataManager implements DataManager {
    private final GithubService githubService;
    private final SharedPreferencesHelper sharedPreferencesHelper;
    @Inject Lazy<Converter<ResponseBody, APIError>> converter;

    @Inject
    public GithubDataManager(@NonNull GithubService githubService, @NonNull SharedPreferencesHelper sharedPreferencesHelper) {
        this.githubService = githubService;
        this.sharedPreferencesHelper = sharedPreferencesHelper;
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
    public void setFirstRun(boolean value) {
        sharedPreferencesHelper.setBoolean(AppSharedPreferencesHelper.PREF_FIRST_RUN, value);
    }

    @Override
    public boolean getFirstRun() {
        return sharedPreferencesHelper.getBoolean(AppSharedPreferencesHelper.PREF_FIRST_RUN, true);
    }

    @Override
    public void setLastUsername(@NonNull String username) {
        sharedPreferencesHelper.setString(AppSharedPreferencesHelper.PREF_LAST_USERNAME, username);
    }

    @Override
    public String getLastUsername() {
        return sharedPreferencesHelper.getString(AppSharedPreferencesHelper.PREF_LAST_USERNAME, GithubService.USERNAME_GITHUB);
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