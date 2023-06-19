package arquitectura.trescapas.primerparcial.clases.patrones.estrategia;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import arquitectura.trescapas.primerparcial.Utils.Utils;

public class EnviarPorWhatsapp extends EstrategiaEnviar {

    public EnviarPorWhatsapp() {
        super();
    }

    @Override
    public void enviarCotizacion(Context context, String numero, StringBuilder mensaje) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+591"+numero+ "&text="+mensaje));
            context.startActivity(intent);
        }catch (Exception e) {
            Utils.mensaje(context,"No se puedo enviar el mensaje " + e.getMessage());
        }

    }
}
