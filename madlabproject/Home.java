package com.example.madlabproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    static ArrayList<String> summaryName = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i= getMenuInflater();
        i.inflate(R.menu.optionsmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed()
    {
        //do nothing
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                Intent i = new Intent(this, Login.class);
                //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    public void editprof(View v)
    {
        Intent i = new Intent(this, EditProf.class);
        startActivity(i);
    }

    public void viewact(View v)
    {
        Intent i = new Intent(this, ViewAct.class);
        startActivity(i);
    }

    public void caloriesConsumed(View v){
        Intent i = new Intent(this, CaloriesConsumed.class);
        startActivity(i);

    }
    public void caloriesLost(View v){
        Intent i = new Intent(this, CaloriesLost.class);
        startActivity(i);
    }

    public void addNewFood(View v){
        Intent i = new Intent(this, AddNewFoodItem.class);
        startActivity(i);
    }
    public void logout(View v){
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }
}
