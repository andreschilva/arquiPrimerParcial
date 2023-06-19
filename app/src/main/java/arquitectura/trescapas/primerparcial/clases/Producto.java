package arquitectura.trescapas.primerparcial.clases;

import androidx.annotation.Nullable;

import arquitectura.trescapas.primerparcial.clases.interfaces.Identificable;

public class Producto implements Identificable {
    private String id;
    private String nombre;
    private String descripcion;
    private String precio;
    private String categoriaId;
    private String foto;

    public Producto(){
        this.id = "";
    }

    public Producto(String id, String nombre, String descripcion, String precio, String foto,String categoriaId) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.foto = foto;
        this.categoriaId = categoriaId;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(String categoriaId) {
        this.categoriaId = categoriaId;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Producto producto = (Producto) obj;
        return (this.id.equals(producto.id) && this.nombre.equals(producto.nombre)
                && this.descripcion.equals(producto.descripcion) && this.foto.equals(producto.foto)
                && this.precio.equals(producto.precio) && this.categoriaId.equals(producto.categoriaId));
    }
}
