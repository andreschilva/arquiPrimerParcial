package arquitectura.trescapas.primerparcial.clases;

import arquitectura.trescapas.primerparcial.clases.interfaces.Identificable;

public class DetalleCotizacion implements Identificable {
    private String id;
    private String productoId;
    private String cotizacionId;
    private  int cantidad;

    public DetalleCotizacion() {
    }

    public DetalleCotizacion(String id, String productoId, String cotizacionId,int cantidad) {
        this.id = id;
        this.productoId = productoId;
        this.cotizacionId = cotizacionId;
        this.cantidad =  cantidad;
    }


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getProductoId() {
        return productoId;
    }

    public void setProductoId(String productoId) {
        this.productoId = productoId;
    }

    public String getCotizacionId() {
        return cotizacionId;
    }

    public void setCotizacionId(String cotizacionId) {
        this.cotizacionId = cotizacionId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
