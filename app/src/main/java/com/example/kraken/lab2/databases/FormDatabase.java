package com.example.kraken.lab2.databases;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.kraken.lab2.dao_access.FormsDao;
import com.example.kraken.lab2.models.Forms;


@Database(entities = {Forms.class}, version = 2, exportSchema = false)
public abstract class FormDatabase extends RoomDatabase {
    public abstract FormsDao formsDao() ;
}