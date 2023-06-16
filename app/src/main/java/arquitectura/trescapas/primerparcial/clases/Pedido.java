package arquitectura.trescapas.primerparcial.clases;

import arquitectura.trescapas.primerparcial.interfaces.Identificable;

public class Pedido implements Identificable {
    private String id;
    private String fecha;
    private String total;
    private String estado;
    private String clienteId;
    private String repartidorId;

    public Pedido(){

    }

    public Pedido(String id,String fecha, String total, String estadoId, String clienteId, String repartidorId) {
        this.id = id;
        this.fecha = fecha;
        this.total = total;
        this.estado = estadoId;
        this.clienteId = clienteId;
        this.repartidorId = repartidorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getRepartidorId() {
        return repartidorId;
    }

    public void setRepartidorId(String repartidorId) {
        this.repartidorId = repartidorId;
    }
}
