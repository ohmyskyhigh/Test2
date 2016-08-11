package com.example.miaor.test2.Account.tools;


import android.text.TextUtils;

import java.util.Objects;

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


    public boolean ValidateEmailEmpty(String email){
        boolean valid = true;
        if(TextUtils.isEmpty(email)){
            valid = false;
        }
        return valid;
    }

    public boolean ValidateEmailDomain(String emailDomain){
        boolean valid = false;
        if (emailDomain.equals("soton.ac.uk")){
            valid = true;
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
