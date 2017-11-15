package optativamoviles.olimpiadas;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Flia. Ferreira on 14/11/2017.
 */

public class ServiceCaller extends IntentService {

    public static final String RESPONSE_ACTION = "Respuesta del servidor";
    public static final String RESPONSE = "DATA RESPONSE";
    public static final String SERVICE_TYPE = "SERVICE_TYPE";
    final String BASE_URL = "http://192.168.1.38:8080/OlimpicRestServer/olimpic/";
    static final String TAG = ServiceCaller.class.getCanonicalName();

    public ServiceCaller() {
        super("CallerServiceTest");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String operation = intent.getStringExtra(MostrarInfo.OPERATION);
        Uri builtURI = Uri.parse(BASE_URL + operation).buildUpon().build();
        InputStream is = null;
        HttpURLConnection conn = null;
        try {
            URL requestURL = new URL(builtURI.toString());

            conn = (HttpURLConnection) requestURL.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int responseCode = conn.getResponseCode();

            is = conn.getInputStream();
            String contentAsString = convertIsToString(is, 1000);
            Log.d(TAG, contentAsString);
            Intent response = new Intent(RESPONSE_ACTION);
            response.putExtra(SERVICE_TYPE,operation);
            response.putExtra(RESPONSE, contentAsString);
            LocalBroadcastManager.getInstance(this).sendBroadcast(response);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public String convertIsToString(InputStream stream, int len)
            throws IOException, UnsupportedEncodingException {


        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}
