package co.tunjos.mvp.util.datafactories;

import java.util.Random;
import java.util.UUID;

import co.tunjos.mvp.api.model.error.APIError;

/**
 *
 */
public class TestDataFactory {

    private static final Random random = new Random();
    public static final RepoDataFactory repoDataFactory = RepoDataFactory.newInstance();

    public static String getRandomUUID() {
        return UUID.randomUUID().toString();
    }

    static int getRandomInt() {
        return random.nextInt();
    }

    public static APIError createApiError() {
        APIError apiError = new APIError();
        apiError.message = getRandomUUID();
        return apiError;
    }
}
