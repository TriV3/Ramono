package com.TriVe.Apps.mycontact.ContactAPI.objects;

import android.content.Context;

import com.TriVe.Apps.Ramono.Ramonage.Client;
import com.TriVe.Apps.mycontact.ContactAPI.ContactAPI;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Clients management API.</b>
 *
 * @author TriVe
 * @version 1.0
 */
public class Clients
{

    private List<Client> clients = new ArrayList<Client>();
    private Context context;
    private ContactAPI ca;

    public Clients(Context context)
    {
        this.context = context;
        ca = new ContactAPI(context);
    }

    public List<Client> setClients()
    {
        List<Client> Ramono = new ArrayList<>();

        for(Contact c : ca.newContactList(context))
        {
            Ramono.add((Client) c);
        }

        setClients(Ramono);

        return this.clients;
    }

    public List<Client> getContacts() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public void addClient(Client client) {
        this.clients.add(client);
    }


    public int GetCount()
    {
        return this.clients.size();
    }

    public Contact GetClient(int index)
    {
        return clients.get(index);
    }


}