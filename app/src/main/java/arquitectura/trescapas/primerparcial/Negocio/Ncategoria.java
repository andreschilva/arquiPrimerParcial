package arquitectura.trescapas.primerparcial.Negocio;

import android.content.Context;

import java.util.List;
import java.util.Map;

import arquitectura.trescapas.primerparcial.Datos.Dcategoria;


public class Ncategoria {
    private Dcategoria dcategoria;

    public Ncategoria(Context context) {
        this.dcategoria = new Dcategoria(context);
    }

    public boolean saveDatos(Map<String, Object> data) {
        this.dcategoria.setData(data);
        return this.dcategoria.save();

    }
    public List<Map<String,Object>> getDatos() {
        return this.dcategoria.getAll();
    }

    public Dcategoria getDcategoriaById(String id) {
        return dcategoria.getById(id);
    }

    public void updateDatos(Map<String,Object> data ) {
        dcategoria.update(data);
    }

    public void delete(String id) {
        dcategoria.delete(id);
    }

}
