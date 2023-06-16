package arquitectura.trescapas.primerparcial.Presentacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.List;
import java.util.stream.Collectors;

import arquitectura.trescapas.primerparcial.Negocio.Ncliente;
import arquitectura.trescapas.primerparcial.R;
import arquitectura.trescapas.primerparcial.clases.Cliente;

public class Pcliente extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ImageButton btnFoto;
    RecyclerView rv1;
    AdaptadorCliente aC;
    Ncliente cliente;
    List<Cliente> listClientes;
    SearchView buscador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcliente);
        //getSupportActionBar().setTitle("Clientes");

        buscador = findViewById(R.id.searchView);
        cliente = new Ncliente(this);
        listClientes = cliente.getDatos();
        rv1 = findViewById(R.id.rv1);
        LinearLayoutManager l=new LinearLayoutManager(this);
        rv1.setLayoutManager(l);
        aC = new AdaptadorCliente();
        rv1.setAdapter(aC);
        buscador.setOnQueryTextListener(this);
    }


    public void agregarCliente(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Crear Cliente");
        View selector = getLayoutInflater().inflate(R.layout.modalcrearusuario,null);
        builder.setView(selector);

        EditText etNombre = selector.findViewById(R.id.editNombreC);
        EditText etApellido  = selector.findViewById(R.id.editApellidoC);
        EditText etCelular = selector.findViewById(R.id.editCelularC);
        EditText etLink = selector.findViewById(R.id.editLink);
        btnFoto = selector.findViewById(R.id.imageButtonC);

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent,CAPTURA_IMAGEN);

            }

        });


        builder.setPositiveButton("crear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String nombre= etNombre.getText().toString();
                String apellido=  etApellido.getText().toString();
                String celular= etCelular.getText().toString();
                String ubicacion= etLink.getText().toString();

                Cliente cl = Cliente.crear("",nombre,apellido,celular,ubicacion);
                cliente.saveDatos(cl);
                listar();
                rv1.scrollToPosition(listClientes.size()-1);

            }
        });

        builder.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        Dialog dialogo = builder.create();
        dialogo.show();

    }


    public void listar() {
        this.listClientes = cliente.getDatos();
        aC.notifyDataSetChanged();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
       aC.filter(newText);
        return  false;
    }


    private class AdaptadorCliente extends RecyclerView.Adapter<AdaptadorCliente.AdaptadorClienteHolder> {

        @NonNull
        @Override
        public AdaptadorClienteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdaptadorClienteHolder(getLayoutInflater().inflate(R.layout.item_usuarios,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull AdaptadorClienteHolder holder, int position) {

            holder.imprimir(position);


        }

        @Override
        public int getItemCount() {
            return listClientes.size();
        }

        public void filter(String strBusqueda){
            if (strBusqueda.length() == 0){
                listClientes = cliente.getDatos();
            }
            else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    listClientes.clear();
                    List<Cliente> collect = cliente.getDatos().stream()
                            .filter(cliente -> cliente.getNombre().toLowerCase().contains(strBusqueda))
                            .collect(Collectors.toList());


                    listClientes.addAll(collect);
                }
                else {
                    listClientes.clear();
                    for (Cliente cl: cliente.getDatos()) {
                        if (cl.getNombre().toLowerCase().contains(strBusqueda)){
                            listClientes.add(cl);
                        }
                    }
                }
            }
            notifyDataSetChanged();
        }


        public class AdaptadorClienteHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

            TextView edNombre, edApellido, edCelular, edLink;
            ImageButton btnEliminar, btnEditar;

            public AdaptadorClienteHolder(@NonNull View itemView) {
                super(itemView);
                edNombre = itemView.findViewById(R.id.edNombreU);
                edApellido = itemView.findViewById(R.id.edApellidoU);
                edCelular = itemView.findViewById(R.id.edCelularU);
                edLink = itemView.findViewById(R.id.edLinkU);
                btnEliminar =  itemView.findViewById(R.id.btnEliminar);
                btnEditar = itemView.findViewById(R.id.btnEdit);
                itemView.setOnClickListener(this);

                btnEliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        eliminar();
                    }
                });

                btnEditar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editar();
                    }
                });

            }



            public void imprimir(int position) {
                //listClientes = cliente.getDatos();
                edNombre.setText(listClientes.get(position).getNombre());
                edApellido.setText(listClientes.get(position).getApellido());
                edCelular.setText(listClientes.get(position).getCelular());
                edLink.setText(listClientes.get(position).getUbicacion());
            }


            @Override
            public void onClick(View v) {
                //int position = getLayoutPosition();
                //mostrar(position);
            }

            private void editar() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Pcliente.this);
                builder.setMessage("Desea guardar los cambios?");

                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = getLayoutPosition();
                        //listClientes = cliente.getDatos();
                        Cliente clienteActual = listClientes.get(position);

                        clienteActual.setNombre(edNombre.getText().toString());
                        clienteActual.setApellido(edApellido.getText().toString());
                        clienteActual.setCelular(edCelular.getText().toString());
                        clienteActual.setUbicacion(edLink.getText().toString());

                        cliente.updateDatos(clienteActual);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(Pcliente.this);
                builder.setMessage("Esta seguro que desea Eliminar este Cliente?");

                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = getLayoutPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            String id = listClientes.get(getLayoutPosition()).getId();
                            cliente.delete(id);
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
        }
    }
}