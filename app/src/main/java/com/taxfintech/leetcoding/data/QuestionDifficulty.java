package com.taxfintech.leetcoding.data;

import android.content.Context;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;

import com.taxfintech.leetcoding.R;

public enum QuestionDifficulty {
    EASY(R.string.easy, R.color.difficulty_easy),
    MEDIUM(R.string.medium, R.color.difficulty_medium),
    HARD(R.string.hard, R.color.difficulty_hard);

    private final int labelResId;
    private final int colorResId;

    QuestionDifficulty(int labelResId, int colorResId) {
        this.labelResId = labelResId;
        this.colorResId = colorResId;
    }

    public String getLabel(@NonNull Context context) {
        return context.getString(labelResId);
    }

    @ColorRes
    public int getColorResId() {
        return colorResId;
    }

    @NonNull
    public static QuestionDifficulty fromValue(@NonNull String value) {
        for (QuestionDifficulty difficulty : values()) {
            if (difficulty.name().equalsIgnoreCase(value)) {
                return difficulty;
            }
        }
        return EASY;
    }
}
