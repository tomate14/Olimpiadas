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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Maxi on 14/11/2017.
 */

public class ServicioPartido extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    private static final String URI_GETPARTIDOS = "http://172.29.95.207:8080/OlimpicRestServer/olimpic/getpartidos";
    private static final String TAG = ServicioPartido.class.getCanonicalName();
    public static final String RESPONSE_ACTION = "RESPUESTA DEL SERVIDOR";
    public static final int ME = 1;


    public ServicioPartido() {
        super("ServicioPartidoTest");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Uri builtURI = Uri.parse(URI_GETPARTIDOS).buildUpon().build();
        try {
            URL requestURL = new URL(builtURI.toString());
            HttpURLConnection conn = (HttpURLConnection) requestURL.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            int intResponse = conn.getResponseCode();
            Log.d(TAG,String.valueOf(intResponse));
            InputStream is = conn.getInputStream();

            String contentAsString = convertIsToString(is, 1000);
            Intent response = new Intent(RESPONSE_ACTION);
            response.putExtra("Accion",ME);
            response.putExtra("Result",contentAsString);
            LocalBroadcastManager.getInstance(this).sendBroadcast(response);


            conn.disconnect();
            if (is != null) {
                is.close();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
