package bankmanagement.builder;


import bankmanagement.BankManagement;
import bankmanagement.composite.Branch;
import bankmanagement.composite.BranchDirectory;

/**
 * Abstract class to build branches.
 * @author stefan
 */
public abstract class BranchBuilder {
    
    protected Branch branch;
    protected BankManagement bankManagement; // reference to main application
    
    
    /**
     * Get branch of builder
     * @return The branch.
     */
    public Branch getBranch() {
        return branch;
    }
    
    /**
     * Set branch of builder.
     * @param branch The branch
     */
    public void setBranch(Branch branch) {
        this.branch = branch;
    }
    
    /**
     * Set reference to BankManagement app.
     * @param bankManagement The bankManagement instance
     */
    public void setBankManagement(BankManagement bankManagement) {
        this.bankManagement = bankManagement;
    }
    
    /**
     * Instantiate the new object.
     */
    public abstract void createObject();
    
    /**
     * Show the correct edit dialog.
     * @return 
     */
    public abstract boolean showDialog();
    
    /**
     * Incorporate branch in correct directory.
     * @param base Base of the structure.
     */
    public abstract void incorporateStructure(BranchDirectory base);
    
        
    
}
