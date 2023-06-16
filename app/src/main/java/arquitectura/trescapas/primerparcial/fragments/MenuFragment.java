package arquitectura.trescapas.primerparcial.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import arquitectura.trescapas.primerparcial.Presentacion.Pcategoria;
import arquitectura.trescapas.primerparcial.Presentacion.Pcliente;
import arquitectura.trescapas.primerparcial.Presentacion.Ppedido;
import arquitectura.trescapas.primerparcial.Presentacion.Pproduto;
import arquitectura.trescapas.primerparcial.Presentacion.Prepartidor;
import arquitectura.trescapas.primerparcial.R;
import arquitectura.trescapas.primerparcial.clases.Repartidor;


public class MenuFragment extends Fragment {
    private CardView cardCliente,cardRepartidor,cardCategoria,cardProducto,cardPedido,cardSalir;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardCliente = view.findViewById(R.id.cardCliente);
        cardRepartidor = view.findViewById(R.id.cardRepartidor);
        cardCategoria = view.findViewById(R.id.cardCategoria);
        cardProducto = view.findViewById(R.id.cardProducto);
        cardPedido = view.findViewById(R.id.cardPedido);
        cardSalir = view.findViewById(R.id.cardSalir);
        vistas();

    }

    private void vistas() {
        cardCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(getContext(), Pcliente.class);
                startActivity(intento);
            }
        });

        cardRepartidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(getContext(), Prepartidor.class);
                startActivity(intento);
            }
        });

        cardCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(getContext(), Pcategoria.class);
                startActivity(intento);
            }
        });

        cardProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(getContext(), Pproduto.class);
                startActivity(intento);
            }
        });

        cardPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(getContext(), Ppedido.class);
                startActivity(intento);
            }
        });

        cardSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Desea salir de la aplicacion?");

                builder.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (getActivity() != null) {
                            getActivity().finishAffinity();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }
}