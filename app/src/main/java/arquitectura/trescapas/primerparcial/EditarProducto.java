package arquitectura.trescapas.primerparcial;

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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import arquitectura.trescapas.primerparcial.Negocio.Ncategoria;
import arquitectura.trescapas.primerparcial.Negocio.NdetallePedido;
import arquitectura.trescapas.primerparcial.Negocio.Nproducto;
import arquitectura.trescapas.primerparcial.Utils.Utils;
import arquitectura.trescapas.primerparcial.clases.DetallePedido;
import arquitectura.trescapas.primerparcial.clases.Producto;

public class EditarProducto extends AppCompatActivity {

    RecyclerView rv1;
    AdaptadorProducto aP;
    Nproducto producto;
    Ncategoria categoria;
    List<Producto> listProductos;
    List<Producto> listProductosSeleccionados;
    List<String> listCantidades;
    String pedidoId;
    NdetallePedido ndetallePedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producto);

        pedidoId = getIntent().getStringExtra("pedido");
        //Utils.mensaje(this,pedidoId);
        producto = new Nproducto(this);
        ndetallePedido = new NdetallePedido(this);
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
        for (int i = 0; i < listProductosSeleccionados.size();i++) {
            Producto productoActual = listProductosSeleccionados.get(i);
            String cantidadActual = listCantidades.get(i);

            DetallePedido detallePedido = new DetallePedido("",cantidadActual,pedidoId,productoActual.getId());
            ndetallePedido.saveDatos(detallePedido);

        }
        Intent intento = new Intent(EditarProducto.this, PdetallePedido.class);
        intento.putExtra("pedido", (Serializable) pedidoId);
        finish();
        startActivity(intento);
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
                    Utils.mensaje(EditarProducto.this,e.getMessage());
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