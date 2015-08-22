package com.TriVe.Apps.Ramono;

import com.TriVe.Apps.Ramono.Ramonage.Chauffage;
import com.TriVe.Apps.Ramono.Ramonage.Client;
import com.TriVe.Apps.Ramono.Ramonage.Conduit;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Data shared by the project.</b>
 *
 * @author TriVe
 * @version 1.0
 */
public class Datas
{
    /**
     * List of the clients (device contact)
     */
    public static List<Client> clients;

    /**
     * Store the client selected from the ClientListFragment
     */
    public static Client Selectedclient;


    /**
     * Store the selected "Conduit"
     */
    public static Conduit SelectedConduit = new Conduit();

    /**
     * Store the selected "Chauffage"
     */
    public static Chauffage SelectedChauffage = new Chauffage();

    /**
     * Store the note data
     */
    public static List<String> NoteElements = new ArrayList<>();

    /**
     * Store the state of modification of the note
     */
    public static  boolean isNoteModified = false;


}
