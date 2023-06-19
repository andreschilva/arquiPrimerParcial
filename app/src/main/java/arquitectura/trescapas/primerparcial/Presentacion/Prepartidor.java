package arquitectura.trescapas.primerparcial.Presentacion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import arquitectura.trescapas.primerparcial.Negocio.Nrepartidor;
import arquitectura.trescapas.primerparcial.R;
import arquitectura.trescapas.primerparcial.Utils.Utils;
import arquitectura.trescapas.primerparcial.clases.Cliente;
import arquitectura.trescapas.primerparcial.clases.Repartidor;

public class Prepartidor extends AppCompatActivity {
    ImageButton btnFoto;
    RecyclerView rv1;
    AdaptadorRepartidor aR;
    Nrepartidor repartidor;
    List<Repartidor> listRepartidores;
    Bitmap bitmap;
    String nombreFoto;

    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepartidor);
       // getSupportActionBar().setTitle("Repartidores");

        repartidor = new Nrepartidor(this);
        listRepartidores = repartidor.getDatos();
        rv1 = findViewById(R.id.rv1);

        LinearLayoutManager l=new LinearLayoutManager(this);
        rv1.setLayoutManager(l);
        aR = new AdaptadorRepartidor();
        rv1.setAdapter(aR);
    }

    public void agregarRepartidor(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Crear Repartidor");
        View selector = getLayoutInflater().inflate(R.layout.modalcrearusuario,null);
        builder.setView(selector);

        EditText etNombre = selector.findViewById(R.id.editNombreC);
        EditText etApellido  = selector.findViewById(R.id.editApellidoC);
        EditText etCelular = selector.findViewById(R.id.editCelularC);
        EditText etPlaca = selector.findViewById(R.id.editLink);
        etPlaca.setHint("Placa");
        btnFoto = selector.findViewById(R.id.imageButtonC);

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                String placa= etPlaca.getText().toString();

                nombreFoto = Utils.crearNombreArchivoJPG();
                Utils.guardarFoto(bitmap,Prepartidor.this,nombreFoto);

                Repartidor rp = Repartidor.crear("",nombre,apellido,celular,placa,nombreFoto);
                repartidor.saveDatos(rp);
                nombreFoto = null;

                listar();
                rv1.scrollToPosition(listRepartidores.size()-1);
            }catch (Exception e){
                Utils.mensaje(Prepartidor.this, e.getMessage());
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

    public void listar() {
        this.listRepartidores = repartidor.getDatos();
        aR.notifyDataSetChanged();
    }

    private class AdaptadorRepartidor extends RecyclerView.Adapter<Prepartidor.AdaptadorRepartidor.AdaptadorRepartidorHolder> {

        @NonNull
        @Override
        public Prepartidor.AdaptadorRepartidor.AdaptadorRepartidorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdaptadorRepartidorHolder(getLayoutInflater().inflate(R.layout.item_usuarios,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull Prepartidor.AdaptadorRepartidor.AdaptadorRepartidorHolder holder, int position) {

            holder.imprimir(position);


        }

        @Override
        public int getItemCount() {
            return listRepartidores.size();
        }

        public class AdaptadorRepartidorHolder extends RecyclerView.ViewHolder  {

            EditText edNombre, edApellido, edCelular, edPlaca;
            TextView tvPlaca;
            ImageButton btnEliminar, btnEditar, btnFotoUsuario;

            public AdaptadorRepartidorHolder(@NonNull View itemView) {
                super(itemView);
                edNombre = itemView.findViewById(R.id.edNombreU);
                edApellido = itemView.findViewById(R.id.edApellidoU);
                edCelular = itemView.findViewById(R.id.edCelularU);
                edPlaca = itemView.findViewById(R.id.edLinkU);
                btnEliminar =  itemView.findViewById(R.id.btnEliminar);
                btnEditar = itemView.findViewById(R.id.btnEdit);
                tvPlaca = itemView.findViewById(R.id.tvGenerico1U);
                btnFotoUsuario =  itemView.findViewById(R.id.btnFotoUsuario);


                tvPlaca.setText("Placa:");

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
                    Repartidor repartidorActual = listRepartidores.get(position);
                    String fotoActual = repartidorActual.getFoto();
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
                    fileInputStream.close();


                    listRepartidores = repartidor.getDatos();
                    edNombre.setText(repartidorActual.getNombre());
                    edApellido.setText(repartidorActual.getApellido());
                    edCelular.setText(repartidorActual.getCelular());
                    edPlaca.setText(repartidorActual.getPlaca());

                }catch (Exception e){
                    Utils.mensaje(Prepartidor.this,e.getMessage());
                }
            }




            private void editar() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Prepartidor.this);
                builder.setMessage("Desea guardar los cambios?");

                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = getLayoutPosition();
                        listRepartidores = repartidor.getDatos();
                        Repartidor repartidorActual = listRepartidores.get(position);

                        repartidorActual.setNombre(edNombre.getText().toString());
                        repartidorActual.setApellido(edApellido.getText().toString());
                        repartidorActual.setCelular(edCelular.getText().toString());
                        repartidorActual.setPlaca(edPlaca.getText().toString());

                        if (nombreFoto != null) {
                            try {
                                File file = new File(getFilesDir(), repartidorActual.getFoto());
                                file.delete();

                                repartidorActual.setFoto(nombreFoto);
                                Utils.guardarFoto(bitmap,Prepartidor.this,nombreFoto);
                            }catch (Exception e){
                                Utils.mensaje(Prepartidor.this,e.getMessage());
                            }
                        }

                        repartidor.updateDatos(repartidorActual);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(Prepartidor.this);
                builder.setMessage("Esta seguro que desea Eliminar este Repartidor?");

                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = getLayoutPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            String id = listRepartidores.get(getLayoutPosition()).getId();
                            repartidor.delete(id);
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