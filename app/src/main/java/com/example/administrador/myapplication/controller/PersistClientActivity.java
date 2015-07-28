package com.example.administrador.myapplication.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrador.myapplication.R;
import com.example.administrador.myapplication.model.entities.Client;
import com.example.administrador.myapplication.model.entities.ClientAddress;
import com.example.administrador.myapplication.model.service.CepService;
import com.example.administrador.myapplication.util.FormHelper;

/**
 * Created by Administrador on 21/07/2015.
 */
public class PersistClientActivity extends AppCompatActivity {
    public static String CLIENT_PARAM = "CLIENT_PARAM";
    public static String CLIENT_ADDRESS = "CLIENTADDRESS_PARAM";

    Client auxClient;

    EditText editName;
    EditText editAge;
    EditText editTel;

    EditText editCep;
    EditText editTipoLogra;
    EditText editLogra;

    EditText editBairro;
    EditText editCidade;
    EditText editEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persistclient);

        editName = (EditText) findViewById(R.id.editTextName);
        editAge = (EditText) findViewById(R.id.editTextAge);
        editTel = (EditText) findViewById(R.id.editTextTel);

        editCep = (EditText) findViewById(R.id.editTextCep);
        editTipoLogra = (EditText) findViewById(R.id.editTextTypeLogra);
        editLogra = (EditText) findViewById(R.id.editTextLogra);

        editBairro = (EditText) findViewById(R.id.editTextBairro);
        editCidade = (EditText) findViewById(R.id.editTextCidade);
        editEstado = (EditText) findViewById(R.id.editTextEstado);


        editCep.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (editCep.getText().length() == 8) {
                    new GetAddressByCep().execute(editCep.getText().toString());
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return false;
            }
        });

        bindEditClient(auxClient);
    }

    private class GetAddressByCep extends AsyncTask<String, Void, ClientAddress> {

        ProgressDialog dialogPross;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogPross = new ProgressDialog(PersistClientActivity.this);
            dialogPross.setTitle("Processando");
            dialogPross.setMessage("Aguarde Processando");
            dialogPross.show();
        }

        @Override
        protected ClientAddress doInBackground(String... params) {
            return CepService.getAddressBy(params[0]);
        }

        @Override
        protected void onPostExecute(ClientAddress clientAddress) {
            super.onPostExecute(clientAddress);
            if (clientAddress == null) {
                Toast.makeText(PersistClientActivity.this, getString(R.string.msg_erro_cep), Toast.LENGTH_SHORT).show();
            } else {
                bindEditClientAdress(clientAddress);
            }
            dialogPross.dismiss();
        }
    }

    private void bindEditClientAdress(ClientAddress clientAddress) {
        editCep.setText(clientAddress.getCep());
        editTipoLogra.setText(clientAddress.getTipoDeLogradouro());
        editLogra.setText(clientAddress.getLogradouro());
        editCidade.setText(clientAddress.getCidade());
        editBairro.setText(clientAddress.getBairro());
        editEstado.setText(clientAddress.getEstado());
    }

    private void bindEditClient(Client client) {
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            client = (Client) extra.getParcelable(CLIENT_PARAM);
            client.setEndereco((ClientAddress) extra.getParcelable(CLIENT_ADDRESS));
        }
        if (client != null) {
            editName.setText(client.getName());
            editAge.setText(client.getAge().toString());
            editTel.setText(client.getPhone());
            if (client.getEndereco() != null) {
                bindEditClientAdress(client.getEndereco());
            }
        }
    }

    private void bindClient() {
        if (auxClient == null || auxClient.getId() == null) {
            auxClient = new Client();
        }
        auxClient.setName(editName.getText().toString());
        auxClient.setAge(Integer.valueOf(editAge.getText().toString()));
        auxClient.setPhone(editTel.getText().toString());

        if (auxClient.getEndereco() == null) {
            auxClient.setEndereco(new ClientAddress());
        }
        auxClient.getEndereco().setCep(editCep.getText().toString());
        auxClient.getEndereco().setTipoDeLogradouro(editTipoLogra.getText().toString());
        auxClient.getEndereco().setLogradouro(editLogra.getText().toString());
        auxClient.getEndereco().setBairro(editBairro.getText().toString());
        auxClient.getEndereco().setCidade(editCidade.getText().toString());
        auxClient.getEndereco().setEstado(editEstado.getText().toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionMenuSave:
                if (FormHelper.requiredValidateEditText(PersistClientActivity.this, editName, editAge, editTel)) {
                    bindClient();
                    auxClient.saveOrUpdateBD();
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
