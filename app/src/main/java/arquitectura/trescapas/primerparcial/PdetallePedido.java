package arquitectura.trescapas.primerparcial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import arquitectura.trescapas.primerparcial.Negocio.Ncategoria;
import arquitectura.trescapas.primerparcial.Negocio.Ncliente;
import arquitectura.trescapas.primerparcial.Negocio.NdetallePedido;
import arquitectura.trescapas.primerparcial.Negocio.Npedido;
import arquitectura.trescapas.primerparcial.Negocio.Nproducto;
import arquitectura.trescapas.primerparcial.Negocio.Nrepartidor;
import arquitectura.trescapas.primerparcial.Utils.Utils;
import arquitectura.trescapas.primerparcial.clases.Categoria;
import arquitectura.trescapas.primerparcial.clases.Cliente;
import arquitectura.trescapas.primerparcial.clases.DetallePedido;
import arquitectura.trescapas.primerparcial.clases.Pedido;
import arquitectura.trescapas.primerparcial.clases.Producto;
import arquitectura.trescapas.primerparcial.clases.Repartidor;

public class PdetallePedido extends AppCompatActivity {
    Pedido pedido;
    NdetallePedido detallePedido;
    Ncliente cliente;
    Nrepartidor repartidor;
    Nproducto producto;
    Ncategoria categoria;
    Npedido nPedido;

