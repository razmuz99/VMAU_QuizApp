package com.example.vmau_quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText repeatPassword;
    private Button register;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email_edittext);
        password = findViewById(R.id.password_edittext);
        repeatPassword = findViewById(R.id.repeat_password_edittext);
        register = findViewById(R.id.register_button);

        auth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();
                String repeatPasswordText = repeatPassword.getText().toString();

                if(TextUtils.isEmpty(emailText) || TextUtils.isEmpty(passwordText) || TextUtils.isEmpty(repeatPasswordText)){
                    Toast.makeText(RegisterActivity.this, "Missing credentials.", Toast.LENGTH_SHORT).show();
                }else if(!passwordText.equals(repeatPasswordText)){
                    Toast.makeText(RegisterActivity.this, "Password mismatch.", Toast.LENGTH_SHORT).show();
                }else if(passwordText.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password too short.", Toast.LENGTH_SHORT).show();
                }else{
                    registerUser(emailText, passwordText);
                }
            }
        });

    }

    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, task -> {
            if(task.isSuccessful()){
                Toast.makeText(RegisterActivity.this, "Registration successful.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(RegisterActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}