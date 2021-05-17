package com.example.androidrealm;

import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

public class ContactoDao {
    private static ContactoDao instance = null;
    private Realm realm;

    private ContactoDao(Realm realm) {
        this.realm = realm;
    }

    public static ContactoDao getInstance(Realm realm) {
        if (instance == null) {
            instance = new ContactoDao(realm);
        }

        return instance;
    }

    public RealmResults<Contacto> obtenerTodos() {
        RealmQuery<Contacto> query = realm.where(Contacto.class);

        return query
            .sort(new String[]{"apellido", "nombre"}, new Sort[]{Sort.ASCENDING, Sort.ASCENDING})
            .findAll()
        ;
    }

    public RealmResults<Contacto> obtenerBusqueda(String busqueda) {
        RealmQuery<Contacto> query = realm.where(Contacto.class);

        String busquedaLike = "*"+busqueda+"*";

        return query
            .like("apellido", busquedaLike)
            .or().like("nombre", busquedaLike)
            .or().like("apodo", busquedaLike)
            .or().like("empresa", busquedaLike)
            .or().like("direccion.calle", busquedaLike)
            .or().like("direccion.nro", busquedaLike)
            .or().like("direccion.piso", busquedaLike)
            .or().like("direccion.depto", busquedaLike)
            .or().like("telefonos.numero", busquedaLike)
            // .or().like("emails.string", busquedaLike) UNSUPPORTED
            .findAll()
        ;
    }

    public Contacto insertar(
        String apellido,
        String nombre,
        Date fechaNacimiento,
        String apodo,
        String empresa,
        Direccion direccion,
        List<Telefono> telefonos,
        List<String> emails
    ) {
        AtomicReference<Contacto> contactoReference = new AtomicReference<>();

        realm.executeTransaction(r -> {
            Contacto contacto = r.createObject(Contacto.class, new ObjectId());

            contacto.apellido = apellido;
            contacto.nombre = nombre;

            if (apodo != null) {
                contacto.apodo = apodo;
            }

            if (fechaNacimiento != null) {
                contacto.fechaNacimiento = fechaNacimiento;
            }

            if (empresa != null) {
                contacto.empresa = empresa;
            }

            if (direccion != null) {
                contacto.direccion = direccion;
            }

            for (Telefono telefono : telefonos) {
                contacto.telefonos.add(telefono);
            }

            for (String email : emails) {
                contacto.emails.add(email);
            }

            contactoReference.set(contacto);
        });

        return contactoReference.get();
    }
}
