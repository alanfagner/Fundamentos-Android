package com.example.administrador.myapplication.model.persistence.User;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.administrador.myapplication.model.entities.User;
import com.example.administrador.myapplication.model.persistence.DatabaseHelper;
import com.example.administrador.myapplication.util.AppUtil;

import java.util.List;

/**
 * Created by Administrador on 30/07/2015.
 */
public class SQLiteUser implements  UserRepository {

    private static  SQLiteUser singletonInstace;
    public SQLiteUser() {
        super();
    }

    public static SQLiteUser getSingletonInstace(){
        if(singletonInstace == null){
            singletonInstace = new SQLiteUser();
            if(singletonInstace.getAll() == null || singletonInstace.getAll().size() == 0) {
             User user = new User();
                user.setLogin("admin");
                user.setPassword("admin");
                singletonInstace.saveOrUpdate(user);
            };
        }
        return  singletonInstace;
    }

    @Override
    public void saveOrUpdate(User user) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        if (user.getId() == null) {
            db.insert(UserContract.TABLE, null, UserContract.getContentValues(user));
        } else {
            String where = UserContract.ID + " = ?";
            String[] args = {user.getId().toString()};
            db.update(UserContract.TABLE, UserContract.getContentValues(user), where, args);
        }
        db.close();
        helper.close();
    }

    @Override
    public List<User> getAll() {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(UserContract.TABLE, UserContract.COLUNS, null, null, null, null, UserContract.ID);
        List<User> user = UserContract.bindList(cursor);
        db.close();
        helper.close();
        return user;
    }

    @Override
    public void delete(User user) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        String where = UserContract.ID + " = ?";
        String[] args = {user.getId().toString()};
        db.delete(UserContract.TABLE, where, args);
        db.close();
        helper.close();
    }

    @Override
    public User getByID(Integer ID) {
        return null;
    }

    @Override
    public Boolean getByUser(User user) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(UserContract.ID);
        sql.append(" , ");
        sql.append(UserContract.LOGIN);
        sql.append(" , ");
        sql.append(UserContract.PASSWORD);
        sql.append(" FROM ");
        sql.append(UserContract.TABLE);
        sql.append(" WHERE ");
        sql.append(UserContract.LOGIN);
        sql.append(" = ? ");
        sql.append(" AND ");
        sql.append(UserContract.PASSWORD);
        sql.append(" = ?");

        Cursor cur = db.rawQuery(sql.toString(), new String[] { String.valueOf(user.getLogin()),String.valueOf(user.getPassword())});
        User User = UserContract.bind(cur);
        db.close();
        helper.close();
        if(User != null) {
            return true;
        }else
        {
            return false;
        }
    }
}
