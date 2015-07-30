package com.example.administrador.myapplication.model.entities;
import com.example.administrador.myapplication.model.persistence.User.SQLiteUser;
import java.util.List;

/**
 * Created by Administrador on 30/07/2015.
 */
public class User {

    private Integer id;
    private String login;
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void saveOrUpdateBD() {
        Boolean isNovo = false;
        SQLiteUser.getSingletonInstace().saveOrUpdate(this);
    }

    public static List<User> listUser() {
        return SQLiteUser.getSingletonInstace().getAll();
    }

    public void deleteBD() {
        SQLiteUser.getSingletonInstace().delete(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        return !(password != null ? !password.equals(user.password) : user.password != null);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
