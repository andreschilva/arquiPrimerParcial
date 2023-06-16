package arquitectura.trescapas.primerparcial.Presentacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import arquitectura.trescapas.primerparcial.Negocio.Ncategoria;
import arquitectura.trescapas.primerparcial.R;
import arquitectura.trescapas.primerparcial.clases.Categoria;

public class Pcategoria extends AppCompatActivity {
    EditText etNombre;
    RecyclerView rv1;
    AdaptadorCategoria aC;
    Ncategoria categoria;
    List<Categoria> list;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcategoria);
        //getSupportActionBar().setTitle("Categorias");

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

        Categoria data = new Categoria();

        data.setId("");
        data.setNombre(nombre);
        categoria.saveDatos(data);
        listar();

    }

    public void mostrar(int position) {
        etNombre.setText(list.get(position).getNombre());
        this.pos = position;
    }

    public void actualizar(View v) {
        String nombre= etNombre.getText().toString();


        String id = list.get(this.pos).getId();
        //Toast.makeText(Pcliente.this, id, Toast.LENGTH_SHORT).show();

        Categoria data = new Categoria();
        data.setId(id);
        data.setNombre(nombre);
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

            TextView tv1;
            ImageButton btnEliminar;

            public AdaptadorCategoriaHolder(@NonNull View itemView) {
                super(itemView);
                tv1 = itemView.findViewById(R.id.edNombreC);
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
                tv1.setText(list.get(position).getNombre());

            }



            @Override
            public void onClick(View v) {
                int position = getLayoutPosition();
                mostrar(position);
            }

            private void onClickEliminar() {
                int position = getLayoutPosition();
                if (position != RecyclerView.NO_POSITION) {
                    String id = list.get(getLayoutPosition()).getId();
                    categoria.delete(id);
                    listar();
                                    }
            }
        }
    }
}