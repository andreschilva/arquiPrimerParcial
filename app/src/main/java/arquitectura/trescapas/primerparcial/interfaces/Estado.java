package arquitectura.trescapas.primerparcial.interfaces;

import arquitectura.trescapas.primerparcial.Datos.Dpedido;

public interface Estado {
    void enProceso();
    void finalizado();
    void cancelado();
    public Boolean update(Dpedido data );
    public Boolean delete(String id) ;
}
