package arquitectura.trescapas.primerparcial.Presentacion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import arquitectura.trescapas.primerparcial.R;
import arquitectura.trescapas.primerparcial.fragments.MenuFragment;

public class MainActivity extends AppCompatActivity {
    MenuFragment fragmentoMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentoMenu = new MenuFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments, fragmentoMenu).commit();

    }



//    public void clientes(View v) {
//        Intent intento = new Intent(this, Pcliente.class);
//        startActivity(intento);
//    }
//
//    public void repartidores(View v) {
//        Intent intento = new Intent(this, Prepartidor.class);
//        startActivity(intento);
//    }
//
//    public void categoria(View v) {
//        Intent intento = new Intent(this, Pcategoria.class);
//        startActivity(intento);
//    }
//    public void productos(View v) {
//        Intent intento = new Intent(this, Pproduto.class);
//        startActivity(intento);
//    }
//
//    public void pedidos(View v) {
//        Intent intento = new Intent(this, Ppedido.class);
//        startActivity(intento);
//    }
//
//    public void envios(View v) {
//        Intent intento = new Intent(this, Pcotizacion.class);
//        startActivity(intento);
//    }
//
//    public void salir(View v) {
//        finish();
//    }
}