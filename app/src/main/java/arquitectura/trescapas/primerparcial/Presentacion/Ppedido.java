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
import java.util.List;

import arquitectura.trescapas.primerparcial.Negocio.Ncliente;
import arquitectura.trescapas.primerparcial.Negocio.Ncotizacion;
import arquitectura.trescapas.primerparcial.Negocio.Npedido;
import arquitectura.trescapas.primerparcial.Negocio.Nproducto;
import arquitectura.trescapas.primerparcial.Negocio.Nrepartidor;
import arquitectura.trescapas.primerparcial.PdetallePedido;
import arquitectura.trescapas.primerparcial.Presentacion.adaptadores.SpinnerAdapter;
import arquitectura.trescapas.primerparcial.R;
import arquitectura.trescapas.primerparcial.clases.Cliente;
import arquitectura.trescapas.primerparcial.clases.Cotizacion;
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
    Ncotizacion cotizacion;

    //listas
    List<Producto> listProductos;
    List<Pedido> listPedidos;
    List<Cliente> listClientes;
    List<Repartidor> listRepartidores;
    List<Cotizacion> listCotizaciones;
    String [] arrayEstados = {"En proceso","finalizado","cancelado"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ppedido);
        //getSupportActionBar().setTitle("Pedidos");

        producto = new Nproducto(this);
        cliente = new Ncliente(this);
        repartidor = new Nrepartidor(this);
        pedido = new Npedido(this);
        cotizacion = new Ncotizacion(this);

        //cargando listas
        listProductos = producto.getDatos();
        listClientes= cliente.getDatos();
        listRepartidores = repartidor.getDatos();
        listPedidos = pedido.getDatos();
        listCotizaciones = cotizacion.getDatos();


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

        Spinner spEstado = selector.findViewById(R.id.spEstado);
        Spinner spCliente = selector.findViewById(R.id.spCliente);
        Spinner spRepartidor = selector.findViewById(R.id.spRepartidor);
        Spinner spCotizacion= selector.findViewById(R.id.spCotizacionCrearP);
        EditText ed1Fecha = selector.findViewById(R.id.edFecha);
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

        SpinnerAdapter adapterClientes = new SpinnerAdapter(this, listClientes);
        spCliente.setAdapter(adapterClientes);

        SpinnerAdapter adapterRepartidores = new SpinnerAdapter(this, listRepartidores);
        spRepartidor.setAdapter(adapterRepartidores);

        SpinnerAdapter adapterCotizaciones = new SpinnerAdapter(this, listCotizaciones);
        spCotizacion.setAdapter(adapterCotizaciones);

        ArrayAdapter<String> adapterEstados = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrayEstados);
        spEstado.setAdapter(adapterEstados);


        builder.setPositiveButton("crear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long positionEstado= spEstado.getSelectedItemPosition();

                Cliente clienteSeleccionado = (Cliente) spCliente.getSelectedItem();
                Repartidor repartidorSeleccionado = (Repartidor) spRepartidor.getSelectedItem();
                Cotizacion cotizacionSeleccionada = (Cotizacion) spCotizacion.getSelectedItem();
                String estadoSeleccionado = arrayEstados[(int) positionEstado];
                String fecha = ed1Fecha.getText().toString();


                Pedido data = new Pedido("",fecha,"",estadoSeleccionado,clienteSeleccionado.getId(),
                        repartidorSeleccionado.getId(),cotizacionSeleccionada.getId());
                pedido.saveDatos(data);
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

            Spinner spEstado, spCliente, spRepartidor, spCotizcion;
            EditText edFecha, edTotal;
            Button  btnDetalles, btnCambiarEstado;
            ImageButton btnEliminar, btnEdit,btnFecha;
            public AdaptadorPedidoHolder(@NonNull View itemView) {
                super(itemView);
                spEstado = itemView.findViewById(R.id.spEstadoPedido);
                spCliente = itemView.findViewById(R.id.spClientePedido);
                spRepartidor = itemView.findViewById(R.id.spRepartidorPedido);
                spCotizcion = itemView.findViewById(R.id.spCotizaciones);
                edFecha = itemView.findViewById(R.id.edFechaPedido);
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

                SpinnerAdapter adapterClientes = new SpinnerAdapter(Ppedido.this, listClientes);
                spCliente.setAdapter(adapterClientes);

                SpinnerAdapter adapterRepartidores = new SpinnerAdapter(Ppedido.this, listRepartidores);
                spRepartidor.setAdapter(adapterRepartidores);

                SpinnerAdapter adapterCotizaciones = new SpinnerAdapter(Ppedido.this, listCotizaciones);
                spCotizcion.setAdapter(adapterCotizaciones);
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
                        detalles();
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
                Pedido pedidoActual = listPedidos.get(position);

                long positionEstado = spEstado.getSelectedItemPosition();
                String estadoSeleccionado = arrayEstados[(int) positionEstado];

                if (estadoSeleccionado.equals("En proceso")){
                   pedido.enProceso(pedidoActual);
                }else if(estadoSeleccionado.equals("finalizado")){
                    pedido.finalizado(pedidoActual);
                }else{
                    pedido.cancelado(pedidoActual);
                }
            }


            public void imprimir(int position) {
                //listPedidos = pedido.getDatos();
                Pedido pedidoActual = listPedidos.get(position);

                if (pedidoActual.getEstado().equals("En proceso")){
                    spEstado.setSelection(0);
                }else if(pedidoActual.getEstado().equals("finalizado")){
                    spEstado.setSelection(1);
                }else{
                    spEstado.setSelection(2);
                }

                String idcliente = pedidoActual.getClienteId();
                Cliente clienteActual= cliente.getById(idcliente);
                int posCliente = listClientes.indexOf(clienteActual);
                spCliente.setSelection(posCliente);

                String idRepartidor =pedidoActual.getRepartidorId();
                Repartidor repartidorActual= repartidor.getById(idRepartidor);
                int posRepartidor = listRepartidores.indexOf(repartidorActual);
                spRepartidor.setSelection(posRepartidor);

                String idCotizacion =pedidoActual.getCotizacionId();
                Cotizacion cotizacionActual= cotizacion.getById(idCotizacion);
                int posCotizacion = listCotizaciones.indexOf(cotizacionActual);
                spCotizcion.setSelection(posCotizacion);

                edFecha.setText(pedidoActual.getFecha());

            }


            private void editar() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Ppedido.this);
                builder.setMessage("Desea guardar los cambios?");

                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = getLayoutPosition();

                        Pedido pedidoActual = listPedidos.get(position);

                        Cliente clienteSeleccionado = (Cliente) spCliente.getSelectedItem();
                        pedidoActual.setClienteId(clienteSeleccionado.getId());

                        Repartidor repartidorSeleccionado = (Repartidor) spRepartidor.getSelectedItem();
                        pedidoActual.setRepartidorId(repartidorSeleccionado.getId());

                        Cotizacion cotizacionSeleccionada = (Cotizacion) spCotizcion.getSelectedItem();
                        pedidoActual.setCotizacionId(cotizacionSeleccionada.getId());

                        pedidoActual.setFecha(edFecha.getText().toString());

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
                            Pedido pedidoActual = listPedidos.get(position);
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

            private void detalles() {
                Intent intento = new Intent(Ppedido.this, PdetallePedido.class);
                int position = getLayoutPosition();
                String pedidoId = listPedidos.get(position).getId();
                intento.putExtra("pedido", (Serializable) pedidoId);
                startActivity(intento);
            }
        }
    }
}