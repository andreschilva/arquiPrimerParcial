package arquitectura.trescapas.primerparcial.clases;

import arquitectura.trescapas.primerparcial.clases.interfaces.Identificable;
import arquitectura.trescapas.primerparcial.clases.interfaces.Nombrable;

public abstract class Persona implements Identificable, Nombrable {
    private String id;
    private String nombre;
    private String apellido;
    private String celular;
    private String foto;

    public Persona() {
    }

    public Persona(Persona persona) {
        if (persona != null) {
            this.id = persona.id;
            this.nombre = persona.nombre;
            this.apellido = persona.apellido;
            this.celular = persona.celular;
            this.foto = persona.foto;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public abstract Persona clone();
}
