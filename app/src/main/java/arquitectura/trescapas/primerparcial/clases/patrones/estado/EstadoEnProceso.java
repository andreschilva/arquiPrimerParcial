package arquitectura.trescapas.primerparcial.clases.patrones.estado;

import android.content.ContentValues;
import android.content.Context;

import arquitectura.trescapas.primerparcial.Datos.Dpedido;
import arquitectura.trescapas.primerparcial.Utils.Utils;
import arquitectura.trescapas.primerparcial.clases.Pedido;
import arquitectura.trescapas.primerparcial.clases.interfaces.Estado;

public class EstadoEnProceso implements Estado {
    private Dpedido dPedido;
    private Context context;

    public EstadoEnProceso(Dpedido dPedido) {
        this.context = dPedido.getContext();
        this.dPedido = dPedido;
    }


    @Override
    public void enProceso() {
        Utils.mensaje(context,"en proceso");
    }

    @Override
    public void finalizado() {
        Utils.mensaje(context,"pedido Finalizado");
        this.dPedido.getPedido().setEstado("finalizado");
        this.updatePrivate();
    }

    @Override
    public void cancelado() {
        Utils.mensaje(context,"pedido cancelado");
        this.dPedido.getPedido().setEstado("cancelado");
        this.updatePrivate();
    }

    @Override
    public Boolean update(Pedido data) {
        try {
            ContentValues values = this.dPedido.getContentValues(data);
            String [] wereArgs = this.dPedido.getArguments(data);
            this.dPedido.open();
            int resultado = this.dPedido.getBasededatos().update(this.dPedido.getTabla(), values,"id = ?" , wereArgs);
            this.dPedido.close();
            return resultado > 0;
        }catch (Exception e) {
            System.out.println("error al actualizar los datos" + e.getMessage());
        }

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

