package com.example.androidrealm;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass(embedded = true)
public class Direccion extends RealmObject {
    public String calle;
    public String nro;
    public String piso;
    public String depto;
}
