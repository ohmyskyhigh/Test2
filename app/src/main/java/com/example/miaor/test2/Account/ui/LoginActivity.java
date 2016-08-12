package com.example.miaor.test2.Account.ui;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.miaor.test2.Account.tools.TaskFailureLogger;
import com.example.miaor.test2.Account.tools.Validator;
import com.example.miaor.test2.App.ProfilePage;
import com.example.miaor.test2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    public FirebaseAuth mAuth;
    public FirebaseAuth.AuthStateListener mAuthStateListener;
    private String email;
    @BindView(R.id.email_login)
    EditText mEmail;
    @BindView(R.id.password_login)
    EditText mPassword;
    @BindView(R.id.Login_login)
    Button mLogin;
    @BindView(R.id.Register_login)
    Button mRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        email = mEmail.getText().toString();
        mAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getEmail());
                    Intent intent = new Intent(LoginActivity.this, ProfilePage.class);
                    startActivity(intent);
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
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }


    public void setLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnFailureListener(new TaskFailureLogger(TAG, "error login"))
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.d(TAG, "Sign in completed");
                        } else {
                            Log.d(TAG, "Sign in failed");
                        }
                    }
                });
    }


    @OnClick(R.id.Login_login)
    void getLogin() {
        String password = mPassword.getText().toString();
        Validator validator = new Validator();
        if (!validator.ValidateEmailEmpty(email)) {
            mEmail.setError("email is empty");
        }
        if (!validator.ValidatePassword(password)) {
            mPassword.setError("password is empty");
        }
        setLogin(email, password);
    }


    @OnClick(R.id.Register_login)
    void register() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        intent.putExtra(getString(R.string.name), email);
        startActivity(intent);
    }
}
