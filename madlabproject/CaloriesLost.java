package com.example.madlabproject;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CaloriesLost extends AppCompatActivity {

    String arr[]={"Running(Ground)", "Running(Treadmill)", "Football", "Cricket", "Weights", "Cross Trainer", "Cycling", "Walking", "Tennis"};
    String c[]={"30","20","15","12","8","10","14","6","12"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories_lost);
        Spinner s = findViewById(R.id.ex);
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adp);
    }


    public void addexercise(View v){
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance("https://yourprojectname.firebaseio.com/");
        DatabaseReference dbref= mDatabase.getReference();
        //FirebaseUser cfu= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference myref = dbref.child("users").child(SignUp.userId).child("summary");
        //Food f=new Food(n,c);
        String exname= ((Spinner) findViewById(R.id.ex)).getSelectedItem().toString();
        int pos = ((Spinner) findViewById(R.id.ex)).getSelectedItemPosition();
        String mins=((EditText) findViewById(R.id.mins)).getText().toString();
        int m=0;
        int p=0;
        try{
            m= Integer.parseInt(mins);
            p=Integer.parseInt(c[pos]);
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Enter number only", Toast.LENGTH_SHORT).show();
        }
        Summary s=new Summary(exname, "-"+m*p);
        //String userId = dbref.push().getKey();
        myref.setValue(s);
        Home.summaryName.add(exname+" : -"+m*p);
        //Home.summaryCals.add("-"+m*p);
        //Home.summList.add(s);
        Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(getApplicationContext(), "Activity added to summary", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
