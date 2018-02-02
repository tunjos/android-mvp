package co.tunjos.mvp.util.preferences;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Base Interface for Helpers that wraps calls to the {@link SharedPreferences}.
 */
public interface SharedPreferencesHelper {

    void setString(@NonNull String key, @Nullable String value);

    String getString(@NonNull String key, @Nullable String defValue);

    void setInt(@NonNull String key, int value);

    int getInt(@NonNull String key, int defValue);

    void setBoolean(@NonNull String key, boolean value);

    boolean getBoolean(@NonNull String key, boolean defValue);
}
