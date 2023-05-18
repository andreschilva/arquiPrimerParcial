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
import arquitectura.trescapas.primerparcial.Negocio.Nrepartidor;
import arquitectura.trescapas.primerparcial.R;

public class Prepartidor extends AppCompatActivity {
    EditText etNombre, etCelular, etApellido;
    RecyclerView rv1;
    AdaptadorRepartidor aC;
    Nrepartidor repartidor;
    List<Map<String,Object>> list;

    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepartidor);
        getSupportActionBar().setTitle("Repartidores");

        repartidor = new Nrepartidor(this);
        list = repartidor.getDatos();
        etNombre = findViewById(R.id.editNombre);
        etCelular = findViewById(R.id.editCelular);
        etApellido = findViewById(R.id.editApellido);
        rv1 = findViewById(R.id.rv1);

        LinearLayoutManager l=new LinearLayoutManager(this);
        rv1.setLayoutManager(l);
        aC = new AdaptadorRepartidor();
        rv1.setAdapter(aC);
    }

    public void agregar(View v) {
        String nombre= etNombre.getText().toString();
        String apellido=  etApellido.getText().toString();
        String celular= etCelular.getText().toString();

        Map<String, Object> data = new HashMap<>();

        data.put(DBmigrations.CLIENTE_NOMBRE,nombre);
        data.put(DBmigrations.CLIENTE_APELLIDO,apellido);
        data.put(DBmigrations.CLIENTE_CELULAR,celular);


        if (repartidor.saveDatos(data)){
            listar();
            rv1.scrollToPosition(list.size()-1);
            Toast.makeText(this, "repartidor creado", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this, "repartidor ya existente", Toast.LENGTH_SHORT).show();
        }

    }

    public void mostrar(int position) {
        etNombre.setText(list.get(position).get(DBmigrations.CLIENTE_NOMBRE).toString());
        etApellido.setText(list.get(position).get(DBmigrations.CLIENTE_APELLIDO).toString());
        etCelular.setText(list.get(position).get(DBmigrations.CLIENTE_CELULAR).toString());
        this.pos = position;
    }
    public void actualizar(View v) {
        String nombre= etNombre.getText().toString();
        String apellido=  etApellido.getText().toString();
        String celular= etCelular.getText().toString();

        String id = list.get(this.pos).get("id").toString();
        //Toast.makeText(Pcliente.this, id, Toast.LENGTH_SHORT).show();

        Map<String, Object> data = new HashMap<>();
        data.put(DBmigrations.CLIENTE_ID,id);
        data.put(DBmigrations.CLIENTE_NOMBRE,nombre);
        data.put(DBmigrations.CLIENTE_APELLIDO,apellido);
        data.put(DBmigrations.CLIENTE_CELULAR,celular);
        repartidor.updateDatos(data);
        listar();

    }
    public void listar() {
        this.list = repartidor.getDatos();
        aC.notifyDataSetChanged();
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
            return list.size();
        }

        public class AdaptadorRepartidorHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

            TextView tv1, tv2, tv3;
            Button btnEliminar;

            public AdaptadorRepartidorHolder(@NonNull View itemView) {
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
                list = repartidor.getDatos();
                tv1.setText(list.get(position).get("nombre").toString());
                tv2.setText(list.get(position).get("apellido").toString());
                tv3.setText(list.get(position).get("celular").toString());

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
                    repartidor.delete(id);
                    listar();
                    Toast.makeText(Prepartidor.this, "eliminado", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}