package arquitectura.trescapas.primerparcial.clases;

import android.content.Context;

import arquitectura.trescapas.primerparcial.Datos.Dpedido;
import arquitectura.trescapas.primerparcial.Utils.Utils;
import arquitectura.trescapas.primerparcial.interfaces.Estado;

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
        Utils.mensaje(context, "no se puede Cancelar el pedido");
    }

    @Override
    public Boolean update(Dpedido data) {
        Utils.mensaje(context, "no se puede Actualizar el pedido");
        return false;
    }

    @Override
    public Boolean delete(String id) {
        Utils.mensaje(context, "no se puede Eliminar el pedido");
        return false;
    }
}
