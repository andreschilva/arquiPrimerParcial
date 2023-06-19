package arquitectura.trescapas.primerparcial.Datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import arquitectura.trescapas.primerparcial.DB.DBmigrations;
import arquitectura.trescapas.primerparcial.clases.Repartidor;

public class Drepartidor extends Dato<Repartidor> {
    Repartidor repartidor;
    public Drepartidor(Context context) {
        super(context);
        this.repartidor = new Repartidor();
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
        values.put(DBmigrations.REPARTIDOR_FOTO,data.getFoto());
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
        repartidor.setFoto(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.REPARTIDOR_FOTO)));

        return  repartidor;
    }
}
