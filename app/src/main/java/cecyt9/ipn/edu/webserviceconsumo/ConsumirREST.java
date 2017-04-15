package cecyt9.ipn.edu.webserviceconsumo;

/**
 * Created by Xochipilli on 15/04/2017.
 */

import android.os.AsyncTask;
import android.widget.TextView;
import com.github.kevinsawicki.http.HttpRequest;

public class ConsumirREST extends AsyncTask<String,Void,String>{
    private TextView contenedor;

    public ConsumirREST(TextView contenedor){
        this.contenedor = contenedor;
    }

    @Override
    protected String doInBackground(String... params) {
        return HttpRequest.get(params[0]).accept("text/plain").body();
    }
    @Override
    protected void onPostExecute(String resultado){
        contenedor.setText(resultado);
    }
}