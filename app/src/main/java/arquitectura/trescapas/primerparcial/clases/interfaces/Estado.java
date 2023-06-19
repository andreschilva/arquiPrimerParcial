package arquitectura.trescapas.primerparcial.clases.interfaces;

import arquitectura.trescapas.primerparcial.clases.Pedido;

public interface Estado {
    void enProceso();
    void finalizado();
    void cancelado();
    public Boolean update(Pedido data );
    public Boolean delete(String id) ;
}
