package co.tunjos.mvp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Helper that wraps calls to the {@link SharedPreferences}.
 */
public class SharedPreferencesHelper {
    private final SharedPreferences sharedPreferences;

    public SharedPreferencesHelper(@NonNull Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setString(@NonNull String key, @Nullable String value) {
        sharedPreferences.edit()
                .putString(key, value)
                .apply();
    }

    public String getString(@NonNull String key, @Nullable String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    public void setInt(@NonNull String key, int value) {
        sharedPreferences.edit()
                .putInt(key, value)
                .apply();
    }

    public int getInt(@NonNull String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    public void setBoolean(@NonNull String key, boolean value) {
        sharedPreferences.edit()
                .putBoolean(key, value)
                .apply();
    }

    public boolean getBoolean(@NonNull String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }
}
