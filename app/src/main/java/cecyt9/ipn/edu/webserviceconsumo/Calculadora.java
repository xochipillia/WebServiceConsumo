package cecyt9.ipn.edu.webserviceconsumo;

/**
 * Created by Xochipilli on 15/04/2017.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class Calculadora extends AppCompatActivity {

    private Double num1;
    private Double num2;
    private String operacion;

    private TextView num1Vista;
    private TextView num2Vista;
    private TextView operacionVista;

    final String NAMESPACE = "http://ws/";
    final String METHOD_NAME = "calculadora";
    final String SOAP_ACTION = "http://ws/SOAPWebService/calculadoraRequest";
    final String WSDL = "http://192.168.0.17:8080/WebServices/SOAPWebService";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);

        num1Vista = (TextView)findViewById(R.id.numero1Vista);
        num2Vista = (TextView)findViewById(R.id.numero2Vista);
        operacionVista = (TextView)findViewById(R.id.operacionVista);

        num1 = new Double(0.0);
        num2 = new Double(0.0);
        operacion = "";
    }

    public void calcular(View o){
        String boton = ((Button)o).getText().toString();
        if(boton.equals("DEL")){
            String contenido = num1Vista.getText().toString();
            if(contenido.length() == 1){
                contenido = "0";
            }else{
                contenido = contenido.substring(0,contenido.length()-2);
            }
            num1 = new Double(contenido);
            num1Vista.setText(contenido);
        }else if(boton.equals("CE")){
            num1 = 0.0;
            num2 = 0.0;
            operacion = "";
            num1Vista.setText("0");
            num2Vista.setText("0");
            operacionVista.setText("");
        }else if(boton.equals("C")){
            num1 = 0.0;
            num1Vista.setText("0");
        }else if(boton.equals("+/-")){
            String contenido = num1Vista.getText().toString();
            if(!contenido.equals("0")){
                contenido = "-" + contenido;
            }
            num1 = new Double(contenido);
            num1Vista.setText(contenido);
        }else if(boton.equals(".")){
            String contenido = num1Vista.getText().toString();
            if(!contenido.contains(".")){
                contenido += ".";
            }
            num1 = new Double(contenido+"0");
            num1Vista.setText(contenido);
        }else if(boton.equals("+") || boton.equals("-") || boton.equals("*") || boton.equals("/")){
            if(num2 == 0.0){
                num2 = new Double(num1);
                num1 = 0.0;
                operacion = boton;
                num2Vista.setText(num2.toString());
                operacionVista.setText(operacion);
            }else{
                operacion = boton;
                Map<String,String> parametros = new HashMap();
                parametros.put("num1", num1.toString());
                parametros.put("num2", num2.toString());
                parametros.put("operacion",operacion);
                TextView contenedor = new TextView(this);
                contenedor.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        num2 = new Double(s.toString());
                        num1 = 0.0;
                        num1Vista.setText("0");
                        num2Vista.setText(num2.toString());
                        operacionVista.setText(operacion);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                ConsumirSOAP soapwebservice = new ConsumirSOAP(contenedor,parametros);
                soapwebservice.execute(NAMESPACE,METHOD_NAME,WSDL,SOAP_ACTION);
            }
        }else if(boton.equals("=")){
            Map<String,String> parametros = new HashMap();
            parametros.put("num1", num1.toString());
            parametros.put("num2", num2.toString());
            parametros.put("operacion",operacion);
            TextView contenedor = new TextView(this);
            contenedor.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    num2 = 0.0;
                    num1 = 0.0;
                    num1Vista.setText(s.toString());
                    num2Vista.setText("0");
                    operacionVista.setText("=");
                    operacion = "=";
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            ConsumirSOAP soapwebservice = new ConsumirSOAP(contenedor,parametros);
            soapwebservice.execute(NAMESPACE,METHOD_NAME,WSDL,SOAP_ACTION);
        }else{
            if(operacion.equals("=")){
                num1Vista.setText(boton);
                num1 = new Double(boton);
                operacionVista.setText("");
                operacion = "";
            }
            else if(!num1.toString().equals(new Double(num1Vista.getText().toString()).toString()) || num1Vista.getText().toString().equals("0")){
                num1Vista.setText(boton);
                num1 = new Double(boton);
                operacionVista.setText("");
            }else{
                String contenido = num1Vista.getText().toString();
                contenido += boton;
                num1 = new Double(contenido);
                num1Vista.setText(contenido);
            }
        }
    }
}
