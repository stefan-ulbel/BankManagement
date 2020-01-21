package bankmanagement.composite;

import bankmanagement.models.Customer;
import javafx.beans.property.DoubleProperty;


/**
 * Leaf of the composite pattern, representig a local branch.
 * @author stefan
 */
public class LocalBranch extends Branch{
    DoubleProperty funding;
    
    public LocalBranch() { 
        this(null, null);
    }

    public LocalBranch(String name, String address) {
        super(name, address);
    }

    @Override
    public double calculateFunding()
    {
        double funding = 0;
        if(customers == null || customers.isEmpty())
            return 0;
        
        for(Customer cus: customers)
            funding += cus.getFunding();
        
        return funding;
    }

    
    @Override
    public String toString(){
        return name.get();
    }
    
}
