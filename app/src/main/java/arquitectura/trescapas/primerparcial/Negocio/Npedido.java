package arquitectura.trescapas.primerparcial.Negocio;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import arquitectura.trescapas.primerparcial.Datos.Dpedido;


public class Npedido {
    private Dpedido dPedido;
    private Context context;

    public Npedido(Context context) {
        this.dPedido = new Dpedido(context);
        this.context = context;
    }

    public void cambiarEstado(String estado){

    };

    public void saveDatos(Dpedido data) {
        try {
            if (data.getPedido().getClienteId().equals("") || data.getPedido().getRepartidorId().equals("")
                    || data.getPedido().getFecha().equals("") || data.getPedido().getEstado().equals("")) {
                throw new Exception("Debe Completar todos los campos");
            }

            if (data.save(data)) {
                //this.dCliente.setData(data);
                Toast.makeText(this.context, "Creado con exito", Toast.LENGTH_SHORT).show();
            }else {
                throw new Exception("Error al crear");
            }

        }catch (Exception e) {
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    public void updateDatos(Dpedido data ) {
        try {
            if (data.getPedido().getClienteId().equals("") || data.getPedido().getRepartidorId().equals("")
                    || data.getPedido().getFecha().equals("") || data.getPedido().getEstado().equals("")) {
                throw new Exception("Debe Completar todos los campos");
            }

            if (data.update(data)){

                Toast.makeText(this.context, "actualizado con exito", Toast.LENGTH_SHORT).show();
            }else{
                throw new Exception("Ninguna fila actualizada");
            }

        }catch (Exception e) {
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public List<Dpedido> getDatos() {
        return this.dPedido.getAll();
    }

    public void delete(String id) {
        try {
            Dpedido dpedido1 = getById(id);
            if ( dpedido1.delete(id)){
                Toast.makeText(this.context, "pedido eliminado con exito", Toast.LENGTH_SHORT).show();
            }else{
                throw new Exception("Ningun pedido fue eliminado");
            }
        }catch (Exception e) {
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public Dpedido getById(String id) {
        try {
            if (dPedido.getById(id) == null){
                throw new Exception("pedido no encontrado");
            }

            return dPedido.getById(id);
        }catch (Exception e){
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public void finalizado() {
        dPedido.finalizado();
    }
    public void cancelado() {
        dPedido.cancelado();
    }
    public void enProceso() {
        dPedido.enProceso();
    }

}
