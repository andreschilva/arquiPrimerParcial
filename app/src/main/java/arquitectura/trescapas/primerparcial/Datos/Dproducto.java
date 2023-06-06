package arquitectura.trescapas.primerparcial.Datos;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import arquitectura.trescapas.primerparcial.DB.DBmanager1;
import arquitectura.trescapas.primerparcial.DB.DBmigrations;
import arquitectura.trescapas.primerparcial.Utils.IDatos1;

public class Dproducto implements IDatos1 {
    private DBmanager1 db;

//    private String id;
//    private String nombre;
//    private String descripcion;
//    private String precio;
//    private String categoriaId;
//    private String foto;

    private Map<String,Object> row;

    public Dproducto(Context context) {
        this.db = new DBmanager1(context);
        this.row = new HashMap<>();

        row.put(DBmigrations.PRODUCTO_ID,"");
        row.put(DBmigrations.PRODUCTO_NOMBRE,"");
        row.put(DBmigrations.PRODUCTO_DESCRIPCION,"");
        row.put(DBmigrations.PRODUCTO_PRECIO,"");
        row.put(DBmigrations.PRODUCTO_CATEGORIAID,"");
        row.put(DBmigrations.PRODUCTO_FOTO,"");
    }


    public void setData(Map<String, Object> data) {
        this.row.putAll(data);
    }


    @Override
    public boolean save( ) {
        return db.insert(DBmigrations.TABLA_PRODUCTO,this.row);
    }

    @Override
    public void update(Map<String, Object> data) {

        db.updateData(DBmigrations.TABLA_PRODUCTO,  data);
    }

    @Override
    public void delete(String id) {
        this.row.put("id",id);
        db.deleteData(DBmigrations.TABLA_PRODUCTO, this.row.get("id").toString());
    }

    @Override
    public Dproducto getById(String id) {
        Map<String,Object> producto =db.getById(DBmigrations.TABLA_PRODUCTO,this.row,id);
        this.row.putAll(producto);
        return this;
    }

    @Override
    public List<Map<String,Object>> getAll() {
        return db.selectAll(DBmigrations.TABLA_PRODUCTO,this.row);
    }


    public  Map<String,Object> row() {
        return this.row;
    }
}
