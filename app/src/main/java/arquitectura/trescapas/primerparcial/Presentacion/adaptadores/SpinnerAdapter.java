package arquitectura.trescapas.primerparcial.Presentacion.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import arquitectura.trescapas.primerparcial.clases.interfaces.Nombrable;


public class SpinnerAdapter<T extends Nombrable> extends ArrayAdapter<T>  {


    public SpinnerAdapter(Context context, List<T> obj) {
        super(context, android.R.layout.simple_spinner_item, obj);
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        // Configura el texto a mostrar en la vista
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(getItem(position).getNombre());

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // Crea una vista personalizada para mostrar el objeto en la lista desplegable del Spinner
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        // Configura el texto a mostrar en la vista personalizada
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(getItem(position).getNombre());

        return convertView;
    }


}
