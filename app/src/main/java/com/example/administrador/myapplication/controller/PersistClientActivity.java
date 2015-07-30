package com.example.administrador.myapplication.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
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
        bindComponent();
        bindOnTouchMetod();
        bindEditClient(auxClient);
    }

    private void bindComponent(){
        editName = (EditText) findViewById(R.id.editTextName);
        editName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_edittext_client, 0);
        editAge = (EditText) findViewById(R.id.editTextAge);
        editTel = (EditText) findViewById(R.id.editTextTel);
        editCep = (EditText) findViewById(R.id.editTextCep);
        editTipoLogra = (EditText) findViewById(R.id.editTextTypeLogra);
        editLogra = (EditText) findViewById(R.id.editTextLogra);
        editBairro = (EditText) findViewById(R.id.editTextBairro);
        editCidade = (EditText) findViewById(R.id.editTextCidade);
        editEstado = (EditText) findViewById(R.id.editTextEstado);
        editCep.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_action_notification_sync, 0);
    }

    private void bindOnTouchMetod(){
        editName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editName.getRight() - editName.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        //TODO: Explanation 2:
                        final Intent goToSOContacts = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        goToSOContacts.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                        startActivityForResult(goToSOContacts, 999);
                    }
                }
                return false;
            }
        });
        editCep.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editCep.getRight() - editCep.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (editCep.getText().length() == 8) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                            new GetAddressByCep().execute(editCep.getText().toString());
                        }else{
                            Toast.makeText(PersistClientActivity.this, getString(R.string.msg_cep_valida), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                return false;
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 999) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    final Uri contactUri = data.getData();
                    final String[] projection = {
                            ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    };
                    final Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                    cursor.moveToFirst();

                    editName.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME)));
                    editTel.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));

                    cursor.close();
                } catch (Exception e) {

                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
            auxClient = new Client();
            auxClient.setId(client.getId());
            auxClient.setEndereco(client.getEndereco());
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
                if (FormHelper.requiredValidateEditText(PersistClientActivity.this, editName, editAge, editTel, editCep, editTipoLogra, editLogra, editEstado, editBairro, editCidade)) {
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
