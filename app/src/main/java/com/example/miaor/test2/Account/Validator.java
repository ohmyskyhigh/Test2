package com.example.miaor.test2.Account;


import android.text.TextUtils;

/**
 * created by the one and only, Runkun Miao!!!!!!!!!
 */
public class Validator {

    public boolean ValidateName(String name){
        boolean valid = true;
        if(TextUtils.isEmpty(name)){
            valid = false;
        }
        return valid;
    }


    public boolean ValidateEmailEmpty(String email, String emailDomain){
        boolean valid = true;
        if(TextUtils.isEmpty(email) && TextUtils.isEmpty(emailDomain)){
            valid = false;
        }
        return valid;
    }

    public boolean ValidateEmailDomain(String emailDomain){
        boolean valid = true;
        if (emailDomain != "soton.ac.uk"){
            valid = false;
        }
        return valid;
    }


    public boolean ValidatePassword(String password){
        boolean valid = true;
        if(password.length() < 6){
            valid = false;
        }
        return valid;
    }
}
