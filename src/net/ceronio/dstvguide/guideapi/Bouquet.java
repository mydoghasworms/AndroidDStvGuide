package net.ceronio.dstvguide.guideapi;

/**
 * User: macky
 * Date: 2012/12/23
 * Time: 5:12 PM
 */
@Deprecated
public class Bouquet {
    String ID;
    String Name;
    String Code;
    String Description;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
