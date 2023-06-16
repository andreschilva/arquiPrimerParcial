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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import arquitectura.trescapas.primerparcial.Datos.Dpedido;
import arquitectura.trescapas.primerparcial.Negocio.Ncliente;
import arquitectura.trescapas.primerparcial.Negocio.Npedido;
import arquitectura.trescapas.primerparcial.Negocio.Nproducto;
import arquitectura.trescapas.primerparcial.Negocio.Nrepartidor;
import arquitectura.trescapas.primerparcial.PdetallePedido;
import arquitectura.trescapas.primerparcial.R;
import arquitectura.trescapas.primerparcial.clases.Cliente;
import arquitectura.trescapas.primerparcial.clases.Pedido;
import arquitectura.trescapas.primerparcial.clases.Producto;
import arquitectura.trescapas.primerparcial.clases.Repartidor;

public class Ppedido extends AppCompatActivity {
    RecyclerView rv1;
    AdaptadorPedido aP;

    //datos necesatios para insertar
    Nproducto producto;
    Npedido pedido;
    Ncliente cliente;
    Nrepartidor repartidor;

    //listas
    List<Producto> listProductos;
    List<Dpedido> listPedidos;
    List<Cliente> listClientes;
    List<Repartidor> listRepartidores;
    List<Pproduto> listProductosSeleccionados;
    String [] arrayEstados = {"En proceso","finalizado","cancelado"};
    List<String> listEstados = new ArrayList<>();




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
        listEstados.add("En proceso");


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

