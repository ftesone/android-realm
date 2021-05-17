package com.example.androidrealm;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private static String[] terminosBusqueda = {"L", "no", "3", "6", "9", "e"};

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreateActivity.class));
            }
        });

        Realm.init(getApplicationContext());

        RealmConfiguration config = new RealmConfiguration.Builder()
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .build()
        ;

        realm = Realm.getInstance(config);

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateContactsList(); // your code
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateContactsList();
    }

    private void updateContactsList()
    {
        String busqueda = null;
        if (0 == ((int) Math.round(Math.random()))) {
            busqueda = terminosBusqueda[(int) Math.round(Math.random() * (terminosBusqueda.length-1))];
        }

        RealmResults<Contacto> contactos = busqueda == null
            ? ContactoDao.getInstance(realm).obtenerTodos()
            : ContactoDao.getInstance(realm).obtenerBusqueda(busqueda)
        ;

        TextView textView = (TextView) findViewById(R.id.busqueda);
        textView.setText(busqueda != null ? "Término de búsqueda: "+busqueda : null);

        ContactoListAdapter adapter = new ContactoListAdapter(contactos);

//        List<Contacto> contactos = busqueda == null ? contactoDao.obtenerTodos() : contactoDao.obtenerPorBusqueda(busqueda);
//
//        TextView textView = (TextView) findViewById(R.id.busqueda);
//        textView.setText(busqueda != null ? "Término de búsqueda: "+busqueda : null);
//
//        ArrayAdapter<Contacto> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactos);
//
        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
