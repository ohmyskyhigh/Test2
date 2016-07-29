package com.example.miaor.test2.Account;


import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * created by the one and only, Runkun Miao!!!!!!!!!
 */
public class BaseActivity extends AppCompatActivity{


    private ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideProgressDialog();
    }
}
