package optativamoviles.olimpiadas;

import android.content.Intent;
import android.content.IntentFilter;
import android.icu.util.Calendar;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Adaptadores.ListViewInfoCulturales;
import Adaptadores.ListViewInfoPartidos;

import Conexiones.LocalReceiver;
import Conexiones.LocalReciever;
import Entidades.Cultural;
import Entidades.Facultad;
import Entidades.Partido;

public class MostrarInfo extends AppCompatActivity {

    private ExpandableListView  listView;
    private TextView fecha;
    private ArrayList<Partido>  partidos;
    private ArrayList<Cultural> culturales;
    private LocalReciever reciever = new LocalReciever();

    static final String TAG = MostrarInfo.class.getCanonicalName();
    public static final String OPERATION = "OPERATION";
    public static final String CULTURAL_SERVICE = "getculturales";
    public static final String PARTIDO_SERVICE = "getpartidos";

    private LocalReceiver reciver = new LocalReceiver();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.ver_ayer:
                    setTitulo("Ayer");
                    return true;
                case R.id.ver_hoy:
                    setTitulo("Hoy");
                    Log.d("Aux","Preveer");
                    return true;
                case R.id.ver_mañana:
                    setTitulo("Mañana");
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

        LocalBroadcastManager.getInstance(this).registerReceiver(reciever,new IntentFilter(ServiceCaller.RESPONSE_ACTION));
        Intent mServiceIntent = new Intent(MostrarInfo.this, ServiceCaller.class);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(MostrarInfo.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            switch (accion){

                case MenuPrincipal.ID_VERCULTURALES:
                    mServiceIntent.putExtra(OPERATION,CULTURAL_SERVICE);
                    startService(mServiceIntent);
                    this.culturales = getCulturales();
                    this.listView = (ExpandableListView)findViewById(R.id.listView);
                    this.listView.setAdapter(new ListViewInfoCulturales(this,this.culturales));
                    break;

                case MenuPrincipal.ID_VERPARTIDOS:
                   // LocalBroadcastManager.getInstance(this).registerReceiver(reciver, new IntentFilter(ServicioPartido.RESPONSE_ACTION));

                    //Intent mServiceIntent = new Intent(MostrarInfo.this, ServicioPartido.class);
                    //startService(mServiceIntent);

                    //this.partidos = cargarPartidos();
                    mServiceIntent.putExtra(OPERATION,PARTIDO_SERVICE);
                    startService(mServiceIntent);

                    this.listView = (ExpandableListView)findViewById(R.id.listView);
                    this.listView.setAdapter(new ListViewInfoPartidos(this,this.partidos));
                    break;
            }

        } else {
            //this.listView = (ExpandableListView)findViewById(R.id.listView);
            Toast.makeText(MostrarInfo.this,"Conexion no disponible",Toast.LENGTH_SHORT);

        }

    }

    private void setTitulo(String fecha) {
        this.fecha      = (TextView) findViewById(R.id.txtFechaTitulo);
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        switch (fecha){
            case "Ayer":
                cal.add(java.util.Calendar.DATE,-1);
                break;
            case "Hoy":
                cal.add(java.util.Calendar.DATE,0);
                break;
            case "Mañana":
                cal.add(java.util.Calendar.DATE,1);
                break;
        }
        this.fecha.setText(format1.format(cal.getTime()));
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
