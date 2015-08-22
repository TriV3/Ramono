package com.TriVe.Apps.Ramono.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.TriVe.Apps.Ramono.R;
import com.TriVe.Apps.mycontact.ContactAPI.objects.Address;

import java.util.List;

/**
 * <b>Adapter for the address spinner.</b>
 *
 * @author TriVe
 * @version 1.0
 */
public class AddressSpinnerAdapter extends ArrayAdapter<Address>
{
    List<Address> Addresses;
    Context context;
    LayoutInflater inflater;

    /**
     * Adapter Constructor.
     * @param context : context linked to this adapter.
     * @param textViewResourceId : Layout linked to the adapter.
     * @param Addresses : List of adresses.
     */
    public AddressSpinnerAdapter(Context context, int textViewResourceId, List<Address> Addresses)
    {
        super(context, textViewResourceId, Addresses);

        this.Addresses = Addresses;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    /**
     * @return the number of adresses in list.
     */
    public int getCount()
    {
        return Addresses.size();
    }

    /**
     * @param position : Row to return.
     * @return the adress in list at the selected position.
     */
    public Address getItem(int position)
    {
        return Addresses.get(position);
    }

    /**
     * @param position : Row to return
     * @return the id of the selected item.
     */
    public long getItemId(int position)
    {
        return position;
    }


    /**
     * Called when view is refreshed (OnCreate, item selected bu user, screen orientation change ...)
     * Called for each address.
     * @return the layout of the selected item modified programatically.
     */
    public View getCustomView(int position, ViewGroup parent)
    {

        // Inflating the layout for the custom Spinner
        View layout = inflater.inflate(R.layout.address_spinner_row, parent, false);

        // Declaring and Typecasting the textview in the inflated layout
        TextView tvType = (TextView) layout.findViewById(R.id.tvContactAddressType);
        tvType.setText(Addresses.get(position).getStringFromType());

        TextView tvPlace = (TextView) layout.findViewById(R.id.tvContactAddressData);
        tvPlace.setText(Addresses.get(position).toString());

        return layout;
    }

    // It gets a View that displays in the drop down popup the data at the specified position
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        return getCustomView(position, parent);
    }

    // It gets a View that displays the data at the specified position
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return getCustomView(position, parent);
    }
}