package arquitectura.trescapas.primerparcial.Negocio;

import android.content.Context;

import java.util.List;
import java.util.Map;

import arquitectura.trescapas.primerparcial.Datos.DdetallePedido;
import arquitectura.trescapas.primerparcial.Datos.Dpedido;

public class NdetallePedido {
    private DdetallePedido dDetallePedido;

    public NdetallePedido(Context context) {
        this.dDetallePedido = new DdetallePedido(context);
    }

    public boolean saveDatos(Map<String, Object> data) {
        this.dDetallePedido.setData(data);
        return this.dDetallePedido.save();

    }
    public void updateDatos(Map<String,Object> data ) {
        dDetallePedido.update(data);
    }

    public List<Map<String,Object>> getDatos() {
        return this.dDetallePedido.getAll();
    }

    public void delete(String id) {
        dDetallePedido.delete(id);
    }

    public DdetallePedido getById(String id) {
        return dDetallePedido.getById(id);
    }
}
