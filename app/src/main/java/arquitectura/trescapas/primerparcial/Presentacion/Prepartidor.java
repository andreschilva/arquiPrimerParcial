package arquitectura.trescapas.primerparcial.Presentacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import arquitectura.trescapas.primerparcial.Negocio.Nrepartidor;
import arquitectura.trescapas.primerparcial.R;
import arquitectura.trescapas.primerparcial.clases.Repartidor;

public class Prepartidor extends AppCompatActivity {
    ImageButton btnFoto;
    RecyclerView rv1;
    AdaptadorRepartidor aR;
    Nrepartidor repartidor;
    List<Repartidor> list;

    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepartidor);
       // getSupportActionBar().setTitle("Repartidores");

        repartidor = new Nrepartidor(this);
        list = repartidor.getDatos();
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
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent,CAPTURA_IMAGEN);

            }

        });


        builder.setPositiveButton("crear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String nombre= etNombre.getText().toString();
                String apellido=  etApellido.getText().toString();
                String celular= etCelular.getText().toString();
                String placa= etPlaca.getText().toString();

                Repartidor rp = Repartidor.crear("",nombre,apellido,celular,placa);
                repartidor.saveDatos(rp);
                listar();
                rv1.scrollToPosition(list.size()-1);

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

    public void listar() {
        this.list = repartidor.getDatos();
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
            return list.size();
        }

        public class AdaptadorRepartidorHolder extends RecyclerView.ViewHolder  {

            EditText edNombre, edApellido, edCelular, edPlaca;
            TextView tvPlaca;
            ImageButton btnEliminar, btnEditar;

            public AdaptadorRepartidorHolder(@NonNull View itemView) {
                super(itemView);
                edNombre = itemView.findViewById(R.id.edNombreU);
                edApellido = itemView.findViewById(R.id.edApellidoU);
                edCelular = itemView.findViewById(R.id.edCelularU);
                edPlaca = itemView.findViewById(R.id.edLinkU);
                btnEliminar =  itemView.findViewById(R.id.btnEliminar);
                btnEditar = itemView.findViewById(R.id.btnEdit);
                tvPlaca = itemView.findViewById(R.id.tvGenerico1U);


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
            }

            public void imprimir(int position) {
                list = repartidor.getDatos();
                edNombre.setText(list.get(position).getNombre());
                edApellido.setText(list.get(position).getApellido());
                edCelular.setText(list.get(position).getCelular());
                edPlaca.setText(list.get(position).getPlaca());
            }




            private void editar() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Prepartidor.this);
                builder.setMessage("Desea guardar los cambios?");

                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = getLayoutPosition();
                        list = repartidor.getDatos();
                        Repartidor repartidorActual = list.get(position);

                        repartidorActual.setNombre(edNombre.getText().toString());
                        repartidorActual.setApellido(edApellido.getText().toString());
                        repartidorActual.setCelular(edCelular.getText().toString());
                        repartidorActual.setPlaca(edPlaca.getText().toString());

                        repartidor.updateDatos(repartidorActual);
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
                            String id = list.get(getLayoutPosition()).getId();
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
        }
    }

}