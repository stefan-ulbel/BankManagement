package bankmanagement.strategy;

import bankmanagement.composite.Branch;

/**
 * Represents a certain strategy which is applied on a branch.
 * @author stefan
 */
public interface Strategy {
    
    /**
     * Handle a branch, execute strategy on branch.
     * @param branch The branch to operate on.
     * @return A message informing what happened.
     */
    public String handleBranch(Branch branch);
    
}
