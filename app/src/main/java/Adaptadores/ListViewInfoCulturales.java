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
        TextView txtFacultad2;
        TextView txtResultado;

        View inflate = View.inflate(this.contexto, R.layout.infopartido,null);

        txtFacultad1  = (TextView) inflate.findViewById(R.id.txtFacultad1);
        //txtFacultad2 = (TextView) inflate.findViewById(R.id.txtFacultad2);
        txtResultado = (TextView) inflate.findViewById(R.id.txtResultado);
        txtFacultad1.setText(culturales.get(groupPosition).getFacultad().getNombre());
        //txtFacultad2.setText(culturales.get(groupPosition).getFacultad2().getNombre());
        txtResultado.setText(culturales.get(groupPosition).getPuntos());

        return inflate;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
