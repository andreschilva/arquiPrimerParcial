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
import arquitectura.trescapas.primerparcial.clases.DetallePedido;
import arquitectura.trescapas.primerparcial.clases.Pedido;

public class DdetallePedido extends Dato<DetallePedido> {

    public DdetallePedido(Context constext) {
        super(constext);
        super.tabla = DBmigrations.TABLA_DETALLE_PEDIDO;
    }

    @Override
    protected ContentValues getContentValues(DetallePedido data) {
        ContentValues values = new ContentValues();

        if (data.getId() != "") {
            values.put(DBmigrations.DETALLE_PEDIDO_ID, data.getId());
        }
        values.put(DBmigrations.DETALLE_PEDIDO_PEDIDO_ID, data.getPedidoId());
        values.put(DBmigrations.DETALLE_PEDIDO_PRODUCTO_ID, data.getProductoId());
        values.put(DBmigrations.DETALLE_PEDIDO_CANTIDAD, data.getCantidad());

        return values;
    }

    @Override
    protected DetallePedido getdata(Cursor cursor) {
        DetallePedido detallePedido = new DetallePedido();

        detallePedido.setId(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.DETALLE_PEDIDO_ID)));
        detallePedido.setPedidoId(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.DETALLE_PEDIDO_PEDIDO_ID)));
        detallePedido.setProductoId(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.DETALLE_PEDIDO_PRODUCTO_ID)));
        detallePedido.setCantidad(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.DETALLE_PEDIDO_CANTIDAD)));
        return detallePedido;
    }
}
