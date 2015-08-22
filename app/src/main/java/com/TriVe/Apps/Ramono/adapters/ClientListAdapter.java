package com.TriVe.Apps.Ramono.adapters;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.TriVe.Apps.Ramono.R;
import com.TriVe.Apps.Ramono.Ramonage.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Adapter for the Client ListView.</b>
 *
 * @author TriVe
 * @version 1.0
 */
public class ClientListAdapter extends BaseAdapter implements Filterable
{
    public static final String TAG = "ClientListAdapterTAG";
    List<Client> _clientList;
    List<Client> storedClientList;

    private Filter clientFilter;
    LayoutInflater inflater;
    protected Context context;

    public ClientListAdapter(Context context,List<Client> contentItems)
    {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this._clientList = contentItems;
        this.storedClientList = new ArrayList<>(contentItems);
    }

    @Override
    public int getCount() {
        return _clientList.size();
    }

    @Override
    public Object getItem(int position) {
        return _clientList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter()
    {
        if (clientFilter == null)
            clientFilter = new ClientFilter();
        return clientFilter;
    }


    private class ViewHolder
    {
        TextView tvName;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
        {

            convertView = mInflater.inflate(R.layout.client_grid_item, null);

            holder = new ViewHolder();
            holder.tvName = (TextView)convertView.findViewById(R.id.clientName);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvName.setText(_clientList.get(position).getDisplayName());

        return convertView;
    }


    private class ClientFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            Log.d(ClientListAdapter.TAG, "**** PERFORM FILTERING for: " + constraint);
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = storedClientList;
                results.count = storedClientList.size();
            }
            else {
                // We perform filtering operation
                List<Client> filteredClientList = new ArrayList<>();

                _clientList = storedClientList;

                for (Client p : _clientList) {
                    if (p.getDisplayName().toUpperCase().contains(constraint.toString().toUpperCase()))
                        filteredClientList.add(p);
                }

                results.values = filteredClientList;
                results.count = filteredClientList.size();

            }
            return results;

        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            Log.d(ClientListAdapter.TAG, "**** PUBLISHING RESULTS for: " + constraint);
            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0)
                notifyDataSetInvalidated();
            else
            {
                _clientList = (List<Client>) results.values;
                notifyDataSetChanged();
            }
        }

    }


}
