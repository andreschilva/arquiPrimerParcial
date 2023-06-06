package arquitectura.trescapas.primerparcial.Utils;

import java.util.List;
import java.util.Map;

public interface IDatos<T> {
    // Métodos para la gestión de datos
    boolean save();
    void update(T obj);
    void delete(String id);
    T getById(String id);
    List<T> getAll();

}
