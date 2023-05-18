package arquitectura.trescapas.primerparcial.Utils;

import java.util.List;
import java.util.Map;

public interface IDatos {

    // Métodos para la gestión de datos
    boolean save();
    void update(Map<String,Object> data);
    void delete(String id);
    Object getById(String id);
    List<Map<String,Object>> getAll();

}
