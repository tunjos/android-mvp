package co.tunjos.mvp.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.webkit.URLUtil;

/**
 * Utility method for network related stuffs.
 */
public class NetworkUtils {

    private NetworkUtils() {
    }

    public static void openUrl(@NonNull Context context, @NonNull String url) {
        if (URLUtil.isValidUrl(url)) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(browserIntent);
        }
    }
}
