package arquitectura.trescapas.primerparcial.Presentacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import arquitectura.trescapas.primerparcial.DB.DBmigrations;
import arquitectura.trescapas.primerparcial.Negocio.Ncliente;
import arquitectura.trescapas.primerparcial.Negocio.Npedido;
import arquitectura.trescapas.primerparcial.Negocio.Nproducto;
import arquitectura.trescapas.primerparcial.Negocio.Nrepartidor;
import arquitectura.trescapas.primerparcial.R;

public class Ppedido extends AppCompatActivity {
    RecyclerView rv1;
    AdaptadorPedido aP;

    //datos necesatios para insertar
    Nproducto producto;
    Npedido pedido;
    Ncliente cliente;
    Nrepartidor repartidor;

    //tabla intermedia


    //listas
    List<Map<String,Object>> listProductos;
    List<Map<String,Object>> listPedidos;
    List<Map<String,Object>> listClientes;
    List<Map<String,Object>> listRepartidores;
    List<Map<String,Object>> listProductosSeleccionados;
    String [] arrayEstados = {"En proceso","entregado"};

    //spiners
    Spinner spestado;
    Spinner spclientes;
    Spinner sprepartidores;


    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ppedido);
        getSupportActionBar().setTitle("Pedidos");

        //inicializando spinners
        spclientes = findViewById(R.id.spCliente);
        sprepartidores = findViewById(R.id.spRepartidor);
        spestado = findViewById(R.id.spRepartidor);


        producto = new Nproducto(this);
        cliente = new Ncliente(this);
        repartidor = new Nrepartidor(this);
        pedido = new Npedido(this);

        //cargando listas
        listProductos = producto.getDatos();
        listClientes= cliente.getDatos();
        listRepartidores = repartidor.getDatos();
        listProductosSeleccionados = new ArrayList<>();


        rv1 = findViewById(R.id.rv1);
        LinearLayoutManager l=new LinearLayoutManager(this);
        rv1.setLayoutManager(l);
        aP= new AdaptadorPedido();
        rv1.setAdapter(aP);

        ArrayAdapter<Map<String,Object>> adapterClientes = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,listClientes);
        ArrayAdapter<Map<String,Object>> adapterRepartidores = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,listRepartidores);
        ArrayAdapter<String> adapterEstados = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrayEstados);


        spclientes.setAdapter(adapterClientes);
        sprepartidores.setAdapter(adapterRepartidores);
        spestado.setAdapter(adapterEstados);


    }

    public void agregar(View v) {
        long positionEstado= spestado.getSelectedItemPosition();
        long positionCliente= spclientes.getSelectedItemPosition();
        long positionRepartidores= sprepartidores.getSelectedItemPosition();

        Map<String, Object> cliente = this.listClientes.get((int) positionCliente);
        Map<String, Object> repartidor = this.listRepartidores.get((int) positionCliente);
        String estado = this.arrayEstados[(int) positionEstado];

        //Toast.makeText(this,  listClientes.get((int) position).get("nombre").toString(), Toast.LENGTH_SHORT).show();
        Map<String, Object> data = new HashMap<>();
        data.put(DBmigrations.PEDIDO_ESTADO,estado);
//        data.put(DBmigrations.PEDIDO,apellido);
//        data.put(DBmigrations.PRODUCTO_PRECIO,celular);
//        data.put(DBmigrations.PRODUCTO_CATEGORIAID,listClientes.get((int) position).get("id").toString());
//
//        if (producto.saveDatos(data)){
//            listar();
//            rv1.scrollToPosition(listProductos.size()-1);
//            Toast.makeText(this, "Producto creado", Toast.LENGTH_SHORT).show();
//
//        }else {
//            Toast.makeText(this, "Producto ya existente", Toast.LENGTH_SHORT).show();
//        }

    }

