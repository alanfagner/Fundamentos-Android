package com.example.administrador.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrador on 20/07/2015.
 */
public class ClientListAdpater extends BaseAdapter {

    private List<Client> clientList;
    Activity context;

    public ClientListAdpater(Activity context, List<Client> clientList) {
        this.clientList = clientList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return clientList.size();
    }

    @Override
    public Client getItem(int position) {
        return clientList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final View view = context.getLayoutInflater().inflate(R.layout.client_list_item, parent, false);

        TextView textViewName = (TextView) view.findViewById(R.id.textViewName);
        TextView textViewAge = (TextView) view.findViewById(R.id.textViewAge);

        textViewName.setText(clientList.get(position).getName());
        textViewAge.setText(clientList.get(position).getAge().toString());

        return view;
    }
}
