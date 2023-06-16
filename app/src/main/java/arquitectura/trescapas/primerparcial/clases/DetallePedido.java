package arquitectura.trescapas.primerparcial.clases;

import arquitectura.trescapas.primerparcial.interfaces.Identificable;

public class DetallePedido implements Identificable {
    private String id;
    private String cantidad;
    private String pedidoId;
    private String productoId;

    public  DetallePedido(){

    }
    public DetallePedido(String id, String cantidad, String pedidoId, String productoId) {
        this.id = id;
        this.cantidad = cantidad;
        this.pedidoId = pedidoId;
        this.productoId = productoId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(String pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getProductoId() {
        return productoId;
    }

    public void setProductoId(String productoId) {
        this.productoId = productoId;
    }
}
