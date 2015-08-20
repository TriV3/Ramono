package com.TriVe.Apps.mycontact.ContactAPI.objects;

public class Phone {
    private String number;
    private String type;
    private String label;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
                AddressType = "MOBILE";
                break;
            case "3":
                AddressType = "WORK";
                break;
            case "4":
                AddressType = "FAX_WORK";
                break;
            case "5":
                AddressType = "FAX_HOME";
                break;
            case "6":
                AddressType = "PAGER";
                break;
            case "7":
                AddressType = "OTHER";
                break;
            case "8":
                AddressType = "CALLBACK";
                break;
            case "9":
                AddressType = "CAR";
                break;
            case "10":
                AddressType = "COMPANY_MAIN";
                break;
            case "11":
                AddressType = "ISDN";
                break;
            case "12":
                AddressType = "MAIN";
                break;
            default:
                AddressType = "?";
        }

        return AddressType;
    }




    public Phone(String n, String t, String l) {
        this.number = n;
        this.type = t;
        this.label = l;
    }

}
