package com.example.miaor.test2.Account.ui;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miaor.test2.Account.tools.TaskFailureLogger;
import com.example.miaor.test2.Account.tools.Validator;
import com.example.miaor.test2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @BindView(R.id.email_register)
    EditText mEmail;
    @BindView(R.id.password_register)
    EditText mPassword;
    @BindView(R.id.name_register)
    EditText mName;
    @BindView(R.id.Register_register)
    Button mRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String email = intent.getStringExtra(getString(R.string.name));
        mEmail.setText(email);
        mAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getEmail());

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }


    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthStateListener);
    }


    private void setRegister(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnFailureListener(new TaskFailureLogger(TAG, "Error creating user"))
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Authentication failed",
                                    Toast.LENGTH_SHORT).show();


                        } else {
                            Toast.makeText(RegisterActivity.this, "Authentication accomplished",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void updateUserProfile(String name) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();


        user.updateProfile(profileUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "profile updated");
                        } else {
                            Toast.makeText(RegisterActivity.this, "can't register your name",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    @OnClick(R.id.Register_register)
    void getRegister() {
        String email = mEmail.getText().toString();
        String[] emailParts = email.split("@");
        String emailID = emailParts[0];
        String emailDomain = emailParts[1];
        String password = mPassword.getText().toString();
        String name = mName.getText().toString();

        Validator validator = new Validator();

        if (!validator.ValidateName(name)) {
            mName.setError("Sorry, we need a name");
            return;
        }

        if (!validator.ValidateEmailEmpty(emailID)) {
            Toast.makeText(RegisterActivity.this, "We need an email address", Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (!validator.ValidateEmailDomain(emailDomain)) {
            mEmail.setError("we need a uni email");
            return;
        }

        if (!validator.ValidatePassword(password)) {
            mPassword.setError("minimum of 6 letters");
            return;
        }

        setRegister(email, password);
        updateUserProfile(name);
    }
}
