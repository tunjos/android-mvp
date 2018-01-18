package co.tunjos.mvp.api;

import com.google.gson.Gson;

import co.tunjos.mvp.AndroidMVPApplication;
import co.tunjos.mvp.BuildConfig;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Factory that creates an implementation of the API endpoints defined by {@link GithubService}.
 * It takes care of creating necessary dependencies.
 */
public class GithubServiceFactory {

    private static final int CACHE_SIZE = 20 * 1024 * 1024; //20MB

    public static GithubService createGithubService() {
        OkHttpClient okHttpClient = createOkHttpClient(createLoggingInterceptor());
        return createGithubService(okHttpClient);
    }

    private static GithubService createGithubService(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.GITHUB_API_BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        return retrofit.create(GithubService.class);
    }

    private static OkHttpClient createOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(httpLoggingInterceptor)
                .cache(createCache())
                .build();
    }

    private static HttpLoggingInterceptor createLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);
        return logging;
    }

    private static Cache createCache() {
        return new Cache(AndroidMVPApplication.getAppContext().getCacheDir(), CACHE_SIZE);
    }
}
