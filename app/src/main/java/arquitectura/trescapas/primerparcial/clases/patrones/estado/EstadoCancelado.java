package arquitectura.trescapas.primerparcial.clases.patrones.estado;

import android.content.ContentValues;
import android.content.Context;

import arquitectura.trescapas.primerparcial.Datos.Dpedido;
import arquitectura.trescapas.primerparcial.Utils.Utils;
import arquitectura.trescapas.primerparcial.clases.Pedido;
import arquitectura.trescapas.primerparcial.clases.interfaces.Estado;

public class EstadoCancelado implements Estado {

    private Dpedido dPedido;
    private Context context;

    public EstadoCancelado(Dpedido dPedido) {
        this.context = dPedido.getContext();
        this.dPedido = dPedido;
    }

    @Override
    public void enProceso() {
        Utils.mensaje(context,"Pedido En Proceso");
        this.dPedido.getPedido().setEstado("En proceso");
        this.updatePrivate();
    }

    @Override
    public void finalizado() {
        Utils.mensaje(context, "no se puede finalizar el pedido desde estado cancelado" );
    }

    @Override
    public void cancelado() {
        Utils.mensaje(context, "ya se encuentra en este estado");
    }

    @Override
    public Boolean update(Pedido data) {
        Utils.mensaje(context, "no se puede Actualizar el pedido");
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

    private void updatePrivate(){
        try {
            ContentValues values = this.dPedido.getContentValues(this.dPedido.getPedido());
            String [] wereArgs = this.dPedido.getArguments(this.dPedido.getPedido());
            this.dPedido.open();
            int resultado = this.dPedido.getBasededatos().update(this.dPedido.getTabla(), values,"id = ?" , wereArgs);
            this.dPedido.close();

        }catch (Exception e) {
            System.out.println("error al actualizar los datos" + e.getMessage());
        }

    }
}
