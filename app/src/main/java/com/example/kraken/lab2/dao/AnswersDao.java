package com.example.kraken.lab2.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.example.kraken.lab2.models.Answers;

import java.util.List;

@Dao
public interface AnswersDao {

    @Insert
    void insertOnlySingleAnswer (Answers answer);

    @Insert
    void insertMultipleAnswers (List<Answers> answersList);

    @Query("SELECT * FROM Answers WHERE answerId = :answerId")
    Answers fetchOneFormbyAnswerId (int answerId);

    @Query("SELECT * FROM Answers")
    List<Answers> fetchAllAnswers();

    @Update
    void updateAnswer (Answers answer);

    @Delete
    void deleteAnswer (Answers answer);
}