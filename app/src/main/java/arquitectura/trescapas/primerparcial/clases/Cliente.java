package arquitectura.trescapas.primerparcial.clases;

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
}
