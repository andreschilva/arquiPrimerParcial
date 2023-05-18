package arquitectura.trescapas.primerparcial.Presentacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import arquitectura.trescapas.primerparcial.DB.DBmigrations;
import arquitectura.trescapas.primerparcial.Negocio.Ncategoria;
import arquitectura.trescapas.primerparcial.R;

public class Pcategoria extends AppCompatActivity {
    EditText etNombre;
    RecyclerView rv1;
    AdaptadorCategoria aC;
    Ncategoria categoria;
    List<Map<String,Object>> list;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcategoria);
        getSupportActionBar().setTitle("Categorias");

        categoria = new Ncategoria(this);
        list = categoria.getDatos();
        etNombre = findViewById(R.id.editNombre);
        rv1 = findViewById(R.id.rv1);

        LinearLayoutManager l=new LinearLayoutManager(this);
        rv1.setLayoutManager(l);
        aC = new AdaptadorCategoria();
        rv1.setAdapter(aC);
    }

    public void agregar(View v) {
        String nombre= etNombre.getText().toString();


        Map<String, Object> data = new HashMap<>();

        data.put(DBmigrations.CATEGORIA_NOMBRE,nombre);


        if (categoria.saveDatos(data)){
            listar();
            rv1.scrollToPosition(list.size()-1);
            Toast.makeText(this, "categoria creado", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this, "categoria ya existente", Toast.LENGTH_SHORT).show();
        }

    }

    public void mostrar(int position) {
        etNombre.setText(list.get(position).get(DBmigrations.CATEGORIA_NOMBRE).toString());

        this.pos = position;
    }
    public void actualizar(View v) {
        String nombre= etNombre.getText().toString();


        String id = list.get(this.pos).get("id").toString();
        //Toast.makeText(Pcliente.this, id, Toast.LENGTH_SHORT).show();

        Map<String, Object> data = new HashMap<>();
        data.put(DBmigrations.CATEGORIA_ID,id);
        data.put(DBmigrations.CATEGORIA_NOMBRE,nombre);
        categoria.updateDatos(data);
        listar();

    }
    public void listar() {
        this.list = categoria.getDatos();
        aC.notifyDataSetChanged();
    }

    private class AdaptadorCategoria extends RecyclerView.Adapter<AdaptadorCategoria.AdaptadorCategoriaHolder> {

        @NonNull
        @Override
        public AdaptadorCategoria.AdaptadorCategoriaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdaptadorCategoria.AdaptadorCategoriaHolder(getLayoutInflater().inflate(R.layout.item_categorias,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull AdaptadorCategoria.AdaptadorCategoriaHolder holder, int position) {

            holder.imprimir(position);


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class AdaptadorCategoriaHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

            TextView tv1, tv2, tv3;
            Button btnEliminar;

            public AdaptadorCategoriaHolder(@NonNull View itemView) {
                super(itemView);
                tv1 = itemView.findViewById(R.id.tvnombre);
                tv2 = itemView.findViewById(R.id.tvDescripcion);
                tv3 = itemView.findViewById(R.id.tvPrecio);
                btnEliminar =  itemView.findViewById(R.id.btnEliminar);
                itemView.setOnClickListener(this);

                btnEliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickEliminar();
                    }
                });

            }

            public void imprimir(int position) {
                list = categoria.getDatos();
                tv1.setText(list.get(position).get("nombre").toString());

            }



            @Override
            public void onClick(View v) {
                int position = getLayoutPosition();
                mostrar(position);
            }

            private void onClickEliminar() {
                int position = getLayoutPosition();
                if (position != RecyclerView.NO_POSITION) {
                    String id = list.get(getLayoutPosition()).get("id").toString();
                    categoria.delete(id);
                    listar();
                    Toast.makeText(Pcategoria.this, "eliminado", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}