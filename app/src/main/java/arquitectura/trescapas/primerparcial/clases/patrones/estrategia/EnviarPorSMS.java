package arquitectura.trescapas.primerparcial.clases.patrones.estrategia;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;

import androidx.core.app.ActivityCompat;

import arquitectura.trescapas.primerparcial.Utils.Utils;

public class EnviarPorSMS extends EstrategiaEnviar {
    public EnviarPorSMS() {
        super();
    }

    @Override
    public void enviarCotizacion(Context context, String numero, StringBuilder mensaje) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(context,Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity) context,new String[] {Manifest.permission.SEND_SMS,},1000);
        }

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(numero, null,mensaje.toString(), null, null);
        }catch (Exception e){
            Utils.mensaje(context,"mensaje no enviado " + e.getMessage());
        }
    }
}
