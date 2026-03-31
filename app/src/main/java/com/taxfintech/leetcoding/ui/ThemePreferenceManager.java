package com.taxfintech.leetcoding.ui;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;

public final class ThemePreferenceManager {

    private static final String PREFS_NAME = "leetcoding_theme_prefs";
    private static final String KEY_DARK_MODE = "dark_mode";

    private ThemePreferenceManager() {
    }

    public static void applySavedTheme(@NonNull Context context) {
        AppCompatDelegate.setDefaultNightMode(
                isDarkModeEnabled(context)
                        ? AppCompatDelegate.MODE_NIGHT_YES
                        : AppCompatDelegate.MODE_NIGHT_NO
        );
    }

    public static void setDarkModeEnabled(@NonNull Context context, boolean enabled) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(KEY_DARK_MODE, enabled).apply();
        applySavedTheme(context);
    }

    public static boolean isDarkModeEnabled(@NonNull Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(KEY_DARK_MODE, false);
    }
}
