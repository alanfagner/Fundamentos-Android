package com.example.administrador.myapplication.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.administrador.myapplication.model.entities.Client;
import com.example.administrador.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Client> clientList = getClients();
        ListView listViewClients = (ListView) findViewById(R.id.listViewClients);

        ClientListAdpater adpterClient = new ClientListAdpater(MainActivity.this,clientList);
        listViewClients.setAdapter(adpterClient);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.actionAdiconarSave:
                Intent mudarDetela = new Intent(MainActivity.this, PersistClientActivity.class);
                startActivity(mudarDetela);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_adicionar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private List<Client>  getClients() {
        List<Client> clientes = new ArrayList<>();

        Client alan = new Client();
        alan.setName("Alan");
        alan.setAge(29);
        clientes.add(alan);

        Client mazao = new Client();
        mazao.setName("mazao");
        mazao.setAge(30);
        clientes.add(mazao);
        new Client();

            return clientes;
    }
}
