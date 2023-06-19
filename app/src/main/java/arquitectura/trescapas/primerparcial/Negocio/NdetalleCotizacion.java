package arquitectura.trescapas.primerparcial.Negocio;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import arquitectura.trescapas.primerparcial.Datos.DdetalleCotizacion;
import arquitectura.trescapas.primerparcial.Datos.Dproducto;
import arquitectura.trescapas.primerparcial.clases.Cotizacion;
import arquitectura.trescapas.primerparcial.clases.DetalleCotizacion;
import arquitectura.trescapas.primerparcial.clases.Producto;
import arquitectura.trescapas.primerparcial.clases.interfaces.Negocio;

public class NdetalleCotizacion implements Negocio<DetalleCotizacion> {
    private DdetalleCotizacion dDetalleCotizacion;
    private Dproducto dProducto;
    private Context context;

    public NdetalleCotizacion(Context context) {
        this.dDetalleCotizacion = new DdetalleCotizacion(context);
        this.dProducto = new Dproducto(context);
        this.context = context;
    }

    public void saveDatos(DetalleCotizacion data) {
        try {
            if (Integer.toString(data.getCantidad()).equals("") || data.getCotizacionId().equals("")
                    || data.getProductoId().equals("")) {
                throw new Exception("Debe Completar todos los campos");
            }

            if (this.dDetalleCotizacion.save(data)) {
                //this.dCliente.setData(data);
                Toast.makeText(this.context, "Creado con exito", Toast.LENGTH_SHORT).show();
            }else {
                throw new Exception("Error al crear");
            }

        }catch (Exception e) {
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void updateDatos(DetalleCotizacion data ) {
        try {
            if (Integer.toString(data.getCantidad()).equals("") || data.getCotizacionId().equals("")
                    || data.getProductoId().equals("")) {
                throw new Exception("Debe Completar todos los campos");
            }
            if (dDetalleCotizacion.update(data)){
                Toast.makeText(this.context, "actualizado con exito", Toast.LENGTH_SHORT).show();
            }else{
                throw new Exception("Ninguna fila actualizada");
            }

        }catch (Exception e) {
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public List<DetalleCotizacion> getDatos() {
        return this.dDetalleCotizacion.getAll();
    }

    public void delete(String id) {
        try {
            if ( dDetalleCotizacion.delete(id)){
                Toast.makeText(this.context, "cotizacion eliminada con exito", Toast.LENGTH_SHORT).show();
            }else{
                throw new Exception("Ningun cotizacion fue eliminado");
            }
        }catch (Exception e) {
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public DetalleCotizacion getById(String id) {
        try {
            if (dDetalleCotizacion.getById(id) == null){
                throw new Exception("detalle cotizacion no encontrado");
            }

            return dDetalleCotizacion.getById(id);
        }catch (Exception e){
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public List<Producto> getListProductosDelPedido(Cotizacion cotizacion) {
        List<DetalleCotizacion> listDetalleCotizacion = dDetalleCotizacion.getAll();
        List<Producto> listProductos = dProducto.getAll();
        List<Producto>  resultado = new ArrayList<>();
        for (DetalleCotizacion cotizacionActual: listDetalleCotizacion) {
            String cotizacionId = cotizacionActual.getCotizacionId();
            if (cotizacionId.equals(cotizacion.getId())) {
                String productoId = cotizacionActual.getProductoId();
                for (Producto productoActual: listProductos ) {
                    if (productoId.equals(productoActual.getId())) {
                        resultado.add(productoActual);
                        break;
                    }
                }
            }
        }
        return resultado;
    }
}
