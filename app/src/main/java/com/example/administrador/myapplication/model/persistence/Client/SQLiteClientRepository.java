package com.example.administrador.myapplication.model.persistence.Client;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.administrador.myapplication.model.entities.Client;
import com.example.administrador.myapplication.model.persistence.DatabaseHelper;
import com.example.administrador.myapplication.util.AppUtil;

import java.util.List;

/**
 * Created by Administrador on 23/07/2015.
 */
public class SQLiteClientRepository implements ClientRepository {

    private static  SQLiteClientRepository singletonInstace;

    public static SQLiteClientRepository getInstace(){
        if(singletonInstace == null){
            singletonInstace = new SQLiteClientRepository();
        }
        return singletonInstace;
    }

    public SQLiteClientRepository() {
        super();
    }

    @Override
    public void saveOrUpdate(Client client) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        if (client.getId() == null) {
          Long id = db.insert(ClientContract.TABLE, null, ClientContract.getContentValues(client));
            client.setId(id.intValue());
        } else {
            String where = ClientContract.ID + " = ?";
            String[] args = {client.getId().toString()};
            db.update(ClientContract.TABLE, ClientContract.getContentValues(client), where, args);
        }
        db.close();
        helper.close();
    }

    @Override
    public List<Client> getAll() {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(ClientContract.TABLE, ClientContract.COLUNS, null, null, null, null, ClientContract.NAME);
        List<Client> clients = ClientContract.bindList(cursor);
        db.close();
        helper.close();
        return clients;
    }

    @Override
    public void delete(Client client) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        String where = ClientContract.ID + " = ?";
        String[] args = {client.getId().toString()};
        db.delete(ClientContract.TABLE, where, args);
        db.close();
        helper.close();
    }
}
