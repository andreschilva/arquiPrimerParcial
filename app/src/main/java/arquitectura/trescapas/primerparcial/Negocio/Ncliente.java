package arquitectura.trescapas.primerparcial.Negocio;

import android.content.Context;

import java.util.List;
import java.util.Map;

import arquitectura.trescapas.primerparcial.Datos.Dcategoria;
import arquitectura.trescapas.primerparcial.Datos.Dcliente;


public class Ncliente {
    private Dcliente dCliente;

    public Ncliente(Context context) {
        this.dCliente = new Dcliente(context);
    }

    public boolean saveDatos(Map<String, Object> data) {
        this.dCliente.setData(data);
        return this.dCliente.save();

    }
    public void updateDatos(Map<String,Object> data ) {
        dCliente.update(data);
    }

    public List<Map<String,Object>> getDatos() {
        return this.dCliente.getAll();
    }

    public void delete(String id) {
        dCliente.delete(id);
    }

    public Dcliente getDcategoriaById(String id) {
        return dCliente.getById(id);
    }

}
