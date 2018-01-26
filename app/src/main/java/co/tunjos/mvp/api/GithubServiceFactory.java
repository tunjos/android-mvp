package co.tunjos.mvp.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    public static GithubService createGithubService(@NonNull Context context) {
        OkHttpClient okHttpClient = createOkHttpClient(createLoggingInterceptor(), createAddHeadersInterceptor(), context);
        return createGithubService(okHttpClient, createGson());
    }

    private static GithubService createGithubService(@NonNull OkHttpClient okHttpClient,
                                                     @NonNull Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.GITHUB_API_BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(GithubService.class);
    }

    private static OkHttpClient createOkHttpClient(@NonNull HttpLoggingInterceptor httpLoggingInterceptor,
                                                   @NonNull AddHeadersInterceptor addHeadersInterceptor,
                                                   @NonNull Context context) {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(addHeadersInterceptor)
                .addNetworkInterceptor(httpLoggingInterceptor)
                .cache(createCache(context))
                .build();
    }

    private static Gson createGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    private static AddHeadersInterceptor createAddHeadersInterceptor() {
        return new AddHeadersInterceptor();
    }

    private static HttpLoggingInterceptor createLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);
        return httpLoggingInterceptor;
    }

    private static Cache createCache(@NonNull Context context) {
        return new Cache(context.getCacheDir(), CACHE_SIZE);
    }
}
