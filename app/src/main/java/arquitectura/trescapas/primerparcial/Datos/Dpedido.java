package arquitectura.trescapas.primerparcial.Datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

import arquitectura.trescapas.primerparcial.DB.DBmigrations;
import arquitectura.trescapas.primerparcial.DB.Dato;
import arquitectura.trescapas.primerparcial.clases.EstadoCancelado;
import arquitectura.trescapas.primerparcial.clases.EstadoEnProceso;
import arquitectura.trescapas.primerparcial.interfaces.Estado;
import arquitectura.trescapas.primerparcial.clases.EstadoFinalizado;
import arquitectura.trescapas.primerparcial.interfaces.Identificable;
import arquitectura.trescapas.primerparcial.clases.Pedido;

public class Dpedido extends Dato<Dpedido> implements Identificable {
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
    public boolean update(Dpedido data) {
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
    public ContentValues getContentValues(Dpedido data) {
       ContentValues values = new ContentValues();

       if (data.pedido.getId() != "") {
           values.put(DBmigrations.PEDIDO_ID, data.pedido.getId());
       }
       values.put(DBmigrations.PEDIDO_ESTADO, data.pedido.getEstado());
       values.put(DBmigrations.PEDIDO_TOTAL, data.pedido.getTotal());
       values.put(DBmigrations.PEDIDO_FECHA, data.pedido.getFecha());
       values.put(DBmigrations.PEDIDO_CLIENTE_ID, data.pedido.getClienteId());
       values.put(DBmigrations.PEDIDO_REPARTIDOR_ID, data.pedido.getRepartidorId());
       return values;
    }

    @Override
    protected Dpedido getdata(Cursor cursor) {
        Dpedido dpedido = new Dpedido(context);
        dpedido.pedido.setId(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.PEDIDO_ID)));
        dpedido.pedido.setEstado(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.PEDIDO_ESTADO)));
        dpedido.pedido.setTotal(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.PEDIDO_TOTAL)));
        dpedido.pedido.setFecha(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.PEDIDO_FECHA)));
        dpedido.pedido.setClienteId(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.PEDIDO_CLIENTE_ID)));
        dpedido.pedido.setRepartidorId(cursor.getString(cursor.getColumnIndexOrThrow(DBmigrations.PEDIDO_REPARTIDOR_ID)));
        dpedido.setEstado(dpedido.pedido.getEstado());
        return dpedido;
    }

    @Override
    public String[] getArguments(Dpedido data) {
        return new String[]{data.getPedido().getId().toString()};
    }


    @Override
    public String getId() {
        return this.pedido.getId();
    }

    @Override
    public void setId(String id) {
        this.pedido.setId(id);
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

}
