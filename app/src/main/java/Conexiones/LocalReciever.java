package Conexiones;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import optativamoviles.olimpiadas.MostrarInfo;

import optativamoviles.olimpiadas.ServiceCaller;

/**
 * Created by Flia. Ferreira on 14/11/2017.
 */

public class LocalReciever extends BroadcastReceiver {

    static final String TAG = LocalReciever.class.getCanonicalName();
    @Override
    public void onReceive(Context context, Intent intent) {
        String operation = intent.getStringExtra(ServiceCaller.SERVICE_TYPE);
        switch (operation){
            case MostrarInfo.CULTURAL_SERVICE : break;

            case MostrarInfo.PARTIDO_SERVICE : break;
        }
    }
}
