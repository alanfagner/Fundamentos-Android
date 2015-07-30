package com.example.administrador.myapplication.controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrador.myapplication.R;
import com.example.administrador.myapplication.model.entities.ClientAddress;
import com.example.administrador.myapplication.model.entities.User;
import com.example.administrador.myapplication.model.persistence.User.SQLiteUser;
import com.example.administrador.myapplication.model.service.CepService;

/**
 * Created by Administrador on 20/07/2015.
 */
public class LoginActivity extends AppCompatActivity {

    Button buttonLogin;
    EditText editTEXTlogin;
    EditText editTEXTpassword;
    Animation fadeIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindLoginButton();
        bindEditText();
        fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new AccelerateInterpolator()); //add this
        fadeIn.setDuration(1000);
    }

    private void bindEditText(){
        editTEXTlogin = (EditText) findViewById(R.id.editTextUserName);
        editTEXTpassword = (EditText) findViewById(R.id.editTextPassword);
    }

    private void bindLoginButton() {
        buttonLogin = (Button) findViewById(R.id.btnLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setLogin(editTEXTlogin.getText().toString());
                user.setPassword(editTEXTpassword.getText().toString());
                if(SQLiteUser.getSingletonInstace().getByUser(user)){
                    Toast.makeText(LoginActivity.this, getString(R.string.msg_login_sucess), Toast.LENGTH_SHORT).show();
                    buttonLogin.startAnimation(AnimationUtils.loadAnimation(LoginActivity.this, R.anim.zoom_out));
                    startActivity(new Intent(LoginActivity.this, ClientListActivity.class));
                }else{
                    buttonLogin.startAnimation(AnimationUtils.loadAnimation(LoginActivity.this, R.anim.zoom_in));
                    Toast.makeText(LoginActivity.this, getString(R.string.msg_login_invalid), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
