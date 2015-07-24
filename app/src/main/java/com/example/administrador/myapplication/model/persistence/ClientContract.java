package com.example.administrador.myapplication.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.administrador.myapplication.model.entities.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

/**
 * Created by Administrador on 23/07/2015.
 */
public class ClientContract {

    public static final String TABLE = "cliente";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String AGE = "age";
    public static final String PHONE = "phone";

    public static final String[] COLUNS = {ID, NAME, AGE, PHONE};

    public static String createTable(){
        final StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(NAME + " TEXT, ");
        sql.append(AGE + " INTEGER, ");
        sql.append(PHONE + " TEXT ");
        sql.append(" ); ");
        return sql.toString();
    }

    public static ContentValues getContentValues(Client client) {
        ContentValues content = new ContentValues();
        content.put(ID, client.getId());
        content.put(NAME, client.getName());
        content.put(AGE, client.getAge());
        content.put(PHONE, client.getPhone());
        return content;
    }

    public static List<Client> bindList(Cursor cursor) {
        final List<Client> clients = new ArrayList<>();
        while (cursor.moveToNext()) {
            clients.add(bind(cursor));
        }
        return clients;
    }

    public static Client bind(Cursor cursor) {
        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            Client client = new Client();
            client.setId((cursor.getInt(cursor.getColumnIndex(ID))));
            client.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            client.setAge(cursor.getInt(cursor.getColumnIndex(AGE)));
            client.setPhone(cursor.getString(cursor.getColumnIndex(PHONE)));
            return client;
        }
        return null;
    }
}
