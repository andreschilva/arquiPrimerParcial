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
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import arquitectura.trescapas.primerparcial.Negocio.Ncategoria;
import arquitectura.trescapas.primerparcial.Negocio.Nproducto;
import arquitectura.trescapas.primerparcial.R;
import arquitectura.trescapas.primerparcial.Utils.Utils;
import arquitectura.trescapas.primerparcial.clases.Categoria;
import arquitectura.trescapas.primerparcial.clases.Producto;


public class Pproduto extends AppCompatActivity{
    final int CAPTURA_IMAGEN = 1;
    RecyclerView rv1;
    TextView  tvNumero;
    ImageButton btnFoto;
    AdaptadorProducto aP;

    Nproducto producto;
    Ncategoria categoria;
    List<Producto> listProductos;
    List<Categoria> lisCategorias;
    List<Map<String,Object>> lisProductoCategoria;
    List<Producto> listProductosSeleccionados;
    Bitmap bitmap;

    String nombreFoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pproduto);

        tvNumero = findViewById(R.id.tvnumero1);

        producto = new Nproducto(this);
        categoria = new Ncategoria(this);
        listProductos = producto.getDatos();
        lisCategorias = categoria.getDatos();
        listProductosSeleccionados = new ArrayList<>();

        rv1 = findViewById(R.id.rv1);

        LinearLayoutManager l = new LinearLayoutManager(this);
        rv1.setLayoutManager(l);
        aP = new AdaptadorProducto();
        rv1.setAdapter(aP);

    }

    public void agregarProducto(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Crear Producto");
        View selector = getLayoutInflater().inflate(R.layout.modal_crear_producto,null);
        builder.setView(selector);


        Spinner spCategoria = selector.findViewById(R.id.spCategoriaP);
        EditText edNombre = selector.findViewById(R.id.editNombreP);
        EditText edDescripcion = selector.findViewById(R.id.editDescripcionP);
        EditText edPrecio = selector.findViewById(R.id.editPrecioP);
        btnFoto = selector.findViewById(R.id.imageButtonP);

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

        ArrayAdapter<String> adapterCategorias = getStringArrayAdapter();
        spCategoria.setAdapter(adapterCategorias);


        builder.setPositiveButton("crear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    String nombre= edNombre.getText().toString();
                    String descripcion=  edDescripcion.getText().toString();
                    String precio= edPrecio.getText().toString();
                    long positionCategoria= spCategoria.getSelectedItemPosition();
                    Categoria categoriaSeleccionada = lisCategorias.get((int) positionCategoria);

                    nombreFoto = crearNombreArchivoJPG();
                    guardarFoto();

                    Producto data = new Producto("",nombre,descripcion,precio,nombreFoto,categoriaSeleccionada.getId());
                    producto.saveDatos(data);
                    nombreFoto = null;

                    listar();
                    rv1.scrollToPosition(listProductos.size()-1);
                }catch (Exception e) {
                    Utils.mensaje(Pproduto.this, e.getMessage());
                }
            }


        });

        builder.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                File file = new File(getFilesDir(), nombreFoto);
//                boolean deleted = file.delete();
                nombreFoto = null;
            }
        });

        Dialog dialogo = builder.create();
        dialogo.show();

    }

    protected ArrayAdapter<String> getStringArrayAdapter() {
        int i=0;
        String [] arrayCategorias = new String[lisCategorias.size()];

        for (Categoria mapCategoria : lisCategorias) {
            arrayCategorias[i] = mapCategoria.getNombre();
            i++;
        }


        return new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrayCategorias);
    }
    protected void guardarFoto() throws IOException {
        FileOutputStream fos = openFileOutput(nombreFoto, Context.MODE_PRIVATE);
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
        fos.close();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)  {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
        // La foto se ha seleccionado exitosamente, puedes obtener la imagen desde 'data' y guardarla en tu tabla
            Uri path = data.getData();
            bitmap = Utils.getBitmapFromUri(this,path);
            int targetWidth = btnFoto.getWidth();
            int targetHeight = btnFoto.getHeight();
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false);
            btnFoto.setImageBitmap(resizedBitmap);

        // Guardar la foto en tu tabla
//            try {
//                nombreFoto = crearNombreArchivoJPG();
//                FileOutputStream fos = openFileOutput(nombreFoto, Context.MODE_PRIVATE);
//                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
//                fos.close();
//            }catch (Exception e) {
//
//            }
         }
