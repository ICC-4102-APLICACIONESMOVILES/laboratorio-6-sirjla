package com.example.kraken.lab2.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.kraken.lab2.models.Questions;

import java.util.List;

@Dao
public interface QuestionsDao {

    @Insert
    void insertOnlySingleQuestion (Questions question);

    @Insert
    void insertMultipleQuestions (List<Questions> questionsList);

    @Query("SELECT * FROM Questions WHERE questionId = :questionId")
    Questions fetchOneFormbyQuestionId (int questionId);

    @Query("SELECT * FROM Questions")
    List<Questions> fetchAllQuestions();

    @Update
    void updateQuestion (Questions question);

    @Delete
    void deleteQuestion (Questions question);
}