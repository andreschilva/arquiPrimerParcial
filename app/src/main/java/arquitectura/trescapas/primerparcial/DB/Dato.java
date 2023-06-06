package arquitectura.trescapas.primerparcial.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public abstract class Dato<T>  {
    private DBconexion conexion;
    private SQLiteDatabase basededatos;
    //private static  DBmanager instance ;
    protected String tabla;

    protected T obj;
    public Dato(Context constext) {
    this.conexion = new DBconexion(constext);
        this.open();
        this.close();
    }

    private Dato open() throws SQLException {
        basededatos = conexion.getWritableDatabase();

        return this;
    }

    private void close() {
        conexion.close();
    }

    public void setData(T obj) {
        this.obj = obj;
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
            System.out.println("error al obtener los datos" + e.getMessage());
       }
        return datos;
    }

    public T getById( String id) {
        try {

        String [] SelectionArgs = {id};
        this.open();
        Cursor cursor = basededatos.query(this.tabla, null ,"id = ?",SelectionArgs,null,null,null);
        T dato = getdata(cursor);

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

    protected abstract String[] getArguments(T obj);
    protected abstract ContentValues getContentValues(T data);
    protected abstract T getdata(Cursor cursor);

   }
