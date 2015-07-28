package com.example.administrador.myapplication.controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrador.myapplication.R;
import com.example.administrador.myapplication.model.entities.ClientAddress;
import com.example.administrador.myapplication.model.service.CepService;

/**
 * Created by Administrador on 20/07/2015.
 */
public class LoginActivity extends AppCompatActivity {

    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindLoginButton();
    }

    private void bindLoginButton() {
        buttonLogin = (Button) findViewById(R.id.btnLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetAddressByCep().execute("14811066");
                startActivity(new Intent(LoginActivity.this, ClientListActivity.class));
            }
        });
    }

    private class GetAddressByCep extends AsyncTask<String, Void, ClientAddress>{

        @Override
        protected ClientAddress doInBackground(String... params) {
            return CepService.getAddressBy(params[0]);
        }

    }
}
