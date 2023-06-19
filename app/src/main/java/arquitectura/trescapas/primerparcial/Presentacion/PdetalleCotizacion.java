package arquitectura.trescapas.primerparcial.Presentacion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import arquitectura.trescapas.primerparcial.Negocio.Ncliente;
import arquitectura.trescapas.primerparcial.Negocio.Ncotizacion;
import arquitectura.trescapas.primerparcial.Negocio.NdetalleCotizacion;
import arquitectura.trescapas.primerparcial.Negocio.Nproducto;
import arquitectura.trescapas.primerparcial.Presentacion.adaptadores.SpinnerAdapter;
import arquitectura.trescapas.primerparcial.R;
import arquitectura.trescapas.primerparcial.Utils.Utils;
import arquitectura.trescapas.primerparcial.clases.Cliente;
import arquitectura.trescapas.primerparcial.clases.Cotizacion;
import arquitectura.trescapas.primerparcial.clases.DetalleCotizacion;
import arquitectura.trescapas.primerparcial.clases.patrones.estrategia.Enviar;
import arquitectura.trescapas.primerparcial.clases.patrones.estrategia.EnviarPorSMS;
import arquitectura.trescapas.primerparcial.clases.patrones.estrategia.EnviarPorWhatsapp;
import arquitectura.trescapas.primerparcial.clases.Producto;

public class PdetalleCotizacion extends AppCompatActivity {
    EditText edNombreCot, edFechaCot, edTotalCot;
    NdetalleCotizacion detalleCotizacion;
    Nproducto producto;
    Ncotizacion nCotizacion;
    Cotizacion cotizacion;
    String cotizacionId;

    List<DetalleCotizacion> listDetalleCotizacion;
    List<Producto> listProductosCotizacion;
    RecyclerView rv;
    AdaptadorProducto aP;

    Enviar enviarCotizacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdetalle_cotizacion);

        edNombreCot = findViewById(R.id.editNombreDetalleCot);
        edFechaCot = findViewById(R.id.editFechaDetalleCot);
        edTotalCot = findViewById(R.id.editTotalDetalleCot);

        nCotizacion = new Ncotizacion(this);
        cotizacionId = getIntent().getStringExtra("cotizacion");
        cotizacion= nCotizacion.getById(cotizacionId);
        enviarCotizacion = new Enviar();

        producto = new Nproducto(this);
        detalleCotizacion = new NdetalleCotizacion(this);

        listDetalleCotizacion = detalleCotizacion.getDatos();
        listProductosCotizacion = detalleCotizacion.getListProductosDelPedido(cotizacion);

        edNombreCot.setText(cotizacion.getNombre());
        edFechaCot.setText(cotizacion.getFecha());
        edTotalCot.setText(Float.toString(cotizacion.getTotal()));

        rv = findViewById(R.id.rvDetalleCot);
        LinearLayoutManager l=new LinearLayoutManager(this);
        rv.setLayoutManager(l);
        aP = new AdaptadorProducto();
        rv.setAdapter(aP);
    }

    public void agregarProductos(View v) {
        Intent intento = new Intent(PdetalleCotizacion.this, PseleccionarProductos.class);
        intento.putExtra("cotizacion", (Serializable) cotizacion.getId());
        startActivityForResult(intento,1);
    }

    public void Edit(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Desea guardar los cambios?");

        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                cotizacion.setNombre(edNombreCot.getText().toString());
                String fechaActual = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
                cotizacion.setFecha(fechaActual);
                nCotizacion.updateDatos(cotizacion);
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

    public void enviarCotizacion(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enviar Cotizacion");
        View selector = getLayoutInflater().inflate(R.layout.modal_enviar_cotizacion,null);
        builder.setView(selector);

        EditText edNumero = selector.findViewById(R.id.edNumeroEnviarCot);
        Spinner spClientes = selector.findViewById(R.id.spEnviarCotizacionCl);
        ImageButton btnSMS = selector.findViewById(R.id.btnEnviarSMS);
        ImageButton btnWpp = selector.findViewById(R.id.btnEnviarCotizacion);
        List<Cliente> listClientes = new Ncliente(this).getDatos();

        SpinnerAdapter adapter = new SpinnerAdapter(this, listClientes);
        spClientes.setAdapter(adapter);

        spClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cliente clienteSeleccionado = (Cliente) parent.getItemAtPosition(position);
                edNumero.setText(clienteSeleccionado.getCelular());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarCotizacion.setEstrategia(new EnviarPorSMS());
                enviarCotizacion.ejecutarEnvio(PdetalleCotizacion.this,edNumero.getText().toString(),listProductosCotizacion);
            }
        });

        btnWpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarCotizacion.setEstrategia(new EnviarPorWhatsapp());
                enviarCotizacion.ejecutarEnvio(PdetalleCotizacion.this,edNumero.getText().toString(),listProductosCotizacion);
            }
        });

        builder.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Se agregaron productos, actualizar la lista aquí
            listar();
            cotizacion = nCotizacion.getById(cotizacionId);
            edTotalCot.setText(Float.toString(cotizacion.getTotal()));
        }
    }

    public void listar() {
        this.listProductosCotizacion = detalleCotizacion.getListProductosDelPedido(cotizacion);;
        aP.notifyDataSetChanged();
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
            return listProductosCotizacion.size();
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

                edCantidad.setEnabled(false);
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

                    Producto productoActual = listProductosCotizacion.get(position);
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
                    Utils.mensaje(PdetalleCotizacion.this,e.getMessage());
                }

            }

            private void eliminar() {
                AlertDialog.Builder builder = new AlertDialog.Builder(PdetalleCotizacion.this);
                builder.setMessage("Esta seguro que desea Eliminar este producto?");

                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = getLayoutPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            DetalleCotizacion detallePedidoActual = listDetalleCotizacion.get(position);
                            float total = cotizacion.getTotal();
                            Producto productoActual = producto.getById(detallePedidoActual.getProductoId());
                            float precio = Float.parseFloat(productoActual.getPrecio());
                            int cantidad = detallePedidoActual.getCantidad();
                            total += -(precio *cantidad );

                            //se actualiza el total de la cotizacion
                            cotizacion.setTotal(total);
                            nCotizacion.updateDatos(cotizacion);
                            String id = detallePedidoActual.getId();

                            //eliminar detalle del pedido
                            detalleCotizacion.delete(id);
                            listar();
                            edTotalCot.setText(Float.toString(total));

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