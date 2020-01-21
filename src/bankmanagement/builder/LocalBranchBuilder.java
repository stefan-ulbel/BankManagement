package bankmanagement.builder;

import bankmanagement.composite.BranchDirectory;
import bankmanagement.composite.LocalBranch;

/**
 * Concrete Builder. Builds a LocalBranch and incorporates it in the correct
 * BranchDirectory.
 * @author stefan
 */
public class LocalBranchBuilder extends BranchBuilder {

    @Override
    public void createObject() {
        branch = new LocalBranch();
    }

    @Override
    public void incorporateStructure(BranchDirectory base) {
        base.addBranch(branch);
    }

    @Override
    public boolean showDialog() {
        return bankManagement.showBranchEditDialog(branch, "Edit a local branch");
    }
    
}
