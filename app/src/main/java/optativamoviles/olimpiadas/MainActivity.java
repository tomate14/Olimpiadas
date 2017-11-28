package optativamoviles.olimpiadas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final long SPLASH_SCREEN_DELAY = 2000;
    static final String TAG = MainActivity.class.getCanonicalName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startApp();
    }

    private void startApp() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // Start the next activity
                Intent login = new Intent(MainActivity.this, MenuPrincipal.class);
                startActivity(login);
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }
}
