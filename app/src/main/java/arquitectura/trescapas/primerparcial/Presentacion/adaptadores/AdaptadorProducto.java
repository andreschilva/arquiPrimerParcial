package arquitectura.trescapas.primerparcial.Presentacion.adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import arquitectura.trescapas.primerparcial.DB.DBmigrations;
import arquitectura.trescapas.primerparcial.R;

public class AdaptadorProducto extends RecyclerView.Adapter<AdaptadorProducto.AdaptadorProductoHolder> {
    Context context;
    List<Map<String,Object>> listProductos;
    List<Map<String,Object>> listProductosSeleccionados;
    Boolean desactivarbtnEliminar;
    Boolean desactivarbtnEdit;
    Boolean desactivarChBox;
    View vista;
    int position = -1;
    private View.OnClickListener listenerBtnEliminar;
    private View.OnClickListener listenerBtnEDit;
    private Map<String,Object> productoSeleccionado;

    public AdaptadorProducto(Context context,List<Map<String,Object>> listProductosSeleccionados,List<Map<String,Object>> lisProductos) {
        this.context = context;
        this.listProductos = lisProductos;
        this.listProductosSeleccionados = listProductosSeleccionados;
        desactivarbtnEdit = false;
        desactivarbtnEliminar= false;
    }



    @NonNull
    @Override
    public AdaptadorProductoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto,parent,false);
        return new AdaptadorProductoHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorProductoHolder holder, int position) {

        holder.imprimir(position);

    }

    @Override
    public int getItemCount() {
        return listProductos.size();
    }

    public void setOnClickListenerBtnEliminar(View.OnClickListener listener) {
        this.listenerBtnEliminar = listener;
    }

    public void setOnClickListenerBtnEdit(View.OnClickListener listener) {
        this.listenerBtnEDit = listener;
    }

    public void setDesactivarbtnEdit(Boolean desactivarbtnEdit) {
        this.desactivarbtnEdit = desactivarbtnEdit;
    }
    public void setDesactivarbtnEliminar(Boolean desactivarbtnEliminar) {
        this.desactivarbtnEliminar = desactivarbtnEliminar;
    }

    public void setDesactivarChBox(Boolean desactivarChBox) {
        this.desactivarChBox = desactivarChBox;
    }

    public void setListProductos(List<Map<String, Object>> listProductos) {
        this.listProductos = listProductos;
    }

    public int getPosition() {
        return position;
    }

    public Map<String, Object> getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public class AdaptadorProductoHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        TextView tv1, tv2, tv3,tv4;
        CheckBox checkBox;
        ImageButton btnEliminar, btnEdit;
        EditText edCantidad;
        ImageView ivFoto;

        public AdaptadorProductoHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.edNombreP);
            tv2 = itemView.findViewById(R.id.edDescripcionP);
            tv3 = itemView.findViewById(R.id.edPrecioP);
            tv4 = itemView.findViewById(R.id.edCategoriaP);
            edCantidad = itemView.findViewById(R.id.edCantidad);
            checkBox = itemView.findViewById(R.id.cBProductos);
            btnEliminar =  itemView.findViewById(R.id.btnEliminar);
            btnEdit =  itemView.findViewById(R.id.btnEdit);
            ivFoto = itemView.findViewById(R.id.iv1);
            itemView.setOnClickListener(this);

            if (desactivarbtnEdit)
                btnEdit.setVisibility(View.GONE);

            if (desactivarbtnEliminar)
                btnEliminar.setVisibility(View.GONE);


            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(listenerBtnEliminar!=null){
                        position = getLayoutPosition();
                        listenerBtnEliminar.onClick(v);
                    }
                }
            });

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listenerBtnEDit!=null){
                        position = getLayoutPosition();

                        String nombre = tv1.getText().toString();
                        String apellido=  tv2.getText().toString();
                        String celular= tv3.getText().toString();
                        String id = listProductos.get(position).get("id").toString();

                        Map<String, Object> data = new HashMap<>();
                        data.put(DBmigrations.PRODUCTO_ID,id);
                        data.put(DBmigrations.PRODUCTO_FOTO,listProductos.get(getLayoutPosition()).get(DBmigrations.PRODUCTO_FOTO).toString());
                        data.put(DBmigrations.PRODUCTO_NOMBRE,nombre);
                        data.put(DBmigrations.PRODUCTO_DESCRIPCION,apellido);
                        data.put(DBmigrations.PRODUCTO_PRECIO,celular);
                        data.put(DBmigrations.PRODUCTO_CATEGORIAID,listProductos.get(getLayoutPosition()).get(DBmigrations.PRODUCTO_CATEGORIAID).toString());
                        productoSeleccionado = new HashMap<>();
                        productoSeleccionado.putAll(data);
                        listenerBtnEDit.onClick(v);

                    }


//                    Intent intento = new Intent(context, EditarProducto.class);
//                    int position = getLayoutPosition();
//                    Map<String,Object> productoSeleccionado = listProductos.get(position);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("pedido", (Serializable) productoSeleccionado);
//                    intento.putExtras(bundle);
//
//                    context.startActivity(intento);
                }
            });

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    onCheckedCotizacion();
                }
            });

        }

        private void onCheckedCotizacion() {
            if (checkBox.isChecked()) {
                Map<String, Object> productoActual = listProductos.get(getLayoutPosition());
                productoActual.put("cantidad", edCantidad.getText());
                listProductosSeleccionados.add(productoActual);
            }
            else {
                listProductosSeleccionados.remove(listProductos.get(getLayoutPosition()));
            }
        }

        public void imprimir(int position) {
//                String []archivos = fileList();
//                List<String> listArchivos= new ArrayList<>();
            try {

                Map<String,Object> productoActual = listProductos.get(position);
                String fotoActual = productoActual.get(DBmigrations.PRODUCTO_FOTO).toString();

                FileInputStream fileInputStream = context.openFileInput(fotoActual);
                Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
                ivFoto.setImageBitmap(bitmap);
                fileInputStream.close();

                tv1.setText(productoActual.get("nombre").toString());
                tv2.setText(productoActual.get("descripcion").toString());
                tv3.setText(productoActual.get("precio").toString());
                tv4.setText(productoActual.get("nombreCategoria").toString());

                if (desactivarChBox){
                    checkBox.setVisibility(View.GONE);
                    edCantidad.setText(productoActual.get(DBmigrations.DETALLE_PEDIDO_CANTIDAD).toString());
                }

            }catch (Exception e) {

            }
        }


        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            //mostrar(position);
        }

        private void onClickEliminar() {
//            int position = getLayoutPosition();
//            if (position != RecyclerView.NO_POSITION) {
//                String id = listProductos.get(position).get("id").toString();
//                producto.delete(id);
//                listar();
//                Toast.makeText(pproduto, "eliminado", Toast.LENGTH_SHORT).show();
//            }
        }
    }
}
