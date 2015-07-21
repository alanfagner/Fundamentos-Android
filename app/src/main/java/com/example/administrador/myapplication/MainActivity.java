package com.example.administrador.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.util.ArrayList;
import java.util.List;

import static android.R.*;


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
