package com.example.administrador.myapplication.model.persistence.ClientAddress;

import com.example.administrador.myapplication.model.entities.Client;
import com.example.administrador.myapplication.model.entities.ClientAddress;

import java.util.List;

/**
 * Created by Administrador on 28/07/2015.
 */
public interface ClientAdressRepository {

    public void saveOrUpdate(ClientAddress clientAddress, Boolean isNovo);
    public List<ClientAddress> getAll();
    public void delete(ClientAddress clientAddress);
    public ClientAddress getByID(Integer ID);
}
