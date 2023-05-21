package arquitectura.trescapas.primerparcial.Datos;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import arquitectura.trescapas.primerparcial.DB.DBmanager;
import arquitectura.trescapas.primerparcial.DB.DBmigrations;
import arquitectura.trescapas.primerparcial.Utils.IDatos;

public class DdetallePedido implements IDatos {
    private DBmanager db;

    private String pedido_id;
    private String producto_id;
    private String cantidad;
    private Map<String,Object> row;

    public DdetallePedido(Context context) {
        this.db = new DBmanager(context);
        this.row = new HashMap<>();

        row.put(DBmigrations.DETALLE_PEDIDO_ID,"");
        row.put(DBmigrations.DETALLE_PEDIDO_PEDIDO_ID,"");
        row.put(DBmigrations.DETALLE_PEDIDO_PRODUCTO_ID,"");
        row.put(DBmigrations.DETALLE_PEDIDO_CANTIDAD,"");

    }



    public void setData(Map<String, Object> data) {
        this.row.putAll(data);

    }

//    public void setData(String nombre,String apellido, String celular) {
//        this.nombre = nombre;
//        this.apellido  = apellido;
//        this.celular = celular;
//    }

    @Override
    public boolean save( ) {
        return db.insert(DBmigrations.TABLA_DETALLE_PEDIDO,this.row);
    }

    @Override
    public void update(Map<String, Object> data) {

        db.updateData(DBmigrations.TABLA_DETALLE_PEDIDO,  data);
    }

    @Override
    public void delete(String id) {
        this.row.put("id",id);
        db.deleteData(DBmigrations.TABLA_DETALLE_PEDIDO, this.row.get("id").toString());
    }

    @Override
    public DdetallePedido getById(String id) {
        Map<String,Object> detallePedido =db.getById(DBmigrations.TABLA_DETALLE_PEDIDO,this.row,id);
        this.row.putAll(detallePedido);
        return this;
    }

    @Override
    public List<Map<String,Object>> getAll() {
        return db.selectAll(DBmigrations.TABLA_DETALLE_PEDIDO,this.row);
    }

    public  Map<String,Object> row() {
        return this.row;
    }
}
