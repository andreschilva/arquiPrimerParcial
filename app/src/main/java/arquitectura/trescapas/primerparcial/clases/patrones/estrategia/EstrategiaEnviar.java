package arquitectura.trescapas.primerparcial.clases.patrones.estrategia;

import android.content.Context;

import java.util.List;

import arquitectura.trescapas.primerparcial.Utils.Utils;
import arquitectura.trescapas.primerparcial.clases.Producto;

public abstract class EstrategiaEnviar {
    public EstrategiaEnviar() {
    }

    public void ejecutarEnvio(Context context,List<Producto> listProductos, String numero){
        try {
            if (numero.equals("")){
                throw new Exception("Debe especificar un numero");
            }

            StringBuilder mensaje = new StringBuilder();
            mensaje.append("Lista de productos:\n");

            double total = 0.0;
            for (Producto producto : listProductos) {
                String nombre =  producto.getNombre();
                double precio =Double.parseDouble( producto.getPrecio());
                int cantidad = 1;

                mensaje.append("Nombre: ").append(nombre).append("\n");
                mensaje.append("Precio: ").append(precio).append("\n");
                mensaje.append("Cantidad: ").append(cantidad).append("\n");
                mensaje.append("-----------------\n");

                total += precio*cantidad;
            }
            mensaje.append("Total: ").append(total).append("\n");

            enviarCotizacion(context,numero,mensaje);

        }catch (Exception e){
            Utils.mensaje(context, e.getMessage());
        }

    }

    public abstract void enviarCotizacion(Context context, String numero, StringBuilder mensaje);
}
