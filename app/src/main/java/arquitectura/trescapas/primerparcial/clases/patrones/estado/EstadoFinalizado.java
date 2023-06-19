package arquitectura.trescapas.primerparcial.clases.patrones.estado;

import android.content.Context;

import arquitectura.trescapas.primerparcial.Datos.Dpedido;
import arquitectura.trescapas.primerparcial.Utils.Utils;
import arquitectura.trescapas.primerparcial.clases.Pedido;
import arquitectura.trescapas.primerparcial.clases.interfaces.Estado;

public class EstadoFinalizado implements Estado {
    private Dpedido dPedido;
    private Context context;

    public EstadoFinalizado(Dpedido dPedido) {
        this.context = dPedido.getContext();
        this.dPedido = dPedido;
    }


    @Override
    public void enProceso() {
        Utils.mensaje(context, "no se puede Cambiar a este estado");
    }

    @Override
    public void finalizado() {
        Utils.mensaje(context, "ya se encuentra en este estado");
    }

    @Override
    public void cancelado() {
        Utils.mensaje(context, "no se puede Cancelar el pedido en este estado");
    }

    @Override
    public Boolean update(Pedido data) {
        Utils.mensaje(context, "no se puede Actualizar el pedido en este estado");
        return false;
    }

    @Override
    public Boolean delete(String id) {
        try {
            String[] wereArgs = {id};
            this.dPedido.open();
            int resultado = this.dPedido.getBasededatos().delete(this.dPedido.getTabla(), "id = ?", wereArgs);
            this.dPedido.close();
            return resultado > 0;
        }catch (Exception e) {
            System.out.println("error al eliminar los datos" + e.getMessage());
        }
        return false;
    }
}