//        if (requestCode==CAPTURA_IMAGEN && resultCode==RESULT_OK)
//        {
//            Bundle extras = data.getExtras();
//            Bitmap bitmap1 = (Bitmap)extras.get("data");
//            btnFoto.setImageBitmap(bitmap1);
//
//            try {
//                nombreFoto = crearNombreArchivoJPG();
//                FileOutputStream fos = openFileOutput(nombreFoto, Context.MODE_PRIVATE);
//                bitmap1.compress(Bitmap.CompressFormat.JPEG,100,fos);
//                fos.close();
//            }catch (Exception e) {
//
//            }
//        }
    }

    private String crearNombreArchivoJPG() {
        String fecha = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return fecha+".jpg";
    }

   public void listar() {
        this.listProductos = producto.getDatos();
        aP.notifyDataSetChanged();
    }

    public void enviarCotizacion(View v) {
        String numero = tvNumero.getText().toString();

        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Lista de productos:\n");

        double total = 0.0;
        for (Producto producto : this.listProductosSeleccionados) {
            String nombre =  producto.getNombre();
            double precio =Double.parseDouble( producto.getPrecio());
            int cantidad = 1;

            mensaje.append("Nombre: ").append(nombre).append("\n");
            mensaje.append("Precio: ").append(precio).append("\n");
            mensaje.append("Cantidad: ").append(cantidad).append("\n");
            mensaje.append("-----------------\n");

            total += precio*cantidad;
        }
        mensaje.append("Total: ").append(total).append("\n");

        //Toast.makeText(this,  mensaje, Toast.LENGTH_SHORT).show();

        //boolean installed = appInstalledOrNot("com.whatsapp");
        //if (installed) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+591"+numero+ "&text="+mensaje));
        startActivity(intent);
//        }else {
//            Toast.makeText(this, "Whasapp no esta instalado en tu dispositivo", Toast.LENGTH_SHORT).show();
//        }
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

        public class AdaptadorProductoHolder extends RecyclerView.ViewHolder  {
            Spinner edCategoria;
            TextView edNombre, edDescripcion, edPrecio, edCantidad;
            CheckBox checkBox;
            ImageButton btnEliminar, btnEditar, ivFoto;

            public AdaptadorProductoHolder(@NonNull View itemView) {
                super(itemView);
                edNombre = itemView.findViewById(R.id.edNombreP);
                edDescripcion = itemView.findViewById(R.id.edDescripcionP);
                edPrecio = itemView.findViewById(R.id.edPrecioP);
                edCategoria = itemView.findViewById(R.id.edCategoriaP);
                edCantidad = itemView.findViewById(R.id.edCantidad);
                checkBox = itemView.findViewById(R.id.cBProductos);
                btnEliminar =  itemView.findViewById(R.id.btnEliminar);
                btnEditar =  itemView.findViewById(R.id.btnEdit);
                ivFoto = itemView.findViewById(R.id.iv1);

                ArrayAdapter<String> adapterCategorias = new ArrayAdapter<String>(Pproduto.this, android.R.layout.simple_spinner_item,categoria.getNombresCategorias());
                edCategoria.setAdapter(adapterCategorias);

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
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        onCheckedCotizacion();
                    }
                });

                ivFoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cambiarFoto();
                    }
                });

            }

            private void cambiarFoto() {
                btnFoto = ivFoto;
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // intent.setType("image/");
                startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicacion"),1);
                nombreFoto = crearNombreArchivoJPG();
            }


            public void imprimir(int position) {
                try {

                    //listProductos = producto.getDatos();
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
                    edDescripcion.setText(productoActual.getDescripcion());
                    edPrecio.setText(productoActual.getPrecio());

                    String categoriaId = productoActual.getCategoriaId();
                    Categoria categoriaActual = categoria.getById(categoriaId);
                    int posCategoria = lisCategorias.indexOf(categoriaActual);
                    edCategoria.setSelection(posCategoria);

                }catch (Exception e) {
                    Utils.mensaje(Pproduto.this,e.getMessage());
                }

            }


            private void editar() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Pproduto.this);
                builder.setMessage("Desea guardar los cambios?");

                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = getLayoutPosition();
                        //listProductos = producto.getDatos();
                        Producto productoActual = listProductos.get(position);

                        productoActual.setNombre(edNombre.getText().toString());
                        productoActual.setDescripcion(edDescripcion.getText().toString());
                        productoActual.setPrecio(edPrecio.getText().toString());

                        long positionCategoria= edCategoria.getSelectedItemPosition();
                        Categoria categoriaSeleccionada = lisCategorias.get((int) positionCategoria);
                        productoActual.setCategoriaId(categoriaSeleccionada.getId());

                        if (nombreFoto != null) {
                            try {
                                File file = new File(getFilesDir(), productoActual.getFoto());
                                file.delete();

                                productoActual.setFoto(nombreFoto);
                                guardarFoto();
                            }catch (Exception e){
                                Utils.mensaje(Pproduto.this,e.getMessage());
                            }
                        }

                        producto.updateDatos(productoActual);
                        listar();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(Pproduto.this);
                builder.setMessage("Esta seguro que desea Eliminar este producto?");

                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = getLayoutPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Producto productoActual = listProductos.get(position);
                            String id = productoActual.getId();
                            //eliminar foto
                            File file = new File(getFilesDir(), productoActual.getFoto());
                            file.delete();
                            //eliminar producto
                            producto.delete(id);
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

            private void onCheckedCotizacion() {
                if (checkBox.isChecked()) {
                    Producto productoActual = listProductos.get(getLayoutPosition());
                    //productoActual.put("cantidad", edCantidad.getText());
                    listProductosSeleccionados.add(productoActual);
                }
                else {
                    listProductosSeleccionados.remove(listProductos.get(getLayoutPosition()));
                }
            }
        }
    }

}