package com.TriVe.Apps.Ramono.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.TriVe.Apps.Ramono.R;
import com.TriVe.Apps.mycontact.ContactAPI.objects.Email;

import java.util.List;

/**
 * <b>Adapter for the Email Spinner.</b>
 *
 * @author TriVe
 * @version 1.0
 */
public class EmailSpinnerAdapter extends ArrayAdapter<Email>
{
    List<Email> Emails;
    Context context;
    LayoutInflater inflater;


    public EmailSpinnerAdapter(Context context, int textViewResourceId, List<Email> Emails)
    {
        super(context, textViewResourceId, Emails);

        this.Emails = Emails;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    public int getCount()
    {
        return Emails.size();
    }

    public Email getItem(int position)
    {
        return Emails.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }


    public View getCustomView(int position, ViewGroup parent)
    {

        // Inflating the layout for the custom Spinner
        View layout = inflater.inflate(R.layout.email_spinner_row, parent, false);

        // Declaring and Typecasting the textview in the inflated layout
        TextView tvType = (TextView) layout.findViewById(R.id.tvContactEmailType);
        tvType.setText(Emails.get(position).getStringFromType());

        TextView tvPlace = (TextView) layout.findViewById(R.id.tvContactEmailData);
        tvPlace.setText(Emails.get(position).getAddress());


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