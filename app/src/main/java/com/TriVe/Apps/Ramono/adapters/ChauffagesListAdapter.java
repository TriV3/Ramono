package com.TriVe.Apps.Ramono.adapters;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.TriVe.Apps.Ramono.R;
import com.TriVe.Apps.Ramono.Ramonage.Chauffage;

import java.util.ArrayList;
import java.util.List;


public class ChauffagesListAdapter extends BaseAdapter
{
    List<Chauffage> chauffages;
    LayoutInflater inflater;
    protected Context context;


    public ChauffagesListAdapter(Context context, List<Chauffage> contentItems)
    {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.chauffages = contentItems;
    }

    @Override
    public int getCount() {
        return chauffages.size();
    }

    @Override
    public Object getItem(int position) {
        return chauffages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getNbOfChauffagesSelected()
{
    int i = 0;

    for(Chauffage c : chauffages)
    {
        if(c.isSelected())
            i++;
    }
    return i;
}

    public List<Integer> getSelectedChauffages()
    {
        List<Integer> idList = new ArrayList<>();

        for(int i = 0; i < chauffages.size(); i++)
        {
            if(chauffages.get(i).isSelected())
                idList.add(i);
        }
        return idList;
    }

    private class ViewHolder
    {
        TextView tvType;
        TextView tvNbr;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
        {
            convertView = mInflater.inflate(R.layout.chauffage_list_item, null);

            holder = new ViewHolder();
            holder.tvType = (TextView)convertView.findViewById(R.id.tvChauffageType);
            holder.tvNbr = (TextView)convertView.findViewById(R.id.tvChauffageNumber);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvType.setText(chauffages.get(position).type);
        holder.tvNbr.setText(chauffages.get(position).nombre);

        convertView.setBackgroundColor(Color.WHITE);

        if(chauffages.get(position).isSelected())
        {
            convertView.setBackgroundColor(Color.parseColor("#ff8895ff"));
        }


        return convertView;
    }


}
