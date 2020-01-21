package bankmanagement.models;

import bankmanagement.composite.Branch;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Abstract class representing a person. Has necessary person properties.
 * @author stefan
 */
public abstract class Person {
    
    int id;
    StringProperty first_name;
    StringProperty last_name;
    String address;
    Branch branch;
    
    public Person() {} 

    public Person(int id, String first_name, String last_name, String address, Branch branch) {
        this.id = id;
        this.first_name = new SimpleStringProperty(first_name);
        this.last_name = new SimpleStringProperty(last_name);
        this.address = address;
        this.branch = branch;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return first_name.get();
    }

    public void setFirstName(String first_name) {
        this.first_name.set(first_name);
    }
    
    public StringProperty firstNameProperty() {
        return first_name;
    }

    public String getLastName() {
        return last_name.get();
    }

    public void setLastName(String last_name) {
        this.last_name.set(last_name);
    }
    
    public StringProperty lastNameProperty() {
        return last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
    
    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }
}
