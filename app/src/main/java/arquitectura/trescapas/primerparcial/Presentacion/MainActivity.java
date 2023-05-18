package arquitectura.trescapas.primerparcial.Presentacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import arquitectura.trescapas.primerparcial.Pcotizacion;
import arquitectura.trescapas.primerparcial.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }



    public void clientes(View v) {
        Intent intento = new Intent(this, Pcliente.class);
        startActivity(intento);
    }

    public void repartidores(View v) {
        Intent intento = new Intent(this, Prepartidor.class);
        startActivity(intento);
    }

    public void categoria(View v) {
        Intent intento = new Intent(this, Pcategoria.class);
        startActivity(intento);
    }
    public void productos(View v) {
        Intent intento = new Intent(this, Pproduto.class);
        startActivity(intento);
    }

    public void pedidos(View v) {
        Intent intento = new Intent(this, Ppedido.class);
        startActivity(intento);
    }

    public void envios(View v) {
        Intent intento = new Intent(this, Pcotizacion.class);
        startActivity(intento);
    }

    public void salir(View v) {
        finish();
    }
}