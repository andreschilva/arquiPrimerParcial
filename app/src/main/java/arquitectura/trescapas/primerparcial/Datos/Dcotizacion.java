package arquitectura.trescapas.primerparcial.Datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import arquitectura.trescapas.primerparcial.DB.DBmigrations;
import arquitectura.trescapas.primerparcial.clases.Cotizacion;

public class Dcotizacion extends Dato<Cotizacion> {
    Cotizacion cotizacion;
    public Dcotizacion(Context constext) {
        super(constext);
        this.cotizacion = new Cotizacion();
        super.tabla = DBmigrations.TABLA_COTIZACION;
    }

    @Override
    protected ContentValues getContentValues(Cotizacion data) {
        ContentValues values = new ContentValues();

        if (data.getId() != "") {
            values.put(DBmigrations.COTIZACION_ID, data.getId());
        }
        values.put(DBmigrations.COTIZACION_NOMBRE, data.getNombre());
        values.put(DBmigrations.COTIZACION_FECHA, data.getFecha());
        values.put(DBmigrations.COTIZACION_TOTAL, data.getTotal());
        return values;
    }

    @Override
    protected Cotizacion getdata(Cursor cursor) {
        Cotizacion cotizacion = new Cotizacion();

        cotizacion.setId(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.COTIZACION_ID)));
        cotizacion.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.COTIZACION_NOMBRE)));
        cotizacion.setFecha(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.COTIZACION_FECHA)));
        cotizacion.setFecha(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.COTIZACION_FECHA)));
        cotizacion.setTotal(cursor.getFloat(cursor.getColumnIndexOrThrow(DBmigrations.COTIZACION_TOTAL)));

        return cotizacion;
    }
}
