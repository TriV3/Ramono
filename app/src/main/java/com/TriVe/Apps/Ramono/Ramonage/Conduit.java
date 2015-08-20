package com.TriVe.Apps.Ramono.Ramonage;

public class Conduit
{
    public String type = "";
    public String place = "";
    public String date = "";
    public int id = -1;
    private boolean _isSelected = false;
    public boolean isToAdd = false;

    public Conduit()
    {
    }

    public Conduit(String type, String place, String date)
    {
        this.type = type;
        this.place = place;
        this.date = date;
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
