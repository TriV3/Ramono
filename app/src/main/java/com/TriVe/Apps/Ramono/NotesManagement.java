package com.TriVe.Apps.Ramono;

import android.util.Log;

import com.TriVe.Apps.Ramono.Ramonage.Chauffage;
import com.TriVe.Apps.Ramono.Ramonage.Conduit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <b>API used to manage contact notes.</b>
 *
 * @author TriVe
 * @version 1.0
 */
public class NotesManagement
{
    public static final String TAG = "NotesManagementTAG";



    public static List<String> separateConduitFromNote(List<String> Notes)
    {
        List<String> datas = new ArrayList<>();

        String s = Notes.get(0);

        try
        {
            Pattern p = Pattern.compile("([\\S\\s]*)##([\\S\\s]*)##([\\S\\s]*)");
            Matcher m = p.matcher(s);

            while (m.find())
            {
                datas.add(m.group(1));
                datas.add(m.group(2));
                datas.add(m.group(3));
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, ex.getMessage());
        }

        if (datas.size() == 0)
            datas.add(Notes.get(0));



        return datas;
    }


    public static List<String> getLinesFromNotes(String RamonageNote)
    {
        List<String> datas = new ArrayList<>();

        if (!RamonageNote.equals(""))
        {
//            RamonageNote = RamonageNote.replace("##","").replace("\r", "").replace("\n", "");
            RamonageNote = RamonageNote.replace("##","");
            Collections.addAll(datas, (RamonageNote.split("\\[")));
        }

        return datas;
    }


    public static List<Conduit> getConduitsFromLine(String conduitEntry)
    {
        String conduiType = getConduitType(conduitEntry);

        List<Conduit>temp = new ArrayList<>();
        String allConduits = "";
        try
        {
            Pattern p = Pattern.compile(".*@\\s*(.*)\\]");
            Matcher m = p.matcher(conduitEntry);
            while (m.find())
            {
                allConduits = m.group(1);
            }
            for(String s : allConduits.split("&"))
            {
                s = s.replace("\n","").replace(" ", "");
                if(s.contains(":"))
                    temp.add(new Conduit(conduiType, s.split(":")[0], s.split(":")[1]));
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG + " - getConduitsFromLine", ex.getMessage());
        }


        return temp;
    }

    public static Chauffage getChauffageFromLine(String chauffageEntry)
    {
        String chauffageType = getChauffageType(chauffageEntry);
        Chauffage temp = null;
        String chauffageNbr = "";
        try
        {
            Pattern p = Pattern.compile(".*@\\s*(.*)\\]");
            Matcher m = p.matcher(chauffageEntry);
            while (m.find())
            {
                chauffageNbr = m.group(1).replace(" ", "");
            }

            int  chauffageValue;
            try
            {
                chauffageValue = Integer.parseInt(chauffageNbr);
            }
            catch (NumberFormatException nfe)
            {
                chauffageValue = 0;
            }

            if(chauffageValue > 0)
                temp = new Chauffage(chauffageType,chauffageNbr);

        }
        catch (NumberFormatException ex)
        {
            Log.e(TAG + " - getChauffageFromLine", ex.getMessage());
        }


        return temp;
    }

    public static String getStringFromLine(String Entry)
    {
        String temp = "";

        try
        {
            Pattern p = Pattern.compile(".*@\\s*([\\S\\s]*)\\]");
            Matcher m = p.matcher(Entry);
            while (m.find())
            {
                temp = m.group(1);
            }

        }
        catch (Exception ex)
        {
            Log.e(TAG + " - getStringFromLine", ex.getMessage());
        }


        return temp;
    }


    private static String getConduitType(String ConduitLine)
    {
        // Get conduit type:
        Pattern p = Pattern.compile("conduit\\s*(.*)\\s*@");
        Matcher m = p.matcher(ConduitLine.toLowerCase());
        while (m.find())
        {
            return m.group(1).trim();
        }

        return "";
    }

    private static String getChauffageType(String ConduitLine)
    {
        // Get chauffage type:
        Pattern p = Pattern.compile("chauffage\\s*(.*)\\s*@");
        Matcher m = p.matcher(ConduitLine.toLowerCase());
        while (m.find())
        {
            return m.group(1).trim();
        }

        return "";
    }


}

