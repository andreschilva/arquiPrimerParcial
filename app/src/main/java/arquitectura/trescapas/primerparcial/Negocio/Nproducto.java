package arquitectura.trescapas.primerparcial.Negocio;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import arquitectura.trescapas.primerparcial.Datos.Dproducto;
import arquitectura.trescapas.primerparcial.clases.Producto;


public class Nproducto {
    private Dproducto dproducto;
    private Context context;

    public Nproducto(Context context) {
        this.dproducto = new Dproducto(context);
        this.context = context;
    }

    public void saveDatos(Producto data) {
        try {
            if (data.getNombre().equals("") || data.getDescripcion().equals("")
                    || data.getPrecio().equals("") || data.getCategoriaId().equals("")) {
                throw new Exception("Debe Completar todos los campos");
            }

            if (this.dproducto.save(data)) {
                //this.dCliente.setData(data);
                Toast.makeText(this.context, "Creado con exito", Toast.LENGTH_SHORT).show();
            }else {
                throw new Exception("Error al crear");
            }

        }catch (Exception e) {
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    public void updateDatos(Producto data ) {
        try {
            if (data.getNombre().equals("") || data.getDescripcion().equals("")
                    || data.getPrecio().equals("") || data.getCategoriaId().equals("")) {
                throw new Exception("Debe Completar todos los campos");
            }
            if (dproducto.update(data)){
                Toast.makeText(this.context, "actualizado con exito", Toast.LENGTH_SHORT).show();
            }else{
                throw new Exception("Ninguna fila actualizada");
            }

        }catch (Exception e) {
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public List<Producto> getDatos() {
        return this.dproducto.getAll();
    }

    public void delete(String id) {
        try {
            if ( dproducto.delete(id)){
                Toast.makeText(this.context, "producto eliminado con exito", Toast.LENGTH_SHORT).show();
            }else{
                throw new Exception("Ningun producto fue eliminado");
            }
        }catch (Exception e) {
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public Producto getById(String id) {
        try {
            if (dproducto.getById(id) == null){
                throw new Exception("producto no encontrado");
            }

            return dproducto.getById(id);
        }catch (Exception e){
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }


}
