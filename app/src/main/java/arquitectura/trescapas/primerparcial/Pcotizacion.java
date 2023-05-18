package arquitectura.trescapas.primerparcial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telecom.StatusHints;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class Pcotizacion extends AppCompatActivity {
    TextView tvMensaje, tvNumero;

    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcotizacion);
        tvMensaje = findViewById(R.id.tvMensaje);
        tvNumero = findViewById(R.id.tvNumero);


    }
    
    public void enviar(View v) {
        String numero = tvNumero.getText().toString();
        String mensaje = tvMensaje.getText().toString();

        boolean installed = appInstalledOrNot("com.whatsapp");
        //if (installed) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+591"+numero+ "&text="+mensaje));
            startActivity(intent);
//        }else {
//            Toast.makeText(this, "Whasapp no esta instalado en tu dispositivo", Toast.LENGTH_SHORT).show();
//        }
    }

    private boolean appInstalledOrNot(String url) {
        PackageManager packageManager = getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
            app_installed = true;

        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }

        return  app_installed;
    }

}