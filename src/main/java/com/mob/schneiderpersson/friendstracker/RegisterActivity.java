package com.mob.schneiderpersson.friendstracker;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    EditText etEmail, etPassword, etUsername;
    Button btRegister;
    ProgressBar pb;
    String email, password, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void initialize(){
        etEmail     = (EditText)findViewById(R.id.etInputEmail);
        etPassword  = (EditText)findViewById(R.id.etInputPassword);
        etUsername  = (EditText)findViewById(R.id.etInputUsername);
        pb          = (ProgressBar)findViewById(R.id.pb);
        btRegister  = (Button)findViewById(R.id.btRegister);

        email       = etEmail.getText().toString();
        password    = etPassword.getText().toString();
        username    = etUsername.getText().toString();
    }

    public void btRegister_OnClick(View view) {
        initialize();
        loadAsyncTask();
    }

    public void execRegisterUser(){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            String userId = task.getResult().getUser().getUid();
                            saveUserDB(userId);
                            Toast.makeText(RegisterActivity.this, "User created successfully.",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }

    public void saveUserDB(final String userId){
        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("email", email);
        userMap.put("username", username);
        userMap.put("userId", userId);
        mDatabase.child("users").push().setValue(userMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                String userNode = databaseReference.getKey().toString();
                saveUserIndex(userId, userNode);
            }
        });
    }

    public void saveUserIndex(String userId, String userNode){
        DatabaseReference usersIndexRef =FirebaseDatabase.getInstance().getReference().child("usersIndex");

        Map<String, String> userData = new HashMap<>();
        userData.put("userNode", userNode);
        userData.put("username", username);

        usersIndexRef.child(userId).setValue(userData);
    }

    public void loadAsyncTask() {

        new AsyncTask<String, Integer, Integer>() {
            @Override
            protected Integer doInBackground(String... params) {
                execRegisterUser();
                return 1;
            }

            @Override
            protected void onPreExecute() {
                btRegister.setVisibility(View.GONE);
                pb.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onProgressUpdate(Integer... values) {}

            @Override
            protected void onPostExecute(Integer integer) {
            }

        }.execute();
    }
}
