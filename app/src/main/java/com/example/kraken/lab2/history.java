package com.example.kraken.lab2;


import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.kraken.lab2.databases.FormDatabase;
import com.example.kraken.lab2.models.Forms;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.SyncFailedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class history extends Fragment {

    private static final String DATABASE_NAME = "forms_db";
    public FormDatabase formDatabase;

    private List<Forms> all_forms;
    private boolean flag = true;
    private NetworkManager networkManager;

    private JSONArray formsJson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        networkManager = NetworkManager.getInstance(getActivity().getApplicationContext());

        formDatabase = Room.databaseBuilder(getActivity().getApplicationContext(),
                FormDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        formsJson = null;
        getForms(view);
    }

    private void getAll(View view){
        final ListView lv = (ListView) view.findViewById(R.id.list);
        new Thread(new Runnable() {
            @Override
            public void run() {
                all_forms = formDatabase.formsDao().fetchAllForms();

                Handler mainHandler = new Handler(getActivity().getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        FormAdapter adapter = new FormAdapter(getContext(), all_forms);
                        lv.setAdapter(adapter);
                    }
                });

            }
        }) .start();
    }

    private void getForms(final View view){
        networkManager.getForms(new Response.Listener<JSONObject>() {

            final View v = view;

            @Override
            public void onResponse(JSONObject response) {
                try {
                    formsJson = response.getJSONArray("0");
                    parseForms();
                    getAll(v);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                System.out.println(error);
            }
        });
    }

    private void parseForms() throws JSONException {
        String name;
        String date;
        String category;
        String comment;
        int questions;

        category = "Categoria 1";
        comment = "Comentario";

        final List<Forms> formsList =  new ArrayList<Forms>();

        Forms form;

        for (int i=0; i<formsJson.length(); i++){
            form = new Forms();
            System.out.println("----");
            name = formsJson.getJSONObject(Integer.parseInt(Integer.toString(i))).get("name").toString();
            date = formsJson.getJSONObject(Integer.parseInt(Integer.toString(i))).get("created_at").toString();
            questions = formsJson.getJSONObject(Integer.parseInt(Integer.toString(i))).getJSONArray("fieldsets").length();

            form.setFormName(name);
            form.setFormDate(date);
            form.setFormComment(comment);
            form.setFormCategory(category);
            form.setQuestions(questions);

            formsList.add(form);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
            formDatabase.formsDao().deleteAllForms();
            formDatabase.formsDao().insertMultipleForms(formsList);
            }
        }) .start();

    }
}