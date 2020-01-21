package bankmanagement.singletons;

import bankmanagement.composite.Branch;
import bankmanagement.models.Employee;

/**
 * A singleton to save and authenticate passwords and properties
 * of the users.
 * 
 * @author stefan
 */
public class UserHandler {
    private final static Employee admin = new Employee(-1, "admin", "", "", null,  Employee.Position.CEO, 0, null);
    private Employee branchmanager = new Employee(-2, "branchmanager", "", "", null,  Employee.Position.BranchManager, 0, null);
    
    private static UserHandler instance;
    
    private UserHandler() {}
         
    /**
     * Get instance of singleton.
     * @return The UserHandler instance
     */
    public static UserHandler getInstance()
    {
        if (instance == null)
                instance = new UserHandler();
        
        return instance;
    }
    
    /**
     * Compares password and username strings and returns corresponding employee
     * object.
     * @param username The username the user put in.
     * @param password The password the user put in.
     * @return The corresponding employee (CEO or Branchmanager), or null for an
     * incorrect password.
     */
    public Employee authenticate(String username, String password)
    {
        if("admin".equals(username) && "admin".equals(password))
            return admin;
        
        if("branchmanager".equals(username) && "branchmanager".equals(password))
            return branchmanager;
        
        return null;
    }
    
    public void setBranch(Branch branch) {
        this.branchmanager.setBranch(branch);
    }
    
    public Branch getBranch() {
        return this.branchmanager.getBranch();
    }
    
    
   
}
