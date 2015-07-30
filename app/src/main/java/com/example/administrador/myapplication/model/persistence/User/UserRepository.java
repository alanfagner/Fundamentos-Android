package com.example.administrador.myapplication.model.persistence.User;

import com.example.administrador.myapplication.model.entities.User;

import java.util.List;

/**
 * Created by Administrador on 30/07/2015.
 */
public interface UserRepository {
    public void saveOrUpdate(User user);
    public List<User> getAll();
    public void delete(User user);
    public User getByID(Integer ID);
    public Boolean getByUser(User user);
}
