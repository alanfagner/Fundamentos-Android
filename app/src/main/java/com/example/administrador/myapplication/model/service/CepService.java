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

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                final ObjectMapper objectMapper = new ObjectMapper();
                clienteAndress = objectMapper.readValue(conn.getInputStream(), ClientAddress.class);
                conn.disconnect();
                return clienteAndress;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
