package arquitectura.trescapas.primerparcial.Negocio;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import arquitectura.trescapas.primerparcial.Datos.DdetallePedido;
import arquitectura.trescapas.primerparcial.clases.DetallePedido;

public class NdetallePedido {
    private DdetallePedido dDetallePedido;
    private Context context;

    public NdetallePedido(Context context) {
        this.dDetallePedido = new DdetallePedido(context);
        this.context = context;
    }

    public void saveDatos(DetallePedido data) {
        try {
            if (data.getCantidad().equals("") || data.getPedidoId().equals("")
                    || data.getProductoId().equals("")) {
                throw new Exception("Debe Completar todos los campos");
            }

            if (this.dDetallePedido.save(data)) {
                //this.dCliente.setData(data);
                Toast.makeText(this.context, "Creado con exito", Toast.LENGTH_SHORT).show();
            }else {
                throw new Exception("Error al crear");
            }

        }catch (Exception e) {
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    public void updateDatos(DetallePedido data ) {
        try {
            if (data.getCantidad().equals("") || data.getPedidoId().equals("")
                    || data.getProductoId().equals("")) {
                throw new Exception("Debe Completar todos los campos");
            }
            if (dDetallePedido.update(data)){
                Toast.makeText(this.context, "actualizado con exito", Toast.LENGTH_SHORT).show();
            }else{
                throw new Exception("Ninguna fila actualizada");
            }

        }catch (Exception e) {
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public List<DetallePedido> getDatos() {
        return this.dDetallePedido.getAll();
    }

    public void delete(String id) {
        try {
            if ( dDetallePedido.delete(id)){
                Toast.makeText(this.context, "pedido eliminado con exito", Toast.LENGTH_SHORT).show();
            }else{
                throw new Exception("Ningun pedido fue eliminado");
            }
        }catch (Exception e) {
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public DetallePedido getById(String id) {
        try {
            if (dDetallePedido.getById(id) == null){
                throw new Exception("pedido no encontrado");
            }

            return dDetallePedido.getById(id);
        }catch (Exception e){
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

}
