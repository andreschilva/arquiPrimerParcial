package arquitectura.trescapas.primerparcial.Negocio;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import arquitectura.trescapas.primerparcial.Datos.Dcliente;
import arquitectura.trescapas.primerparcial.clases.Cliente;
import arquitectura.trescapas.primerparcial.clases.interfaces.Negocio;


public class Ncliente implements Negocio<Cliente> {
    private Dcliente dCliente;
    Context context;

    public Ncliente(Context context) {
        this.dCliente = new Dcliente(context);
        this.context =context;
    }

    public void saveDatos(Cliente data) {
        try {
            if (data.getNombre().equals("") || data.equals("")|| data.getCelular().equals("")) {
                throw new Exception("Debe Completar todos los campos");
            }

            if (this.dCliente.save(data)) {
                    //this.dCliente.setData(data);
                    Toast.makeText(this.context, "Creado con exito", Toast.LENGTH_SHORT).show();
            }else {
                    throw new Exception("Error al crear");
            }

        }catch (Exception e) {
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    public void updateDatos(Cliente data ) {
        try {
            if (data.getNombre().equals("") || data.equals("")|| data.getCelular().equals("")) {
                throw new Exception("Debe Completar todos los campos");
            }
            Cliente client = (Cliente) data.clone();
            if (dCliente.update(client)){
                Toast.makeText(this.context, "actualizado con exito", Toast.LENGTH_SHORT).show();
            }else{
                throw new Exception("Ninguna fila actualizada");
            }

        }catch (Exception e) {
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public List<Cliente> getDatos() {
        return this.dCliente.getAll();
    }

    public void delete(String id) {
        try {
            if ( dCliente.delete(id)){
            Toast.makeText(this.context, "cliente eliminado con exito", Toast.LENGTH_SHORT).show();
            }else{
                throw new Exception("Ningun cliente fue eliminado");
            }
        }catch (Exception e) {
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public Cliente getById(String id) {
        try {
            if (dCliente.getById(id) == null){
                throw new Exception("cliente no encontrado");
            }

            return dCliente.getById(id);
        }catch (Exception e){
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }
    public String [] getNombresClientes(){
        List<Cliente> listClientes= this.dCliente.getAll();

        //int i=0;
        String [] arrayClientes = new String[listClientes.size()];
        for (int i=0;i < arrayClientes.length;i++) {
            arrayClientes[i] = listClientes.get(i).getNombre();
        }

        return arrayClientes;
    }

}
