package com.example.kraken.lab2.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Forms {
    @NonNull
    @PrimaryKey (autoGenerate = true)
    private int formId;

    private String formName;
    private String formDate;
    private String formCategory;
    private String formComment;

    public Forms() {
    }
    public int getFormId() {return formId;}
    public void setFormId(int formId) {this.formId = formId;}

    public String getFormName() {return formName;}
    public void setFormName(String formName) {this.formName = formName;}

    public String getFormDate() {return formDate;}
    public void setFormDate(String formDate) {this.formDate = formDate;}

    public String getFormCategory() {return formCategory;}
    public void setFormCategory(String formCategory) {this.formCategory = formCategory;}

    public String getFormComment() {return formComment;}
    public void setFormComment(String formComment) {this.formComment = formComment;}

}