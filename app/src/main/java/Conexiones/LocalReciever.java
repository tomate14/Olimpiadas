package Conexiones;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import Entidades.Cultural;
import Entidades.Facultad;
import Entidades.Partido;
import optativamoviles.olimpiadas.MostrarInfo;

import optativamoviles.olimpiadas.ServiceCaller;

/**
 * Created by Flia. Ferreira on 14/11/2017.
 */

public class LocalReciever extends BroadcastReceiver {

    static final String TAG = LocalReciever.class.getCanonicalName();

    private MostrarInfo actividadInfo;

    public LocalReciever(MostrarInfo actividadInfo){
        this.actividadInfo = actividadInfo;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String operation = intent.getStringExtra(ServiceCaller.SERVICE_TYPE);
        switch (operation){
            case MostrarInfo.CULTURAL_SERVICE :

                try {

                    JSONArray jsonArray = new JSONArray(intent.getStringExtra(ServiceCaller.RESPONSE));

                    ArrayList<Cultural> culturales = new ArrayList<>();

                    for(int i = 0; i < jsonArray.length(); i++){

                        JSONObject json = jsonArray.getJSONObject(i);
                        Cultural c1 = new Cultural();

                        c1.setId(json.getInt("id"));

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
                actividadInfo.getCulturales(culturales);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case MostrarInfo.PARTIDO_SERVICE :try {

                JSONArray jsonArray = new JSONArray(intent.getStringExtra(ServiceCaller.RESPONSE));

                ArrayList<Partido> partidos = new ArrayList<>();

                for(int i = 0; i < jsonArray.length(); i++){

                    JSONObject json = jsonArray.getJSONObject(i);
                    Partido p1 = new Partido();

                    p1.setId(json.getInt("id"));

                    //setFacultad al Cultural
                    JSONObject jsonFacultad = new JSONObject(json.getString("facultad1"));
                    Facultad f1 = new Facultad();
                    f1.setId(jsonFacultad.getInt("id"));
                    f1.setNombre(jsonFacultad.getString("nombre"));
                    p1.setFacultad1(f1);

                    jsonFacultad = new JSONObject(json.getString("facultad2"));
                    Facultad f2 = new Facultad();
                    f2.setId(jsonFacultad.getInt("id"));
                    f2.setNombre(jsonFacultad.getString("nombre"));
                    p1.setFacultad2(f2);

                    //setFecha al Cultural
                    // java.util.Calendar cal = java.util.Calendar.getInstance();
                    //SimpleDateFormat sdf = new SimpleDateFormat();
                    //cal.setTime(sdf.parse(json.getString("fecha")));
                    //c1.setFecha(cal);

                    p1.setLugar(json.getString("lugar"));
                    p1.setDeporte(json.getString("deporte"));
                    p1.setResultado(json.getString("resultado"));

                    partidos.add(p1);

                }
                actividadInfo.getPartidos(partidos);
            } catch (Exception e) {
                e.printStackTrace();
            }
                break;
        }
    }
}
