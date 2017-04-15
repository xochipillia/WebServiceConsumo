package cecyt9.ipn.edu.webserviceconsumo;

/**
 * Created by Xochipilli on 15/04/2017.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void calculadora(View o){
        Intent intento = new Intent(this,Calculadora.class);
        startActivity(intento);
    }

    public void convertidorDivisas(View o){
        Intent intento = new Intent(this,Divisas.class);
        startActivity(intento);
    }
}
