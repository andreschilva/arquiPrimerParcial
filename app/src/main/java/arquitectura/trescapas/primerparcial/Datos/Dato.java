package arquitectura.trescapas.primerparcial.Datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import arquitectura.trescapas.primerparcial.DB.DBconexion;
import arquitectura.trescapas.primerparcial.clases.interfaces.Identificable;

public abstract class Dato<T extends Identificable>  {
    private DBconexion conexion;
    protected SQLiteDatabase basededatos;
    //private static  DBmanager instance ;
    protected String tabla;

    public Dato(Context constext) {
    this.conexion = new DBconexion(constext);
        this.open();
        this.close();
    }

    protected void open() throws SQLException {
        basededatos = conexion.getWritableDatabase();
    }

    protected void close() {
        conexion.close();
    }


    public Boolean save(T data) {
        try {
            this.open();
            ContentValues content = getContentValues(data);

            long resultado = basededatos.insert(this.tabla,null,content);
                this.close();
                return  resultado != -1;
        }catch (Exception e){
            System.out.println("error al guardar" + e.getMessage());
            return false;
        }
    }

    public List<T> getAll() {
        List<T> datos= new ArrayList<>();
        try {
           this.open();
           //String [] columns= {DBmigrations.CLIENTE_NOMBRE,DBmigrations.CLIENTE_APELLIDO,DBmigrations.CLIENTE_CELULAR};
           Cursor cursor = basededatos.query(this.tabla, null ,null,null,null,null,null);
           while (cursor.moveToNext()) {
               T dato = getdata(cursor);
               datos.add(dato);
           }
           cursor.close();
           this.close();
           return datos;
       }catch (Exception e){
            System.out.println("error al obtener los datos " + e.getMessage());
       }
        return datos;
    }

    public T getById( String id) {
        try {

        String [] SelectionArgs = {id};
        this.open();
        Cursor cursor = basededatos.query(this.tabla, null ,"id = ?",SelectionArgs,null,null,null);
        T dato = null;
        while (cursor.moveToNext()) {
             dato = getdata(cursor);
        }

            cursor.close();
            return dato;
        }catch (Exception e) {
            System.out.println("error al actualizar los datos" + e.getMessage());
        }finally {
            this.close();
        }
        return null;
    }

    public boolean update(T data) {
        try {
            ContentValues values = getContentValues(data);
            String [] wereArgs = getArguments(data);
            this.open();
            int resultado = basededatos.update(this.tabla, values,"id = ?" , wereArgs);
            this.close();
            return resultado > 0;
        }catch (Exception e) {
            System.out.println("error al actualizar los datos" + e.getMessage());
        }

        return false;
    }

    public boolean delete(String id) {
         try {
             String[] wereArgs = {id};
             this.open();
             int resultado = basededatos.delete(this.tabla, "id = ?", wereArgs);
             this.close();
             return resultado > 0;
         }catch (Exception e) {
            System.out.println("error al eliminar los datos" + e.getMessage());
        }
        return false;
    }

    protected String[] getArguments(T data) {
        return new String[]{data.getId().toString()};
    }
    protected abstract ContentValues getContentValues(T data);
    protected abstract T getdata(Cursor cursor);

   }
