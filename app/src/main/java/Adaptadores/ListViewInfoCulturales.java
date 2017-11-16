package Adaptadores;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import Entidades.Cultural;
import optativamoviles.olimpiadas.MostrarInfo;
import optativamoviles.olimpiadas.R;

/**
 * Created by Maxi on 13/11/2017.
 */

public class ListViewInfoCulturales extends BaseExpandableListAdapter {

    private ArrayList<Cultural> culturales;
    private Context contexto;

    public ListViewInfoCulturales(Context contexto, ArrayList<Cultural> culturales) {
        this.contexto = contexto;
        this.culturales = culturales;
    }

    @Override
    public int getGroupCount() {
        return culturales.size();
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
        TextView txtResultado;
        TextView txtTitulo;
        View inflate = View.inflate(this.contexto, R.layout.infocultural,null);

        txtFacultad1  = (TextView) inflate.findViewById(R.id.txtFacultad1);
        txtTitulo = (TextView) inflate.findViewById(R.id.txtCultural);
        txtResultado = (TextView) inflate.findViewById(R.id.txtResultado);

        txtTitulo.setText(culturales.get(groupPosition).getActividad());
        txtFacultad1.setText(culturales.get(groupPosition).getFacultad().getNombre());
        txtResultado.setText(culturales.get(groupPosition).getPuntos());

        return inflate;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        TextView txtFecha;
        TextView txtLugar;

        View inflate = View.inflate(this.contexto, R.layout.infoculturalhijo,null);
        txtFecha  = (TextView) inflate.findViewById(R.id.txtFecha);
        txtLugar = (TextView) inflate.findViewById(R.id.txtLugar);
        txtLugar.setText(culturales.get(groupPosition).getLugar());


        return inflate;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
