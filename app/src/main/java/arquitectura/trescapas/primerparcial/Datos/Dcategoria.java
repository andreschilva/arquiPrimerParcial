package arquitectura.trescapas.primerparcial.Datos;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import arquitectura.trescapas.primerparcial.DB.DBmanager1;
import arquitectura.trescapas.primerparcial.DB.DBmigrations;
import arquitectura.trescapas.primerparcial.Utils.IDatos1;

public class Dcategoria implements IDatos1 {
    private DBmanager1 db;

    private String id;
    private String nombre;
    private Map<String,Object> row;

    public Dcategoria(Context context) {
        this.db = new DBmanager1(context);
        this.row = new HashMap<>();

        row.put(DBmigrations.CATEGORIA_ID,"");
        row.put(DBmigrations.CATEGORIA_NOMBRE,"");
    }


    public void setData(Map<String, Object> data) {
        this.row.putAll(data);

    }

    @Override
    public boolean save( ) {

        return db.insert(DBmigrations.TABLA_CATEGORIA,this.row);

    }

    @Override
    public void update(Map<String, Object> data) {

        db.updateData(DBmigrations.TABLA_CATEGORIA,  data);
    }

    @Override
    public void delete(String id) {
        this.row.put("id",id);
        db.deleteData(DBmigrations.TABLA_CATEGORIA, this.row.get("id").toString());
    }

    @Override
    public Dcategoria getById(String id) {
        Map<String,Object> categoria =db.getById(DBmigrations.TABLA_CATEGORIA,this.row,id);
        this.row.putAll(categoria);
        return this;
    }

    @Override
    public List<Map<String,Object>> getAll() {
        return db.selectAll(DBmigrations.TABLA_CATEGORIA,this.row);
    }


    public  Map<String,Object> row() {
        return this.row;
    }
 }
