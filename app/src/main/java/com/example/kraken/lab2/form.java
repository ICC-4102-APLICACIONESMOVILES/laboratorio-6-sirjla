package com.example.kraken.lab2;


import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.kraken.lab2.databases.FormDatabase;
import com.example.kraken.lab2.models.Forms;

import java.util.ArrayList;
import java.util.List;

public class form extends Fragment {

    private static final String DATABASE_NAME = "forms_db";
    public FormDatabase formDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        formDatabase = Room.databaseBuilder(getActivity().getApplicationContext(),
                FormDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.SubmitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, category, date, comment;

                EditText name_form = (EditText) view.getRootView().findViewById(R.id.name);
                name = name_form.getText().toString();

                EditText date_form = view.getRootView().findViewById(R.id.date);
                date = date_form.getText().toString();

                EditText comment_form = view.getRootView().findViewById(R.id.comment);
                comment = comment_form.getText().toString();

                Spinner category_form = view.getRootView().findViewById(R.id.category_s);
                category = category_form.getSelectedItem().toString();

                insertForm(name, category, comment, date);
            }
        });
    }

    private void insertForm(String fName, String fCategory, String fComment, String fDate){
        final String name = fName;
        final String category = fCategory;
        final String comment = fComment;
        final String date = fDate;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Forms form =new Forms();
                form.setFormName(name);
                form.setFormCategory(category);
                form.setFormComment(comment);
                form.setFormDate(date);
                formDatabase.formsDao().insertOnlySingleForm(form);
            }
        }) .start();
    }
}