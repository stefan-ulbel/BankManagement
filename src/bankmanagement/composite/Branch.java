package bankmanagement.composite;

import bankmanagement.models.Account;
import bankmanagement.models.Customer;
import bankmanagement.models.Employee;
import bankmanagement.models.Person;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents a branch. Has employees, customers and accounts. This is the 
 * component of the composite pattern and requires implementation of
 * funds calculation.
 * @author stefan
 */
public abstract class Branch {
    StringProperty name;
    StringProperty address;
    ObservableList<Employee> employees;
    ObservableList<Customer> customers;
    ObservableList<Account> accounts;
    DoubleProperty balanceProperty;
    
    public Branch(String name, String address) {
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.employees = FXCollections.observableArrayList();
        this.customers = FXCollections.observableArrayList();
        this.accounts = FXCollections.observableArrayList();
        balanceProperty = new SimpleDoubleProperty();
    }
    
    /**
     * Sums up account balances of all customers in the branch.
     * @return The sum of account balances.
     */
    public abstract double calculateFunding();
    
    /**
     * Calculates average customer satisfaction.
     * @return The average satisfaction between 0 and 100
     */
    public int getAverageCustomerSatisfaction()
    {
       int satisfaction = 0;
       if(this.getNumCustomers() == 0)
           return 0;

       for(Customer c: customers )
           satisfaction += c.getSatisfaction();

       return satisfaction/this.getNumCustomers();
    }
    
    /**
     * Clears lists of branch and sets properties null.
     */
    public void deleteBranch()
    {
        for(Person e :employees)
            e.setBranch(null);

        for(Customer c :customers) {
            c.removeAccounts();
            c.setBranch(null);
        }   

        employees.clear();
        accounts.clear();
        customers.clear();
    }
    
    /**
     * Gets number of customers.
     * @return Number of customers in list.
     */
    public int getNumCustomers()
    {
        if(customers == null || customers.isEmpty())
         return 0;

        return customers.size();
    }
    
    /**
     * Gets number of employees.
     * @return Number of employees in list.
     */
    public int getNumEmployees()
    {
       if(employees == null || employees.isEmpty())
        return 0;

       return employees.size();
    }
    
    // getter, setter, FX properties and list functions:
    
    public void addEmployee(Employee employee) {
         if(employees.contains(employee) == false)
             employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }

     public void addCustomer(Customer customer) {
         if(customers.contains(customer) == false)
             customers.add(customer);
    }

    public void removeCustomer(Customer customer) {
        customers.remove(customer);
    }

     public ObservableList<Employee> getEmployees() {
         return employees;
     }

     public ObservableList<Customer> getCustomers() {
         return customers;
     }
     
    public void addAccount(Account account) {
        if(accounts.contains(account) == false)
             accounts.add(account);
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
    }

     public ObservableList<Account> getAccounts() {
         return accounts;
     }
     
     public DoubleProperty balanceProperty() {
        return this.balanceProperty;
    }
    
     public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }
    
    public StringProperty nameProperty() {
        return name;
    }
    
     public String getAddress() {
        return address.get();
    }

    public void setAddress(String name) {
        this.address.set(name);
    }
    
    public StringProperty addressProperty() {
        return address;
    }
       
    @Override
    public String toString() {
        return name.get();
    }
    
}
