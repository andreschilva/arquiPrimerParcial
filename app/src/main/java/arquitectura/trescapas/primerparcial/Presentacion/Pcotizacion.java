package arquitectura.trescapas.primerparcial.Presentacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import arquitectura.trescapas.primerparcial.Negocio.Ncotizacion;
import arquitectura.trescapas.primerparcial.R;
import arquitectura.trescapas.primerparcial.clases.Cotizacion;

public class Pcotizacion extends AppCompatActivity {
    RecyclerView rv1;
    AdaptadorCotizacion aCot;

    //datos necesatios para insertar
    Ncotizacion cotizacion;

    //listas
    List<Cotizacion> listCotizaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcotizacion);

        cotizacion = new Ncotizacion(this);

        //cargando listas
        listCotizaciones = cotizacion.getDatos();

        rv1 = findViewById(R.id.rvCotizacion);
        LinearLayoutManager l=new LinearLayoutManager(this);
        rv1.setLayoutManager(l);
        aCot = new AdaptadorCotizacion();
        rv1.setAdapter(aCot);

    }

    public void agregarCotizacion(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Crear Cotizacion");
        View selector = getLayoutInflater().inflate(R.layout.modal_crear_cotizacion,null);
        builder.setView(selector);

        EditText edNombre = selector.findViewById(R.id.editNombreCot);

        builder.setPositiveButton("crear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String nombre = edNombre.getText().toString();
                String fechaActual = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());

                Cotizacion data = new Cotizacion("",nombre,fechaActual,0);
                cotizacion.saveDatos(data);
                listar();
                rv1.scrollToPosition(listCotizaciones.size()-1);
            }
        });

        builder.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();

    }


    public void listar() {
        this.listCotizaciones = cotizacion.getDatos();
        aCot.notifyDataSetChanged();
    }
    

    private class AdaptadorCotizacion extends RecyclerView.Adapter<AdaptadorCotizacion.AdaptadorCotizacionHolder> {

        @NonNull
        @Override
        public AdaptadorCotizacionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdaptadorCotizacionHolder(getLayoutInflater().inflate(R.layout.item_cotizacin,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull AdaptadorCotizacionHolder holder, int position) {

            holder.imprimir(position);


        }

        @Override
        public int getItemCount() {
            return listCotizaciones.size();
        }

        public class AdaptadorCotizacionHolder extends RecyclerView.ViewHolder  {

            EditText edNombre,edFecha;
            Button  btnDetalles;
            ImageButton btnEliminar, btnEdit;
            public AdaptadorCotizacionHolder(@NonNull View itemView) {
                super(itemView);
                edNombre = itemView.findViewById(R.id.edNombreCot);
                edFecha = itemView.findViewById(R.id.edFechaCot);
                btnEliminar =  itemView.findViewById(R.id.btnEliminar);
                btnEdit = itemView.findViewById(R.id.btnActualizarCot);
                btnDetalles = itemView.findViewById(R.id.btnDetallesCot);
               eventos();

            }

            private void eventos() {
                btnEliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        eliminar();
                    }
                });

                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editar();
                    }
                });

                btnDetalles.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        detalles();
                    }
                });

            }


            public void imprimir(int position) {
                Cotizacion cotizacionActual = listCotizaciones.get(position);
                edNombre.setText(cotizacionActual.getNombre());
                edFecha.setText(cotizacionActual.getFecha());
                String fecha= cotizacionActual.getFecha();
            }


            private void editar() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Pcotizacion.this);
                builder.setMessage("Desea guardar los cambios?");

                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = getLayoutPosition();

                        Cotizacion cotizacionActual = listCotizaciones.get(position);
                        cotizacionActual.setNombre(edNombre.getText().toString());
                        String fechaActual = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
                        cotizacionActual.setFecha(fechaActual);
                        cotizacion.updateDatos(cotizacionActual);
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

            private void eliminar() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Pcotizacion.this);
                builder.setMessage("Esta seguro que desea Eliminar este pedido?");

                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = getLayoutPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Cotizacion cotizacionActual = listCotizaciones.get(position);
                            String id = cotizacionActual.getId();

                            //eliminar pedido
                            cotizacion.delete(id);
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

            private void detalles() {
                Intent intento = new Intent(Pcotizacion.this, PdetalleCotizacion.class);
                int position = getLayoutPosition();
                String cotizacionId = listCotizaciones.get(position).getId();
                intento.putExtra("cotizacion", (Serializable) cotizacionId);
                startActivity(intento);
            }
        }
    }
}