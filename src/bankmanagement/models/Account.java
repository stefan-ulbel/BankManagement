package bankmanagement.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;


/**
 * Class representing an account, is owned by customers.
 * @author stefan
 */
public class Account {
    
    /**
     * Enum for account type.
     */
    public enum Type {
        Checking, Investment, Savings;
    }
    
    /**
     * Enum for account currency.
     */
    public enum Currency {
        Euro, USDollar, Renminbi, SwissFrank;
    }
      
    private int account_nr;
    private DoubleProperty balance; 
    ObjectProperty<Currency> currency;
    ObjectProperty<Type> account_type;
    private DoubleProperty interest_rate;
    private ObjectProperty<Customer> owner;
    
    public Account() {
        this(0, 0, null, null, 0);
    }

    public Account(int account_nr, double balance, Type account_type, Currency currency, double interest_rate) {
        this.account_nr = account_nr;
        this.balance = new SimpleDoubleProperty(balance);
        this.account_type = new SimpleObjectProperty<>(account_type);
        this.currency = new SimpleObjectProperty<>(currency);
        this.interest_rate = new SimpleDoubleProperty(interest_rate);
        this.owner = new SimpleObjectProperty<>();
    }

    // Getter, Setter and FX Properties:
    public int getAccount_nr() {
        return account_nr;
    }

    public void setAccount_nr(int account_nr) {
        this.account_nr = account_nr;
    }

    public double getBalance() {
        return balance.get();
    }

    public void setBalance(double balance) {
        this.balance.set(balance);
    }
    
    public DoubleProperty balanceProperty() {
        return balance;
    }

    public Type getType() {
        return account_type.get();
    }

    public void setType(Type account_type) {
        this.account_type.set(account_type);
    }
    
    public ObjectProperty accountTypeProperty() {
        return account_type;
    }

    public double getInterest_rate() {
        return interest_rate.get();
    }

    public void setInterest_rate(double interest_rate) {
        this.interest_rate.set(interest_rate);
    }
    
    public DoubleProperty interestRateProperty() {
        return interest_rate;
    }
    
    public ObjectProperty currencyProperty() {
        return currency;
    }

    public Currency getCurrency() {
        return currency.get();
    }

    public void setCurrency(Currency currency) {
        this.currency.set(currency);
    }

    public Customer getOwner() {
        return owner.get();
    }

    public void setOwner(Customer owner) {
        this.owner.set(owner);
    }
    
    public ObjectProperty ownerProperty() {
        return owner;
    }
    
    @Override
    public String toString() {
        return "account nr: " + account_nr;
    }
    
    
}
