package arquitectura.trescapas.primerparcial.Presentacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import arquitectura.trescapas.primerparcial.DB.DBmigrations;
import arquitectura.trescapas.primerparcial.Negocio.Ncliente;
import arquitectura.trescapas.primerparcial.Negocio.Npedido;
import arquitectura.trescapas.primerparcial.Negocio.Nproducto;
import arquitectura.trescapas.primerparcial.Negocio.Nrepartidor;
import arquitectura.trescapas.primerparcial.PdetallePedido;
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


    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ppedido);
        //getSupportActionBar().setTitle("Pedidos");



        producto = new Nproducto(this);
        cliente = new Ncliente(this);
        repartidor = new Nrepartidor(this);
        pedido = new Npedido(this);

        //cargando listas
        listProductos = producto.getDatos();
        listClientes= cliente.getDatos();
        listRepartidores = repartidor.getDatos();
        listPedidos = pedido.getDatos();
        listProductosSeleccionados = new ArrayList<>();


        rv1 = findViewById(R.id.rv1);
        LinearLayoutManager l=new LinearLayoutManager(this);
        rv1.setLayoutManager(l);
        aP= new AdaptadorPedido();
        rv1.setAdapter(aP);

    }

    public void agregarPedido(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Crear Pedido");
        View selector = getLayoutInflater().inflate(R.layout.modalcrearpedido,null);
        builder.setView(selector);

        Spinner sp1 = selector.findViewById(R.id.spEstado);
        Spinner sp2 = selector.findViewById(R.id.spCliente);
        Spinner sp3 = selector.findViewById(R.id.spRepartidor);
        EditText ed1Fecha = selector.findViewById(R.id.edFecha);
        EditText ed2Total = selector.findViewById(R.id.edTotal);
        ImageButton btnCalendario = selector.findViewById(R.id.btnCalendario);

        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog d = new DatePickerDialog(Ppedido.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        ed1Fecha.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                },2023,0,1);
                d.show();
            }
        });

        int i=0;
        String [] arrayClientes = new String[listClientes.size()];

        for (Map<String,Object> mapcliente : listClientes) {
            arrayClientes[i] = mapcliente.get("nombre").toString();
            i++;
        }
        i=0;
        String [] arrayRepartidores = new String[listRepartidores.size()];

        for (Map<String,Object> mapRepartidor : listRepartidores) {
            arrayRepartidores[i] = mapRepartidor.get("nombre").toString();
            i++;
        }


        ArrayAdapter<String> adapterClientes = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrayClientes);
        ArrayAdapter<String> adapterRepartidores = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrayRepartidores);
        ArrayAdapter<String> adapterEstados = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrayEstados);

        sp1.setAdapter(adapterEstados);
        sp2.setAdapter(adapterClientes);
        sp3.setAdapter(adapterRepartidores);

        builder.setPositiveButton("crear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long positionEstado= sp1.getSelectedItemPosition();
                long positionCliente= sp2.getSelectedItemPosition();
                long positionRepartidor= sp3.getSelectedItemPosition();

                Map<String, Object> clienteSeleccionado = listClientes.get((int) positionCliente);
                Map<String, Object> repartidorSeleccionado = listRepartidores.get((int) positionRepartidor);
                String estadoSeleccionado = arrayEstados[(int) positionEstado];
                String fecha = ed1Fecha.getText().toString();
                String total = ed2Total.getText().toString();


                //Toast.makeText(this,  listClientes.get((int) position).get("nombre").toString(), Toast.LENGTH_SHORT).show();
                Map<String, Object> data = new HashMap<>();
                data.put(DBmigrations.PEDIDO_ESTADO,estadoSeleccionado);
                data.put(DBmigrations.PEDIDO_FECHA,fecha);
                data.put(DBmigrations.PEDIDO_TOTAL,total);
                data.put(DBmigrations.PEDIDO_CLIENTE_ID,clienteSeleccionado.get("id").toString());
                data.put(DBmigrations.PEDIDO_REPARTIDOR_ID,repartidorSeleccionado.get("id").toString());

                if (pedido.saveDatos(data)){
                    listar();
                    rv1.scrollToPosition(listPedidos.size()-1);
                    Toast.makeText(Ppedido.this, "Pedido creado", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(Ppedido.this, "Pedido ya existente", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();

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
        this.listPedidos = pedido.getDatos();
        aP.notifyDataSetChanged();
    }


    private class AdaptadorPedido extends RecyclerView.Adapter<AdaptadorPedido.AdaptadorPedidoHolder> {

        @NonNull
        @Override
        public AdaptadorPedido.AdaptadorPedidoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdaptadorPedido.AdaptadorPedidoHolder(getLayoutInflater().inflate(R.layout.item_pedido,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull AdaptadorPedido.AdaptadorPedidoHolder holder, int position) {

            holder.imprimir(position);


        }

        @Override
        public int getItemCount() {
            return listPedidos.size();
        }

        public class AdaptadorPedidoHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

            TextView tv1, tv2, tv3,tv4, tv5;
            Button btnEliminar, btnDetalles;

            public AdaptadorPedidoHolder(@NonNull View itemView) {
                super(itemView);
                tv1 = itemView.findViewById(R.id.tvEstado);
                tv2 = itemView.findViewById(R.id.tvCliente);
                tv3 = itemView.findViewById(R.id.tvRepartidor);
                tv4 = itemView.findViewById(R.id.tvFecha);
                tv5 = itemView.findViewById(R.id.tvTotal);

                btnEliminar =  itemView.findViewById(R.id.btnEliminar);
                itemView.setOnClickListener(this);

//                btnEliminar.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        onClickEliminar();
//                    }
//                });

                btnDetalles = itemView.findViewById(R.id.btnDetalles);
                btnDetalles.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickDetalles();
                    }
                });


            }

            private void onClickDetalles() {
                Intent intento = new Intent(Ppedido.this, PdetallePedido.class);
                int position = getLayoutPosition();
                Map<String,Object> pedidoSeleccionado = listPedidos.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("pedido", (Serializable) pedidoSeleccionado);
                intento.putExtras(bundle);

                startActivity(intento);
            }


            public void imprimir(int position) {
                listPedidos = pedido.getDatos();
                tv1.setText(listPedidos.get(position).get(DBmigrations.PEDIDO_ESTADO).toString());

                String idcliente =listPedidos.get(position).get(DBmigrations.PEDIDO_CLIENTE_ID).toString();
                String nombreCliente= cliente.getById(idcliente).row().get("nombre").toString();
                tv2.setText(nombreCliente);

                String idRepartidor =listPedidos.get(position).get(DBmigrations.PEDIDO_REPARTIDOR_ID).toString();
                String nombreRepartidor= repartidor.getById(idRepartidor).row().get("nombre").toString();
                tv3.setText(nombreRepartidor);

                tv4.setText(listPedidos.get(position).get(DBmigrations.PEDIDO_FECHA).toString());
                tv5.setText(listPedidos.get(position).get(DBmigrations.PEDIDO_TOTAL).toString());

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
//                    Toast.makeText(Ppedido.this, "eliminado", Toast.LENGTH_SHORT).show();
//                }
//            }
        }
    }
}