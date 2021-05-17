package com.example.androidrealm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class CreateActivity extends AppCompatActivity {
    private static String[] apellidos = {"Sánchez", "Ginóbilli", "Montecchia", "Oberto", "Victoriano", "Fernández", "Sconochini", "Scola", "Gutiérrez", "Nocioni", "Palladino", "Wolkowyski", "Magnano"};
    private static String[] nombres = {"Juan", "Emanuel", "Alejandro", "Fabricio", "Lucas", "Gabriel", "Hugo", "Luis", "Leonardo", "Andrés", "Leandro", "Rubén", "Ignacio"};
    private static String[] apodos = {"Pepe", "Manu", "Chapu", "Leo", "Colo", "Luifa", "Puma"};
    private static String[] empresas = {"Pied Piper", "Aviato", "Raviga", "Nucleus", "Endframe", "SeeFood"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        String apellido = apellidos[random(0, apellidos.length-1)];
        String nombre = nombres[random(0, nombres.length-1)];

        String apodo = null;
        if (0 == random(0,3)) {
            apodo = apodos[random(0, apodos.length-1)];
        }

        Date fechaNacimiento = null;
        if (0 == random(0,3)) {
            fechaNacimiento = getRandomDate();
        }

        String empresa = null;
        if (0 == random(0,3)) {
            empresa = empresas[random(0, empresas.length-1)];
        }

        Direccion direccion = null;
        if (0 == random(0,3)) {
            direccion.calle = String.valueOf(random(1, 72));
            direccion.nro = String.valueOf(random(100, 1000));

            if (0 != random(0,2)) {
                direccion.piso = random(1,15) + "º";
            }

            if (0 != random(0,2)) {
                direccion.depto = String.valueOf((char) random(65,70));
            }
        }

        List<Telefono> telefonos = new ArrayList<>();
        for (int i=0, j=random(0,4) ; i<=j ; i++) {
            Telefono telefono = new Telefono();
            telefono.numero = String.valueOf(random(1000,9999));
            telefono.setTipo(getRandomTipoTelefono());
            telefonos.add(telefono);
        }

        List<String> emails = new ArrayList<>();
        for (int i=0, j=random(0,4) ; i<=j ; i++) {
            emails.add(nombre.toLowerCase().charAt(0) + apellido.toLowerCase() + (i==0 ? "" : i) + "@mail.com");
        }

        Realm.init(getApplicationContext());

        RealmConfiguration config = new RealmConfiguration.Builder()
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .build()
        ;

        Realm realm = Realm.getInstance(config);

        Contacto contacto = ContactoDao.getInstance(realm).insertar(apellido, nombre, fechaNacimiento, apodo, empresa, direccion, telefonos, emails);

        TextView textView = (TextView) findViewById(R.id.contacto);
        textView.setText(contacto.toString());

        ArrayAdapter<Telefono> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacto.telefonos);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        ArrayAdapter<String> emailsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacto.emails);
        ListView listViewEmails = (ListView) findViewById(R.id.emails);
        listViewEmails.setAdapter(emailsAdapter);
    }

    private int random(int start, int end)
    {
        return start + (int) Math.round(Math.random() * (end-start));
    }

    private Date getRandomDate()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, random(1950,2000));
        calendar.set(Calendar.DAY_OF_YEAR, random(1,365));

        return calendar.getTime();
    }

    private TipoTelefono getRandomTipoTelefono()
    {
        TipoTelefono[] values = TipoTelefono.values();

        return values[random(0, values.length-1)];
    }
}
