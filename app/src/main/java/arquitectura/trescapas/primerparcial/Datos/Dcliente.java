package arquitectura.trescapas.primerparcial.Datos;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import arquitectura.trescapas.primerparcial.DB.DBmanager1;
import arquitectura.trescapas.primerparcial.DB.DBmigrations;
import arquitectura.trescapas.primerparcial.Utils.IDatos1;

public class Dcliente implements IDatos1 {
    private DBmanager1 db;

    private String id;
    private String nombre;
    private String apellido;
    private String celular;
    private Map<String,Object> row;

    public Dcliente(Context context) {
        this.db = new DBmanager1(context);
        this.row = new HashMap<>();

        row.put(DBmigrations.CLIENTE_ID,"");
        row.put(DBmigrations.CLIENTE_NOMBRE,"");
        row.put(DBmigrations.CLIENTE_APELLIDO,"");
        row.put(DBmigrations.CLIENTE_CELULAR,"");
        row.put(DBmigrations.CLIENTE_UBICACION,"");
    }


    public void setData(Map<String, Object> data) {
        this.row.putAll(data);

    }


    public void setData(String nombre,String apellido, String celular) {
        this.nombre = nombre;
        this.apellido  = apellido;
        this.celular = celular;
    }

    @Override
    public boolean save( ) {
        return db.insert(DBmigrations.TABLA_CLIENTE,this.row);
    }

    @Override
    public void update(Map<String, Object> data) {

        db.updateData(DBmigrations.TABLA_CLIENTE,  data);
    }

    @Override
    public void delete(String id) {
        this.row.put("id",id);
        db.deleteData(DBmigrations.TABLA_CLIENTE, this.row.get("id").toString());
    }

    @Override
    public Dcliente getById(String id) {
        Map<String,Object> cliente =db.getById(DBmigrations.TABLA_CLIENTE,this.row,id);
        this.row.putAll(cliente);
        return this;
    }

    @Override
    public List<Map<String,Object>> getAll() {
        return db.selectAll(DBmigrations.TABLA_CLIENTE,this.row);
    }

    public  Map<String,Object> row() {
        return this.row;
    }
}
