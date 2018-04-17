package com.example.kraken.lab2.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Answers {
    @NonNull
    @PrimaryKey (autoGenerate = true)
    private int answerId;

    private String content;
    @ForeignKey(entity = Questions.class, parentColumns = "questionId", childColumns = "question")
    private int question;
    private String answerSet;

    public Answers() {
    }
    public int getAnswerId() {return answerId;}
    public void setAnswerId(int answerId) {this.answerId = answerId;}

    public String getContent() {return content;}
    public void setContent(String content) {this.content = content;}

    public int getQuestion() {return question;}
    public void setQuestion(int question) {this.question = question;}

    public String getAnswerSet() {return answerSet;}
    public void setAnswerSet(String answerSet) {this.answerSet = answerSet;}
}