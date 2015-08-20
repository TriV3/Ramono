package com.TriVe.Apps.Ramono;

import com.TriVe.Apps.Ramono.Ramonage.Chauffage;
import com.TriVe.Apps.Ramono.Ramonage.Client;
import com.TriVe.Apps.Ramono.Ramonage.Conduit;

import java.util.ArrayList;
import java.util.List;

public class Datas
{
    // Fragments shared variables:
    public static List<Client> clients;

    public static Client Selectedclient;

    public static String DisplayedClientID = "";

    public static Conduit SelectedConduit = new Conduit();

    public static Chauffage SelectedChauffage = new Chauffage();


    public static List<String> NoteElements = new ArrayList<>();

    public static  boolean isNoteModified = false;


}
