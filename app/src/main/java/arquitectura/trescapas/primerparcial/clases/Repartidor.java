package arquitectura.trescapas.primerparcial.clases;

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

    public Repartidor crear(String id, String nom, String ape, String cel, String placa){
        Repartidor repartidor = new Repartidor();
        repartidor.setId(id);
        repartidor.setNombre(nom);
        repartidor.setApellido(ape);
        repartidor.setCelular(cel);
        repartidor.setPlaca(placa);
        return repartidor;
    };

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
}
