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
import arquitectura.trescapas.primerparcial.clases.Categoria;
import arquitectura.trescapas.primerparcial.clases.Cliente;

public class Dcategoria extends Dato<Categoria> {

    public Dcategoria(Context constext) {
        super(constext);
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
