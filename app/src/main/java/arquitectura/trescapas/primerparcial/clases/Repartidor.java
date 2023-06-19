package arquitectura.trescapas.primerparcial.clases;

import androidx.annotation.Nullable;

public class Repartidor extends Persona{

    private String placa;

    public Repartidor() {
    }

    public Repartidor(Repartidor repartidor) {
        super(repartidor);
        if (repartidor != null) {
            this.placa = repartidor.placa;
        }
    }

    public static Repartidor crear(String id, String nom, String ape, String cel, String placa, String foto){
        Repartidor repartidor = new Repartidor();
        repartidor.setId(id);
        repartidor.setNombre(nom);
        repartidor.setApellido(ape);
        repartidor.setCelular(cel);
        repartidor.setPlaca(placa);
        repartidor.setFoto(foto);
        return repartidor;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    @Override
    public Persona clone() {
        return new Repartidor(this);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Repartidor repartidor = (Repartidor) obj;
        return (this.getId().equals(repartidor.getId()) && this.getNombre().equals(repartidor.getNombre())
                && this.getApellido().equals(repartidor.getApellido()) && this.getCelular().equals(repartidor.getCelular())
                && this.getPlaca().equals(repartidor.getPlaca()));
    }
}
