package arquitectura.trescapas.primerparcial.clases.patrones.estrategia;

import android.content.Context;

import java.util.List;

import arquitectura.trescapas.primerparcial.clases.Producto;

public class Enviar {
    EstrategiaEnviar estrategia;

    public Enviar() {

    }

    public Enviar(EstrategiaEnviar estrategia) {
        this.estrategia = estrategia;
    }

    public void ejecutarEnvio(Context context, String numero, List<Producto> listProductos){
        estrategia.ejecutarEnvio(context,listProductos,numero);
    }

    public EstrategiaEnviar getEstrategia() {
        return estrategia;
    }

    public void setEstrategia(EstrategiaEnviar estrategia) {
        this.estrategia = estrategia;
    }
}
