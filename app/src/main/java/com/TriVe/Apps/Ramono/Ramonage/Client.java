package com.TriVe.Apps.Ramono.Ramonage;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;

import com.TriVe.Apps.Ramono.Datas;
import com.TriVe.Apps.mycontact.ContactAPI.objects.Contact;


public class Client extends Contact implements Comparable
{
    public static final String TAG = "ClientTAG";

    public Client(Contact contact)
    {
        super(contact);
    }


    @Override
    public int compareTo(@NonNull Object another)
    {
        Client f = (Client)another;

        return (this.getDisplayName().toLowerCase().compareTo(f.getDisplayName().toLowerCase()));

    }

    public boolean updateClient(Context context)
    {
        String note = Datas.Selectedclient.getNotes().get(0);

        try
        {
            ContentResolver cr = context.getContentResolver();

            ContentValues values = new ContentValues();
            values.clear();

            values.put(ContactsContract.CommonDataKinds.Note.NOTE, note);

            String noteWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
            String[] noteWhereParams = new String[]{Datas.Selectedclient.getId(), ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE};


            int mRowsUpdated = cr.update(ContactsContract.Data.CONTENT_URI, values, noteWhere, noteWhereParams);

            // If note does not exist for contact in database:
            if (mRowsUpdated == 0)
            {
                values.clear();
                values.put(ContactsContract.Data.RAW_CONTACT_ID, this.getRawId());
                values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE);
                values.put(ContactsContract.CommonDataKinds.Note.NOTE, note);

                context.getContentResolver().insert(android.provider.ContactsContract.Data.CONTENT_URI, values);
            }

            Log.d(Client.TAG, "Rows updated : " + mRowsUpdated);

            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

    }

}
