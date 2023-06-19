package arquitectura.trescapas.primerparcial.clases;

import androidx.annotation.Nullable;

import arquitectura.trescapas.primerparcial.clases.interfaces.Identificable;
import arquitectura.trescapas.primerparcial.clases.interfaces.Nombrable;

public class Cotizacion  implements Identificable , Nombrable {
    String id;
    String nombre;
    String fecha;
    float total;

    public Cotizacion(){

    }

    public Cotizacion(String id, String nombre, String fecha,float total) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.total = total;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cotizacion cotizacion = (Cotizacion) obj;
        return (this.getId().equals(cotizacion.getId()) && this.getNombre().equals(cotizacion.getNombre())
                && this.fecha.equals(cotizacion.fecha));
    }
}
