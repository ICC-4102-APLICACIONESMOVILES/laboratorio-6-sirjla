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
import android.widget.AdapterView;
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
        getAll(view);
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
                        final FormAdapter adapter = new FormAdapter(getContext(), all_forms);
                        lv.setAdapter(adapter);
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Fragment fragment = new FormAnswer();
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                                Bundle bundle = new Bundle();
                                bundle.putInt("form", adapter.getItem(i).getFormId());
                                fragment.setArguments(bundle);
                            }
                        });
                    }
                });

            }
        }) .start();
    }
}