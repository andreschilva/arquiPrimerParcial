package arquitectura.trescapas.primerparcial.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import arquitectura.trescapas.primerparcial.Presentacion.adaptadores.SpinnerAdapter;
import arquitectura.trescapas.primerparcial.clases.interfaces.Negocio;

public  class Utils {

    public static void mensaje(Context context,String mensaje){
        Toast.makeText(context,  mensaje, Toast.LENGTH_SHORT).show();
    }

    public static Bitmap getBitmapFromUri(Context context, Uri uri) {
        Bitmap bitmap = null;
        InputStream inputStream = null;
        try {
            inputStream = context.getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    public static void guardarFoto(Bitmap bitmap, Context context,String nombreFoto) throws IOException {
        FileOutputStream fos = context.openFileOutput(nombreFoto, Context.MODE_PRIVATE);
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
        fos.close();
    }

    public static String crearNombreArchivoJPG() {
        String fecha = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return fecha+".jpg";
    }

    public static <T>  void setSpinner(Spinner spinner, List<T> dataList, String id, Context context, Negocio negocio) {
        dataList = negocio.getDatos();
        SpinnerAdapter adapter = new SpinnerAdapter(context, dataList);
        spinner.setAdapter(adapter);

        T itemActual= (T) negocio.getById(id);
        int posItem = dataList.indexOf(itemActual);
        spinner.setSelection(posItem);
    }
}
