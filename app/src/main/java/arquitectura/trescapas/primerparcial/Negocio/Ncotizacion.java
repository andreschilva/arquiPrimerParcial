package arquitectura.trescapas.primerparcial.Negocio;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import arquitectura.trescapas.primerparcial.Datos.Dcotizacion;
import arquitectura.trescapas.primerparcial.clases.Cotizacion;
import arquitectura.trescapas.primerparcial.clases.interfaces.Negocio;

public class Ncotizacion implements Negocio<Cotizacion> {
    private Dcotizacion dcotizacion;
    Context context;

    public Ncotizacion(Context context) {
        this.dcotizacion = new Dcotizacion(context);
        this.context =context;
    }

    public void saveDatos(Cotizacion data) {
        try {
            if (data.getNombre().equals("")) {
                throw new Exception("Debe Completar todos los campos");
            }

            if (this.dcotizacion.save(data)) {

                Toast.makeText(this.context, "Creado con exito", Toast.LENGTH_SHORT).show();
            }else {
                throw new Exception("Error al crear");
            }

        }catch (Exception e) {
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    public void updateDatos(Cotizacion data ) {
        try {
            if (data.getNombre().equals("")) {
                throw new Exception("Debe Completar todos los campos");
            }

            if (dcotizacion.update(data)){
                Toast.makeText(this.context, "actualizado con exito", Toast.LENGTH_SHORT).show();
            }else{
                throw new Exception("Ninguna fila actualizada");
            }

        }catch (Exception e) {
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public List<Cotizacion> getDatos() {
        return this.dcotizacion.getAll();
    }

    public void delete(String id) {
        try {
            if ( dcotizacion.delete(id)){
                Toast.makeText(this.context, "cotizacion eliminada con exito", Toast.LENGTH_SHORT).show();
            }else{
                throw new Exception("Ninguna cotizacion fue eliminado");
            }
        }catch (Exception e) {
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public Cotizacion getById(String id) {
        try {
            if (dcotizacion.getById(id) == null){
                throw new Exception("cotizacion no encontrada");
            }

            return dcotizacion.getById(id);
        }catch (Exception e){
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public String [] getNombresCotizacion(){
        List<Cotizacion> listCotizaciones= this.dcotizacion.getAll();

        //int i=0;
        String [] arrayCotizaciones = new String[listCotizaciones.size()];
        for (int i=0;i < arrayCotizaciones.length;i++) {
            arrayCotizaciones[i] = listCotizaciones.get(i).getNombre();
        }

        return arrayCotizaciones;
    }
}
