package arquitectura.trescapas.primerparcial.Datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import arquitectura.trescapas.primerparcial.DB.DBmanager1;
import arquitectura.trescapas.primerparcial.DB.DBmigrations;
import arquitectura.trescapas.primerparcial.DB.Dato;
import arquitectura.trescapas.primerparcial.Utils.IDatos1;
import arquitectura.trescapas.primerparcial.clases.Cliente;
import arquitectura.trescapas.primerparcial.clases.Producto;

public class Dproducto extends Dato<Producto> {

    public Dproducto(Context constext) {
        super(constext);
        super.tabla = DBmigrations.TABLA_PRODUCTO;
    }

    @Override
    protected ContentValues getContentValues(Producto data) {
        ContentValues values = new ContentValues();

        if (data.getId() != "") {
            values.put(DBmigrations.PRODUCTO_ID, data.getId());
        }
        values.put(DBmigrations.PRODUCTO_NOMBRE, data.getNombre());
        values.put(DBmigrations.PRODUCTO_DESCRIPCION, data.getDescripcion());
        values.put(DBmigrations.PRODUCTO_PRECIO, data.getPrecio());
        values.put(DBmigrations.PRODUCTO_FOTO, data.getFoto());
        values.put(DBmigrations.PRODUCTO_CATEGORIAID, data.getCategoriaId());

        return values;
    }

    @Override
    protected Producto getdata(Cursor cursor) {
        Producto producto = new Producto();
        producto.setId(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.PRODUCTO_ID)));
        producto.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.PRODUCTO_NOMBRE)));
        producto.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.PRODUCTO_DESCRIPCION)));
        producto.setPrecio(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.PRODUCTO_PRECIO)));
        producto.setFoto(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.PRODUCTO_FOTO)));
        producto.setCategoriaId(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.PRODUCTO_CATEGORIAID)));
        return producto;
    }
}
