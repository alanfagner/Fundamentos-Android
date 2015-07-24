package com.example.administrador.myapplication.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrador.myapplication.R;
import com.example.administrador.myapplication.components.ClientListAdapter;
import com.example.administrador.myapplication.model.entities.Client;
import com.example.administrador.myapplication.model.persistence.MemoryClientRepository;
import com.example.administrador.myapplication.model.persistence.SQLiteClientRepository;

public class ClientListActivity extends AppCompatActivity {

    private ListView listViewClients;
    private Client editDelClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindClientList();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        this.getMenuInflater().inflate(R.menu.menu_client_list, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionListMenuDelete:
                alertMessage();
                break;

            case R.id.actionListMenuEdit:
                Intent auxIntent = new Intent(ClientListActivity.this, PersistClientActivity.class);
                auxIntent.putExtra(PersistClientActivity.CLIENT_PARAM, (Parcelable) editDelClient);
                startActivity(auxIntent);
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void bindClientList() {
        listViewClients = (ListView) findViewById(R.id.listViewClients);
        listViewClients.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                editDelClient = (Client) parent.getItemAtPosition(position);
                return false;
            }
        });

        super.registerForContextMenu(listViewClients);
    }

    private void refreshListView() {
        listViewClients.setAdapter(new ClientListAdapter(ClientListActivity.this, SQLiteClientRepository.getInstace().getAll()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshListView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionAdiconarSave:
                Intent mudarDetela = new Intent(ClientListActivity.this, PersistClientActivity.class);
                startActivity(mudarDetela);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_adicionar, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void alertMessage() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        editDelClient.deleteBD();
                        Toast.makeText(ClientListActivity.this, getString(R.string.msg_delete_client), Toast.LENGTH_SHORT).show();
                        refreshListView();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.confirm)).setMessage(getString(R.string.msg_valid_delete)).setPositiveButton(getString(R.string.yes), dialogClickListener).setNegativeButton(getString(R.string.no), dialogClickListener).show();
    }


}
