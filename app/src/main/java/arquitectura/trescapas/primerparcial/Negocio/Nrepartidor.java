package arquitectura.trescapas.primerparcial.Negocio;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import arquitectura.trescapas.primerparcial.Datos.Drepartidor;
import arquitectura.trescapas.primerparcial.clases.Repartidor;
import arquitectura.trescapas.primerparcial.clases.interfaces.Negocio;

public class Nrepartidor implements Negocio<Repartidor> {

    private Drepartidor drepartidor;
    Context context;

    public Nrepartidor(Context context) {
        this.drepartidor = new Drepartidor(context);
        this.context =context;
    }

    public void saveDatos(Repartidor data) {
        try {
            if (data.getNombre().equals("") || data.getApellido().equals("")|| data.getCelular().equals("")) {
                throw new Exception("Debe Completar todos los campos");
            }

            if (this.drepartidor.save(data)) {
                //this.dCliente.setData(data);
                Toast.makeText(this.context, "Creado con exito", Toast.LENGTH_SHORT).show();
            }else {
                throw new Exception("Error al crear");
            }

        }catch (Exception e) {
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    public void updateDatos(Repartidor data ) {
        try {
            if (data.getNombre().equals("") || data.getApellido().equals("")|| data.getCelular().equals("")) {
                throw new Exception("Debe Completar todos los campos");
            }
            Repartidor repartidor = (Repartidor) data.clone();
            if (drepartidor.update(repartidor)){
                Toast.makeText(this.context, "actualizado con exito", Toast.LENGTH_SHORT).show();
            }else{
                throw new Exception("Ninguna fila actualizada");
            }

        }catch (Exception e) {
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public List<Repartidor> getDatos() {
        return this.drepartidor.getAll();
    }

    public void delete(String id) {
        try {
            if ( drepartidor.delete(id)){
                Toast.makeText(this.context, "repartidor eliminado con exito", Toast.LENGTH_SHORT).show();
            }else{
                throw new Exception("Ningun repartidor fue eliminado");
            }
        }catch (Exception e) {
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public Repartidor getById(String id) {
        try {
            if (drepartidor.getById(id) == null){
                throw new Exception("repartidor no encontrado");
            }

            return drepartidor.getById(id);
        }catch (Exception e){
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public String [] getNombresRepartidores(){
        List<Repartidor> listRepartidores= this.drepartidor.getAll();

        //int i=0;
        String [] arrayRepartidores = new String[listRepartidores.size()];
        for (int i=0;i < arrayRepartidores.length;i++) {
            arrayRepartidores[i] = listRepartidores.get(i).getNombre();
        }

        return arrayRepartidores;
    }
}
