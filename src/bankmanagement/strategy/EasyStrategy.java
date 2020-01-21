package bankmanagement.strategy;

import bankmanagement.models.Account;
import bankmanagement.composite.Branch;
import bankmanagement.models.Customer;

/**
 * The easy strategy increases interest rate of branch up to a defined maximum.
 * @author stefan
 */
public class EasyStrategy implements Strategy{

    @Override
    public String handleBranch(Branch branch) {
        double numCustomers = branch.getNumCustomers();

        if(numCustomers == 0)
            return "Branch has no customers.";
        
        // increase interest rates:
           for(Customer c: branch.getCustomers())
           {
               for(Account acc: c.getAccounts()) {
                   // increase interest rate of each account 
                   double rate = acc.getInterest_rate();
                   rate += 0.7;
                   if(rate > 9)
                       rate = 9;
                   
                   acc.setInterest_rate(rate);
               }
                   
           }
           return "Increased interest rate of all branch accounts";
        
    }
    
}
