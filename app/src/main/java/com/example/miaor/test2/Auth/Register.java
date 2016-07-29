package com.example.miaor.test2.Auth;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miaor.test2.R;
import com.example.miaor.test2.TaskFailureLogger;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Register extends AppCompatActivity {
    private static final String TAG = Register.class.getSimpleName();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @BindView(R.id.email_register)
    EditText mEmail;
    @BindView(R.id.emailDomain_register)
    EditText mEmailDomain;
    @BindView(R.id.password_register)
    EditText mPassword;
    @BindView(R.id.Register_register)
    Button mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
    }

    private void setRegister(String email, String password) {



        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnFailureListener(new TaskFailureLogger(TAG, "Error creating user"))
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(Register.this, "Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Register.this, "Authentication accomplished",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @OnClick(R.id.Register_register)
    void doRegister(){
        String emailID = mEmail.getText().toString();
        String emailDomain = mEmailDomain.getText().toString();
        String email = emailID + "@" + emailDomain;
        String password = mPassword.getText().toString();

        setRegister(email, password);
    }

}
