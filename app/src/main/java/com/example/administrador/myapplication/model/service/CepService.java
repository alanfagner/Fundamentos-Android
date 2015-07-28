package com.example.administrador.myapplication.model.service;

import com.example.administrador.myapplication.model.entities.ClientAddress;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.example.administrador.myapplication.util.UtilConstante;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;


/**
 * Created by Alan.Gonçalves on 27/07/2015.
 */
public final class CepService {

    private CepService() {
        super();
    }

    public static ClientAddress getAddressBy(String cep) {

        try {
            ClientAddress clienteAndress = new ClientAddress();
            URL url = new URL(UtilConstante.URL + cep);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod(UtilConstante.GET);
            conn.setRequestProperty(UtilConstante.ACCEPT, UtilConstante.APPJSON);

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException(UtilConstante.MSGERROR + conn.getResponseCode());
            }

            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, ClientAddress.class);
            clienteAndress = objectMapper.readValue(conn.getInputStream(), collectionType);
            conn.disconnect();
            return clienteAndress;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
