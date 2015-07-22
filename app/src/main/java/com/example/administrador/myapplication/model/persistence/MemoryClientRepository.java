package com.example.administrador.myapplication.model.persistence;

import com.example.administrador.myapplication.model.entities.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrador on 21/07/2015.
 */
public class MemoryClientRepository implements ClientRepository {

    private static  MemoryClientRepository singletonInstace;

    private List<Client> clients;

    private MemoryClientRepository(){
        super();
        clients = new ArrayList<Client>();
    }

    public static ClientRepository getInstace(){
        if(singletonInstace == null) {
            singletonInstace = new MemoryClientRepository();
        }
        return  singletonInstace;
    }

    @Override
    public void saveOrUpdate(Client client) {
        clients.add(client);
    }

    @Override
    public List<Client> getAll() {
        return clients;
    }

    @Override
    public void delete(Client client) {
        clients.remove(client);
    }
}
