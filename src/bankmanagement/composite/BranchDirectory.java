package bankmanagement.composite;

import bankmanagement.models.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Composite, owns a list of branches and sums up their funding.
 * @author stefan
 */
public class BranchDirectory extends Branch {

    private ObservableList<Branch> branches = FXCollections.observableArrayList();
    
    public BranchDirectory() {
        super(null, null);
    }
    
    public BranchDirectory(String name, String address) {
        super(name, address);
    }
    
    @Override
    public double calculateFunding() {
       double funding = 0;
       for(Branch branch : branches)
       { 
           funding += branch.calculateFunding(); 
       } 
        this.balanceProperty().set(funding);
        return funding;
    }
    
    /**
     * Perform a liquidity Check.
     * @return String informing about liquidity.
     */
    public String liquidityCheck() {
        String info = "";
        if(calculateFunding() < 5000) {
            Employee branchManager = null;
            for(Employee emp : employees) {
                if(emp.getPosition() == Employee.Position.BranchManager)
                    branchManager = emp; }
                    
            if(branchManager != null)        
                info = "Informing " + branchManager.getFirstName() + " "+ 
                        branchManager.getLastName() + " that Branch " +
                        getName() + " is running low on funds!";
            else
                info = "Branch " + getName() + " is running low on funds!";
        }
        else
            info = "Branch " + getName() + " has proper funds.";
        
        return info;
    }
    
    public void addBranch(Branch branch) {
        if(branches.contains(branch) == false)
            branches.add(branch);
    }
    
    public void removeBranch(Branch branch) {
        branches.remove(branch);
    }
 
    public void removeAllBranches() {
        branches.clear();
    }

    public ObservableList<Branch> getBranches() {
        return branches;
    }
    
}
