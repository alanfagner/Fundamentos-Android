package com.example.administrador.myapplication.util;

import android.content.Context;
import android.widget.EditText;

import com.example.administrador.myapplication.R;

/**
 * Created by Administrador on 22/07/2015.
 */
public class FormHelper {

    public static Boolean requiredValidateEditText(Context context,EditText... editTexts) {

        Boolean isValido = true;

        for (EditText editText : editTexts){
            if (editText.getText() == null || editText.getText().toString().trim().length() == 0) {
                editText.setError(context.getString(R.string.fieldInvalid));
                isValido = false;
            }
        }
        return isValido;
    }
}
