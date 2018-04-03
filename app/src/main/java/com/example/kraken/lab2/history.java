package com.example.kraken.lab2;


import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kraken.lab2.databases.FormDatabase;
import com.example.kraken.lab2.models.Forms;

import java.util.ArrayList;
import java.util.List;


public class history extends Fragment {

    private static final String DATABASE_NAME = "forms_db";
    public FormDatabase formDatabase;

    private List<Forms> all_forms;
    private boolean flag = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        formDatabase = Room.databaseBuilder(getActivity().getApplicationContext(),
                FormDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getAll(view);

    }

    private void getAll(View view){
        final ListView lv = (ListView) view.findViewById(R.id.list);
        new Thread(new Runnable() {
            @Override
            public void run() {
                all_forms = formDatabase.formsDao().fetchAllForms();
                List<String> list = new ArrayList<String>();
                for (int i=0; i<all_forms.size(); i++) {
                    list.add(all_forms.get(i).getFormName());
                }
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                        (getActivity(), R.layout.form_adapter, list);

                Handler mainHandler = new Handler(getActivity().getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        lv.setAdapter(arrayAdapter);
                    }
                });

            }
        }) .start();

    }
}