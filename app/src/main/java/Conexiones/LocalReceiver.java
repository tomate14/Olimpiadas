package Conexiones;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import optativamoviles.olimpiadas.ServicioPartido;

/**
 * Created by Maxi on 14/11/2017.
 */

public class LocalReceiver extends BroadcastReceiver {
    private static final String TAG = ServicioPartido.class.getCanonicalName();

    @Override
    public void onReceive(Context context, Intent intent) {
            //Aca hay que parsear el JSON
        String msg = intent.getStringExtra("Result");
        Log.d(TAG,msg);

    }
}
