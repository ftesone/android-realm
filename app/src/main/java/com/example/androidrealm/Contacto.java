package com.example.androidrealm;

import androidx.annotation.NonNull;

import org.bson.types.ObjectId;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Contacto extends RealmObject {
    @PrimaryKey
    public ObjectId _id;

    @Required
    public String apellido;

    @Required
    public String nombre;

    public Date fechaNacimiento = null;
    public String apodo = null;
    public String empresa = null;
    public Direccion direccion = null;
    public RealmList<Telefono> telefonos = new RealmList<>();
    public RealmList<String> emails = new RealmList<>();

    @NonNull
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(apellido+", "+nombre);

        if (apodo != null) {
            s.append(" '"+apodo+"'");
        }

        if (fechaNacimiento != null) {
            s.append(" ("+(new SimpleDateFormat("dd/MM/yyyy")).format(fechaNacimiento)+")");
        }

        if (empresa != null) {
            s.append(" @"+empresa);
        }

        if (direccion != null) {
            s.append(" Dir: " + direccion.calle +" "+ direccion.nro);

            if (direccion.piso != null || direccion.depto != null) {
                s.append(" ");
                if (direccion.piso != null) {
                    s.append(direccion.piso);
                }
                if (direccion.depto != null) {
                    s.append(direccion.depto);
                }
            }
        }

        for (Telefono telefono : telefonos) {
            s.append("["+telefono.getTipo().name()+": "+telefono.numero+"]");
        }

        for (String email : emails) {
            s.append("<"+email+">");
        }

        return s.toString();
    }
}
