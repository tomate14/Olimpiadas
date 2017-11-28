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
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import Adaptadores.ListViewInfoCulturales;
import Adaptadores.ListViewInfoPartidos;
import Conexiones.LocalReciever;
import Entidades.Cultural;
import Entidades.Partido;

public class MostrarInfo extends AppCompatActivity {

    private ExpandableListView listView;
    private int accion;

    static final String TAG = MostrarInfo.class.getCanonicalName();
    public static final String OPERATION = "OPERATION";
    public static final String CULTURAL_SERVICE = "getculturales";
    public static final String PARTIDO_SERVICE = "getpartidos";
    public ArrayList<Cultural> culturales = new ArrayList<>();
    public ArrayList<Partido> partidos = new ArrayList<>();


    private LocalReciever reciever = new LocalReciever(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        //Inicio la tab "HOY" (segunda pagina)
        View view = navigation.findViewById(R.id.ver_hoy);
        view.performClick();
        setTitulo("Hoy");
        Intent intent = getIntent();
        this.accion = intent.getIntExtra("lista_mostrar", 0);

        //Pedir servicio

        LocalBroadcastManager.getInstance(this).registerReceiver(reciever, new IntentFilter(ServiceCaller.RESPONSE_ACTION));
        final Intent mServiceIntent = new Intent(MostrarInfo.this, ServiceCaller.class);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(MostrarInfo.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.ver_ayer:
                            setTitulo("Ayer");
                            mServiceIntent.putExtra("dia", "Ayer");
                            break;
                        case R.id.ver_hoy:
                            setTitulo("Hoy");
                            mServiceIntent.putExtra("dia", "Hoy");
                            break;
                        case R.id.ver_mañana:
                            setTitulo("Mañana");
                            mServiceIntent.putExtra("dia", "Mañana");
                            break;
                        default:
                            return false;
                    }
                    switch (accion) {

                        case MenuPrincipal.ID_VERCULTURALES:
                            mServiceIntent.putExtra(OPERATION, CULTURAL_SERVICE);
                            break;

                        case MenuPrincipal.ID_VERPARTIDOS:
                            mServiceIntent.putExtra(OPERATION, PARTIDO_SERVICE);
                            break;
                    }
                    startService(mServiceIntent);
                    return true;
                }
            });
        //llamado automatico al servicio la primera vez q se crea la actividad
            switch (accion) {
                case MenuPrincipal.ID_VERCULTURALES:
                    mServiceIntent.putExtra(OPERATION, CULTURAL_SERVICE);
                    break;

                case MenuPrincipal.ID_VERPARTIDOS:
                    mServiceIntent.putExtra(OPERATION, PARTIDO_SERVICE);
                    break;
            }
            mServiceIntent.putExtra("dia", "Hoy");
            startService(mServiceIntent);

        } else {
            Toast.makeText(MostrarInfo.this, "Conexion no disponible", Toast.LENGTH_SHORT);
        }
    }

    private void setTitulo(String fecha) {
        java.util.Calendar calendarioTab = java.util.Calendar.getInstance();
        TextView fechaTextView = (TextView) findViewById(R.id.txtFechaTitulo);
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        switch (fecha) {
            case "Ayer":
                calendarioTab.add(java.util.Calendar.DATE, -1);
                break;
            case "Hoy":
                calendarioTab.add(java.util.Calendar.DATE, 0);
                break;
            case "Mañana":
                calendarioTab.add(java.util.Calendar.DATE, 1);
                break;
        }
        fechaTextView.setText(format1.format(calendarioTab.getTime()));
    }


    public void getCulturales(ArrayList<Cultural> culturales,String dia) {
        culturales = filtrarCulturales(culturales,dia);
        this.listView = (ExpandableListView) findViewById(R.id.listView);
        this.listView.setAdapter(new ListViewInfoCulturales(this, culturales));
    }

    public void getPartidos(ArrayList<Partido> partidos, String dia) {
        partidos = filtrarPartidos(partidos, dia);
        this.listView = (ExpandableListView) findViewById(R.id.listView);
        this.listView.setAdapter(new ListViewInfoPartidos(this, partidos));
    }

    private ArrayList<Cultural> filtrarCulturales(ArrayList<Cultural> culturales, String dia) {
        java.util.Calendar calendarTab = java.util.Calendar.getInstance();
        switch (dia) {
            case "Ayer":
                calendarTab.add(java.util.Calendar.DATE, -1);
                break;
            case "Hoy":
                calendarTab.add(java.util.Calendar.DATE, 0);
                break;
            case "Mañana":
                calendarTab.add(java.util.Calendar.DATE, 1);
                break;
        }
        String fechaGeneral = new SimpleDateFormat("dd/MM/yyyy").format(calendarTab.getTime());
        ArrayList<Cultural> filtroCulturales = new ArrayList<Cultural>();
        for (Cultural cultural : culturales) {
            String fecha = new SimpleDateFormat("dd/MM/yyyy").format(cultural.getFecha().getTime());
            if (fechaGeneral.equals(fecha)) {
                filtroCulturales.add(cultural);
            }
        }
        return filtroCulturales;
    }

    private ArrayList<Partido> filtrarPartidos(ArrayList<Partido> partidos, String dia) {
        java.util.Calendar calendarTab = java.util.Calendar.getInstance();
        switch (dia) {
            case "Ayer":
                calendarTab.add(java.util.Calendar.DATE, -1);
                break;
            case "Hoy":
                calendarTab.add(java.util.Calendar.DATE, 0);
                break;
            case "Mañana":
                calendarTab.add(java.util.Calendar.DATE, 1);
                break;
        }
        String fechaGeneral = new SimpleDateFormat("dd/MM/yyyy").format(calendarTab.getTime());
        ArrayList<Partido> filtroPartidos = new ArrayList<Partido>();
        for (Partido partido : partidos) {
            String fecha = new SimpleDateFormat("dd/MM/yyyy").format(partido.getFecha().getTime());
            if (fechaGeneral.equals(fecha)) {
                filtroPartidos.add(partido);
            }
        }
        return filtroPartidos;
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
