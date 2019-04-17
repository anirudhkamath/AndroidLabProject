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


public class CaloriesConsumed extends AppCompatActivity {

    String  servings[]={"1","2","3","4","5","6","7","8","9","10"};
    Spinner s,f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories_consumed);
        s = findViewById(R.id.servings);
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, servings);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adp);

        f=findViewById(R.id.nameOfFood);
        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, AddNewFoodItem.TOTALFOODS);
        adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        f.setAdapter(adp2);

    }

    public void submit(View v)
    {
        //add the name of food + calories to "summary" database child
        String chosenfood = f.getSelectedItem().toString();
        String noOfS = s.getSelectedItem().toString();
        int posofFood=AddNewFoodItem.TOTALFOODS.indexOf(chosenfood);
        String cals=AddNewFoodItem.TOTALCALS.get(posofFood);
        int totalc=0;
        try{
            totalc=Integer.parseInt(cals);
            totalc*=Integer.parseInt(noOfS);
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Enter number only", Toast.LENGTH_SHORT).show();
        }
        cals=Integer.toString(totalc);
        cals = "+"+cals;
        Summary s = new Summary(chosenfood,cals);

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance("https://yourprojectname.firebaseio.com/");
        DatabaseReference dbref= mDatabase.getReference();
        //FirebaseUser cfu= FirebaseAuth.getInstance().getCurrentUser();
        //String uid=cfu.getUid();
        DatabaseReference myref = dbref.child("users").child(SignUp.userId).child("summary");
        String userId = dbref.push().getKey();
        myref.setValue(s);
        Home.summaryName.add(chosenfood+" : "+cals);
        //Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
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
