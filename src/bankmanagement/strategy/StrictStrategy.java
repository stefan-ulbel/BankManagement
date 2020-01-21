package bankmanagement.strategy;

import bankmanagement.models.Account;
import bankmanagement.composite.Branch;
import bankmanagement.models.Customer;
import bankmanagement.models.Employee;
import javafx.collections.ObservableList;

/**
 * The strict strategy fires the current Branch Manager of the LocalBranch if the
 * customer satisfaction is low and the average funding of the bank is
 * too low. The employee with the highest salary will become the new branch manager.
 * @author stefan
 */
public class StrictStrategy implements Strategy {
    
    @Override
    public String handleBranch(Branch branch) {
        double funding = branch.calculateFunding();
        double numCustomers = branch.getNumCustomers();
        double numEmployees = branch.getNumEmployees();
        String result = "";
        
        if(numCustomers == 0 || numEmployees == 0)
            return "Branch has no customers/employees.";
        
        // if low funding and low satisfaction:
        if(funding / numCustomers < 150 && branch.getAverageCustomerSatisfaction() < 50)
        {
           // go through all employees of branch: 
           ObservableList<Employee> employees = branch.getEmployees();
           Employee newBranchManager = employees.get(0);
           Employee oldBranchManager = null;
           for(Employee e : employees)
           {
               // get current branch manager(s)
               if(e.getPosition() == Employee.Position.BranchManager)
                   oldBranchManager = e;
               
               
               // get employee with highest salary:
               if(e.getPosition() == Employee.Position.Employee && e.getSalary() > newBranchManager.getSalary())
                   newBranchManager = e;
           }
           // fire old branch manager:
           if(oldBranchManager != null) {
               employees.remove(oldBranchManager);
               result += "Fired " + oldBranchManager.getFirstName() + " " + oldBranchManager.getLastName()+".\n";
           }
               
           // promote other employee to branch manager:
           newBranchManager.setPosition(Employee.Position.BranchManager);
           result += "Promoted " + newBranchManager.getFirstName() + " ";
           result += newBranchManager.getLastName() + " to branch manager.\n";
           // lower interest rate:
           for(Customer c: branch.getCustomers())
           {
               for(Account acc: c.getAccounts()) {
                   // lower interest rate of each account,but keep at a minimum of 0.5
                   double rate = acc.getInterest_rate();
                   rate -= 0.4;
                   if(rate < 0.5)
                       rate = 0.5;
                   
                   acc.setInterest_rate(rate);
               }
                   
           }
           result += "Interest rates have been lowered.";
           
        }
        else // funding and satisfaction high enough:
            result = "Branch is ok. No action taken.";
            
        return result;
        
    }
    
    
}
