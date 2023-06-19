package arquitectura.trescapas.primerparcial.Datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import arquitectura.trescapas.primerparcial.DB.DBmigrations;
import arquitectura.trescapas.primerparcial.clases.DetalleCotizacion;

public class DdetalleCotizacion extends Dato<DetalleCotizacion> {
    DetalleCotizacion detalleCotizacion;
    public DdetalleCotizacion(Context constext) {
        super(constext);
        this.detalleCotizacion = new DetalleCotizacion();
        super.tabla = DBmigrations.TABLA_DETALLE_COTIZACION;
    }

    @Override
    protected ContentValues getContentValues(DetalleCotizacion data) {
        ContentValues values = new ContentValues();

        if (data.getId() != "") {
            values.put(DBmigrations.DETALLE_COTIZACION_ID, data.getId());
        }
        values.put(DBmigrations.DETALLE_COTIZACION_COTIZACION_ID, data.getCotizacionId());
        values.put(DBmigrations.DETALLE_COTIZACION_PRODUCTO_ID, data.getProductoId());
        values.put(DBmigrations.DETALLE_COTIZACION_CANTIDAD, data.getCantidad());

        return values;
    }

    @Override
    protected DetalleCotizacion getdata(Cursor cursor) {
        DetalleCotizacion detalleCotizacion = new DetalleCotizacion();

        detalleCotizacion.setId(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.DETALLE_COTIZACION_ID)));
        detalleCotizacion.setCotizacionId(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.DETALLE_COTIZACION_COTIZACION_ID)));
        detalleCotizacion.setProductoId(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.DETALLE_COTIZACION_PRODUCTO_ID)));
        detalleCotizacion.setCantidad(cursor.getInt(cursor.getColumnIndexOrThrow(DBmigrations.DETALLE_COTIZACION_CANTIDAD)));
        return detalleCotizacion;
    }
}
