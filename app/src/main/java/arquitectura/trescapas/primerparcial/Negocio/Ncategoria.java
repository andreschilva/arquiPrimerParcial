package arquitectura.trescapas.primerparcial.Negocio;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import arquitectura.trescapas.primerparcial.Datos.Dcategoria;
import arquitectura.trescapas.primerparcial.clases.Categoria;


public class Ncategoria {
    private Dcategoria dcategoria;
    Context context;

    public Ncategoria(Context context) {
        this.dcategoria = new Dcategoria(context);
        this.context =context;
    }

    public void saveDatos(Categoria data) {
        try {
            if (data.getNombre().equals("")) {
                throw new Exception("Debe Completar todos los campos");
            }

            if (this.dcategoria.save(data)) {

                Toast.makeText(this.context, "Creado con exito", Toast.LENGTH_SHORT).show();
            }else {
                throw new Exception("Error al crear");
            }

        }catch (Exception e) {
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    public void updateDatos(Categoria data ) {
        try {
            if (data.getNombre().equals("")) {
                throw new Exception("Debe Completar todos los campos");
            }

            if (dcategoria.update(data)){
                Toast.makeText(this.context, "actualizado con exito", Toast.LENGTH_SHORT).show();
            }else{
                throw new Exception("Ninguna fila actualizada");
            }

        }catch (Exception e) {
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public List<Categoria> getDatos() {
        return this.dcategoria.getAll();
    }

    public void delete(String id) {
        try {
            if ( dcategoria.delete(id)){
                Toast.makeText(this.context, "categoria eliminado con exito", Toast.LENGTH_SHORT).show();
            }else{
                throw new Exception("Ninguna categoria fue eliminado");
            }
        }catch (Exception e) {
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public Categoria getById(String id) {
        try {
            if (dcategoria.getById(id) == null){
                throw new Exception("categoria no encontrada");
            }

            return dcategoria.getById(id);
        }catch (Exception e){
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public String [] getNombresCategorias(){
        List<Categoria> listCategoria= this.dcategoria.getAll();

        //int i=0;
        String [] arrayCategorias = new String[listCategoria.size()];
        for (int i=0;i < arrayCategorias.length;i++) {
            arrayCategorias[i] = listCategoria.get(i).getNombre();
        }

        return arrayCategorias;
    }

}
