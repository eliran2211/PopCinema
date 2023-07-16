package com.example.popcinema;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MaterialButton submitLogin;
    private MaterialButton registerBtn;
    private EditText emailTxt;
    private EditText passwordTxt;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseInit();
        addViews();
        addListeners();
    }

    private void addViews() {
        submitLogin = findViewById(R.id.login_BTN_submit);
        registerBtn = findViewById(R.id.login_BTN_register);
        emailTxt = findViewById(R.id.login_TXT_email);
        passwordTxt = findViewById(R.id.login_TXT_password);
    }

    private void addListeners() {
        submitLogin.setOnClickListener(e -> {
            String emailStr = emailTxt.getText().toString();
            String passwordStr = passwordTxt.getText().toString();
            loginUser(emailStr, passwordStr);
        });
        registerBtn.setOnClickListener(e -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void firebaseInit(){
       auth = FirebaseAuth.getInstance();
    }

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    currentUser = auth.getCurrentUser();
                    Intent intent = new Intent(getBaseContext(), MovieActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, task.getException().getMessage() + "", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}