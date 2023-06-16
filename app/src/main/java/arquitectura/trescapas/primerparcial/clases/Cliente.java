package arquitectura.trescapas.primerparcial.clases;

import androidx.annotation.Nullable;

public class Cliente extends Persona{
    private String ubicacion;

    public Cliente() {
    }

    public Cliente(Cliente cliente) {
        super(cliente);
        if (cliente != null) {
            this.ubicacion = cliente.ubicacion;
        }
    }

    public static Cliente crear(String id, String nom, String ape, String cel, String ubi){
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNombre(nom);
        cliente.setApellido(ape);
        cliente.setCelular(cel);
        cliente.setUbicacion(ubi);
        return cliente;
    };

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public Persona clone() {
        return new Cliente(this);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cliente cliente = (Cliente) obj;
        return (this.getId().equals(cliente.getId()) && this.getNombre().equals(cliente.getNombre())
        && this.getApellido().equals(cliente.getApellido()) && this.getCelular().equals(cliente.getCelular())
        && this.getUbicacion().equals(cliente.getUbicacion()));
    }
}
