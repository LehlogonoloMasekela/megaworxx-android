package com.doosy.megaworxx.util.validations;

import android.content.Context;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {
    public static boolean isNullOrEmpty(String input) {
        return input == null || input.isEmpty();
    }

    /*
    public static boolean isValidUsername(EditText context, TextView errorView, String username) {
        return isValidUsername(context,errorView, username, "^[a-zA-Z0-9._-]{3,20}$");
    }

    public static boolean isValidUsername(EditText editText, TextView errorView, String username, String regex) {
        if (isNullOrEmpty(username)) {
            showToast(context, "Please enter User name first.");
        } else if (!Pattern.matches(regex, username)) {
            showToast(context, "Please enter a valid User name.");
        } else {
            return true;
        }
        return false;
    }
*/
    public static boolean isValidEmail(String email, EditText editText, TextView textView) {
        if (isNullOrEmpty(email)) {
            textView.setText("Email address is required.");
            textView.setVisibility(View.VISIBLE);
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textView.setText("Valid email is required.");
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setText(null);
            textView.setVisibility(View.GONE);
            return true;
        }
        return false;
    }

    public static boolean isValidMobile(String mobile, EditText editText, TextView textView) {
        return isValidMobile(mobile, editText,textView, "^[0-9]{10}$");
    }

    public static boolean isValidMobile(String mobile,EditText editText,TextView textView, String regex) {
        if (isNullOrEmpty(mobile)) {
            textView.setText("Phone number is required.");
            textView.setVisibility(View.VISIBLE);
        } else if (!Pattern.matches(regex, mobile)) {
            textView.setText("Please enter a valid phone number.");
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setText(null);
            textView.setVisibility(View.GONE);
            return true;
        }
        return false;
    }

    public static boolean isValidPassword(String password, EditText editText, TextView textView) {
        if (isNullOrEmpty(password)) {
            textView.setText("Password is required.");
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setText(null);
            textView.setVisibility(View.GONE);
            return true;
        }
        return false;
    }

    public static  boolean isCurrencyValid(String value){
        //String money="23.234,00";
        Pattern p=Pattern.compile("^-?(\\d{1,3}\\s*?([.,]|$|\\s)\\s*?)+?$");
        Matcher m=p.matcher(value);
       return m.matches();
    }

    public static  boolean isDigitOnly(String value){
        //String money="23.234,00";
        Pattern p=Pattern.compile("^[0-9]*$");
        Matcher m=p.matcher(value);
       return m.matches();
    }
}
