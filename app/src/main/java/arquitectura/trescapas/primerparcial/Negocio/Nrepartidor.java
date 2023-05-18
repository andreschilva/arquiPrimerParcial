package arquitectura.trescapas.primerparcial.Negocio;

import android.content.Context;

import java.util.List;
import java.util.Map;

import arquitectura.trescapas.primerparcial.Datos.Dpedido;
import arquitectura.trescapas.primerparcial.Datos.Drepartidor;

public class Nrepartidor {

    private Drepartidor dRepartidor;

    public Nrepartidor(Context context) {
        this.dRepartidor = new Drepartidor(context);
    }

    public boolean saveDatos(Map<String, Object> data) {
        this.dRepartidor.setData(data);
        return this.dRepartidor.save();

    }

    public List<Map<String,Object>> getDatos() {
        return this.dRepartidor.getAll();
    }

    public void updateDatos(Map<String,Object> data ) {
        dRepartidor.update(data);
    }

    public void delete(String id) {
        dRepartidor.delete(id);
    }

    public Drepartidor getDcategoriaById(String id) {
        return dRepartidor.getById(id);
    }
}
