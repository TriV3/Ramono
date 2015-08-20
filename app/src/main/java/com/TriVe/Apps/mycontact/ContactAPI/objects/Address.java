package com.TriVe.Apps.mycontact.ContactAPI.objects;

public class Address
{

    private String poBox;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String type;
    private String label;
    private String asString = "";


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
            default:
                AddressType = "?";
        }


        return AddressType;
    }

    public String getType()
    {
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

    public String getPoBox() {
        return poBox;
    }

    public void setPoBox(String poBox) {
        this.poBox = poBox;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String toString()
    {
        if (this.asString.length() > 0)
        {
            return(this.asString);
        }
        else
        {
            String addr = "";
            if (this.getPoBox() != null) {
                addr = addr + this.getPoBox() + " ";
            }
            if (this.getStreet() != null) {
                addr = addr + this.getStreet() + " ";
            }
//            if (this.getState() != null) {
//                addr = addr + this.getState() + " ";
//            }
            if (this.getPostalCode() != null) {
                addr = addr + this.getPostalCode() + " ";
            }
            if (this.getCity() != null) {
                addr = addr + this.getCity();
            }
//            if (this.getCountry() != null) {
//                addr = addr + this.getCountry();
//            }
            return(addr);
        }
    }

    public Address(String asString, String type) {
        this.asString = asString;
        this.type = type;
    }

    public Address(String poBox, String street, String city, String state,
                   String postal, String country, String type, String label) {
        this.setPoBox(poBox);
        this.setStreet(street);
        this.setCity(city);
        this.setState(state);
        this.setPostalCode(postal);
        this.setCountry(country);
        this.setType(type);
        this.setLabel(label);
    }
}
