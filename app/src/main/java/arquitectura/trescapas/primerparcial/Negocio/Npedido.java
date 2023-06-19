package arquitectura.trescapas.primerparcial.Negocio;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import arquitectura.trescapas.primerparcial.Datos.Dpedido;
import arquitectura.trescapas.primerparcial.clases.Pedido;
import arquitectura.trescapas.primerparcial.clases.interfaces.Negocio;


public class Npedido implements Negocio<Pedido> {
    private Dpedido dPedido;
    private Context context;

    public Npedido(Context context) {
        this.dPedido = new Dpedido(context);
        this.context = context;
    }


    public void saveDatos(Pedido data) {
        try {
            if (data.getClienteId().equals("") || data.getRepartidorId().equals("")
                    || data.getFecha().equals("") || data.getEstado().equals("")) {
                throw new Exception("Debe Completar todos los campos");
            }
            Dpedido dPedido1 = new Dpedido(context,data);
            if (dPedido1.save(data)) {
                //this.dCliente.setData(data);
                Toast.makeText(this.context, "Creado con exito", Toast.LENGTH_SHORT).show();
            }else {
                throw new Exception("Error al crear");
            }

        }catch (Exception e) {
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    public void updateDatos(Pedido data ) {
        try {
            if (data.getClienteId().equals("") || data.getRepartidorId().equals("")
                    || data.getFecha().equals("") || data.getEstado().equals("")) {
                throw new Exception("Debe Completar todos los campos");
            }
            Dpedido dPedido1 = new Dpedido(context,data);
            dPedido1.setEstado(data.getEstado());
            if (dPedido1.update(data)){

                Toast.makeText(this.context, "actualizado con exito", Toast.LENGTH_SHORT).show();
            }else{
                throw new Exception("Ninguna fila actualizada");
            }

        }catch (Exception e) {
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public List<Pedido> getDatos() {
        return this.dPedido.getAll();
    }

    public void delete(String id) {
        try {
            Pedido pedido = getById(id);
            Dpedido dPedido1 = new Dpedido(context,pedido);
            dPedido1.setEstado(pedido.getEstado());
            if ( dPedido1.delete(id)){
                Toast.makeText(this.context, "pedido eliminado con exito", Toast.LENGTH_SHORT).show();
            }else{
                throw new Exception("Ningun pedido fue eliminado");
            }
        }catch (Exception e) {
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public Pedido getById(String id) {
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

    public void finalizado(Pedido pedido) {
        Dpedido dPedido1 = new Dpedido(context,pedido);
        dPedido1.setEstado(pedido.getEstado());
        dPedido1.finalizado();
    }
    public void cancelado(Pedido pedido) {
        Dpedido dPedido1 = new Dpedido(context,pedido);
        dPedido1.setEstado(pedido.getEstado());
        dPedido1.cancelado();
    }
    public void enProceso(Pedido pedido) {
        Dpedido dPedido1 = new Dpedido(context,pedido);
        dPedido1.setEstado(pedido.getEstado());
        dPedido1.enProceso();
    }



}
