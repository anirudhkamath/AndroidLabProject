package com.example.madlabproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    Intent i;
    String n,p;
    EditText email, password;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "MainActivity";
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(getApplicationContext());
        email = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    Log.d(TAG, "Signed in: "+user.getUid());
                    Toast.makeText(getApplicationContext(), "Successfully signed in with "+user.getEmail(), Toast.LENGTH_SHORT).show();
                }
                else{
                    //Log.d(TAG, "Signed out"+user.getUid());
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
       mAuth.addAuthStateListener(mAuthListener);
        //updateUI(currentUser);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener!=null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        mAuth.signOut();
    }

    @Override
    public void onBackPressed()
    {
        //do nothing
    }

    public void login(View v)
    {
        //check validation using database and if valid then :
        n = email.getText().toString();
        p = password.getText().toString();
        if(!n.equals("") || !p.equals("")) {
            mAuth.signInWithEmailAndPassword(n, p)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                i = new Intent(getApplicationContext(), Home.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(), "Not recognized", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please enter all details correctly!", Toast.LENGTH_SHORT).show();
        }
        /*Intent i = new Intent(this, Home.class);
        startActivity(i);*/

    }

    public void signup(View v)
    {
        //open the sign up page
        Intent i = new Intent(this, SignUp.class);
        startActivity(i);
    }
}
