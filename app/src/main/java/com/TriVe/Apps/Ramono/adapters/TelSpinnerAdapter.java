package com.TriVe.Apps.Ramono.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.TriVe.Apps.Ramono.R;
import com.TriVe.Apps.mycontact.ContactAPI.objects.Phone;

import java.util.List;

/**
 * <b>Adapter for the Phone Spinner.</b>
 *
 * @author TriVe
 * @version 1.0
 */
public class TelSpinnerAdapter extends ArrayAdapter<Phone>
{
    List<Phone> Phones;
    Context context;
    LayoutInflater inflater;


    public TelSpinnerAdapter(Context context, int textViewResourceId, List<Phone> Phones)
    {
        super(context, textViewResourceId, Phones);

        this.Phones = Phones;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    public int getCount()
    {
        return Phones.size();
    }

    public Phone getItem(int position)
    {
        return Phones.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }


    public View getCustomView(int position, ViewGroup parent)
    {

        // Inflating the layout for the custom Spinner
        View layout = inflater.inflate(R.layout.tel_spinner_row, parent, false);

        // Declaring and Typecasting the textview in the inflated layout
        TextView tvType = (TextView) layout.findViewById(R.id.tvContactTelType);
        tvType.setText(Phones.get(position).getStringFromType());

        TextView tvPlace = (TextView) layout.findViewById(R.id.tvContactTelData);
        tvPlace.setText(Phones.get(position).getNumber());


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