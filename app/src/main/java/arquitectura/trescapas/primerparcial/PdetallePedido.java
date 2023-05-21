package arquitectura.trescapas.primerparcial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import arquitectura.trescapas.primerparcial.Negocio.Ncategoria;
import arquitectura.trescapas.primerparcial.Negocio.Nproducto;
import arquitectura.trescapas.primerparcial.Presentacion.Ppedido;
import arquitectura.trescapas.primerparcial.Presentacion.Pproduto;

public class PdetallePedido extends AppCompatActivity {
    Map<String, Object> pedido;

    Nproducto producto;
    Ncategoria categoria;
    List<Map<String,Object>> listProductos;
    List<Map<String,Object>> lisCategorias;
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
        listProductos = producto.getDatos();
        lisCategorias= categoria.getDatos();


    }

    public void agregarProductos(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Agregar Productos");
        View selector = getLayoutInflater().inflate(R.layout.modalseleccionarproducto,null);
        builder.setView(selector);

        RecyclerView rv1 = selector.findViewById(R.id.rv);
        LinearLayoutManager l=new LinearLayoutManager(this);
        rv1.setLayoutManager(l);
        AdaptadorProducto aD= new AdaptadorProducto();
        rv1.setAdapter(aD);

        builder.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }

    private class AdaptadorProducto extends RecyclerView.Adapter<AdaptadorProducto.AdaptadorProductoHolder> {

        @NonNull
        @Override
        public AdaptadorProducto.AdaptadorProductoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdaptadorProducto.AdaptadorProductoHolder(getLayoutInflater().inflate(R.layout.item_producto,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull AdaptadorProducto.AdaptadorProductoHolder holder, int position) {

            holder.imprimir(position);


        }

        @Override
        public int getItemCount() {
            return listProductos.size();
        }

        public class AdaptadorProductoHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

            TextView tv1, tv2, tv3,tv4;
            CheckBox chCotizacion;
            Button btnEliminar;

            public AdaptadorProductoHolder(@NonNull View itemView) {
                super(itemView);
                tv1 = itemView.findViewById(R.id.tvEstado);
                tv2 = itemView.findViewById(R.id.tvCliente);
                tv3 = itemView.findViewById(R.id.tvRepartidor);
                tv4 = itemView.findViewById(R.id.tvFecha);
                chCotizacion = itemView.findViewById(R.id.cBCotizacion);
                btnEliminar =  itemView.findViewById(R.id.btnEliminar);
                itemView.setOnClickListener(this);

                btnEliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //onClickEliminar();
                    }
                });

                chCotizacion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        //onCheckedCotizacion();
                    }
                });

            }

//            private void onCheckedCotizacion() {
//                if (chCotizacion.isChecked())
//                    listCotizacion.add(listProductos.get(getLayoutPosition()));
//                else {
//                    listCotizacion.remove(listProductos.get(getLayoutPosition()));
//                }
//            }

            public void imprimir(int position) {
                listProductos = producto.getDatos();
                tv1.setText(listProductos.get(position).get("nombre").toString());
                tv2.setText(listProductos.get(position).get("descripcion").toString());
                tv3.setText(listProductos.get(position).get("precio").toString());
                String idcategoria =listProductos.get(position).get("categoria_id").toString();
                String nombreCategoria= categoria.getDcategoriaById(idcategoria).row().get("nombre").toString();
                tv4.setText(nombreCategoria);
            }


            @Override
            public void onClick(View v) {
                int position = getLayoutPosition();
                //mostrar(position);
            }

//            private void onClickEliminar() {
//                int position = getLayoutPosition();
//                if (position != RecyclerView.NO_POSITION) {
//                    String id = listProductos.get(getLayoutPosition()).get("id").toString();
//                    producto.delete(id);
//                    listar();
//                    Toast.makeText(Pproduto.this, "eliminado", Toast.LENGTH_SHORT).show();
//                }
//            }
        }
    }
}