package com.example.internship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {
    EditText email, password, name, city, age, gender;
    Button register,login;

    String userid, Email;
    FirebaseAuth auth;
    Users user;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email = (EditText) findViewById(R.id.Email);
        password = (EditText) findViewById(R.id.password);
        name = (EditText) findViewById(R.id.name);
        city = (EditText) findViewById(R.id.City);
        gender = (EditText) findViewById(R.id.Gender);
        age = (EditText) findViewById(R.id.Age);
        register = (Button)findViewById(R.id.submit);
        login=(Button)findViewById(R.id.Login);
        auth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        user = new Users();
        reference=database.getReference("Users");
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailInput = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                final String User = name.getText().toString().trim();
                String Age = age.getText().toString().trim();
                String City = city.getText().toString().trim();
                String Gender = gender.getText().toString().trim();

                if (TextUtils.isEmpty(User)) {
                    Toast.makeText(getApplicationContext(), "Enter username!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(emailInput)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(Age)||TextUtils.isEmpty(City)||TextUtils.isEmpty(Gender))
                {
                    Toast.makeText(SignUpActivity.this,"Add all the data!",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    addDataToFirebase(User,Age,City,Gender);
                }
                if(auth!=null) {
                    auth.createUserWithEmailAndPassword(emailInput, pass)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(SignUpActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();


                                    // If sign-in fails, display a message to the user. If sign-in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(),
                                                Toast.LENGTH_LONG).show();
                                        Log.e("MyTag", task.getException().toString());
                                    } else {

                                        startActivity(new Intent(SignUpActivity.this, SignOut.class));
                                        finish();
                                    }
                                }
                            });
                }

            }


        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            }
        });

    }
    private void addDataToFirebase(String User, String age, String city, String gender) {
        user.setName(User);
        user.setAge(age);
        user.setCity(city);
        user.setGender(gender);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              reference.child(User).setValue(user);
              Toast.makeText(SignUpActivity.this,"Data Added Successfully!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SignUpActivity.this,"Failed to add data!",Toast.LENGTH_SHORT).show();

            }
        });
    }
}

