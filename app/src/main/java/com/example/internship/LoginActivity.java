package com.example.internship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    Button login;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText)findViewById(R.id.Email);
        password = (EditText)findViewById(R.id.Password);
        login = (Button)findViewById(R.id.LOGIN);
        auth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });


    }

    private void loginUserAccount() {
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        if(TextUtils.isEmpty(Email)||TextUtils.isEmpty(Password))
        {
            Toast.makeText(LoginActivity.this,"Add all details",Toast.LENGTH_SHORT).show();
            return;
        }
        if(auth!=null)
        {
            auth.signInWithEmailAndPassword(Email, Password)
                    .addOnCompleteListener(
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(
                                        @NonNull Task<AuthResult> task)
                                {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this,
                                                "Login successful!!",
                                                Toast.LENGTH_LONG)
                                                .show();

                                        // hide the progress bar


                                        // if sign-in is successful
                                        // intent to home activity
                                        Intent intent
                                                = new Intent(LoginActivity.this,
                                                SignOut.class);
                                        startActivity(intent);
                                    }

                                    else {

                                        // sign-in failed
                                        Toast.makeText(LoginActivity.this,
                                                "Login failed!!",
                                                Toast.LENGTH_LONG)
                                                .show();

                                        // hide the progress bar

                                    }
                                }
                            });
        }

    }
}