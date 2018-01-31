package co.tunjos.mvp.util;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import co.tunjos.mvp.repo.RepoActivity;

/**
 * Utility class for stating your {@link Intent}s.
 */
public class IntentUtils {

    private IntentUtils() {
    }

    public static void startRepoActivity(@NonNull Context context, @NonNull String repoOwner, @NonNull String repoName) {
        Intent intent = new Intent(context, RepoActivity.class);
        intent.putExtra(RepoActivity.EXTRA_REPO_OWNER, repoOwner);
        intent.putExtra(RepoActivity.EXTRA_REPO_NAME, repoName);
        context.startActivity(intent);
    }
}
