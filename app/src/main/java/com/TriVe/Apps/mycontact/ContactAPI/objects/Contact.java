package com.TriVe.Apps.mycontact.ContactAPI.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Represent a device contact.</b>
 *
 * @author TriVe
 * @version 1.0
 */
public class Contact
{
    private String id = "-1";
    private String Rawid = "-1";
    private String displayName = "";
    private List<Phone> phone = new ArrayList<Phone>();
    private List<Email> email = new ArrayList<Email>();
    private List<String> notes = new ArrayList<String>();
    private List<Address> addresses = new ArrayList<Address>();
    private List<IM> imAddresses = new ArrayList<>();
    private Organization organization;

    public Contact()
    {

    }

    public Contact(Contact contact)
    {
        if (!contact.equals(null))
        {
            this.setId(contact.getId());
            this.setRawId(contact.getRawId());
            this.setDisplayName(contact.getDisplayName());
            this.setAddresses(contact.getAddresses());
            this.setEmail(contact.getEmail());
            this.setNotes(contact.getNotes());
            this.setPhone(contact.getPhone());
            this.setImAddresses(contact.getImAddresses());
            this.setOrganization(contact.getOrganization());

        }
    }


    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<IM> getImAddresses() {
        return imAddresses;
    }

    public void setImAddresses(List<IM> imAddresses) {
        this.imAddresses = imAddresses;
    }

    public void addImAddresses(IM imAddr) {
        this.imAddresses.add(imAddr);
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    public void addNote(String note) {
        this.notes.add(note);
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public void addAddress(Address address) {
        this.addresses.add(address);
    }

    public List<Email> getEmail() {
        return email;
    }

    public void setEmail(List<Email> email) {
        this.email = email;
    }

    public void addEmail(Email e) {
        this.email.add(e);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRawId() {
        return id;
    }

    public void setRawId(String id) {
        this.Rawid = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String dName) {
        this.displayName = dName;
    }

    public List<Phone> getPhone() {
        return phone;
    }

    public void setPhone(List<Phone> phone) {
        this.phone = phone;
    }

    public void addPhone(Phone phone) {
        this.phone.add(phone);
    }

}

