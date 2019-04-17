package com.example.madlabproject;

import android.os.TokenWatcher;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class EditProf extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_prof);
    }
    public void edited(View v) {
        //UPDATE THE DATABASE WITH DETAILS
        Toast.makeText(this, "Successfully edited", Toast.LENGTH_SHORT).show();
    }
}