    List<Producto> listProductos;
    List<Categoria> lisCategorias;
    List<DetallePedido> listDetallePedido;
    List<Producto>  listProductosDelPedido;
    RecyclerView rv2;
    AdaptadorProducto aP2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdetalle_pedido);
        nPedido = new Npedido(this);
        String pedidoId = getIntent().getStringExtra("pedido");
        pedido= nPedido.getById(pedidoId).getPedido();

        producto = new Nproducto(this);
        categoria = new Ncategoria(this);
        detallePedido = new NdetallePedido(this);
        cliente = new Ncliente(this);
        repartidor = new Nrepartidor(this);

        listProductos = producto.getDatos();
        lisCategorias= categoria.getDatos();
        listDetallePedido = detallePedido.getDatos();
        listProductosDelPedido = getListProductosDelPedido();

        rv2 = findViewById(R.id.rv2);
        LinearLayoutManager l=new LinearLayoutManager(this);
        rv2.setLayoutManager(l);
        aP2= new AdaptadorProducto();
        rv2.setAdapter(aP2);

    }

    private List<Producto> getListProductosDelPedido() {
        listDetallePedido = detallePedido.getDatos();
        List<Producto>  resultado = new ArrayList<>();
        for (DetallePedido pedidoActual: listDetallePedido ) {
            String pedidoId = pedidoActual.getPedidoId();
            if (pedidoId.equals(pedido.getId())) {
                String productoId = pedidoActual.getProductoId();
                String cantidad = pedidoActual.getCantidad();
                for (Producto productoActual: listProductos ) {
                    if (productoId.equals(productoActual.getId())) {
                        resultado.add(productoActual);
                        break;
                    }
                }
            }
        }
        return resultado;
    }

    public void agregarProductos(View v) {
        Intent intento = new Intent(PdetallePedido.this, EditarProducto.class);
        intento.putExtra("pedido", (Serializable) pedido.getId());
        startActivity(intento);
        aP2.notifyDataSetChanged();
    }

    public  void enviarDatosARepartidor(View v) {
        Cliente clienteDelPedido= cliente.getById(pedido.getClienteId());
        Repartidor repartidorDelPedido= repartidor.getById(pedido.getRepartidorId());
        String numeroRepartidor = repartidorDelPedido.getCelular();
        String ubicacionCliente = clienteDelPedido.getUbicacion();

        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Lista de productos:\n");

        double total = 0.0;
        for (Producto producto : this.listProductosDelPedido) {
            String nombre =  producto.getNombre();
            double precio =Double.parseDouble( producto.getPrecio());
            int cantidad = 1;

            mensaje.append("Nombre: ").append(nombre).append("\n");
            mensaje.append("Cantidad: ").append(cantidad).append("\n");
            mensaje.append("-----------------\n");

            total += precio*cantidad;
        }
        mensaje.append("Ubicacion: ").append("\n");
        mensaje.append(ubicacionCliente).append("\n");
        mensaje.append("Total a Cobrar: ").append(total).append("\n");

        //Toast.makeText(this,  mensaje, Toast.LENGTH_SHORT).show();

        //boolean installed = appInstalledOrNot("com.whatsapp");
        //if (installed) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+591"+numeroRepartidor+ "&text="+mensaje));
        startActivity(intent);
//        }else {
//            Toast.makeText(this, "Whasapp no esta instalado en tu dispositivo", Toast.LENGTH_SHORT).show();
//        }
    }

    public void listar() {
        this.listProductosDelPedido = getListProductosDelPedido();
        aP2.notifyDataSetChanged();
    }

    private class AdaptadorProducto extends RecyclerView.Adapter<AdaptadorProducto.AdaptadorProductoHolder> {

        @NonNull
        @Override
        public AdaptadorProducto.AdaptadorProductoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdaptadorProducto.AdaptadorProductoHolder(getLayoutInflater().inflate(R.layout.itemseleccionarproductos,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull AdaptadorProducto.AdaptadorProductoHolder holder, int position) {
            holder.imprimir(position);
        }

        @Override
        public int getItemCount() {
            return listProductosDelPedido.size();
        }

        public class AdaptadorProductoHolder extends RecyclerView.ViewHolder  {
            TextView edNombre, edPrecio, edCantidad;
            CheckBox checkBox;
            ImageButton btnEliminar;
            ImageView ivFoto;

            public AdaptadorProductoHolder(@NonNull View itemView) {
                super(itemView);
                edNombre = itemView.findViewById(R.id.tvnombreDetalle);
                edPrecio = itemView.findViewById(R.id.tvPrecioDetalle);
                edCantidad = itemView.findViewById(R.id.edCantidadDetalle);
                checkBox = itemView.findViewById(R.id.checkBoxDetalle);
                ivFoto = itemView.findViewById(R.id.ivProductoDetalle);
                btnEliminar = itemView.findViewById(R.id.btnEliminar);

                checkBox.setVisibility(View.GONE);

                btnEliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        eliminar();
                    }
                });

            }

            public void imprimir(int position) {
                try {

                    Producto productoActual = listProductosDelPedido.get(position);
                    String fotoActual = productoActual.getFoto();
                    FileInputStream fileInputStream = openFileInput(fotoActual);
                    Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);

                    ivFoto.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            // Elimina el listener para evitar llamadas adicionales
                            ivFoto.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                            // Obtiene las dimensiones del ImageView
                            int targetWidth = ivFoto.getWidth();
                            int targetHeight = ivFoto.getHeight();

                            // Crea el nuevo Bitmap redimensionado
                            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false);

                            // Asigna el nuevo Bitmap redimensionado al ImageView
                            ivFoto.setImageBitmap(resizedBitmap);
                        }
                    });

                    //ivFoto.setImageBitmap(resizedBitmap);
                    fileInputStream.close();

                    edNombre.setText(productoActual.getNombre());
                    edPrecio.setText(productoActual.getPrecio());
                    edCantidad.setText(listDetallePedido.get(position).getCantidad());

                }catch (Exception e) {
                    Utils.mensaje(PdetallePedido.this,e.getMessage());
                }

            }

            private void eliminar() {
                AlertDialog.Builder builder = new AlertDialog.Builder(PdetallePedido.this);
                builder.setMessage("Esta seguro que desea Eliminar este producto?");

                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = getLayoutPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            DetallePedido detallePedidoActual = listDetallePedido.get(position);
                            String id = detallePedidoActual.getId();

                            //eliminar detalle del pedido
                            detallePedido.delete(id);
                            listar();
                        }

                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create().show();

            }
        }
    }
}


