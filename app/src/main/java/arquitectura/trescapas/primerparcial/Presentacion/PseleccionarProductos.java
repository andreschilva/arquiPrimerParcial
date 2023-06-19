package arquitectura.trescapas.primerparcial.Presentacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import arquitectura.trescapas.primerparcial.Negocio.Ncategoria;
import arquitectura.trescapas.primerparcial.Negocio.Ncotizacion;
import arquitectura.trescapas.primerparcial.Negocio.NdetalleCotizacion;
import arquitectura.trescapas.primerparcial.Negocio.Nproducto;
import arquitectura.trescapas.primerparcial.R;
import arquitectura.trescapas.primerparcial.Utils.Utils;
import arquitectura.trescapas.primerparcial.clases.Cotizacion;
import arquitectura.trescapas.primerparcial.clases.DetalleCotizacion;
import arquitectura.trescapas.primerparcial.clases.Producto;

public class PseleccionarProductos extends AppCompatActivity {

    RecyclerView rv1;
    AdaptadorProducto aP;
    Nproducto producto;
    List<Producto> listProductos;
    List<Producto> listProductosSeleccionados;
    List<String> listCantidades;
    String cotizacionId;
    NdetalleCotizacion nDetalleCotizacion;
    Ncotizacion ncotizacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pseleccionar_productos);

        cotizacionId = getIntent().getStringExtra("cotizacion");
        ncotizacion = new Ncotizacion(this);
        //Utils.mensaje(this,cotizacionId);
        producto = new Nproducto(this);
        nDetalleCotizacion = new NdetalleCotizacion(this);
        listProductos = producto.getDatos();
        listProductosSeleccionados = new ArrayList<>();
        listCantidades = new ArrayList<>();

        rv1 = findViewById(R.id.rvProducto);
        LinearLayoutManager l = new LinearLayoutManager(this);
        rv1.setLayoutManager(l);
        aP = new AdaptadorProducto();
        rv1.setAdapter(aP);
    }

    public void agregarProductos(View v) {
        Cotizacion cotizacion = ncotizacion.getById(cotizacionId);

        float total = cotizacion.getTotal();
        for (int i = 0; i < listProductosSeleccionados.size();i++) {
            Producto productoActual = listProductosSeleccionados.get(i);
            int cantidadActual = Integer.parseInt(listCantidades.get(i));

            DetalleCotizacion detalleCotizacion = new DetalleCotizacion("", productoActual.getId(),cotizacionId,cantidadActual);
            nDetalleCotizacion.saveDatos(detalleCotizacion);
             total +=(cantidadActual * Integer.parseInt(productoActual.getPrecio()));
        }
        cotizacion.setTotal(total);
        ncotizacion.updateDatos(cotizacion);

        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void cancelar(View v) {
        finish();
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
            return listProductos.size();
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
                btnEliminar.setVisibility(View.GONE);

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        onCheckedCotizacion();
                    }
                });

            }

            public void imprimir(int position) {
                try {

                    Producto productoActual = listProductos.get(position);
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

                }catch (Exception e) {
                    Utils.mensaje(PseleccionarProductos.this,e.getMessage());
                }

            }

            private void onCheckedCotizacion() {

                if (checkBox.isChecked()) {
                    Producto productoActual = listProductos.get(getLayoutPosition());
                    String cantidad = edCantidad.getText().toString();
                    listProductosSeleccionados.add(productoActual);
                    listCantidades.add(cantidad);
                }
                else {
                    int posicionProductoActual = listProductosSeleccionados.indexOf(listProductos.get(getLayoutPosition()));
                    listProductosSeleccionados.remove(listProductos.get(getLayoutPosition()));
                    listCantidades.remove(posicionProductoActual);
                }
            }
        }
    }
}