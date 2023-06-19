package arquitectura.trescapas.primerparcial.Datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import arquitectura.trescapas.primerparcial.DB.DBmigrations;
import arquitectura.trescapas.primerparcial.Negocio.Ncategoria;
import arquitectura.trescapas.primerparcial.clases.Categoria;

public class Dcategoria extends Dato<Categoria> {
    Categoria categoria;
    public Dcategoria(Context constext) {
        super(constext);
        categoria = new Categoria();
        super.tabla = DBmigrations.TABLA_CATEGORIA;
    }


    @Override
    protected ContentValues getContentValues(Categoria data) {
        ContentValues values = new ContentValues();

        if (data.getId() != "") {
            values.put(DBmigrations.CATEGORIA_ID, data.getId());
        }
        values.put(DBmigrations.CATEGORIA_NOMBRE, data.getNombre());

        return values;
    }

    @Override
    protected Categoria getdata(Cursor cursor) {

        Categoria categoria = new Categoria();

        categoria.setId(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.CATEGORIA_ID)));
        categoria.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.CATEGORIA_NOMBRE)));


        return categoria;
    }
}
