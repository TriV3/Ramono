package com.TriVe.Apps.Ramono;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.TriVe.Apps.Ramono.Ramonage.Client;
import com.TriVe.Apps.mycontact.ContactAPI.ContactAPI;
import com.TriVe.Apps.mycontact.ContactAPI.objects.Contact;

import java.util.ArrayList;
import java.util.Collections;

/**
 * <b>Main activity of the project used to display action bar and fragments.</b>
 *
 * @author TriVe
 * @version 1.0
 */
public class MainActivity extends ActionBarActivity implements ClientListFragment.OnFragmentInteractionListener
{
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        // TODO : If first time launched, set preferences (Address, conduit etc...)

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        CreateClientList();


        // Get a reference to the FragmentManager
        FragmentManager fm = getFragmentManager();
        // find the clientList fragment on activity restarts
        ClientListFragment clientListFragment = (ClientListFragment) fm.findFragmentByTag(ClientListFragment.TAG);

        // create the fragment and data the first time
        if (clientListFragment == null)
        {
            clientListFragment = new ClientListFragment();
        }

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // Add the TitleFragment to the layout
        fragmentTransaction.add(R.id.first_pane_container,clientListFragment, ClientListFragment.TAG);
        // Commit the FragmentTransaction
        fragmentTransaction.commit();
        fm.executePendingTransactions();

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        addShortcut();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.options_main_menu_item:
                startActivity(new Intent(MainActivity.this, UserSettingActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onClientSelected()
    {
        onClientSelectedOnePane();
        Datas.isNoteModified = false;
    }

    private void onClientSelectedOnePane()
    {

        FragmentManager fm = this.getFragmentManager();
        ClientInfoFragment clientInfoFragment = (ClientInfoFragment)fm.findFragmentByTag(ClientInfoFragment.TAG);


        if(clientInfoFragment == null)
        {
            clientInfoFragment = new ClientInfoFragment();
        }

        FragmentTransaction fTransaction = fm.beginTransaction();
        fTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        fTransaction.setCustomAnimations(R.anim.anim_push_right_in,
//                R.anim.anim_push_right_out,
//                R.anim.anim_push_left_in,
//                R.anim.anim_push_left_out);
        fTransaction.replace(R.id.first_pane_container, clientInfoFragment, ClientInfoFragment.TAG);
        fTransaction.addToBackStack(null);
        fTransaction.commit();
        fm.executePendingTransactions();

        clientInfoFragment.displayContact();

    }


    @Override
    public void onBackPressed()
    {
        FragmentManager fm = this.getFragmentManager();
        ClientInfoFragment clientInfoFragment = (ClientInfoFragment)fm.findFragmentByTag(ClientInfoFragment.TAG);

        if ((clientInfoFragment != null) && (clientInfoFragment.isAdded()))
        {
            if(Datas.isNoteModified)
            {
                QuitDialog();
            }
            else
                fm.popBackStack();
        }
        else
        {
            super.onBackPressed();
        }

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }


    public void CreateClientList()
    {
        Datas.clients = new ArrayList<>();

        for(Contact c : ContactAPI.newContactList(this))
        {
            Client cl = new Client(c);

            Datas.clients.add(cl);
        }

        Collections.sort(Datas.clients);
    }

    /**
     * "Quit" menu has been clicked, answer for confirmation.
     */
    private void QuitDialog()
    {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setIcon(android.R.drawable.ic_dialog_alert);
        ad.setTitle("Quitter ?");
        ad.setMessage("Le client à été modifié, êtes vous sur de vouloir quitter ?");
        ad.setPositiveButton("Yes",
                new android.content.DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int arg1)
                    {
                        FragmentManager fm = getFragmentManager();
                        ClientInfoFragment clientInfoFragment = (ClientInfoFragment)fm.findFragmentByTag(ClientInfoFragment.TAG);

                        if ((clientInfoFragment != null) && (clientInfoFragment.isAdded()))
                        {
                            fm.popBackStack();
                        }
                    }
                });

        ad.setNegativeButton("No",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {


                    }
                }
        );
        ad.show();
    }


    private void addShortcut() {

        //Adding shortcut for MainActivity on Home screen
        Intent shortcutIntent = new Intent(getApplicationContext(), MainActivity.class);

        shortcutIntent.setAction(Intent.ACTION_MAIN);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Ramono");
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.ic_launcher));
        addIntent.putExtra("duplicate", false);

        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(addIntent);

    }

}
