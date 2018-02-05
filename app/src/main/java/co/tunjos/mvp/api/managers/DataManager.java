package co.tunjos.mvp.api.managers;

import android.support.annotation.NonNull;

import java.util.List;

import co.tunjos.mvp.api.model.Repo;
import co.tunjos.mvp.api.model.error.APIError;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 *
 */
public interface DataManager {

    Single<Response<List<Repo>>> getRepos(@NonNull String username);

    Single<Response<Repo>> getRepo(@NonNull String owner, @NonNull String repo);

    void setFirstRun(boolean value);

    boolean getFirstRun();

    void setLastUsername(@NonNull String username);

    String getLastUsername();

        /**
         * Convert an error {@link ResponseBody} to an {@link APIError}.
         *
         * @param errorBody The error {@link ResponseBody} to convert.
         * @return The converted {@link APIError}.
         */
    APIError convertToError(@NonNull ResponseBody errorBody);
}
