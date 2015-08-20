package com.TriVe.Apps.mycontact.ContactAPI.objects;

public class Email {
    private String address;
    private String type;
    private String label;


    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getType() {
        return type;
    }
    public void setType(String t) {
        this.type = t;
    }
    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getStringFromType()
    {
        String AddressType = "";
        switch(type) {
            case "0":
                AddressType = label;
                break;
            case "1":
                AddressType = "HOME";
                break;
            case "2":
                AddressType = "WORK";
                break;
            case "3":
                AddressType = "OTHER";
                break;
            case "4":
                AddressType = "MOBILE";
                break;
            default:
                AddressType = "?";
        }


        return AddressType;
    }


    public Email(String a, String t, String l)
    {
        this.address = a;
        this.type = t;
        this.label = l;
    }
}
