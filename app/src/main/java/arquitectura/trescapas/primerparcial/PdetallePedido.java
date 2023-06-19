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
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import arquitectura.trescapas.primerparcial.Negocio.Ncliente;
import arquitectura.trescapas.primerparcial.Negocio.Ncotizacion;
import arquitectura.trescapas.primerparcial.Negocio.NdetalleCotizacion;
import arquitectura.trescapas.primerparcial.Negocio.Npedido;
import arquitectura.trescapas.primerparcial.Negocio.Nproducto;
import arquitectura.trescapas.primerparcial.Negocio.Nrepartidor;
import arquitectura.trescapas.primerparcial.Utils.Utils;
import arquitectura.trescapas.primerparcial.clases.Cliente;
import arquitectura.trescapas.primerparcial.clases.Cotizacion;
import arquitectura.trescapas.primerparcial.clases.DetalleCotizacion;
import arquitectura.trescapas.primerparcial.clases.Pedido;
import arquitectura.trescapas.primerparcial.clases.Producto;
import arquitectura.trescapas.primerparcial.clases.Repartidor;
import arquitectura.trescapas.primerparcial.clases.interfaces.Negocio;

public class PdetallePedido extends AppCompatActivity {
    Spinner spDetallePCot, spCliente, spRepartidor;
    EditText edFechaDetalleP;

    Pedido pedido;
    NdetalleCotizacion detalleCotizacion;
    Negocio cliente;
    Nrepartidor repartidor;
    Nproducto producto;
    Npedido nPedido;
    Cotizacion cotizacion;
    Ncotizacion nCotizacion;

    List<Producto> listProductos;
    List<DetalleCotizacion> listDetalleCotizacion;
    List<Producto>  listProductosDelPedido;
    List<Cliente> listClientes;
    List<Repartidor> listRepartidores;
    List<Cotizacion> listCotizaciones;

    RecyclerView rv2;
    AdaptadorProducto aP2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdetalle_pedido);

        nPedido = new Npedido(this);
        String pedidoId = getIntent().getStringExtra("pedido");
        pedido= nPedido.getById(pedidoId);
        nCotizacion = new Ncotizacion(this);
        cotizacion = nCotizacion.getById(pedido.getCotizacionId());

        producto = new Nproducto(this);
        detalleCotizacion = new NdetalleCotizacion(this);
        cliente = new Ncliente(this);
        repartidor = new Nrepartidor(this);

        listProductos = producto.getDatos();
        listDetalleCotizacion = detalleCotizacion.getDatos();
        listProductosDelPedido = detalleCotizacion.getListProductosDelPedido(cotizacion);

        cargarSpinners();
        edFechaDetalleP = findViewById(R.id.edFechaDetalleP);
        edFechaDetalleP.setText(cotizacion.getFecha());

        rv2 = findViewById(R.id.rv2);
        LinearLayoutManager l=new LinearLayoutManager(this);
        rv2.setLayoutManager(l);
        aP2= new AdaptadorProducto();
        rv2.setAdapter(aP2);

    }

    private void cargarSpinners() {
        //Cotizaciones
        spDetallePCot = findViewById(R.id.spCotizacionesDetalleP);
        Utils.setSpinner(spDetallePCot,listCotizaciones,cotizacion.getId(),this,nCotizacion);

        spDetallePCot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cotizacion = (Cotizacion) parent.getItemAtPosition(position);
                listar();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Clientes
        spCliente = findViewById(R.id.spClienteDetalleP);
        String idcliente = pedido.getClienteId();
        Utils.setSpinner(spCliente,listClientes,idcliente,this,cliente);

        //Repartidores
        spRepartidor = findViewById(R.id.spRepartidorDetalleP);
        String idRepartidor = pedido.getRepartidorId();
        Utils.setSpinner(spRepartidor,listRepartidores,idRepartidor,this,repartidor);

    }


    public void edit(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Desea guardar los cambios?");

        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Cliente clienteSeleccionado = (Cliente) spCliente.getSelectedItem();
                pedido.setClienteId(clienteSeleccionado.getId());

                Repartidor repartidorSeleccionado = (Repartidor) spRepartidor.getSelectedItem();
                pedido.setRepartidorId(repartidorSeleccionado.getId());

                Cotizacion cotizacionSeleccionada = (Cotizacion) spDetallePCot.getSelectedItem();
                pedido.setCotizacionId(cotizacionSeleccionada.getId());

                String fechaActual = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
                pedido.setFecha(fechaActual);

                nPedido.updateDatos(pedido);
                listar();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }

    public  void enviarDatosARepartidor(View v) {
        Cliente clienteDelPedido= (Cliente) cliente.getById(pedido.getClienteId());
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
        this.listProductosDelPedido = detalleCotizacion.getListProductosDelPedido(cotizacion);
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
                btnEliminar.setVisibility(View.GONE);
                edCantidad.setEnabled(false);

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
                    edCantidad.setText(Integer.toString(listDetalleCotizacion.get(position).getCantidad()));

                }catch (Exception e) {
                    Utils.mensaje(PdetallePedido.this,e.getMessage());
                }

            }

        }
    }
}


