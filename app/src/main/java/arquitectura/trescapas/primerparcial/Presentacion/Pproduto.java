package arquitectura.trescapas.primerparcial.Presentacion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import arquitectura.trescapas.primerparcial.DB.DBmigrations;
import arquitectura.trescapas.primerparcial.Negocio.Ncategoria;
import arquitectura.trescapas.primerparcial.Negocio.Nproducto;
import arquitectura.trescapas.primerparcial.R;

public class Pproduto extends AppCompatActivity {
    final int CAPTURA_IMAGEN = 1;
    RecyclerView rv1;
    View selector2 = getLayoutInflater().inflate(R.layout.modal_crear_producto,null);
    final ImageButton btnFotoP = selector2.findViewById(R.id.imageButton);
    AdaptadorProducto aP;
    Nproducto producto;
    Ncategoria categoria;
    List<Map<String,Object>> listProductos;
    List<Map<String,Object>> lisCategorias;

    //Intent intent;

    List<Map<String,Object>> listCotizacion;
    TextView  tvNumero;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pproduto);

        tvNumero = findViewById(R.id.tvnumero1);

        producto = new Nproducto(this);
        categoria = new Ncategoria(this);
        listProductos = producto.getDatos();
        lisCategorias= categoria.getDatos();
        listCotizacion = new ArrayList<>();

        rv1 = findViewById(R.id.rv1);

        LinearLayoutManager l=new LinearLayoutManager(this);
        rv1.setLayoutManager(l);
        aP= new AdaptadorProducto();
        rv1.setAdapter(aP);

        //btnFotoP = findViewById(R.id.imgb);

        btnFotoP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAPTURA_IMAGEN);

            }

        });
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
        ImageButton btnFoto = selector.findViewById(R.id.imageButton);

//        btnFoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent,CAPTURA_IMAGEN);
//
//            }
//
//        });

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

    public void cargarFoto(View v) {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent,CAPTURA_IMAGEN);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CAPTURA_IMAGEN && resultCode==RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap bitmap1 = (Bitmap)extras.get("data");
            btnFotoP.setImageBitmap(bitmap1);
        }
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
        aP.notifyDataSetChanged();
    }

    public void enviarCotizacion(View v) {
        String numero = tvNumero.getText().toString();

        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Lista de productos:\n");

        double total = 0.0;
        for (Map<String, Object> producto : this.listCotizacion) {
            String nombre =  producto.get("nombre").toString();
            double precio =Double.parseDouble( producto.get("precio").toString());

            mensaje.append("Nombre: ").append(nombre).append("\n");
            mensaje.append("Precio: ").append(precio).append("\n");
            mensaje.append("-----------------\n");

            total += precio;
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

        public class AdaptadorProductoHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

            TextView tv1, tv2, tv3,tv4;
            CheckBox chCotizacion;
            Button btnEliminar;

            public AdaptadorProductoHolder(@NonNull View itemView) {
                super(itemView);
                tv1 = itemView.findViewById(R.id.tvNombre);
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
                    listCotizacion.add(listProductos.get(getLayoutPosition()));
                else {
                    listCotizacion.remove(listProductos.get(getLayoutPosition()));
                }
            }

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
                mostrar(position);
            }

            private void onClickEliminar() {
                int position = getLayoutPosition();
                if (position != RecyclerView.NO_POSITION) {
                    String id = listProductos.get(getLayoutPosition()).get("id").toString();
                    producto.delete(id);
                    listar();
                    Toast.makeText(Pproduto.this, "eliminado", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}