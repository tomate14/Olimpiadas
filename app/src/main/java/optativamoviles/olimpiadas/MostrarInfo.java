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

import Conexiones.LocalReciever;
import Entidades.Cultural;
import Entidades.Facultad;
import Entidades.Partido;

public class MostrarInfo extends AppCompatActivity {

    private ExpandableListView  listView;
    private TextView fecha;


    static final String TAG = MostrarInfo.class.getCanonicalName();
    public static final String OPERATION = "OPERATION";
    public static final String CULTURAL_SERVICE = "getculturales";
    public static final String PARTIDO_SERVICE = "getpartidos";

    private LocalReciever reciever = new LocalReciever(this);

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
                    break;

                case MenuPrincipal.ID_VERPARTIDOS:

                    mServiceIntent.putExtra(OPERATION,PARTIDO_SERVICE);
                    startService(mServiceIntent);
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

    public void getCulturales(ArrayList<Cultural> culturales){
        this.listView = (ExpandableListView)findViewById(R.id.listView);
        this.listView.setAdapter(new ListViewInfoCulturales(this,culturales));

    }
    public void getPartidos(ArrayList<Partido> partidos) {
        this.listView = (ExpandableListView)findViewById(R.id.listView);
        this.listView.setAdapter(new ListViewInfoPartidos(this,partidos));

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