//    public void mostrar(int position) {
//        etNombre.setText(listProductos.get(position).get(DBmigrations.PRODUCTO_NOMBRE).toString());
//        etDescripcion.setText(listProductos.get(position).get(DBmigrations.PRODUCTO_DESCRIPCION).toString());
//        etPrecio.setText(listProductos.get(position).get(DBmigrations.PRODUCTO_PRECIO).toString());
//        String idcategoria =listProductos.get(position).get(DBmigrations.PRODUCTO_CATEGORIAID).toString();
//        //String nombreCategoria= categoria.getDcategoriaById(idcategoria).row();
//        int posCategoria = listClientes.indexOf(cliente.getDcategoriaById(idcategoria).row());
//        spCategoria.setSelection(posCategoria);
//        //Toast.makeText(this,  posCategoria, Toast.LENGTH_SHORT).show();
//        this.pos = position;
//    }

    public void actualizar(View v) {
//        String nombre= etNombre.getText().toString();
//        String apellido=  etDescripcion.getText().toString();
//        String celular= etPrecio.getText().toString();
//        long position = spCategoria.getSelectedItemId();
//        String id = listProductos.get(this.pos).get("id").toString();
//
//
//        Map<String, Object> data = new HashMap<>();
//        data.put(DBmigrations.PRODUCTO_ID,id);
//        data.put(DBmigrations.PRODUCTO_NOMBRE,nombre);
//        data.put(DBmigrations.PRODUCTO_DESCRIPCION,apellido);
//        data.put(DBmigrations.PRODUCTO_PRECIO,celular);
//        data.put(DBmigrations.PRODUCTO_CATEGORIAID,lisCategorias.get((int)position).get("id").toString());
//        producto.updateDatos(data);
//        listar();

    }
    public void listar() {
        this.listProductos = producto.getDatos();
        aP.notifyDataSetChanged();
    }


    private class AdaptadorPedido extends RecyclerView.Adapter<AdaptadorPedido.AdaptadorPedidoHolder> {

        @NonNull
        @Override
        public AdaptadorPedido.AdaptadorPedidoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdaptadorPedido.AdaptadorPedidoHolder(getLayoutInflater().inflate(R.layout.item_producto,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull AdaptadorPedido.AdaptadorPedidoHolder holder, int position) {

            holder.imprimir(position);


        }

        @Override
        public int getItemCount() {
            return listProductos.size();
        }

        public class AdaptadorPedidoHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

            TextView tv1, tv2, tv3,tv4;
            CheckBox chCotizacion;
            Button btnEliminar;

            public AdaptadorPedidoHolder(@NonNull View itemView) {
                super(itemView);
                tv1 = itemView.findViewById(R.id.tvnombre);
                tv2 = itemView.findViewById(R.id.tvDescripcion);
                tv3 = itemView.findViewById(R.id.tvPrecio);
                tv4 = itemView.findViewById(R.id.tvCategoria);
                chCotizacion = itemView.findViewById(R.id.cBCotizacion);
                btnEliminar =  itemView.findViewById(R.id.btnEliminar);
                itemView.setOnClickListener(this);

                btnEliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickEliminar();
                    }
                });

                chCotizacion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        onCheckedCotizacion();
                    }
                });

            }

            private void onCheckedCotizacion() {
                if (chCotizacion.isChecked())
                    listProductosSeleccionados.add(listProductos.get(getLayoutPosition()));
                else {
                    listProductosSeleccionados.remove(listProductos.get(getLayoutPosition()));
                }
            }

            public void imprimir(int position) {
                listProductos = producto.getDatos();
                tv1.setText(listProductos.get(position).get("nombre").toString());
                tv2.setText(listProductos.get(position).get("descripcion").toString());
                tv3.setText(listProductos.get(position).get("precio").toString());
                String idcategoria =listProductos.get(position).get("categoria_id").toString();
                String nombreCategoria= cliente.getDcategoriaById(idcategoria).row().get("nombre").toString();
                tv4.setText(nombreCategoria);
            }


            @Override
            public void onClick(View v) {
                int position = getLayoutPosition();
                //mostrar(position);
            }

            private void onClickEliminar() {
                int position = getLayoutPosition();
                if (position != RecyclerView.NO_POSITION) {
                    String id = listProductos.get(getLayoutPosition()).get("id").toString();
                    producto.delete(id);
                    listar();
                    Toast.makeText(Ppedido.this, "eliminado", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}