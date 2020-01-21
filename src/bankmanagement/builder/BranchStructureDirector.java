package bankmanagement.builder;

import bankmanagement.BankManagement;
import bankmanagement.singletons.Bank;
import bankmanagement.composite.Branch;
import bankmanagement.composite.BranchDirectory;

/**
 * Director of branch builder. Calls correct functions of the builder.
 * @author stefan
 */
public class BranchStructureDirector {
    private BranchBuilder branchBuilder;    

    /**
     * Set branchBuilder instance.
     * @param branchBuilder
     */
    public void setBranchBuilder(BranchBuilder branchBuilder) {
        this.branchBuilder = branchBuilder;
    }
    
    
    /**
     * Get the branch of builder.
     * @return Branch of builder.
     */
    public Branch getBranch() {
        return branchBuilder.getBranch();
    }

    /**
     * Construct branch step by step
     * @param branch
     * @param base
     * @param bankManagement
     */
    public void createBranch(Branch branch, BranchDirectory base, BankManagement bankManagement) {
        // Create new object or set it if it exists (for editing)
        if(branch == null)
            branchBuilder.createObject();
        else
            branchBuilder.setBranch(branch);
        
        // base parameter null --> use root as parent directory
        BranchDirectory parent;
        if(base == null)
            parent = Bank.getInstance().getRootBranchDirectory();
        else
            parent = base;
            
        
        // set reference to main app:
        branchBuilder.setBankManagement(bankManagement);
        
        // show dialog and save branch only if user presses ok:
        if(branchBuilder.showDialog())
            branchBuilder.incorporateStructure(parent);
    }
    
}
