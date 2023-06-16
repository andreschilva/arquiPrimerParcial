package arquitectura.trescapas.primerparcial.Datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.HashMap;
import java.util.Map;

import arquitectura.trescapas.primerparcial.DB.Dato;
import arquitectura.trescapas.primerparcial.DB.DBmigrations;
import arquitectura.trescapas.primerparcial.clases.Cliente;

public class Dcliente extends Dato<Cliente> {

    public Dcliente(Context context) {
        super(context);
        super.tabla = DBmigrations.TABLA_CLIENTE;
    }

    @Override
    protected ContentValues getContentValues(Cliente data) {
        ContentValues values = new ContentValues();

        if (data.getId() != "") {
            values.put(DBmigrations.CLIENTE_ID, data.getId());
        }
        values.put(DBmigrations.CLIENTE_NOMBRE, data.getNombre());
        values.put(DBmigrations.CLIENTE_APELLIDO, data.getApellido());
        values.put(DBmigrations.CLIENTE_CELULAR, data.getCelular());
        values.put(DBmigrations.CLIENTE_UBICACION, data.getUbicacion());
        return values;
    }

    @Override
    protected Cliente getdata(Cursor cursor) {

        Cliente cliente = new Cliente();

        cliente.setId(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.CLIENTE_ID)));
        cliente.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.CLIENTE_NOMBRE)));
        cliente.setApellido(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.CLIENTE_APELLIDO)));
        cliente.setCelular(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.CLIENTE_CELULAR)));
        cliente.setUbicacion(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.CLIENTE_UBICACION)));

        return cliente;
    }
}


