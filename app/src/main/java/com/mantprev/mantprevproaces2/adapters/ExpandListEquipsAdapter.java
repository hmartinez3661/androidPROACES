package com.mantprev.mantprevproaces2.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.mantprev.mantprevproaces2.R;

import java.util.List;
import java.util.Map;

public class ExpandListEquipsAdapter extends BaseExpandableListAdapter {

    private Context context;
    private Map<String, List<String>> equipsMap;  //Mapa de padres y lista de Hijos
    private List<String> parentList;              //Lista de Padres

    //Constructor
    public ExpandListEquipsAdapter(Context context, List<String> parentsList, Map<String, List<String>> equipsMap){
        this.context = context;
        this.parentList = parentsList;
        this.equipsMap = equipsMap;
    }


    @Override
    public int getGroupCount() {
        return equipsMap.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return equipsMap.get(parentList.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return equipsMap.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return equipsMap.get(parentList.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String parentName = parentList.get(i);

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_parent, null);
        }

        TextView tvParent = view.findViewById(R.id.parent);
        tvParent.setTypeface(null, Typeface.BOLD);
        tvParent.setText(parentName);

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String nombreEquipo = getChild(i, i1).toString();

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_child, null);
        }

        TextView tvChild = view.findViewById(R.id.tvChild);
        tvChild.setText(nombreEquipo);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
