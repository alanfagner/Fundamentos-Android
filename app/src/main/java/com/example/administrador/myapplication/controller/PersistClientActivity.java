package com.example.administrador.myapplication.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrador.myapplication.R;
import com.example.administrador.myapplication.model.entities.Client;
import com.example.administrador.myapplication.util.FormHelper;

/**
 * Created by Administrador on 21/07/2015.
 */
public class PersistClientActivity extends AppCompatActivity {
    public static String CLIENT_PARAM = "CLIENT_PARAM";

    Client auxClient;

    EditText editName;
    EditText editAge;
    EditText editTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persistclient);

        editName = (EditText) findViewById(R.id.editTextName);
        editAge = (EditText) findViewById(R.id.editTextAge);
        editTel = (EditText) findViewById(R.id.editTextTel);

        bindEditClient();
    }

    private void bindEditClient() {
        Bundle extra = getIntent().getExtras();
        if(extra != null){
            auxClient = (Client) extra.getParcelable(CLIENT_PARAM);
        }
        if(auxClient != null) {
            editName.setText(auxClient.getName());
            editAge.setText(auxClient.getAge().toString());
            editTel.setText(auxClient.getPhone());
        }
    }

    private void clearFields() {
        editAge.setText("");
        editName.setText("");
        editTel.setText("");
    }

    private void bindClient() {
        auxClient = new Client();
        auxClient.setName(editName.getText().toString());
        auxClient.setAge(Integer.valueOf(editAge.getText().toString()));
        auxClient.setPhone(editTel.getText().toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionMenuSave:
                if (FormHelper.requiredValidateEditText(PersistClientActivity.this, editName, editAge, editTel)) {
                    bindClient();
                    auxClient.save();
                    clearFields();
                    Toast.makeText(PersistClientActivity.this, getString(R.string.saveClient), Toast.LENGTH_SHORT).show();

                    Intent mudarDetela = new Intent(PersistClientActivity.this, ClientListActivity.class);
                    startActivity(mudarDetela);
                    finish();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_client_persist, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
