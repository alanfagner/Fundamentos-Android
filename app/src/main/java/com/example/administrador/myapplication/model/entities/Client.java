package com.example.administrador.myapplication.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.administrador.myapplication.model.persistence.MemoryClientRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrador on 20/07/2015.
 */
public class Client implements  Parcelable{

    public Client(){
        super();
    }

    private String name;
    private Integer age;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (name != null ? !name.equals(client.name) : client.name != null) return false;
        if (age != null ? !age.equals(client.age) : client.age != null) return false;
        return !(phone != null ? !phone.equals(client.phone) : client.phone != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }

    public void save(){
        MemoryClientRepository.getInstace().saveOrUpdate(this);
    }

    public static List<Client>  listaClientes(){
        return MemoryClientRepository.getInstace().getAll();
    }

    public void delete(){
        MemoryClientRepository.getInstace().delete(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name == null ? "" : name);
        dest.writeInt(age == null ? -1: age);
        dest.writeString(phone == null ? "" : phone);
    }

    public Client(Parcel in){
        super();
        readToParcel(in);
    }

    private void readToParcel(Parcel in) {

        name  = in.readString();
        int partialAge = in.readInt();
        age = partialAge == -1 ? null : partialAge;
        phone = in.readString();
    }

    public static final Parcelable.Creator<Client> CREATOR = new
            Parcelable.Creator<Client>() {
                public Client createFromParcel(Parcel in) {
                    return new Client(in);
                }
                public Client[] newArray(int size) {
                    return new Client[size];
                }
            };

}
