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
import com.TriVe.Apps.Ramono.Ramonage.Conduit;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Adapter for the Conduits ListView.</b>
 *
 * @author TriVe
 * @version 1.0
 */
public class ConduitsListAdapter extends BaseAdapter
{
    List<Conduit> conduits;
    LayoutInflater inflater;
    protected Context context;


    public ConduitsListAdapter(Context context, List<Conduit> contentItems)
    {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.conduits = contentItems;
    }

    @Override
    public int getCount() {
        return conduits.size();
    }

    @Override
    public Object getItem(int position) {
        return conduits.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getNbOfconduitSelected()
{
    int i = 0;

    for(Conduit c : conduits)
    {
        if(c.isSelected())
            i++;
    }
    return i;
}

    public List<Integer> getSelectedConduits()
    {
        List<Integer> idList = new ArrayList<>();

        for(int i = 0; i < conduits.size(); i++)
        {
            if(conduits.get(i).isSelected())
                idList.add(i);
        }
        return idList;
    }

    private class ViewHolder
    {
        TextView tvType;
        TextView tvPlace;
        TextView tvDate;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
        {
            convertView = mInflater.inflate(R.layout.conduit_list_item, null);

            holder = new ViewHolder();
            holder.tvType = (TextView)convertView.findViewById(R.id.tvConduitType);
            holder.tvPlace = (TextView)convertView.findViewById(R.id.tvConduitPlace);
            holder.tvDate = (TextView)convertView.findViewById(R.id.tvConduitDate);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvType.setText(conduits.get(position).type);
        holder.tvPlace.setText(conduits.get(position).place);
        holder.tvDate.setText(conduits.get(position).date);

        convertView.setBackgroundColor(Color.WHITE);

        if(conduits.get(position).isSelected())
        {
            convertView.setBackgroundColor(Color.parseColor("#ff8895ff"));
        }


        return convertView;
    }

}
