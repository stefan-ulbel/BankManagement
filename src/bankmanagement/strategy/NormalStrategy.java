package bankmanagement.strategy;

import bankmanagement.models.Account;
import bankmanagement.composite.Branch;
import bankmanagement.models.Customer;

/**
 * The normal strategy increases or decreases interest rate slightly depending
 * on economic outlook.
 * @author stefan
 */
public class NormalStrategy implements Strategy{

    @Override
    public String handleBranch(Branch branch) {
        double funding = branch.calculateFunding();
        double numCustomers = branch.getNumCustomers();
        double numEmployees = branch.getNumEmployees();
        
        if(numCustomers == 0)
            return "Branch has no customers.";
        
        // lower interest rate only if bad economic outlook:
        if(funding / numCustomers < 100)
        {
           // lower interest rates:
           for(Customer c: branch.getCustomers())
           {
               for(Account acc: c.getAccounts()) {
                   // lower interest rate of each account 
                   double rate = acc.getInterest_rate();
                   rate -= 0.2;
                   if(rate < 1)
                       rate = 1;
                   
                   acc.setInterest_rate(rate);
               }
                   
           }
           return "Decreased interest rates of customer accounts.";
        } // increase interest rates if really good economic outlook
        else if(funding / numCustomers > 1000)
        {
           // increate interest rates:
           for(Customer c: branch.getCustomers())
           {
               for(Account acc: c.getAccounts()) {
                   // increase interest rate of each account 
                   double rate = acc.getInterest_rate();
                   rate += 0.5;
                   if(rate > 6)
                       rate = 6;
                   
                   acc.setInterest_rate(rate);
               }
                   
           }
           
        }
        return "Increased interest rates of customer accounts.";
    }
    
}