        ArrayAdapter<String> adapterClientes = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,cliente.getNombresClientes());
        ArrayAdapter<String> adapterRepartidores = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,repartidor.getNombresRepartidores());
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

                Cliente clienteSeleccionado = listClientes.get((int) positionCliente);
                Repartidor repartidorSeleccionado = listRepartidores.get((int) positionRepartidor);
                String estadoSeleccionado = arrayEstados[(int) positionEstado];
                String fecha = ed1Fecha.getText().toString();
                String total = ed2Total.getText().toString();


                //Toast.makeText(this,  listClientes.get((int) position).get("nombre").toString(), Toast.LENGTH_SHORT).show();
                Pedido data = new Pedido();
                data.setEstado(estadoSeleccionado);
                data.setFecha(fecha);
                data.setTotal(total);
                data.setClienteId(clienteSeleccionado.getId());
                data.setRepartidorId(repartidorSeleccionado.getId());
                Dpedido dPedido = new Dpedido(Ppedido.this,data);
                pedido.saveDatos(dPedido);
                    listar();
                    rv1.scrollToPosition(listPedidos.size()-1);
            }
        });

        builder.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();

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

        public class AdaptadorPedidoHolder extends RecyclerView.ViewHolder  {

            Spinner spEstado, spCliente, spRepartidor;
            EditText edFecha, edTotal;
            Button  btnDetalles, btnCambiarEstado;
            ImageButton btnEliminar, btnEdit,btnFecha;
            public AdaptadorPedidoHolder(@NonNull View itemView) {
                super(itemView);
                spEstado = itemView.findViewById(R.id.spEstadoPedido);
                spCliente = itemView.findViewById(R.id.spClientePedido);
                spRepartidor = itemView.findViewById(R.id.spRepartidorPedido);
                edFecha = itemView.findViewById(R.id.edFechaPedido);
                edTotal = itemView.findViewById(R.id.edTotalPedido);
                btnEliminar =  itemView.findViewById(R.id.btnEliminar);
                btnEdit = itemView.findViewById(R.id.btnSave);
                btnDetalles = itemView.findViewById(R.id.btnDetalles);
                btnFecha = itemView.findViewById(R.id.btnFecha);
                btnCambiarEstado = itemView.findViewById(R.id.btnCambiarEstado);

                cargarSpinners();
                eventos();

            }

            public void cargarSpinners() {
                ArrayAdapter<String> adapterEstados = new ArrayAdapter<String>(Ppedido.this, android.R.layout.simple_spinner_item,arrayEstados);
                spEstado.setAdapter(adapterEstados);

                ArrayAdapter<String> adapterClientes = new ArrayAdapter<String>(Ppedido.this, android.R.layout.simple_spinner_item,cliente.getNombresClientes());
                spCliente.setAdapter(adapterClientes);

                ArrayAdapter<String> adapterRepartidores = new ArrayAdapter<String>(Ppedido.this, android.R.layout.simple_spinner_item,repartidor.getNombresRepartidores());
                spRepartidor.setAdapter(adapterRepartidores);
            }

            public void eventos() {
                btnEliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        eliminar();
                    }
                });

                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editar();
                    }
                });

                btnDetalles.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickDetalles();
                    }
                });

                btnFecha.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog d = new DatePickerDialog(Ppedido.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                edFecha.setText(dayOfMonth+"/"+month+"/"+year);
                            }
                        },2023,0,1);
                        d.show();
                    }
                });

                btnCambiarEstado.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cambiarEstado();
                    }

                });


            }

            private void cambiarEstado() {
                int position = getLayoutPosition();
                Dpedido pedidoActual = listPedidos.get(position);

                long positionEstado = spEstado.getSelectedItemPosition();
                String estadoSeleccionado = arrayEstados[(int) positionEstado];

                if (estadoSeleccionado.equals("En proceso")){
                   pedidoActual.enProceso();
                }else if(estadoSeleccionado.equals("finalizado")){
                    pedidoActual.finalizado();
                }else{
                    pedidoActual.cancelado();
                }
            }


            public void imprimir(int position) {
                //listPedidos = pedido.getDatos();
                Dpedido pedidoActual = listPedidos.get(position);

                if (pedidoActual.getPedido().getEstado().equals("En proceso")){
                    spEstado.setSelection(0);
                }else if(pedidoActual.getPedido().getEstado().equals("finalizado")){
                    spEstado.setSelection(1);
                }else{
                    spEstado.setSelection(2);
                }

                String idcliente = pedidoActual.getPedido().getClienteId();
                Cliente clienteActual= cliente.getById(idcliente);
                int posCliente = listClientes.indexOf(clienteActual);
                spCliente.setSelection(posCliente);

                String idRepartidor =pedidoActual.getPedido().getRepartidorId();
                Repartidor repartidorActual= repartidor.getById(idRepartidor);
                int posRepartidor = listRepartidores.indexOf(repartidorActual);
                spRepartidor.setSelection(posRepartidor);

                edFecha.setText(pedidoActual.getPedido().getFecha());
                edTotal.setText(pedidoActual.getPedido().getTotal());

            }


            private void editar() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Ppedido.this);
                builder.setMessage("Desea guardar los cambios?");

                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = getLayoutPosition();

                        Dpedido pedidoActual = listPedidos.get(position);

                        long positionEstado = spEstado.getSelectedItemPosition();
                        String estadoSeleccionado = arrayEstados[(int) positionEstado];
                        pedidoActual.getPedido().setEstado(estadoSeleccionado);

                        long posicionCliente = spCliente.getSelectedItemPosition();
                        Cliente clienteSeleccionado = listClientes.get((int) posicionCliente);
                        pedidoActual.getPedido().setClienteId(clienteSeleccionado.getId());

                        long posicionRepartidor = spRepartidor.getSelectedItemPosition();
                        String repartidorId = listRepartidores.get((int) posicionRepartidor).getId();
                        pedidoActual.getPedido().setRepartidorId(repartidorId);

                        pedidoActual.getPedido().setFecha(edFecha.getText().toString());
                        pedidoActual.getPedido().setTotal(edTotal.getText().toString());

                        pedido.updateDatos(pedidoActual);
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

            private void eliminar() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Ppedido.this);
                builder.setMessage("Esta seguro que desea Eliminar este pedido?");

                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = getLayoutPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Dpedido pedidoActual = listPedidos.get(position);
                            String id = pedidoActual.getId();

                            //eliminar pedido
                            pedido.delete(id);
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

            private void onClickDetalles() {
                Intent intento = new Intent(Ppedido.this, PdetallePedido.class);
                int position = getLayoutPosition();
                String pedidoId = listPedidos.get(position).getId();
                intento.putExtra("pedido", (Serializable) pedidoId);
                startActivity(intento);
            }
        }
    }
}