package com.example.miaor.test2;


import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;

/**
 * created by the one and only, Runkun Miao!!!!!!!!!
 */
public class TaskFailureLogger implements OnFailureListener {
    private String mTag;
    private String mMessage;
    public TaskFailureLogger(String tag, String s) {
        mTag = tag;
        mMessage = s;
    }


    @Override
    public void onFailure(@NonNull Exception e) {
        Log.wtf(mTag, mMessage, e);
    }
}
