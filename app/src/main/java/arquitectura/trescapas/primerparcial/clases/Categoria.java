package arquitectura.trescapas.primerparcial.clases;

import androidx.annotation.Nullable;

import arquitectura.trescapas.primerparcial.interfaces.Identificable;

public class Categoria implements Identificable {
    private String id;
    private String nombre;

    public Categoria(){

    }

    public Categoria(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Categoria categoria = (Categoria) obj;
        return this.id.equals(categoria.id) && this.nombre.equals(categoria.nombre);

    }
}
