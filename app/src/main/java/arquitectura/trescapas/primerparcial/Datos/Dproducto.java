package arquitectura.trescapas.primerparcial.Datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import arquitectura.trescapas.primerparcial.DB.DBmigrations;
import arquitectura.trescapas.primerparcial.clases.Producto;

public class Dproducto extends Dato<Producto> {
Producto producto;
    public Dproducto(Context constext) {
        super(constext);
        this.producto = new Producto();
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
