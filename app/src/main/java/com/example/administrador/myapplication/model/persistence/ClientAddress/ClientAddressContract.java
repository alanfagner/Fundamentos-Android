package com.example.administrador.myapplication.model.persistence.ClientAddress;

import android.content.ContentValues;
import android.database.Cursor;
import com.example.administrador.myapplication.model.entities.ClientAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrador on 28/07/2015.
 */
public class ClientAddressContract {
    public static final String TABLE = "clientAddress";
    public static final String ID = "id";
    public static final String CEP = "cep";
    public static final String TIPODELOGRADURO = "tipoDeLogradouro";
    public static final String LOGRADOURO = "logradouro";
    public static final String BAIRRO = "bairro";
    public static final String CIDADE = "cidade";
    public static final String ESTADO = "estado";

    public static final String[] COLUNS = {ID, CEP , TIPODELOGRADURO, LOGRADOURO, BAIRRO, CIDADE, ESTADO };

    public static String createTable(){
        final StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER, ");
        sql.append(CEP + " TEXT, ");
        sql.append(TIPODELOGRADURO + " TEXT, ");
        sql.append(LOGRADOURO + " TEXT, ");
        sql.append(BAIRRO + " TEXT, ");
        sql.append(CIDADE + " TEXT, ");
        sql.append(ESTADO + " TEXT ");
        sql.append(" ); ");
        return sql.toString();
    }

    public static ContentValues getContentValues(ClientAddress clientAddress) {
        ContentValues content = new ContentValues();
        content.put(ID, clientAddress.getId());
        content.put(CEP, clientAddress.getCep());
        content.put(TIPODELOGRADURO, clientAddress.getTipoDeLogradouro());
        content.put(LOGRADOURO, clientAddress.getLogradouro());
        content.put(BAIRRO, clientAddress.getBairro());
        content.put(CIDADE, clientAddress.getCidade());
        content.put(ESTADO, clientAddress.getBairro());
        return content;
    }

    public static List<ClientAddress> bindList(Cursor cursor) {
        final List<ClientAddress> clientAddresses = new ArrayList<>();
        while (cursor.moveToNext()) {
            clientAddresses.add(bind(cursor));
        }
        return clientAddresses;
    }

    public static ClientAddress bind(Cursor cursor) {
        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            ClientAddress clientAddress = new ClientAddress();
            clientAddress.setId((cursor.getInt(cursor.getColumnIndex(ID))));
            clientAddress.setCep(cursor.getString(cursor.getColumnIndex(CEP)));
            clientAddress.setTipoDeLogradouro(cursor.getString(cursor.getColumnIndex(TIPODELOGRADURO)));
            clientAddress.setLogradouro(cursor.getString(cursor.getColumnIndex(LOGRADOURO)));
            clientAddress.setBairro(cursor.getString(cursor.getColumnIndex(BAIRRO)));
            clientAddress.setCidade(cursor.getString(cursor.getColumnIndex(CIDADE)));
            clientAddress.setEstado(cursor.getString(cursor.getColumnIndex(ESTADO)));
            return clientAddress;
        }
        return null;
    }

}
