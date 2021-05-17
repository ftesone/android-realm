package com.example.androidrealm;

import androidx.annotation.NonNull;

import io.realm.RealmModel;
import io.realm.annotations.Ignore;
import io.realm.annotations.RealmClass;

@RealmClass(embedded = true)
public class Telefono implements RealmModel {
    public String numero;

    @Ignore
    private TipoTelefono tipo;

    private String tipoTelefono;

    public TipoTelefono getTipo() {
        if (tipo == null) {
            tipo = TipoTelefono.valueOf(tipoTelefono);
        }

        return tipo;
    }

    public void setTipo(TipoTelefono tipo) {
        this.tipo = tipo;

        tipoTelefono = tipo.name();
    }

    @NonNull
    @Override
    public String toString() {
        return getTipo().toString() + ": "+numero;
    }
}
