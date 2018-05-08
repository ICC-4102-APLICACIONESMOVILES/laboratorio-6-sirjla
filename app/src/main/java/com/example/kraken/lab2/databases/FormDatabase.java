package com.example.kraken.lab2.databases;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.kraken.lab2.dao.AnswersDao;
import com.example.kraken.lab2.dao.FormAnswersDao;
import com.example.kraken.lab2.dao.FormsDao;
import com.example.kraken.lab2.dao.QuestionsDao;
import com.example.kraken.lab2.models.Answers;
import com.example.kraken.lab2.models.FormAnswers;
import com.example.kraken.lab2.models.Forms;
import com.example.kraken.lab2.models.Questions;


@Database(entities = {Forms.class, Answers.class, Questions.class, FormAnswers.class}, version = 5, exportSchema = false)
public abstract class FormDatabase extends RoomDatabase {
    public abstract FormsDao formsDao();
    public abstract QuestionsDao questionsDao();
    public abstract AnswersDao answersDao();
    public abstract FormAnswersDao formAnswersDao();
}