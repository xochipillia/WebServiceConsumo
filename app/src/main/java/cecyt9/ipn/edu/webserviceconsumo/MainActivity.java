package cecyt9.ipn.edu.webserviceconsumo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView usuario;
    TextView contra;
    String respuestaSOAP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView olvidaste = (TextView)findViewById(R.id.olvidaste);
        olvidaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Usuario: XochipilliArenas\nContraseña: 123456",Toast.LENGTH_LONG).show();
            }
        });
        usuario = (TextView)findViewById(R.id.usuario);
        contra = (TextView)findViewById(R.id.contra);
        respuestaSOAP = "";
    }

    public void ingresar(View o){
        final String NAMESPACE = "http://ws/";
        final String METHOD_NAME = "login";
        final String SOAP_ACTION = "http://ws/SOAPWebService/loginRequest";
        final String WSDL = "http://192.168.0.17:8080/WebServices/SOAPWebService";
        Map<String,String> parametros = new HashMap();
        parametros.put("usuario", usuario.getText().toString());
        parametros.put("contraseña", contra.getText().toString());
        TextView contenedor = new TextView(this);
        contenedor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().startsWith("ERROR")){
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Error")
                            .setMessage(s.toString())
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else{
                    Boolean resultado = new Boolean(s.toString());
                    if(resultado){
                        Intent intento = new Intent(MainActivity.this,Menu.class);
                        MainActivity.this.startActivity(intento);
                    }else{
                        Toast.makeText(MainActivity.this,"Datos incorrectos",Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ConsumirSOAP soapws = new ConsumirSOAP(contenedor,parametros);
        soapws.execute(NAMESPACE,METHOD_NAME,WSDL,SOAP_ACTION);
    }
}
