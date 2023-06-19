package arquitectura.trescapas.primerparcial.clases.interfaces;

import java.util.List;

public interface Negocio<T> {

    public void saveDatos(T data);

    public void updateDatos(T data );

    public List<T> getDatos();

    public void delete(String id) ;

    public T getById(String id);

}
