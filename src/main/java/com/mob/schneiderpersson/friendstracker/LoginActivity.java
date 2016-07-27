package com.mob.schneiderpersson.friendstracker;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    ProgressBar pb;
    Button btLogin;
    boolean isInputOk;
    String email, password;

    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        mAuth = FirebaseAuth.getInstance();
    }

    public void initialize(){
        etEmail     = (EditText)findViewById(R.id.etInputEmail);
        etPassword  = (EditText)findViewById(R.id.etInputPassword);
        pb = (ProgressBar) findViewById(R.id.pb);
        btLogin = (Button) findViewById(R.id.btLogin);
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
    }

    public void btLogin_OnClick(View view) {
        initialize();
        checkInput();
        if (isInputOk == false){return;}
        loadAsyncTask();
    }

    public void checkInput(){
        if (email.length() < 1 || password.length() < 1){
           isInputOk = false;
            Toast.makeText(this, "Fields Email and Password cannot be empty", Toast.LENGTH_SHORT).show();
        }else {
            isInputOk = true;
        }
    }

    public void execLogin(){
        mAuth.signInWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("LP", "signInWithEmail:onComplete:" + task.isSuccessful());
                            Intent it = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(it);
                            finish();
                        }else {
                            Log.w("LP", task.getException());
                            Toast.makeText(LoginActivity.this, "Email or password incorrect" , Toast.LENGTH_SHORT).show();
                            pb.setVisibility(View.GONE);
                            btLogin.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    public void tvRegister_OnClick(View view) {
        Intent it;
        it = new Intent(this, RegisterActivity.class);
        startActivity(it);
    }

    public void loadAsyncTask() {

        new AsyncTask<String, Integer, Integer>() {
            @Override
            protected Integer doInBackground(String... params) {

/*                  try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

                execLogin();
                return 1;
            }

            @Override
            protected void onPreExecute() {
                btLogin.setVisibility(View.GONE);
                pb.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onProgressUpdate(Integer... values) {}

            @Override
            protected void onPostExecute(Integer integer) {}

        }.execute();
    }
}
