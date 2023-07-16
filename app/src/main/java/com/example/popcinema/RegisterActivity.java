package com.example.popcinema;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private MaterialButton registerBtn;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private EditText firstName;
    private EditText lastName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseInit();
        addViews();
        addListeners();
    }

    private void addViews() {
        email = findViewById(R.id.register_TXT_email);
        password = findViewById(R.id.register_TXT_password);
        password = findViewById(R.id.register_TXT_password);
        confirmPassword = findViewById(R.id.register_TXT_confirm);
        firstName = findViewById(R.id.register_TXT_first);
        lastName = findViewById(R.id.register_TXT_last);

        registerBtn = findViewById(R.id.register_BTN_register);
        registerBtn.setOnClickListener(e -> {
            String emailStr = email.getText().toString();
            String passwordStr = password.getText().toString();
            String confirmPasswordStr = confirmPassword.getText().toString();
            String firstNameStr = firstName.getText().toString();
            String lastNameStr = lastName.getText().toString();

            if(isValidForm(emailStr, passwordStr, confirmPasswordStr, firstNameStr, lastNameStr)){
                String displayName = firstNameStr.trim() + " " + lastNameStr.trim();
                registerUser(emailStr, passwordStr, displayName);
            }
        });
    }

    private Boolean isValidForm(String email, String password, String confirmPassword, String firstName, String lastName){
        if(email.length() == 0 || password.length() == 0 || confirmPassword.length() == 0 || firstName.length() == 0 || lastName.length() == 0){
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show();
            return false;
        }

        if(!(password.equals(confirmPassword))){
            Toast.makeText(this, "Password field is not match to confirm password", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void addListeners() {

    }

    private void firebaseInit(){
        auth = FirebaseAuth.getInstance();
    }

    private void registerUser(String email, String password, String displayName) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            new UserProfileChangeRequest.Builder()
                            .setDisplayName(displayName)
                            .build();

                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}