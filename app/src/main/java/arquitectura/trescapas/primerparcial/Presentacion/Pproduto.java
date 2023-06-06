package arquitectura.trescapas.primerparcial.Presentacion;

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
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import arquitectura.trescapas.primerparcial.DB.DBmigrations;
import arquitectura.trescapas.primerparcial.Negocio.Ncategoria;
import arquitectura.trescapas.primerparcial.Negocio.Nproducto;
import arquitectura.trescapas.primerparcial.Presentacion.adaptadores.AdaptadorProducto;
import arquitectura.trescapas.primerparcial.R;

public class Pproduto extends AppCompatActivity{
    final int CAPTURA_IMAGEN = 1;
    RecyclerView rv1;
    TextView  tvNumero;
    ImageButton btnFoto;
    AdaptadorProducto aP;

    Nproducto producto;
    Ncategoria categoria;
    List<Map<String,Object>> listProductos;
    List<Map<String,Object>> lisCategorias;
    List<Map<String,Object>> lisProductoCategoria;
    List<Map<String,Object>> listProductosSeleccionados;

    //int pos;

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
        unirtListaProductoCategoria();
        listProductosSeleccionados = new ArrayList<>();

        rv1 = findViewById(R.id.rv1);

        LinearLayoutManager l = new LinearLayoutManager(this);
        rv1.setLayoutManager(l);
        aP = new AdaptadorProducto(this,listProductosSeleccionados, lisProductoCategoria);
        eliminarProducto();
        editarProducto();
        rv1.setAdapter(aP);

    }

    private void eliminarProducto() {
        aP.setOnClickListenerBtnEliminar(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Pproduto.this);
                builder.setMessage("Esta seguro que desea Eliminar este producto?");

                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = aP.getPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            String id = listProductos.get(position).get("id").toString();
                            producto.delete(id);
                            listar();
                            Toast.makeText(Pproduto.this, "eliminado", Toast.LENGTH_SHORT).show();
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
        });
    }
    private void editarProducto() {
        aP.setOnClickListenerBtnEdit(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Pproduto.this);
                builder.setMessage("Desea Guardar Los Cambios?");

                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Map<String, Object> data = aP.getProductoSeleccionado();
                        producto.updateDatos(data);
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
        });
    }


    private void unirtListaProductoCategoria() {
        lisProductoCategoria = new ArrayList<>() ;
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

    public void agregarProducto(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Crear Producto");
        View selector = getLayoutInflater().inflate(R.layout.modal_crear_producto,null);
        builder.setView(selector);


        Spinner spCategoria = selector.findViewById(R.id.spCategoriaP);
        EditText edNombre = selector.findViewById(R.id.editNombreP);
        EditText edDescripcion = selector.findViewById(R.id.editDescripcionP);
        EditText edPrecio = selector.findViewById(R.id.editPrecioP);
        btnFoto = selector.findViewById(R.id.imageButton);

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAPTURA_IMAGEN);

            }

        });

        int i=0;
        String [] arrayCategorias = new String[lisCategorias.size()];

        for (Map<String,Object> mapCategoria : lisCategorias) {
            arrayCategorias[i] = mapCategoria.get("nombre").toString();
            i++;
        }


        ArrayAdapter<String> adapterCategorias = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrayCategorias);
        spCategoria.setAdapter(adapterCategorias);


        builder.setPositiveButton("crear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String nombre= edNombre.getText().toString();
                String descripcion=  edDescripcion.getText().toString();
                String precio= edPrecio.getText().toString();
                long positionCategoria= spCategoria.getSelectedItemPosition();
                Map<String, Object> categoriaSeleccionada = lisCategorias.get((int) positionCategoria);


                //Toast.makeText(this,  lisCategorias.get((int) position).get("nombre").toString(), Toast.LENGTH_SHORT).show();

                Map<String, Object> data = new HashMap<>();

                data.put(DBmigrations.PRODUCTO_NOMBRE,nombre);
                data.put(DBmigrations.PRODUCTO_DESCRIPCION,descripcion);
                data.put(DBmigrations.PRODUCTO_PRECIO,precio);
                data.put(DBmigrations.PRODUCTO_CATEGORIAID,categoriaSeleccionada.get("id"));
                data.put(DBmigrations.PRODUCTO_FOTO,nombreFoto);


                if (producto.saveDatos(data)){
                    listar();
                    rv1.scrollToPosition(listProductos.size()-1);
                    Toast.makeText(Pproduto.this, "Producto creado", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(Pproduto.this, "Producto ya existente", Toast.LENGTH_SHORT).show();
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
        if (requestCode==CAPTURA_IMAGEN && resultCode==RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap bitmap1 = (Bitmap)extras.get("data");
            btnFoto.setImageBitmap(bitmap1);

            try {
                nombreFoto = crearNombreArchivoJPG();
                FileOutputStream fos = openFileOutput(nombreFoto, Context.MODE_PRIVATE);
                bitmap1.compress(Bitmap.CompressFormat.JPEG,100,fos);
                fos.close();
            }catch (Exception e) {

            }
        }
    }

    private String crearNombreArchivoJPG() {
        String fecha = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return fecha+".jpg";
    }

    //    public void agregar(View v) {
//        String nombre= etNombre.getText().toString();
//        String apellido=  etDescripcion.getText().toString();
//        String celular= etPrecio.getText().toString();
//        long position = spCategoria.getSelectedItemId();
//        //Toast.makeText(this,  lisCategorias.get((int) position).get("nombre").toString(), Toast.LENGTH_SHORT).show();
//        Map<String, Object> data = new HashMap<>();
//
//        data.put(DBmigrations.PRODUCTO_NOMBRE,nombre);
//        data.put(DBmigrations.PRODUCTO_DESCRIPCION,apellido);
//        data.put(DBmigrations.PRODUCTO_PRECIO,celular);
//        data.put(DBmigrations.PRODUCTO_CATEGORIAID,lisCategorias.get((int) position).get("id").toString());
//
//
//
//        if (producto.saveDatos(data)){
//            listar();
//            rv1.scrollToPosition(listProductos.size()-1);
//            Toast.makeText(this, "Producto creado", Toast.LENGTH_SHORT).show();
//
//        }else {
//            Toast.makeText(this, "Producto ya existente", Toast.LENGTH_SHORT).show();
//        }
//
//    }

    public void mostrar(int position) {
//        etNombre.setText(listProductos.get(position).get(DBmigrations.PRODUCTO_NOMBRE).toString());
//        etDescripcion.setText(listProductos.get(position).get(DBmigrations.PRODUCTO_DESCRIPCION).toString());
//        etPrecio.setText(listProductos.get(position).get(DBmigrations.PRODUCTO_PRECIO).toString());
//        String idcategoria =listProductos.get(position).get(DBmigrations.PRODUCTO_CATEGORIAID).toString();
//        //String nombreCategoria= categoria.getDcategoriaById(idcategoria).row();
//        int posCategoria = lisCategorias.indexOf(categoria.getDcategoriaById(idcategoria).row());
//        spCategoria.setSelection(posCategoria);
//        //Toast.makeText(this,  posCategoria, Toast.LENGTH_SHORT).show();
//        this.pos = position;
    }
//    public void actualizar(View v) {
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
//
//    }
    public void listar() {
        this.listProductos = producto.getDatos();
        unirtListaProductoCategoria();
        aP.setListProductos(this.lisProductoCategoria);
        aP.notifyDataSetChanged();
    }

    public void enviarCotizacion(View v) {
        String numero = tvNumero.getText().toString();

        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Lista de productos:\n");

        double total = 0.0;
        for (Map<String, Object> producto : this.listProductosSeleccionados) {
            String nombre =  producto.get("nombre").toString();
            double precio =Double.parseDouble( producto.get("precio").toString());
            int cantidad = Integer.parseInt(producto.get("cantidad").toString());

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

}