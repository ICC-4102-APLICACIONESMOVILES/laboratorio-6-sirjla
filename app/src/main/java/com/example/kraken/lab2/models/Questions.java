package com.example.kraken.lab2.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Questions {
    @NonNull
    @PrimaryKey (autoGenerate = true)
    private int questionId;

    private String content;
    private String type;
    @ForeignKey(entity = Forms.class, parentColumns = "formId", childColumns = "form")
    private int form;

    public Questions() {
    }
    public int getQuestionId() {return questionId;}
    public void setQuestionId(int questionId) {this.questionId = questionId;}

    public String getContent() {return content;}
    public void setContent(String content) {this.content = content;}

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

    public int getForm() {return form;}
    public void setForm(int form) {this.form = form;}
}