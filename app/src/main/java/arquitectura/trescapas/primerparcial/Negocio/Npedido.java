package arquitectura.trescapas.primerparcial.Negocio;

import android.content.Context;

import java.util.List;
import java.util.Map;

import arquitectura.trescapas.primerparcial.Datos.Dcliente;
import arquitectura.trescapas.primerparcial.Datos.Dpedido;


public class Npedido {
    private Dpedido dPedido;

    public Npedido(Context context) {
        this.dPedido = new Dpedido(context);
    }

    public boolean saveDatos(Map<String, Object> data) {
        this.dPedido.setData(data);
        return this.dPedido.save();

    }
    public void updateDatos(Map<String,Object> data ) {
        dPedido.update(data);
    }

    public List<Map<String,Object>> getDatos() {
        return this.dPedido.getAll();
    }

    public void delete(String id) {
        dPedido.delete(id);
    }

    public Dpedido getDcategoriaById(String id) {
        return dPedido.getById(id);
    }
}
