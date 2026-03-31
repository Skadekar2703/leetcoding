package com.taxfintech.leetcoding.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LeetCodeDao {

    @Query("SELECT * FROM questions ORDER BY solved_at DESC")
    List<LeetCodeEntry> getAllQuestions();

    @Query("SELECT * FROM questions WHERE id = :questionId LIMIT 1")
    LeetCodeEntry getQuestionById(long questionId);

    @Insert
    void insertQuestion(LeetCodeEntry entry);
}
