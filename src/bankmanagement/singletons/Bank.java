package bankmanagement.singletons;

import bankmanagement.adapter.EUForeignTransfer;
import bankmanagement.adapter.InternationalTransfer;
import bankmanagement.adapter.InternationalTransferAdapter;
import bankmanagement.strategy.Strategy;
import bankmanagement.models.Account;
import bankmanagement.composite.Branch;
import bankmanagement.composite.BranchDirectory;
import bankmanagement.models.Employee;

/**
 * Bank singleton, representing the Bank and it's most important fields like
 * funds, leadership and the branch directory.
 * @author stefan
 */
public class Bank {
    
    private static Bank instance;
    
    double balance; // funds of bank
    private BranchDirectory rootBranchDirectory; // root branch directory containing
                                                // more branch directories
    public final String name = "Bank of Central Europe"; // name of bank
    private Strategy strategy; // current strategy of bank
    private Employee CEO;       // CEO of bank
    private Employee VicePresident; // Vice President of bank
    
    
    
    private Bank() {
        // create root branch directory containing country directories:
        rootBranchDirectory = new BranchDirectory("Root", "Europe");
        balance = 0;
    }
    
    public static Bank getInstance()
    {
        if (instance == null)
                instance = new Bank();
        
        return instance;
    }
    
    public String executeStrategy(Branch branch)
    {
        if(strategy != null)
            return strategy.handleBranch(branch);
        
        return "";
    }
    
    /**
     * Send funds to a foreign bank.
     * @param accountNr Receiver account nr.
     * @param amount Amount of money to send.
     * @param currency Type of currency to use.
     * @return A message indicating success or failure
     */
    public String sendFunds(String accountNr, double amount, Account.Currency currency) {
        // Create a new object using the interface and adapter:
        InternationalTransfer t = new InternationalTransferAdapter(new EUForeignTransfer());
        // call adapter method and obtain result:
        boolean result = t.makeTransaction(accountNr, amount, currency);
        if(result == true)
        {
            balance -= amount;
            return "Successfully sent " + amount + " " + currency + " to\n" + accountNr + ".";
        }   
        else
            return "Erorr sending money.";
        
    }
   
    public BranchDirectory getRootBranchDirectory() {
        return rootBranchDirectory;
    }

    public void setRootBranchDirectory(BranchDirectory rootBranchDirectory) {
        this.rootBranchDirectory = rootBranchDirectory;
    }

    public String getName() {
        return name;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Employee getCEO() {
        return CEO;
    }

    public void setCEO(Employee CEO) {
        this.CEO = CEO;
    }

    public Employee getVicePresident() {
        return VicePresident;
    }

    public void setVicePresident(Employee VicePresident) {
        this.VicePresident = VicePresident;
    }
    
}
