package bankmanagement.models;

import bankmanagement.composite.Branch;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents a customer who can own multiple accounts.
 * @author stefan
 */
public class Customer extends Person{
    
    String telephone_nr;
    List<Account> accounts;
    IntegerProperty satisfaction;
    
      public Customer() {
          this(0,null,null,null,null,null,0); } 

    public Customer(int id, String first_name, String last_name, String address, Branch branch
            ,String telephone_nr, int satisfaction) {
        super(id, first_name, last_name, address, branch);
        this.telephone_nr = telephone_nr;
        this.satisfaction = new SimpleIntegerProperty(satisfaction);
        accounts = new ArrayList<>();
    }

    public String getTelephone_nr() {
        return telephone_nr;
    }

    public void setTelephone_nr(String telephone_nr) {
        this.telephone_nr = telephone_nr;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        if(accounts.contains(account) == false)
            accounts.add(account);
    }
    
    public void removeAccount(Account account) {
        accounts.remove(account);
    }

    public int getSatisfaction() {
        return satisfaction.get();
    }

    public void setSatisfaction(int satisfaction) {
        this.satisfaction.set(satisfaction);
    }
    
    public IntegerProperty satisfactionProperty() {
        return satisfaction;
    }
    
    public DoubleProperty balanceProperty() {
        return new SimpleDoubleProperty(getFunding());
    }
    
    public void removeAccounts() {
        for(Account acc : accounts) {
            branch.removeAccount(acc);
        }
        accounts.clear();
    }
    
    public double getFunding() {
        double funding = 0;
        if(accounts == null || accounts.isEmpty())
            return 0;
        
        for(Account acc: accounts)
            funding += acc.getBalance();
        
        return funding;
        
    }

    @Override
    public String toString() {
        return super.toString();
    }
    
    
}
