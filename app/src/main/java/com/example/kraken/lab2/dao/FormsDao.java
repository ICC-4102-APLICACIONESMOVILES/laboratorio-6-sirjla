package com.example.kraken.lab2.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.kraken.lab2.models.Forms;

import java.util.List;

@Dao
public interface FormsDao {

    @Insert
    void insertOnlySingleForm (Forms form);

    @Insert
    void insertMultipleForms (List<Forms> formList);

    @Query("SELECT * FROM Forms WHERE formId = :formId")
    Forms fetchOneFormbyFormId (int formId);

    @Query("SELECT * FROM Forms")
    List<Forms> fetchAllForms();

    @Update
    void updateForm (Forms form);

    @Delete
    void deleteForm (Forms form);

    @Query("DELETE FROM Forms;")
    void deleteAllForms();
}