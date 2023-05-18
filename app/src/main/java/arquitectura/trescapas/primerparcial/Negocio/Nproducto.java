package arquitectura.trescapas.primerparcial.Negocio;

import android.content.Context;

import java.util.List;
import java.util.Map;

import arquitectura.trescapas.primerparcial.Datos.Dpedido;
import arquitectura.trescapas.primerparcial.Datos.Dproducto;


public class Nproducto {
    private Dproducto dproducto;

    public Nproducto(Context context) {
        this.dproducto = new Dproducto(context);
    }

    public boolean saveDatos(Map<String, Object> data) {
        this.dproducto.setData(data);
        return this.dproducto.save();

    }
    public void updateDatos(Map<String,Object> data ) {
        dproducto.update(data);
    }

    public List<Map<String,Object>> getDatos() {
        return this.dproducto.getAll();
    }

    public void delete(String id) {
        dproducto.delete(id);
    }

    public Dproducto getDcategoriaById(String id) {
        return dproducto.getById(id);
    }
}
