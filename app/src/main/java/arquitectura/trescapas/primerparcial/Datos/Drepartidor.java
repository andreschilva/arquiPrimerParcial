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
import arquitectura.trescapas.primerparcial.clases.Repartidor;

public class Drepartidor extends Dato<Repartidor> {

    public Drepartidor(Context context) {
        super(context);
        super.tabla = DBmigrations.TABLA_REPARTIDOR;
    }


    @Override
    protected ContentValues getContentValues(Repartidor data) {
        ContentValues values = new ContentValues();

        if (data.getId() != "") {
            values.put(DBmigrations.REPARTIDOR_ID, data.getId());
        }
        values.put(DBmigrations.REPARTIDOR_NOMBRE, data.getNombre());
        values.put(DBmigrations.REPARTIDOR_APELLIDO, data.getApellido());
        values.put(DBmigrations.REPARTIDOR_CELULAR, data.getCelular());
        values.put(DBmigrations.REPARTIDOR_PLACA, data.getPlaca());
        return values;
    }

    @Override
    protected Repartidor getdata(Cursor cursor) {

        Repartidor repartidor = new Repartidor();

        repartidor.setId(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.REPARTIDOR_ID)));
        repartidor.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.REPARTIDOR_NOMBRE)));
        repartidor.setApellido(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.REPARTIDOR_APELLIDO)));
        repartidor.setCelular(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.REPARTIDOR_CELULAR)));
        repartidor.setPlaca(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.REPARTIDOR_PLACA)));

        return  repartidor;
    }
}
