package com.example.miaor.test2.App;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.miaor.test2.Account.ui.LoginActivity;
import com.example.miaor.test2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.OnClick;

public class ProfilePage extends AppCompatActivity {
    private static final String TAG = ProfilePage.class.getSimpleName();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @BindView(R.id.userName_profile)
    EditText mUserName;
    @BindView(R.id.logout_profile)
    Button mLogout;
    @BindView(R.id.submit_profile)
    Button mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        mAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    getUserUpdate();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    Intent intent = new Intent(ProfilePage.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        };
    }


    private void getUserUpdate() {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            String name = user.getDisplayName();
            mUserName.setText(name);
        }
    }


    @OnClick(R.id.logout_profile)
    void logout(){
        mAuth.signOut();
    }
}
