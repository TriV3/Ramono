package com.TriVe.Apps.mycontact.ContactAPI.objects;

/**
 * <b>Represent a contact IM.</b>
 *
 * @author TriVe
 * @version 1.0
 */
public class IM {
    private String name;
    private String type;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public IM(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
