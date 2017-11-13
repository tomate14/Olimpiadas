package optativamoviles.olimpiadas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button boton = (Button)findViewById(R.id.button);

        boton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent menuPrincipal = new Intent(MainActivity.this, MenuPrincipal.class);
                startActivity(menuPrincipal);
            }
        });
    }
}
