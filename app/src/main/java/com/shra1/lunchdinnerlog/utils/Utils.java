package com.shra1.lunchdinnerlog.utils;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

public class Utils {
    public static void showToast(Context mCtx, String text) {
        Toast.makeText(mCtx, text, Toast.LENGTH_SHORT).show();
    }

    public static boolean cantBeEmptyET(EditText et) {
        if (et.getText().toString().trim().length() == 0) {
            et.setError("Can't be empty!");
            et.requestFocus();
            return true;
        }
        return false;
    }
}
