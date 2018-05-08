package com.example.kraken.lab2.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class FormAnswers {
    @NonNull
    @PrimaryKey (autoGenerate = true)
    private int formAnswerId;

    @ForeignKey(entity = Forms.class, parentColumns = "formId", childColumns = "form")
    private int form;

    @ForeignKey(entity = Questions.class, parentColumns = "questionId", childColumns = "question")
    private int question;

    public FormAnswers() {
    }

    public int getFormAnswerId() {return formAnswerId;}
    public void setFormAnswerId(int formAnswerId) {this.formAnswerId = formAnswerId;}

    public int getQuestion() {return question;}
    public void setQuestion(int question) {this.question = question;}

    public int getForm() {return form;}
    public void setForm(int form) {this.form = form;}
}