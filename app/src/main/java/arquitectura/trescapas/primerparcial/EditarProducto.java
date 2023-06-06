package arquitectura.trescapas.primerparcial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Map;

public class EditarProducto extends AppCompatActivity {

    private Map<String, Object> producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producto);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        producto= (Map<String, Object>) bundle.getSerializable("pedido");
    }
}