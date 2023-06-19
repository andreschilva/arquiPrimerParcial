package arquitectura.trescapas.primerparcial.Presentacion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import arquitectura.trescapas.primerparcial.Negocio.Ncliente;
import arquitectura.trescapas.primerparcial.R;
import arquitectura.trescapas.primerparcial.Utils.Utils;
import arquitectura.trescapas.primerparcial.clases.Cliente;
import arquitectura.trescapas.primerparcial.clases.Producto;

public class Pcliente extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ImageButton btnFoto;
    RecyclerView rv1;
    AdaptadorCliente aC;
    Ncliente cliente;
    List<Cliente> listClientes;
    SearchView buscador;
    Bitmap bitmap;
    String nombreFoto;

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
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // intent.setType("image/");
                startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicacion"),1);


            }

        });


        builder.setPositiveButton("crear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    String nombre= etNombre.getText().toString();
                    String apellido=  etApellido.getText().toString();
                    String celular= etCelular.getText().toString();
                    String ubicacion= etLink.getText().toString();

                    nombreFoto = Utils.crearNombreArchivoJPG();
                    Utils.guardarFoto(bitmap,Pcliente.this,nombreFoto);

                    Cliente cl = Cliente.crear("",nombre,apellido,celular,ubicacion,nombreFoto);
                    cliente.saveDatos(cl);
                    nombreFoto = null;

                    listar();
                    rv1.scrollToPosition(listClientes.size()-1);
                }catch (Exception e){
                    Utils.mensaje(Pcliente.this, e.getMessage());
                }


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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            // La foto se ha seleccionado exitosamente, puedes obtener la imagen desde 'data' y guardarla en tu tabla
            Uri path = data.getData();
            bitmap = Utils.getBitmapFromUri(this, path);
            int targetWidth = btnFoto.getWidth();
            int targetHeight = btnFoto.getHeight();
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false);
            btnFoto.setImageBitmap(resizedBitmap);
        }
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


        public class AdaptadorClienteHolder extends RecyclerView.ViewHolder {

            TextView edNombre, edApellido, edCelular, edLink;
            ImageButton btnEliminar, btnEditar,btnFotoUsuario;

            public AdaptadorClienteHolder(@NonNull View itemView) {
                super(itemView);
                edNombre = itemView.findViewById(R.id.edNombreU);
                edApellido = itemView.findViewById(R.id.edApellidoU);
                edCelular = itemView.findViewById(R.id.edCelularU);
                edLink = itemView.findViewById(R.id.edLinkU);
                btnEliminar =  itemView.findViewById(R.id.btnEliminar);
                btnEditar = itemView.findViewById(R.id.btnEdit);
                btnFotoUsuario =  itemView.findViewById(R.id.btnFotoUsuario);


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

                btnFotoUsuario.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cambiarFoto();
                    }
                });

            }



            public void imprimir(int position) {
                try {
                    Cliente clienteActual = listClientes.get(position);
                    String fotoActual = clienteActual.getFoto();
                    FileInputStream fileInputStream = openFileInput(fotoActual);
                    Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);

                    btnFotoUsuario.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            // Elimina el listener para evitar llamadas adicionales
                            btnFotoUsuario.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                            // Obtiene las dimensiones del ImageView
                            int targetWidth = btnFotoUsuario.getWidth();
                            int targetHeight = btnFotoUsuario.getHeight();

                            // Crea el nuevo Bitmap redimensionado
                            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false);

                            // Asigna el nuevo Bitmap redimensionado al ImageView
                            btnFotoUsuario.setImageBitmap(resizedBitmap);
                        }
                    });

                    //ivFoto.setImageBitmap(resizedBitmap);
                    fileInputStream.close();

                    edNombre.setText(clienteActual.getNombre());
                    edApellido.setText(clienteActual.getApellido());
                    edCelular.setText(clienteActual.getCelular());
                    edLink.setText(clienteActual.getUbicacion());

                }catch (Exception e){
                    Utils.mensaje(Pcliente.this,e.getMessage());
                }
            }


            private void editar() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Pcliente.this);
                builder.setMessage("Desea guardar los cambios?");

                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = getLayoutPosition();
                        Cliente clienteActual = listClientes.get(position);

                        clienteActual.setNombre(edNombre.getText().toString());
                        clienteActual.setApellido(edApellido.getText().toString());
                        clienteActual.setCelular(edCelular.getText().toString());
                        clienteActual.setUbicacion(edLink.getText().toString());

                        if (nombreFoto != null) {
                            try {
                                File file = new File(getFilesDir(), clienteActual.getFoto());
                                file.delete();

                                clienteActual.setFoto(nombreFoto);
                                Utils.guardarFoto(bitmap,Pcliente.this,nombreFoto);
                            }catch (Exception e){
                                Utils.mensaje(Pcliente.this,e.getMessage());
                            }
                        }

                        cliente.updateDatos(clienteActual);
                        nombreFoto = null;
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

            private void cambiarFoto() {
                btnFoto = btnFotoUsuario;
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // intent.setType("image/");
                startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicacion"),1);
                nombreFoto = Utils.crearNombreArchivoJPG();
            }
        }
    }
}