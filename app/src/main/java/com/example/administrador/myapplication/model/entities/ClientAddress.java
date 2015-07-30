package com.example.administrador.myapplication.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.administrador.myapplication.model.persistence.ClientAddress.SQLiteClientAddress;

import java.util.List;

/**
 * Created by Administrador on 27/07/2015.
 */
public class ClientAddress implements Parcelable {

    public ClientAddress() {
        super();
    }
    private Integer id;
    private String cep;
    private String tipoDeLogradouro;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String estado;

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTipoDeLogradouro() {
        return tipoDeLogradouro;
    }

    public void setTipoDeLogradouro(String tipoDeLogradouro) {
        this.tipoDeLogradouro = tipoDeLogradouro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id == null ? -1 : id);
        dest.writeString(cep == null ? "" : cep);
        dest.writeString(tipoDeLogradouro == null ? "" : tipoDeLogradouro);
        dest.writeString(logradouro == null ? "" : logradouro);
        dest.writeString(bairro == null ? "" : bairro);
        dest.writeString(cidade == null ? "" : cidade);
        dest.writeString(estado == null ? "" : estado);
    }

    private void readToParcel(Parcel in) {
        id = in.readInt();
        cep = in.readString();
        tipoDeLogradouro = in.readString();
        logradouro = in.readString();
        bairro = in.readString();
        cidade = in.readString();
        estado = in.readString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientAddress that = (ClientAddress) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (cep != null ? !cep.equals(that.cep) : that.cep != null) return false;
        if (tipoDeLogradouro != null ? !tipoDeLogradouro.equals(that.tipoDeLogradouro) : that.tipoDeLogradouro != null)
            return false;
        if (logradouro != null ? !logradouro.equals(that.logradouro) : that.logradouro != null)
            return false;
        if (bairro != null ? !bairro.equals(that.bairro) : that.bairro != null) return false;
        if (cidade != null ? !cidade.equals(that.cidade) : that.cidade != null) return false;
        return !(estado != null ? !estado.equals(that.estado) : that.estado != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cep != null ? cep.hashCode() : 0);
        result = 31 * result + (tipoDeLogradouro != null ? tipoDeLogradouro.hashCode() : 0);
        result = 31 * result + (logradouro != null ? logradouro.hashCode() : 0);
        result = 31 * result + (bairro != null ? bairro.hashCode() : 0);
        result = 31 * result + (cidade != null ? cidade.hashCode() : 0);
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        return result;
    }


    public void saveOrUpdate(Boolean isNovo){
        SQLiteClientAddress.getSingletonInstace().saveOrUpdate(this, isNovo);
    }
    public static List<ClientAddress> getAllClientAddress(){
        return SQLiteClientAddress.getSingletonInstace().getAll();
    }

    public static ClientAddress getByID(Integer ID){
       return  SQLiteClientAddress.getSingletonInstace().getByID(ID);
    }

    public void delete(){
        SQLiteClientAddress.getSingletonInstace().delete(this);
    }


    public ClientAddress(Parcel in) {
        super();
        readToParcel(in);
    }

    public static final Parcelable.Creator<ClientAddress> CREATOR = new
            Parcelable.Creator<ClientAddress>() {
                public ClientAddress createFromParcel(Parcel in) {
                    return new ClientAddress(in);
                }

                public ClientAddress[] newArray(int size) {
                    return new ClientAddress[size];
                }
            };

}
