package Model;

public class ContactsModel {

    private String name , number ;

    public ContactsModel(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public ContactsModel() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
