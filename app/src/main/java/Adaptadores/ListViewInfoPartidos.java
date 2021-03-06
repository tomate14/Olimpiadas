package Adaptadores;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Entidades.Partido;
import optativamoviles.olimpiadas.MostrarInfo;
import optativamoviles.olimpiadas.R;

/**
 * Created by Maxi on 13/11/2017.
 */

public class ListViewInfoPartidos extends BaseExpandableListAdapter {
    private ArrayList<Partido> partidos;
    private Context contexto;

    public ListViewInfoPartidos(Context contexto, ArrayList<Partido> partidos) {
        this.partidos = partidos;
        this.contexto = contexto;
    }

    @Override
    public int getGroupCount() {
        return partidos.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TextView txtFacultad1;
        TextView txtFacultad2;
        TextView txtResultado;
        TextView txtDeporte;

        View inflate = View.inflate(this.contexto, R.layout.infopartido,null);

        txtDeporte = (TextView) inflate.findViewById(R.id.txtDeporte);
        txtFacultad1  = (TextView) inflate.findViewById(R.id.txtFacultad1);
        txtFacultad2 = (TextView) inflate.findViewById(R.id.txtFacultad2);
        txtResultado = (TextView) inflate.findViewById(R.id.txtResultado);

        txtFacultad1.setText(partidos.get(groupPosition).getFacultad1().getNombre());
        txtFacultad2.setText(partidos.get(groupPosition).getFacultad2().getNombre());
        txtResultado.setText(partidos.get(groupPosition).getResultado());
        txtDeporte.setText(partidos.get(groupPosition).getDeporte());

        return inflate;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        TextView txtFecha;
        TextView txtLugar;

        View inflate = View.inflate(this.contexto, R.layout.infoculturalhijo,null);
        txtFecha  = (TextView) inflate.findViewById(R.id.txtFecha);
        txtLugar = (TextView) inflate.findViewById(R.id.txtLugar);
        Date fecha = partidos.get(groupPosition).getFecha().getTime();
        String s = new SimpleDateFormat("HH:mm").format(fecha);
        txtFecha.setText("Horario: "+ s + " hs");
        txtLugar.setText("Lugar: " + partidos.get(groupPosition).getLugar());

        return inflate;    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
