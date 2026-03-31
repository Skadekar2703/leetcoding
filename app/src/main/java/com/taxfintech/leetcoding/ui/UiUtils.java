package com.taxfintech.leetcoding.ui;

import android.content.Context;
import android.content.res.ColorStateList;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.taxfintech.leetcoding.data.QuestionDifficulty;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class UiUtils {

    private UiUtils() {
    }

    @NonNull
    public static ColorStateList getDifficultyTint(@NonNull Context context, @NonNull QuestionDifficulty difficulty) {
        return ColorStateList.valueOf(ContextCompat.getColor(context, difficulty.getColorResId()));
    }

    @NonNull
    public static String formatDate(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        return format.format(new Date(timestamp));
    }
}
