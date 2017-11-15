package optativamoviles.olimpiadas;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;

import Adaptadores.ListViewInfoCulturales;
import Adaptadores.ListViewInfoPartidos;
import Conexiones.LocalReceiver;
import Entidades.Cultural;
import Entidades.Facultad;
import Entidades.Partido;

public class MostrarInfo extends AppCompatActivity {
    private static final String TAG = MostrarInfo.class.getCanonicalName();

    private ExpandableListView  listView;
    private ArrayList<Partido>  partidos;
    private ArrayList<Cultural> culturales;

    private LocalReceiver reciver = new LocalReceiver();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.ver_ayer:
                    //mTextMessage.setText(R.string.ver_ayer);
                    return true;
                case R.id.ver_hoy:
                    Log.d("Aux","Preveer");
                    //Toast.makeText(MostrarInfo.this,"Preveer",Toast.LENGTH_SHORT);
                    return true;
                case R.id.ver_mañana:
                   // mTextMessage.setText(R.string.ver_mañana);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //Inicio la tab en la segunda pagina
        View view = navigation.findViewById(R.id.ver_hoy);
        view.performClick();
        Intent intent = getIntent();
        int accion = intent.getIntExtra("lista_mostrar",0);

        //Pedir servicio
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(MostrarInfo.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            switch (accion){
                case MenuPrincipal.ID_VERCULTURALES:
                    this.culturales = getCulturales();
                    this.listView = (ExpandableListView)findViewById(R.id.listView);
                    this.listView.setAdapter(new ListViewInfoCulturales(this,this.culturales));
                    break;
                case MenuPrincipal.ID_VERPARTIDOS:
                    LocalBroadcastManager.getInstance(this).registerReceiver(reciver, new IntentFilter(ServicioPartido.RESPONSE_ACTION));

                    Intent mServiceIntent = new Intent(MostrarInfo.this, ServicioPartido.class);
                    //mServiceIntent.putExtra(ITERATION,i);
                    startService(mServiceIntent);

                    this.partidos = cargarPartidos();
                    this.listView = (ExpandableListView)findViewById(R.id.listView);
                    this.listView.setAdapter(new ListViewInfoPartidos(this,this.partidos));
                    break;
            }

        } else {
            //this.listView = (ExpandableListView)findViewById(R.id.listView);
            Toast.makeText(MostrarInfo.this,"Conexion no disponible",Toast.LENGTH_SHORT);
        }

    }

    private ArrayList<Cultural> getCulturales(){
        ArrayList<Cultural> culturales = new ArrayList<Cultural>();

        Facultad f1 = new Facultad();
        f1.setId(1);
        f1.setNombre("Exactas");

        Facultad f2 = new Facultad();
        f2.setId(2);
        f2.setNombre("Economicas");


        Cultural c1 = new Cultural();
        c1.setId(1);
        c1.setFacultad(f1);
        c1.setFecha(java.util.Calendar.getInstance());
        c1.setActividad("Musica");
        c1.setLugar("Campus");
        c1.setPuntos("5");
        culturales.add(c1);

        Cultural c2 = new Cultural();
        c2.setId(2);
        c2.setFacultad(f2);
        c2.setFecha(java.util.Calendar.getInstance());
        c2.setActividad("Musica");
        c2.setLugar("Campus");
        c2.setPuntos("0");
        culturales.add(c2);

        return culturales;
    }
    private ArrayList<Partido> cargarPartidos() {

        ArrayList<Partido> partidos = new ArrayList<Partido>();
        Facultad f1 = new Facultad();
        f1.setId(1);
        f1.setNombre("Exactas");

        Facultad f2 = new Facultad();
        f2.setId(2);
        f2.setNombre("Economicas");

        Partido p1 = new Partido();
        p1.setId(1);
        p1.setFacultad1(f1);
        p1.setFacultad2(f2);
        p1.setFecha(java.util.Calendar.getInstance());
        p1.setDeporte("Futbol");
        p1.setLugar("EDAL");
        p1.setResultado("4-0 zapatero");
        partidos.add(p1);

        Partido p2 = new Partido();
        p2.setId(2);
        p2.setFacultad1(f1);
        p2.setFacultad2(f2);
        p2.setFecha(java.util.Calendar.getInstance());
        p2.setDeporte("Basquet");
        p2.setLugar("CCU");
        p2.setResultado("90-87");
        partidos.add(p2);

        return partidos;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
