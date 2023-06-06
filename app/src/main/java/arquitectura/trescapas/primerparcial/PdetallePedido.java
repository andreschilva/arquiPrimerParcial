package arquitectura.trescapas.primerparcial;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import arquitectura.trescapas.primerparcial.DB.DBmigrations;
import arquitectura.trescapas.primerparcial.Datos.Dcliente;
import arquitectura.trescapas.primerparcial.Datos.Drepartidor;
import arquitectura.trescapas.primerparcial.Negocio.Ncategoria;
import arquitectura.trescapas.primerparcial.Negocio.Ncliente;
import arquitectura.trescapas.primerparcial.Negocio.NdetallePedido;
import arquitectura.trescapas.primerparcial.Negocio.Nproducto;
import arquitectura.trescapas.primerparcial.Negocio.Nrepartidor;
import arquitectura.trescapas.primerparcial.Presentacion.Pcliente;
import arquitectura.trescapas.primerparcial.Presentacion.adaptadores.AdaptadorProducto;

public class PdetallePedido extends AppCompatActivity {
    Map<String, Object> pedido;
    NdetallePedido detallePedido;

    Nproducto producto;
    Ncategoria categoria;
    List<Map<String,Object>> listProductos;
    List<Map<String,Object>> lisCategorias;
    List<Map<String,Object>> listProductosSeleccionados;
    List<Map<String,Object>>  lisProductoCategoria;
    List<Map<String,Object>>  listDetallePedido;
    List<Map<String,Object>>  listProductosDelPedido;
    RecyclerView rv2;
    AdaptadorProducto aP2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdetalle_pedido);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        pedido= (Map<String, Object>) bundle.getSerializable("pedido");

        Toast.makeText(this, pedido.get("id").toString(), Toast.LENGTH_SHORT).show();

        producto = new Nproducto(this);
        categoria = new Ncategoria(this);
        detallePedido = new NdetallePedido(this);
        listProductos = producto.getDatos();
        lisCategorias= categoria.getDatos();
        listDetallePedido = detallePedido.getDatos();
        listProductosSeleccionados = new ArrayList<>();
        lisProductoCategoria = new ArrayList<>();
        unirtListaProductoCategoria();
        listProductosDelPedido = getListProductosDelPedido();

        Dcliente cliente = new Ncliente(this).getById(pedido.get(DBmigrations.PEDIDO_CLIENTE_ID).toString());
        Drepartidor repartidor = new Nrepartidor(this).getById(pedido.get(DBmigrations.PEDIDO_REPARTIDOR_ID).toString());


        rv2 = findViewById(R.id.rv2);
        LinearLayoutManager l=new LinearLayoutManager(this);
        rv2.setLayoutManager(l);
        aP2= new AdaptadorProducto(this,null,listProductosDelPedido);
        aP2.setDesactivarbtnEdit(true);
        aP2.setDesactivarChBox(true);
        rv2.setAdapter(aP2);

    }



    private void unirtListaProductoCategoria() {
        for (Map<String,Object> productoActual: listProductos ) {
            String categoriaId = productoActual.get(DBmigrations.PRODUCTO_CATEGORIAID).toString();
            for (Map<String,Object> categoriaActual: lisCategorias ) {
                if (categoriaId.compareTo(categoriaActual.get(DBmigrations.CATEGORIA_ID).toString()) == 0) {
                    productoActual.put("nombreCategoria",categoriaActual.get(DBmigrations.CATEGORIA_NOMBRE));
                    break;
                }
            }
            lisProductoCategoria.add(productoActual);
        }
    }

    private List<Map<String,Object>> getListProductosDelPedido() {
        List<Map<String,Object>>  resultado = new ArrayList<>();
        for (Map<String,Object> pedidoActual: listDetallePedido ) {
            String pedidoId = pedidoActual.get(DBmigrations.DETALLE_PEDIDO_PEDIDO_ID).toString();
            if (pedidoId.compareTo(pedido.get("id").toString()) == 0) {
                String productoId = pedidoActual.get(DBmigrations.DETALLE_PEDIDO_PRODUCTO_ID).toString();
                String cantidad = pedidoActual.get(DBmigrations.DETALLE_PEDIDO_CANTIDAD).toString();
                for (Map<String,Object> productoActual: lisProductoCategoria ) {
                    if (productoId.compareTo(productoActual.get(DBmigrations.PRODUCTO_ID).toString()) == 0) {
                        productoActual.put(DBmigrations.DETALLE_PEDIDO_CANTIDAD,cantidad);
                        resultado.add(productoActual);
                        break;
                    }
                }
            }

        }
        return resultado;
    }

    public void agregarProductos(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Agregar Productos");
        View selector = getLayoutInflater().inflate(R.layout.modalseleccionarproducto,null);
        builder.setView(selector);

        RecyclerView rv1 = selector.findViewById(R.id.rv2);
        Button btnAgregar = selector.findViewById(R.id.btnAgregar);
        Button btnCancelar = selector.findViewById(R.id.btncancelar);
        LinearLayoutManager l=new LinearLayoutManager(this);
        rv1.setLayoutManager(l);
        AdaptadorProducto aP= new AdaptadorProducto(this,listProductosSeleccionados,lisProductoCategoria);
        aP.setDesactivarbtnEliminar(true);
        aP.setDesactivarbtnEdit(true);
        rv1.setAdapter(aP);

        AlertDialog dialog = builder.create();

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (Map<String,Object> productoSeleccionado: listProductosSeleccionados) {
                    Map<String,Object> mapDetallePedido = new HashMap<>();
                    mapDetallePedido.put(DBmigrations.DETALLE_PEDIDO_PEDIDO_ID,pedido.get("id"));
                    mapDetallePedido.put(DBmigrations.DETALLE_PEDIDO_PRODUCTO_ID,productoSeleccionado.get("id"));
                    mapDetallePedido.put(DBmigrations.DETALLE_PEDIDO_CANTIDAD,productoSeleccionado.get("cantidad"));
                    detallePedido.saveDatos(mapDetallePedido);
                }
                dialog.dismiss();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

//    private class AdaptadorProducto extends RecyclerView.Adapter<AdaptadorProducto.AdaptadorProductoHolder> {
//
//        @NonNull
//        @Override
//        public AdaptadorProducto.AdaptadorProductoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            return new AdaptadorProducto.AdaptadorProductoHolder(getLayoutInflater().inflate(R.layout.item_producto,parent,false));
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull AdaptadorProducto.AdaptadorProductoHolder holder, int position) {
//
//            holder.imprimir(position);
//
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return listProductos.size();
//        }
//
//        public class AdaptadorProductoHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
//
//            TextView tv1, tv2, tv3,tv4;
//            CheckBox chProductos;
//            ImageButton btnEliminar, btnEdit;
//            EditText edCantidad;
//            ImageView ivFoto;
//            public AdaptadorProductoHolder(@NonNull View itemView) {
//                super(itemView);
//                tv1 = itemView.findViewById(R.id.tvNombre);
//                tv2 = itemView.findViewById(R.id.tvDescripcion);
//                tv3 = itemView.findViewById(R.id.tvPrecio);
//                tv4 = itemView.findViewById(R.id.tvCategoria);
//                chProductos = itemView.findViewById(R.id.cBProductos);
//                btnEliminar =  itemView.findViewById(R.id.btnEliminar);
//                btnEdit =  itemView.findViewById(R.id.btnEdit);
//                edCantidad = itemView.findViewById(R.id.edCantidad);
//                ivFoto = itemView.findViewById(R.id.iv1);
//                itemView.setOnClickListener(this);
//
//                chProductos.setText("Agregar");
//                btnEliminar.setVisibility(View.GONE);
//                btnEdit.setVisibility(View.GONE);
//
//                btnEliminar.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //onClickEliminar();
//                    }
//                });
//
//                chProductos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        onCheckedProductos();
//                    }
//                });
//
//            }
//
//            private void onCheckedProductos() {
//                if (chProductos.isChecked()) {
//                    Map<String,Object> productoActual =  listProductos.get(getLayoutPosition());
//                    productoActual.put("cantidad",edCantidad.getText());
//                    listProductosSeleccionados.add(productoActual);
//                }
//                else {
//                    listProductosSeleccionados.remove(listProductos.get(getLayoutPosition()));
//                }
//            }
//
//            public void imprimir(int position) {
//                try {
//                    listProductos = producto.getDatos();
//                    Map<String,Object> productoActual = listProductos.get(position);
//                    String fotoActual = productoActual.get(DBmigrations.PRODUCTO_FOTO).toString();
//
//                    FileInputStream fileInputStream = openFileInput(fotoActual);
//                    Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
//                    ivFoto.setImageBitmap(bitmap);
//                    fileInputStream.close();
//
//                    tv1.setText(productoActual.get("nombre").toString());
//                    tv2.setText(productoActual.get("descripcion").toString());
//                    tv3.setText(productoActual.get("precio").toString());
//                    String idcategoria =productoActual.get("categoria_id").toString();
//                    String nombreCategoria= categoria.getDcategoriaById(idcategoria).row().get("nombre").toString();
//                    tv4.setText(nombreCategoria);
//
//                }catch (Exception e) {
//
//                }
//            }
//
//
//            @Override
//            public void onClick(View v) {
//                int position = getLayoutPosition();
//                //mostrar(position);
//            }
//
////            private void onClickEliminar() {
////                int position = getLayoutPosition();
////                if (position != RecyclerView.NO_POSITION) {
////                    String id = listProductos.get(getLayoutPosition()).get("id").toString();
////                    producto.delete(id);
////                    listar();
////                    Toast.makeText(Pproduto.this, "eliminado", Toast.LENGTH_SHORT).show();
////                }
////            }
//        }
//    }
}