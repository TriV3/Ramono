package com.TriVe.Apps.Ramono.Ramonage;

/**
 * Created by Titou on 23/03/2015.
 */
public class Chauffage
{
    public String type = "";
    public String nombre = "0";

    public int id = -1;
    private boolean _isSelected = false;

    public Chauffage()
    {

    }

    public Chauffage (String type, String nombre)
    {
        this.type = type;
        this.nombre = nombre;
    }


    public boolean isSelected()
    {
        return _isSelected;
    }

    public void setSelected(boolean isSelected)
    {
        _isSelected =  isSelected;
    }

    public void toggleSelected()
    {
        _isSelected = !_isSelected;
    }

}

