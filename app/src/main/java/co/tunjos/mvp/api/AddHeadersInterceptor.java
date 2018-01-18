package co.tunjos.mvp.api;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static co.tunjos.mvp.api.GithubService.ACCEPT_HEADER_JSON;

/**
 *
 */

class AddHeadersInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("Accept", ACCEPT_HEADER_JSON)
                .build();

        return chain.proceed(request);
    }
}
