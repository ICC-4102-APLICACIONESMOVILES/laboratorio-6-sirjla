package com.example.kraken.lab2;


import android.Manifest;
import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kraken.lab2.databases.FormDatabase;
import com.example.kraken.lab2.models.Forms;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.List;

public class FormAnswer extends Fragment {

    private static final String FORMS_DATABASE = "forms_db";
    public FormDatabase formDatabase;
    public List<Forms> formsList;
    private HashMap<Integer, Integer> formsHash = new HashMap<Integer, Integer>();
    private int form;
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        formDatabase = Room.databaseBuilder(getActivity().getApplicationContext(),
                FormDatabase.class, FORMS_DATABASE).fallbackToDestructiveMigration().build();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_answer, container, false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout ly = (LinearLayout) view.findViewById(R.id.answer_container);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        getForms(view);
        insertQuestion(view, ly);
        insertQuestion(view, ly);

        Bundle bundle = getArguments();
        if (bundle != null) {
            form = bundle.getInt("form");
        }

        Button submit_button = new Button(getContext());
        submit_button.setText("Submit");
        submit_button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                System.out.println("---------------------------");
                System.out.println(form);

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    System.out.println("LA PUTA MADRE");
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                    return;
                }
                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener((Activity) getContext(), new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    System.out.println("latitude:");
                                    System.out.println(location.getLatitude());
                                    System.out.println("longitude:");
                                    System.out.println(location.getLongitude());
                                }
                                else {
                                    System.out.println("LA PUTA MADRE X2");
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println("Algo Fallo");
                                System.out.println(e.getMessage());
                            }
                        });

            }
        });
        ly.addView(submit_button);

        /*
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
        });*/
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

    private void insertQuestion(View view, LinearLayout ly){
        TextView question = new TextView(view.getContext());
        question.setText("LOREM IPSUM");
        ly.addView(question);
        EditText answer = new EditText(view.getContext());
        ly.addView(answer);
    }

    private void getForms(final View view){
        new Thread(new Runnable() {
            Spinner formSpinner = (Spinner) view.findViewById(R.id.form_spinner);
            @Override
            public void run() {
                formsList = formDatabase.formsDao().fetchAllForms();
                Handler mainHandler = new Handler(getActivity().getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] spinnerArray = new String[formsList.size()];
                        for (int i=0; i<formsList.size(); i++){
                            formsHash.put(i, formsList.get(i).getFormId());
                            spinnerArray[i] = formsList.get(i).getFormName();
                        }
                        ArrayAdapter<String> adapter =new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, spinnerArray);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        formSpinner.setAdapter(adapter);
                    }
                });

            }
        }) .start();
    }

    private int getSelectedForm(Spinner formSpinner){
        String name = formSpinner.getSelectedItem().toString();
        return formsHash.get(formSpinner.getSelectedItemPosition());
    }
}