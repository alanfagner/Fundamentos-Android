package com.example.administrador.myapplication.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.administrador.myapplication.R;
import com.example.administrador.myapplication.model.persistence.MemoryClientRepository;

public class MainActivity extends AppCompatActivity {

    private ListView listViewClients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewClients = (ListView) findViewById(R.id.listViewClients);
        listViewClients.setAdapter(new ClientListAdpater(MainActivity.this, MemoryClientRepository.getInstace().getAll()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        listViewClients.getAdapter().getCount();
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

}
