package co.tunjos.mvp.util.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Helper that wraps calls to the {@link SharedPreferences}.
 */
public class AppSharedPreferencesHelper implements SharedPreferencesHelper {

    private final SharedPreferences sharedPreferences;

    public static final String PREF_FIRST_RUN = "pref_first_run";
    public static final String PREF_LAST_USERNAME = "pref_last_username";

    public AppSharedPreferencesHelper(@NonNull Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public void setString(@NonNull String key, @Nullable String value) {
        sharedPreferences.edit()
                .putString(key, value)
                .apply();
    }

    @Override
    public String getString(@NonNull String key, @Nullable String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    @Override
    public void setInt(@NonNull String key, int value) {
        sharedPreferences.edit()
                .putInt(key, value)
                .apply();
    }

    @Override
    public int getInt(@NonNull String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    @Override
    public void setBoolean(@NonNull String key, boolean value) {
        sharedPreferences.edit()
                .putBoolean(key, value)
                .apply();
    }

    @Override
    public boolean getBoolean(@NonNull String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }
}