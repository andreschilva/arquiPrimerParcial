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
import arquitectura.trescapas.primerparcial.Negocio.Ncliente;
import arquitectura.trescapas.primerparcial.R;

public class Pcliente extends AppCompatActivity {
    EditText etNombre, etCelular, etApellido;
    RecyclerView rv1;
    AdaptadorCliente aC;
    Ncliente cliente;
    List<Map<String,Object>> list;

    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcliente);
        getSupportActionBar().setTitle("Clientes");

        cliente = new Ncliente(this);
        list = cliente.getDatos();
        etNombre = findViewById(R.id.editNombre);
        etCelular = findViewById(R.id.editCelular);
        etApellido = findViewById(R.id.editApellido);
        rv1 = findViewById(R.id.rv1);

        LinearLayoutManager l=new LinearLayoutManager(this);
        rv1.setLayoutManager(l);
        aC = new AdaptadorCliente();
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
        //Ncliente cliente = new Ncliente(this);

            if (cliente.saveDatos(data)){
                listar();
                rv1.scrollToPosition(list.size()-1);
                Toast.makeText(this, "cliente creado", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(this, "cliente ya existente", Toast.LENGTH_SHORT).show();
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
        cliente.updateDatos(data);
        listar();

    }
    public void listar() {
        this.list = cliente.getDatos();
        aC.notifyDataSetChanged();
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
            return list.size();
        }

        public class AdaptadorClienteHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

            TextView tv1, tv2, tv3;
            Button btnEliminar;

            public AdaptadorClienteHolder(@NonNull View itemView) {
                super(itemView);
                tv1 = itemView.findViewById(R.id.tvEstado);
                tv2 = itemView.findViewById(R.id.tvCliente);
                tv3 = itemView.findViewById(R.id.tvRepartidor);
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
                list = cliente.getDatos();
                tv1.setText(list.get(position).get("nombre").toString());
                tv2.setText(list.get(position).get("apellido").toString());
                tv3.setText(list.get(position).get("celular").toString());

            }

//            public void eliminar(View v) {
//                list = cliente.getDatos();
//                String id =  list.get(getLayoutPosition()).get("id").toString();
//                cliente.delete(id);
//            }



            @Override
            public void onClick(View v) {
                int position = getLayoutPosition();
                mostrar(position);
            }

            private void onClickEliminar() {
                int position = getLayoutPosition();
                if (position != RecyclerView.NO_POSITION) {
                    String id = list.get(getLayoutPosition()).get("id").toString();
                    cliente.delete(id);
                    listar();
                    Toast.makeText(Pcliente.this, "eliminado", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}