package co.tunjos.mvp.injection.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.annotation.Annotation;

import javax.inject.Singleton;

import co.tunjos.mvp.BuildConfig;
import co.tunjos.mvp.api.AddHeadersInterceptor;
import co.tunjos.mvp.api.GithubService;
import co.tunjos.mvp.api.model.error.APIError;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class NetworkModule {

    private static final int CACHE_SIZE = 20 * 1024 * 1024; //20MB

    @Provides
    @Singleton
    static GithubService provideGithubService(@NonNull Retrofit retrofit) {
        return retrofit.create(GithubService.class);
    }

    @Provides
    @Singleton
    static Retrofit provideRetrofit(@NonNull OkHttpClient okHttpClient,
                                    @NonNull Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.GITHUB_API_BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    @Singleton
    static OkHttpClient provideOkHttpClient(@NonNull HttpLoggingInterceptor httpLoggingInterceptor,
                                            @NonNull AddHeadersInterceptor addHeadersInterceptor,
                                            @NonNull Cache cache) {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(addHeadersInterceptor)
                .addNetworkInterceptor(httpLoggingInterceptor)
                .cache(cache)
                .build();
    }

    @Provides
    @Singleton
    static Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    @Provides
    @Singleton
    static AddHeadersInterceptor provideAddHeadersInterceptor() {
        return new AddHeadersInterceptor();
    }

    @Provides
    @Singleton
    static HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);
        return httpLoggingInterceptor;
    }

    @Provides
    @Singleton
    static Cache provideCache(@NonNull Context context) {
        return new Cache(context.getCacheDir(), CACHE_SIZE);
    }

    @Provides
    @Singleton
    static Converter<ResponseBody, APIError> provideResponseBodyConverter(@NonNull Retrofit retrofit) {
        return retrofit.responseBodyConverter(APIError.class, new Annotation[0]);
    }
}
