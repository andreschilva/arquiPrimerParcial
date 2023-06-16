package arquitectura.trescapas.primerparcial.clases;

import android.content.ContentValues;
import android.content.Context;

import java.lang.reflect.Array;
import java.util.List;

import arquitectura.trescapas.primerparcial.Datos.Dpedido;
import arquitectura.trescapas.primerparcial.Utils.Utils;
import arquitectura.trescapas.primerparcial.interfaces.Estado;

public class EstadoEnProceso implements Estado {
    private Dpedido dPedido;
    private Context context;
    private String[] estados = {"finalizado","cancelado"};

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
        this.dPedido.setEstado("finalizado");
    }

    @Override
    public void cancelado() {
        Utils.mensaje(context,"pedido cancelado");
        this.dPedido.setEstado("cancelado");
    }

    @Override
    public Boolean update(Dpedido data) {
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


}

