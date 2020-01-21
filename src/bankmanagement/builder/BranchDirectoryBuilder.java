package bankmanagement.builder;

import bankmanagement.composite.BranchDirectory;

/**
 * Concrete Builder. Builds a BranchDirectory and incorporates it in the base
 * structure.
 * @author stefan
 */
public class BranchDirectoryBuilder extends BranchBuilder {

    @Override
    public void createObject() {
        branch = new BranchDirectory();
    }

    @Override
    public void incorporateStructure(BranchDirectory base) {
       base.addBranch(branch);
    }

    @Override
    public boolean showDialog() {
        return bankManagement.showBranchEditDialog(branch, "Edit a branch directory");
    }
    
}
