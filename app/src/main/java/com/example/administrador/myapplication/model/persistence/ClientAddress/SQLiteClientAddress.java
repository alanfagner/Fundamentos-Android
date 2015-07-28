package com.example.administrador.myapplication.model.persistence.ClientAddress;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrador.myapplication.model.entities.ClientAddress;
import com.example.administrador.myapplication.model.persistence.DatabaseHelper;
import com.example.administrador.myapplication.util.AppUtil;

import java.util.List;

/**
 * Created by Administrador on 28/07/2015.
 */
public class SQLiteClientAddress implements ClientAdressRepository {

    private static  SQLiteClientAddress singletonInstace;

    public SQLiteClientAddress() {
        super();
    }

    public static SQLiteClientAddress getSingletonInstace(){
        if(singletonInstace == null){
            singletonInstace = new SQLiteClientAddress();
        }
        return  singletonInstace;
    }
    @Override
    public void saveOrUpdate(ClientAddress clientAddress, Boolean isNovo) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        if (isNovo) {
            db.insert(ClientAddressContract.TABLE, null, ClientAddressContract.getContentValues(clientAddress));
        } else {
            String where = ClientAddressContract.ID + " = ?";
            String[] args = {clientAddress.getId().toString()};
            db.update(ClientAddressContract.TABLE, ClientAddressContract.getContentValues(clientAddress), where, args);
        }
        db.close();
        helper.close();
    }

    @Override
    public List<ClientAddress> getAll() {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(ClientAddressContract.TABLE, ClientAddressContract.COLUNS, null, null, null, null, ClientAddressContract.CEP);
        List<ClientAddress> clientAddresses = ClientAddressContract.bindList(cursor);
        db.close();
        helper.close();
        return clientAddresses;
    }

    @Override
    public void delete(ClientAddress clientAddress) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        String where = ClientAddressContract.ID + " = ?";
        String[] args = {clientAddress.getId().toString()};
        db.delete(ClientAddressContract.TABLE, where, args);
        db.close();
        helper.close();
    }

    @Override
    public ClientAddress getByID(Integer ID) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(ClientAddressContract.ID);
        sql.append(" , ");
        sql.append(ClientAddressContract.CEP);
        sql.append(" , ");
        sql.append(ClientAddressContract.TIPODELOGRADURO);
        sql.append(" , ");
        sql.append(ClientAddressContract.LOGRADOURO);
        sql.append(" , ");
        sql.append(ClientAddressContract.ESTADO);
        sql.append(" , ");
        sql.append(ClientAddressContract.BAIRRO);
        sql.append(" , ");
        sql.append(ClientAddressContract.CIDADE);
        sql.append(" FROM ");
        sql.append(ClientAddressContract.TABLE);
        sql.append(" WHERE ");
        sql.append(ClientAddressContract.ID);
        sql.append(" = ? ");

        Cursor cur = db.rawQuery(sql.toString(), new String[] { String.valueOf(ID) });
        List<ClientAddress> clientAddresses = ClientAddressContract.bindList(cur);
        db.close();
        helper.close();
        if(clientAddresses != null && clientAddresses.size() == 1) {
            return clientAddresses.get(0);
        }else
        {
            return null;
        }
    }
}
