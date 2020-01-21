package bankmanagement.models;

import bankmanagement.composite.Branch;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Class extends person and represents an employee who works at a branch.
 * @author stefan
 */
public class Employee extends Person{
    
    public enum Position {
        CEO ("CEO"),
        VicePresident ("VicePresident"),
        BranchManager ("BranchManager"),
        Employee ("Employee"),
        Trainee ("Trainee");
        private String description;
        private Position(String description) {this.description = description; }
        @Override
        public String toString() {
            return description;
        }
    }
    
    
    ObjectProperty<Position> position;
    IntegerProperty salary;
    String email;
    
    
    public Employee() {
        this(0,null,null,null,null,null,0,null); } 
 
    public Employee(int id, String first_name, String last_name, String address, Branch branch,
            Position position, int salary, String email) {
        super(id, first_name, last_name, address, branch);
        this.position = new SimpleObjectProperty<>(position);
        this.salary = new SimpleIntegerProperty(salary);
        this.email = email;
    }
    
    public ObjectProperty positionProperty() {
        return position;
    }

    public Position getPosition() {
        return position.get();
    }

    public void setPosition(Position position) {
        this.position.set(position);
    }

    public int getSalary() {
        return salary.get();
    }

    public void setSalary(int salary) {
        this.salary.set(salary);
    }
    
    public IntegerProperty salaryProperty() {
        return salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return super.toString() + ", position: " + position.toString() + ", salary: "
        + salary + ", email: " + email;
    }
}
