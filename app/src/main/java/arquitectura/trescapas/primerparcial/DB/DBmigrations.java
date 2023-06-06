package arquitectura.trescapas.primerparcial.DB;

public class DBmigrations {

    //TABLA CLIENTE
    public static final String TABLA_CLIENTE = "clientes";
    public static final String CLIENTE_ID = "id";
    public static final String CLIENTE_NOMBRE = "nombre";
    public static final String CLIENTE_APELLIDO = "apellido";
    public static final String CLIENTE_CELULAR = "celular";
    public static final String CLIENTE_UBICACION = "ubicacion";

    public static final String TABLA_CLIENTE_CREATE =
            "create table " + TABLA_CLIENTE +
                    "("
                    + CLIENTE_ID + " integer not null primary key autoincrement, "
                    + CLIENTE_NOMBRE + " text not null, "
                    + CLIENTE_APELLIDO + " text not null, "
                    + CLIENTE_CELULAR + " text not null, "
                    + CLIENTE_UBICACION + " text not null)";


    //TABLA REPARTIDOR
    public static final String TABLA_REPARTIDOR = "repartidores";
    public static final String REPARTIDOR_ID = "id";
    public static final String REPARTIDOR_NOMBRE = "nombre";
    public static final String REPARTIDOR_APELLIDO = "apellido";
    public static final String REPARTIDOR_CELULAR = "celular";

    public static final String TABLA_REPARTIDOR_CREATE =
            "create table " + TABLA_REPARTIDOR +
                    "("
                    + REPARTIDOR_ID + " integer not null primary key autoincrement, "
                    + REPARTIDOR_NOMBRE + " text not null, "
                    + REPARTIDOR_APELLIDO + " text not null, "
                    + REPARTIDOR_CELULAR + " text not null)";

    //TABLA CATEGORIA
    public static final String TABLA_CATEGORIA = "categorias";
    public static final String CATEGORIA_ID = "id";
    public static final String CATEGORIA_NOMBRE = "nombre";

    public static final String TABLA_CATEGORIA_CREATE =
            "create table " + TABLA_CATEGORIA +
                    "("
                    + CATEGORIA_ID + " integer not null primary key autoincrement, "
                    + CATEGORIA_NOMBRE + " text not null)";


    //TABLA PRODUCTO
    public static final String TABLA_PRODUCTO = "productos";
    public static final String PRODUCTO_ID = "id";
    public static final String PRODUCTO_NOMBRE = "nombre";
    public static final String PRODUCTO_DESCRIPCION = "descripcion";
    public static final String PRODUCTO_PRECIO = "precio";
    public static final String PRODUCTO_FOTO = "foto";
    public static final String PRODUCTO_CATEGORIAID= "categoria_id";


    public static final String TABLA_PRODUCTO_CREATE =
            "create table " + TABLA_PRODUCTO +
                    "("
                    + PRODUCTO_ID + " integer not null primary key autoincrement, "
                    + PRODUCTO_NOMBRE + " text not null, "
                    + PRODUCTO_DESCRIPCION + " text not null, "
                    + PRODUCTO_PRECIO + " integer not null, "
                    + PRODUCTO_FOTO + " text not null, "
                    + PRODUCTO_CATEGORIAID + " integer not null, "
                    +"foreign key ("+PRODUCTO_CATEGORIAID+") references "+TABLA_CATEGORIA+"(id) )";

    //TABLA PEDIDO
    public static final String TABLA_PEDIDO = "pedidos";
    public static final String PEDIDO_ID = "id";
    public static final String PEDIDO_ESTADO = "estado";
    public static final String PEDIDO_TOTAL = "total";
    public static final String PEDIDO_FECHA = "fecha";
    public static final String PEDIDO_CLIENTE_ID = "cliente_id";
    public static final String PEDIDO_REPARTIDOR_ID = "repartidor_id";




    public static final String TABLA_PEDIDO_CREATE =
            "create table " + TABLA_PEDIDO +
                    "("
                    + PEDIDO_ID+ " integer not null primary key autoincrement, "
                    + PEDIDO_ESTADO + " text not null, "
                    + PEDIDO_TOTAL + " float not null, "
                    + PEDIDO_FECHA + " date not null, "
                    + PEDIDO_CLIENTE_ID + " integer not null, "
                    + PEDIDO_REPARTIDOR_ID + " integer not null, "
                    +"foreign key ("+PEDIDO_CLIENTE_ID+") references "+TABLA_CLIENTE+"(id), "
                    +"foreign key ("+PEDIDO_REPARTIDOR_ID+") references "+TABLA_REPARTIDOR+"(id) )";

    //TABLA DETALLE_PEDIDO
    public static final String TABLA_DETALLE_PEDIDO = "detalle_pedidos";
    public static final String DETALLE_PEDIDO_ID = "id";
    public static final String DETALLE_PEDIDO_PEDIDO_ID = "pedido_id";
    public static final String DETALLE_PEDIDO_PRODUCTO_ID = "producto_id";
    public static final String DETALLE_PEDIDO_CANTIDAD = "cantidad";


    public static final String TABLA_DETALLE_PEDIDO_CREATE =
            "create table " + TABLA_DETALLE_PEDIDO +
                    "("
                    + DETALLE_PEDIDO_ID+ " integer not null primary key autoincrement, "
                    + DETALLE_PEDIDO_PEDIDO_ID + " integer not null, "
                    + DETALLE_PEDIDO_PRODUCTO_ID + " integer not null, "
                    + DETALLE_PEDIDO_CANTIDAD + " text not null, "
                    +"foreign key ("+DETALLE_PEDIDO_PEDIDO_ID+") references "+TABLA_PEDIDO+"(id) "
                    + "foreign key ("+DETALLE_PEDIDO_PRODUCTO_ID+") references "+TABLA_PRODUCTO+"(id) )";


}

