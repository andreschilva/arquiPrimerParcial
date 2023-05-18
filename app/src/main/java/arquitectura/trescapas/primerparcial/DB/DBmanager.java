package arquitectura.trescapas.primerparcial.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DBmanager {

    private DBconexion conexion;
    private SQLiteDatabase basededatos;

    public DBmanager(Context constext) {

        this.conexion = new DBconexion(constext);
        this.open();
//        Map<String, Object> data = new HashMap<>();

//        data.put("id",1);
//        data.put(DBmigrations.CLIENTE_NOMBRE,"Andres");
//        data.put(DBmigrations.CLIENTE_APELLIDO,"Silva");
//        data.put(DBmigrations.CLIENTE_CELULAR,"6396332");
//
//        this.insert(DBmigrations.TABLA_CLIENTE,data);

        this.close();
    }

    public DBmanager open() throws SQLException {
        basededatos = conexion.getWritableDatabase();

        return this;
    }

    public void close() {
        conexion.close();
    }

    public boolean  insert(String tabla,Map<String,Object> data) {
        //para validaar los datos
        String condicion = "";
        String[] argumentos = new String[data.size()-1];

        ContentValues content = getContentValues(data);

        this.open();
        int i = 0;
        for (String key : data.keySet()) {
            if (key != "id") {
                Object value = data.get(key);

                if (i != data.size()-2) {
                    condicion += key + " = ? AND ";
                    argumentos[i] = value.toString();
                } else {
                    condicion += key + " = ?";
                    argumentos[i] = value.toString();
                }
                i++;
            }

        }

        Cursor cursor = basededatos.query(tabla, null, condicion, argumentos, null, null, null);
        //si la cantidad de filas es = 0 no hay datos repetidos
        if (cursor.getCount() == 0) {
            long resultado = basededatos.insert(tabla,null,content);
            this.close();
            return  true;
        } else {
            this.close();
            return false;
        }




    }

    private ContentValues getContentValues(Map<String, Object> data) {
        ContentValues content = new ContentValues();

        for (String key : data.keySet()) {
            if (key != "id"){
                Object value = data.get(key);
                if (value instanceof String) {
                    content.put(key, (String) value);
                } else if (value instanceof Integer) {
                    content.put(key, (Integer) value);
                } else if (value instanceof Long) {
                    content.put(key, (Long) value);
                } else if (value instanceof Double) {
                    content.put(key, (Double) value);
                } else if (value instanceof Float) {
                    content.put(key, (Float) value);
                } else if (value instanceof byte[]) {
                    content.put(key, (byte[]) value);
                } else {
                    content.put(key, value.toString());
                }
            }

        }
        return content;
    }

    public List<Map<String,Object>> selectAll(String tabla,Map<String,Object> data) {
        List<Map<String,Object>> datos= new ArrayList<>();
       this.open();
       //String [] columns= {DBmigrations.CLIENTE_NOMBRE,DBmigrations.CLIENTE_APELLIDO,DBmigrations.CLIENTE_CELULAR};
        Cursor cursor = basededatos.query(tabla, null ,null,null,null,null,null);
        while (cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<>();
            for (String key : data.keySet()) {
                map.put(key, cursor.getString(cursor.getColumnIndexOrThrow(key)));
            }
            datos.add(map);

        }
        cursor.close();
        this.close();
        return datos;
    }

    public  Map<String,Object> getById(String tabla,Map<String,Object> data, String id) {
        String [] SelectionArgs = {id};
        this.open();
        Cursor cursor = basededatos.query(tabla, null ,"id = ?",SelectionArgs,null,null,null);
        Map<String, Object> datos = new HashMap<>();
        while (cursor.moveToNext()) {
            for (String key : data.keySet()) {
                datos.put(key, cursor.getString(cursor.getColumnIndexOrThrow(key)));
            }


        }
        cursor.close();
        this.close();
        return datos;
    }

    public void  updateData(String tabla, Map<String,Object> data) {
        ContentValues values = getContentValues(data);
        String [] wereArgs = {data.get("id").toString()};
        this.open();
            basededatos.update(tabla, values,"id = ?" , wereArgs);
        this.close();
    }

    public void deleteData(String tabla, String id) {
        String [] wereArgs = {id};
        this.open();
        basededatos.delete(tabla, "id = ?" , wereArgs);
        this.close();
    }
}
