package com.TriVe.Apps.Ramono;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.TriVe.Apps.Ramono.Ramonage.Client;
import com.TriVe.Apps.Ramono.adapters.ClientListAdapter;
import com.TriVe.Apps.mycontact.ContactAPI.ContactAPI;
import com.TriVe.Apps.mycontact.ContactAPI.objects.Contact;

import java.util.ArrayList;
import java.util.Collections;


/**
 * <b>Fragment containing the contact list.</b>
 *
 * @author TriVe
 * @version 1.0
 */
public class ClientListFragment extends Fragment implements AdapterView.OnItemClickListener
{

    public static final String TAG = "ClientListFragmentTAG";

    private Context context;

    private OnFragmentInteractionListener mListener;

    static final int INTENT_CONTACT_ADD = 1;

    private EditText inputSearch;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ClientListAdapter clientListAdapter;

    public ClientListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        clientListAdapter = new ClientListAdapter(context, Datas.clients);
//        setRetainInstance(true);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clientlist, container, false);

        mListView = (ListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(clientListAdapter);

        mListView.setOnItemClickListener(this);

        inputSearch = (EditText) view.findViewById(R.id.inputSearch);

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                clientListAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });


        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        context = activity;

        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        if (mListener != null)
        {
            mListView.setItemChecked(position, true);
            Client item = (Client)mListView.getItemAtPosition(position);
            Datas.Selectedclient = item;
            mListener.onClientSelected();
            Log.i(ClientListFragment.TAG + " : onItemClick", "Client Name = " + Datas.Selectedclient.getDisplayName() + ", Client ID = " + Datas.Selectedclient.getId());
        }
    }


    public interface OnFragmentInteractionListener {

        public void onClientSelected();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.client_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent;

        switch (item.getItemId())
        {
            case R.id.client_add_menu_item:
                intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                intent.putExtra("finishActivityOnSaveCompleted", true);
                startActivityForResult(intent, INTENT_CONTACT_ADD);
                return true;

//            case R.id.locate_all_menu_item:
//
//                if (isOnline())
//                {
//                    intent = new Intent(context, MapsActivity.class);
//                    startActivity(intent);
//                }
//                else
//                {
//                    Toast.makeText(getActivity().getApplicationContext(), "Connection au réseau (3G ou wifi) requise)", Toast.LENGTH_LONG).show();
//                }
//
//                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_CONTACT_ADD)
        {
            RefreshList();
        }

    }

    public void RefreshList()
    {
        Datas.clients = new ArrayList<>();

        for(Contact c : ContactAPI.newContactList(context))
        {
            Client cl = new Client(c);

            Datas.clients.add(cl);
        }

        Collections.sort(Datas.clients);

        // FIXME : Ce n'est probablemnt pas le moyen le plus propre d'actualiser la liste des clients.
        clientListAdapter = new ClientListAdapter(context, Datas.clients);
        clientListAdapter.notifyDataSetChanged();
        mListView.setAdapter(clientListAdapter);
    }


    private boolean isOnline()
    {
        ConnectivityManager cm =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
