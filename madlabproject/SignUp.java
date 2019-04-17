package com.example.madlabproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {
    static  String userId="idBuffer";

    EditText email,password,name,age;
    String e,p,n,a;
    FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email= (EditText)findViewById(R.id.emailID);
        password = findViewById(R.id.editText6);
        name= (EditText)findViewById(R.id.editText3);
        age = findViewById(R.id.editText4);

        mAuth = FirebaseAuth.getInstance();
    }



    public void registered(View v)
    {
        e = email.getText().toString();
        n=name.getText().toString();
        a=age.getText().toString();
        p = password.getText().toString();

        if(!e.isEmpty()&&e.contains("@")&&e.contains(".")&&!n.isEmpty()&&!a.isEmpty()&&!p.isEmpty()&&p.length()>4)
        {mAuth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mDatabase = FirebaseDatabase.getInstance("https://yourprojectname.firebaseio.com/");
        DatabaseReference dbref= mDatabase.getReference();
        DatabaseReference myref = dbref.child("users");
        User u=new User(n,e,a);
        userId = dbref.push().getKey();
        myref.child(userId).setValue(u);
        //Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(getApplicationContext(), "Done value added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Intent i=new Intent(this,Login.class);
        startActivity(i);}
        else
        {
            Toast.makeText(getApplicationContext(), "Please enter all details correctly!", Toast.LENGTH_SHORT).show();
        }
    }

}
