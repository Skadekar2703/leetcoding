package com.taxfintech.leetcoding.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "questions")
public class LeetCodeEntry {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    @ColumnInfo(name = "question_name")
    private final String questionName;

    @NonNull
    @ColumnInfo(name = "description")
    private final String description;

    @NonNull
    @ColumnInfo(name = "code")
    private final String code;

    @NonNull
    @ColumnInfo(name = "difficulty")
    private final String difficulty;

    @ColumnInfo(name = "solved_at")
    private final long solvedAt;

    public LeetCodeEntry(
            @NonNull String questionName,
            @NonNull String description,
            @NonNull String code,
            @NonNull String difficulty,
            long solvedAt
    ) {
        this.questionName = questionName;
        this.description = description;
        this.code = code;
        this.difficulty = difficulty;
        this.solvedAt = solvedAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getQuestionName() {
        return questionName;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    @NonNull
    public String getCode() {
        return code;
    }

    @NonNull
    public String getDifficulty() {
        return difficulty;
    }

    public long getSolvedAt() {
        return solvedAt;
    }
}
