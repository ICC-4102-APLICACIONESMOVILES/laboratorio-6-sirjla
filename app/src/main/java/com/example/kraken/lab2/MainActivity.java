package com.example.kraken.lab2;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kraken.lab2.databases.FormDatabase;

import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected String token;
    protected SharedPreferences sharedPref;

    private NetworkManager networkManager;

    private static final String DATABASE_NAME = "forms_db";
    public FormDatabase formDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkManager = NetworkManager.getInstance(this);

        sharedPref = getApplicationContext().getSharedPreferences(
                "com.example.lab2.PREFERENCE_FILE_KEY",
                Context.MODE_PRIVATE

        );

        formDatabase = Room.databaseBuilder(getApplicationContext(),
                FormDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();

        token = sharedPref.getString("token", "");

        if (Objects.equals(token, "")){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            MainActivity.this.startActivity(intent);
            finish();
        }
        networkManager.setToken(token);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        history historyFragment = new history();
        historyFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, historyFragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_form) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            form formFragment = new form();
            formFragment.setArguments(getIntent().getExtras());

            transaction.replace(R.id.fragment_container, formFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_history) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            history historyFragment = new history();
            historyFragment.setArguments(getIntent().getExtras());

            transaction.replace(R.id.fragment_container, historyFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        } else if (id == R.id.nav_summary) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            summary summaryFragment = new summary();
            summaryFragment.setArguments(getIntent().getExtras());

            transaction.replace(R.id.fragment_container, summaryFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        } else if (id == R.id.nav_logout) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.remove("token");
            editor.apply();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    formDatabase.formsDao().deleteAllForms();
                }
            }) .start();

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            MainActivity.this.startActivity(intent);
            finish();
        } else if (id == R.id.nav_answer) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            FormAnswer answerFragment = new FormAnswer();
            answerFragment.setArguments(getIntent().getExtras());

            transaction.replace(R.id.fragment_container, answerFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
