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

import com.google.android.gms.common.api.Api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import arquitectura.trescapas.primerparcial.DB.DBmigrations;
import arquitectura.trescapas.primerparcial.Negocio.Ncliente;
import arquitectura.trescapas.primerparcial.Negocio.Ncliente1;
import arquitectura.trescapas.primerparcial.R;
import arquitectura.trescapas.primerparcial.clases.Cliente;

public class Pcliente extends AppCompatActivity {
    EditText etNombre, etCelular, etApellido,etLink;
    RecyclerView rv1;
    AdaptadorCliente aC;
    Ncliente1 cliente;
    List<Cliente> list;

    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcliente);
        //getSupportActionBar().setTitle("Clientes");

        cliente = new Ncliente1(this);
        list = cliente.getDatos();
        etNombre = findViewById(R.id.editNombre);
        etCelular = findViewById(R.id.editCelular);
        etApellido = findViewById(R.id.editApellido);
        etLink = findViewById(R.id.editLink);
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
        String ubicacion= etLink.getText().toString();

        Cliente cl = Cliente.crear("",nombre,apellido,celular,ubicacion);
        cliente.saveDatos(cl);
        listar();
        rv1.scrollToPosition(list.size()-1);
    }

    public void mostrar(int position) {
        etNombre.setText(list.get(position).getNombre());
        etApellido.setText(list.get(position).getApellido());
        etCelular.setText(list.get(position).getCelular());
        this.pos = position;
    }
    public void actualizar(View v) {
        String nombre= etNombre.getText().toString();
        String apellido=  etApellido.getText().toString();
        String celular= etCelular.getText().toString();

        String id = list.get(this.pos).getId();
        //Toast.makeText(Pcliente.this, id, Toast.LENGTH_SHORT).show();

        Cliente cl = new Cliente();
        cl.crear(id,nombre,apellido,celular,"");
        cliente.updateDatos(cl);
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
                tv1.setText(list.get(position).getNombre());
                tv2.setText(list.get(position).getApellido());
                tv3.setText(list.get(position).getCelular());

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
                    String id = list.get(getLayoutPosition()).getId();
                    cliente.delete(id);
                    listar();
                    Toast.makeText(Pcliente.this, "eliminado", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}