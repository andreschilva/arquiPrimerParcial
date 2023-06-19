package arquitectura.trescapas.primerparcial.Datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

import arquitectura.trescapas.primerparcial.DB.DBmigrations;
import arquitectura.trescapas.primerparcial.clases.patrones.estado.EstadoCancelado;
import arquitectura.trescapas.primerparcial.clases.patrones.estado.EstadoEnProceso;
import arquitectura.trescapas.primerparcial.clases.interfaces.Estado;
import arquitectura.trescapas.primerparcial.clases.patrones.estado.EstadoFinalizado;
import arquitectura.trescapas.primerparcial.clases.Pedido;

public class Dpedido extends Dato<Pedido>  {
    Map<String,Estado> estados;
    Estado estadoActual;
    Context context;
    Pedido pedido;

    public Dpedido(Context constext) {
        super(constext);
        this.pedido = new Pedido();
        this.context = constext;
        super.tabla = DBmigrations.TABLA_PEDIDO;
        this.cagarMapEstados();
    }

    public Dpedido(Context constext, Pedido pedido) {
        super(constext);
        this.pedido = pedido;
        this.context = constext;
        super.tabla = DBmigrations.TABLA_PEDIDO;
        this.cagarMapEstados();
        this.setEstado(this.pedido.getEstado());
    }

    private void cagarMapEstados(){
        this.estados = new HashMap<>();
        estados.put("En proceso",new EstadoEnProceso(this));
        estados.put("finalizado",new EstadoFinalizado(this));
        estados.put("cancelado",new EstadoCancelado(this));
    }

    public void setEstado(String estado){
        this.pedido.setEstado(estado);
        this.estadoActual = estados.get(estado);
    }

    @Override
    public boolean update(Pedido data) {
        return this.estadoActual.update(data);
    }

    @Override
    public boolean delete(String id) {
        return this.estadoActual.delete(id);
    }

    public void enProceso() {
        this.estadoActual.enProceso();
    }

    public void finalizado() {
        this.estadoActual.finalizado();
    }

    public void cancelado() {
        this.estadoActual.cancelado();
    }


    @Override
    public ContentValues getContentValues(Pedido data) {
       ContentValues values = new ContentValues();

       if (data.getId() != "") {
           values.put(DBmigrations.PEDIDO_ID, data.getId());
       }
       values.put(DBmigrations.PEDIDO_ESTADO, data.getEstado());
       values.put(DBmigrations.PEDIDO_TOTAL, data.getTotal());
       values.put(DBmigrations.PEDIDO_FECHA, data.getFecha());
       values.put(DBmigrations.PEDIDO_CLIENTE_ID, data.getClienteId());
       values.put(DBmigrations.PEDIDO_REPARTIDOR_ID, data.getRepartidorId());
       values.put(DBmigrations.PEDIDO_COTIZACION_ID, data.getCotizacionId());

        return values;
    }

    @Override
    protected Pedido getdata(Cursor cursor) {
        Pedido pedido = new Pedido();
        pedido.setId(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.PEDIDO_ID)));
        pedido.setEstado(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.PEDIDO_ESTADO)));
        pedido.setTotal(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.PEDIDO_TOTAL)));
        pedido.setFecha(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.PEDIDO_FECHA)));
        pedido.setClienteId(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.PEDIDO_CLIENTE_ID)));
        pedido.setRepartidorId(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.PEDIDO_REPARTIDOR_ID)));
        pedido.setCotizacionId(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.PEDIDO_COTIZACION_ID)));
        return pedido;
    }

    public Context getContext() {
        return this.context;
    }
    public String getTabla(){
        return super.tabla;
    }
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    public Pedido getPedido() {
        return this.pedido;
    }

    public void open() {
        super.open();
    }
    public void close() {
        super.close();
    }

    public SQLiteDatabase getBasededatos() {
        return super.basededatos;
    }

    public String [] getArguments(Pedido pedido) {
        return super.getArguments(pedido);
    }

}
