package com.TriVe.Apps.Ramono.Ramonage;

/**
 * <b>Represent a "Chauffage" object.</b>
 *
 * @author TriVe
 * @version 1.0
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

