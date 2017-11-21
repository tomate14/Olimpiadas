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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private java.util.Calendar calendario = java.util.Calendar.getInstance();
    private int accion;


    static final String TAG = MostrarInfo.class.getCanonicalName();
    public static final String OPERATION = "OPERATION";
    public static final String CULTURAL_SERVICE = "getculturales";
    public static final String PARTIDO_SERVICE = "getpartidos";
    public ArrayList<Cultural> culturales = new ArrayList<>();
    public ArrayList<Partido> partidos = new ArrayList<>();


    private LocalReciever reciever = new LocalReciever(this);

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        /*Al seleccionar el tab, es el filtro que se va a aplicar */
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
            switch (accion){
                case MenuPrincipal.ID_VERCULTURALES:
                    ArrayList<Cultural> filtrocult = filtrarCulturales();
                    getCulturales(filtrocult);
                    break;
                case MenuPrincipal.ID_VERPARTIDOS:
                    ArrayList<Partido> filtropart = filtrarPartidos();
                    getPartidos(filtrarPartidos());
                    break;
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
        this.accion = intent.getIntExtra("lista_mostrar",0);
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
        switch (fecha){
            case "Ayer":
                calendario.add(java.util.Calendar.DATE,-1);
                break;
            case "Hoy":
                calendario.add(java.util.Calendar.DATE,0);
                break;
            case "Mañana":
                calendario.add(java.util.Calendar.DATE,1);
                break;
        }
        this.fecha.setText(format1.format(calendario.getTime()));
    }

    /**TESTING SIN LLAMADO A CONSUMIR SERVICIO*/

   /* private void testParser(java.util.Calendar cal){
        try {
            JSONArray jsonArray = new JSONArray("[{\"id\":1,\"facultad\":{\"id\":1,\"nombre\":\"Exactas\"},\"lugar\":\"Campus\",\"fecha\":1510667709531,\"puntos\":\"5\",\"actividad\":\"Musica\"},{\"id\":2,\"facultad\":{\"id\":2,\"nombre\":\"Economicas\"},\"lugar\":\"Campus\",\"fecha\":1510667709531,\"puntos\":\"0\",\"actividad\":\"Musica\"}]");
            ArrayList<Cultural> culturales = new ArrayList<>();

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject json = jsonArray.getJSONObject(i);
                //if(json.getString("fecha").equals(cal.getTime().toString()))
                Date fecha = new Date(json.getLong("fecha"));
                Log.d(TAG+" Fecha Calendar = ",cal.getTime().toString());
                Log.d(TAG+" fecha JSON = ",fecha.toString());

                if(cal.getTime().toString().equals(fecha.toString())){
                    Cultural c1 = new Cultural();
                    //setFacultad al Cultural
                    JSONObject jsonFacultad = new JSONObject(json.getString("facultad"));
                    Facultad f1 = new Facultad();
                    f1.setId(jsonFacultad.getInt("id"));
                    f1.setNombre(jsonFacultad.getString("nombre"));
                    c1.setFacultad(f1);

                    //setFecha al Cultural
                    // java.util.Calendar cal = java.util.Calendar.getInstance();
                    //SimpleDateFormat sdf = new SimpleDateFormat();
                    //cal.setTime(sdf.parse(json.getString("fecha")));
                    //c1.setFecha(cal);

                    c1.setLugar(json.getString("lugar"));
                    c1.setActividad(json.getString("actividad"));
                    c1.setPuntos(json.getString("puntos"));

                    culturales.add(c1);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/


    public void getCulturales(ArrayList<Cultural> culturales){
        this.listView = (ExpandableListView)findViewById(R.id.listView);
        this.listView.setAdapter(new ListViewInfoCulturales(this,culturales));

    }
    public void getPartidos(ArrayList<Partido> partidos) {
        this.listView = (ExpandableListView)findViewById(R.id.listView);
        this.listView.setAdapter(new ListViewInfoPartidos(this,partidos));

    }

    private ArrayList<Cultural> filtrarCulturales(){
        String fechaGeneral = new SimpleDateFormat("dd/MM/yyyy").format(calendario.getTime());
        ArrayList<Cultural> filtroCulturales = new ArrayList<Cultural>();
        for(Cultural cultural:culturales){
            String fecha = new SimpleDateFormat("dd/MM/yyyy").format(cultural.getFecha().getTime());
            if(fechaGeneral.equals(fecha)){
                filtroCulturales.add(cultural);
            }
        }
        return filtroCulturales;
    }
    private ArrayList<Partido> filtrarPartidos(){
        String fechaGeneral = new SimpleDateFormat("dd/MM/yyyy").format(calendario.getTime());
        ArrayList<Partido> filtroPartidos = new ArrayList<Partido>();
        for (Partido partido: partidos){
            String fecha = new SimpleDateFormat("dd/MM/yyyy").format(partido.getFecha().getTime());
            if(fechaGeneral.equals(fecha)){
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
