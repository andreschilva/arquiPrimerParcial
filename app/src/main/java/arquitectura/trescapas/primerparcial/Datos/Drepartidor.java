package arquitectura.trescapas.primerparcial.Datos;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import arquitectura.trescapas.primerparcial.DB.DBmanager1;
import arquitectura.trescapas.primerparcial.DB.DBmigrations;
import arquitectura.trescapas.primerparcial.Utils.IDatos1;

public class Drepartidor implements IDatos1 {
    private DBmanager1 db;

    private String id;
    private String nombre;
    private String apellido;
    private String celular;
    private Map<String,Object> row;

    public Drepartidor(Context context) {
        this.db = new DBmanager1(context);
        this.row = new HashMap<>();

        row.put(DBmigrations.REPARTIDOR_ID,"");
        row.put(DBmigrations.REPARTIDOR_NOMBRE,"");
        row.put(DBmigrations.REPARTIDOR_APELLIDO,"");
        row.put(DBmigrations.REPARTIDOR_CELULAR,"");
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
        return db.insert(DBmigrations.TABLA_REPARTIDOR,this.row);
    }

    @Override
    public void update(Map<String, Object> data) {

        db.updateData(DBmigrations.TABLA_REPARTIDOR,  data);
    }

    @Override
    public void delete(String id) {
        this.row.put("id",id);
        db.deleteData(DBmigrations.TABLA_REPARTIDOR, this.row.get("id").toString());
    }

    @Override
    public Drepartidor getById(String id) {
        Map<String,Object> repartidor =db.getById(DBmigrations.TABLA_REPARTIDOR,this.row,id);
        this.row.putAll(repartidor);
        return this;
    }

    @Override
    public List<Map<String,Object>> getAll() {
        return db.selectAll(DBmigrations.TABLA_REPARTIDOR,this.row);
    }

    public  Map<String,Object> row() {
        return this.row;
    }
}
