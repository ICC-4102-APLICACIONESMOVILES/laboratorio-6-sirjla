package com.example.kraken.lab2.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.kraken.lab2.models.FormAnswers;

import java.util.List;

@Dao
public interface FormAnswersDao {

    @Insert
    void insertOnlySingleFormAnswer (FormAnswers form);

    @Insert
    void insertMultipleFormAnswers (List<FormAnswers> formAnswerList);

    @Query("SELECT * FROM FormAnswers WHERE formAnswerId = :formAnswerId")
    FormAnswers fetchOneFormAnswerbyFormId (int formAnswerId);

    @Query("SELECT * FROM FormAnswers")
    List<FormAnswers> fetchAllFormAnswers();

    @Update
    void updateFormAnswer (FormAnswers formAnswer);

    @Delete
    void deleteFormAnswer (FormAnswers formAnswer);

    @Query("DELETE FROM FormAnswers;")
    void deleteAllFormAnswers();
}