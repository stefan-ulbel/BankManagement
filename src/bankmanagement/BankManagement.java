package bankmanagement;

import bankmanagement.observer.Subject;
import bankmanagement.observer.ConcreteSubject;
import bankmanagement.observer.Observer;
import bankmanagement.observer.BranchObserver;
import bankmanagement.strategy.NormalStrategy;
import bankmanagement.strategy.EasyStrategy;
import bankmanagement.strategy.StrictStrategy;
import bankmanagement.strategy.Strategy;
import bankmanagement.singletons.UserHandler;
import bankmanagement.controllers.AccountEditDialogController;
import bankmanagement.controllers.BranchEditDialogController;
import bankmanagement.controllers.CustomerEditDialogController;
import bankmanagement.controllers.EmployeeEditDialogController;
import bankmanagement.controllers.LoginController;
import bankmanagement.controllers.MainController;
import bankmanagement.models.Employee;
import bankmanagement.models.Customer;
import bankmanagement.composite.LocalBranch;
import bankmanagement.models.Account;
import bankmanagement.singletons.Bank;
import bankmanagement.composite.Branch;
import bankmanagement.composite.BranchDirectory;
import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Handles display of views and edit dialogs.
 * @author stefan
 */
public class BankManagement extends Application {
    
    private Stage primaryStage;
    private BorderPane rootLayout;
    
    private ObservableList<String> strategyList = FXCollections.observableArrayList(); 
    
    Subject branchSubject;
    Observer branchObserver;
    
    // packete create for classes
    public BankManagement() { }
    
    /**
     * Prepares program for display, shows Login View.
     * @param stage
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        
        this.primaryStage = stage;
        this.primaryStage.setTitle("Bank Management");

        initRootLayout();
        buildStrategyList();
        showLoginOverview();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Initializes the root structure of the user interface.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(BankManagement.class.getResource("views/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
     /**
     * Loads and displays the login overview. 
     */
    public void showLoginOverview() {
        try {
            // Load login overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(BankManagement.class.getResource("views/LoginView.fxml"));
            AnchorPane loginOverview = (AnchorPane) loader.load();

            // Set login overview into the center of root layout.
            rootLayout.setCenter(loginOverview);

            // Give the controller access to the main app.
            LoginController controller = loader.getController();
            controller.setBankManagement(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
     /**
     * Loads and displays the main overview.
     * @param user The logged in user
     */
    public void showMainOverview(Employee user) {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(BankManagement.class.getResource("views/MainView.fxml"));
            AnchorPane mainOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(mainOverview);

            // Give the controller access to the main app.
            MainController controller = loader.getController();
            controller.setBankManagement(this);
            controller.setUser(user);
            // create necessary branch structure:
            buildBaseData();
            // create test data:
            createTestData();
            // create observer:
            createBranchObserver(controller);
            controller.setData();
            // notify observers for new data:
            branchSubject.notifyObservers();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
     /**
     * Creates the observer for branch data.
     * @param mainController The MainView controller 
     */
    public void createBranchObserver(MainController mainController) {
        branchSubject = new ConcreteSubject();
        branchSubject.setValue(Bank.getInstance().getRootBranchDirectory());
        branchObserver = new BranchObserver(branchSubject, mainController);
    }

    /**
     * Builds necessary base structure with one branch in one branch directory.
     */
    public void buildBaseData() {
        BranchDirectory bd = new BranchDirectory("Austria", "Vienna");
        LocalBranch lb = new LocalBranch("HQ Branch", "Vienna 1");
        bd.addBranch(lb);
        UserHandler.getInstance().setBranch(lb);
        Bank.getInstance().getRootBranchDirectory().addBranch(bd);
        
    }
   
    /**
     * Fills lists and objects with employees, customers, accounts, branch directories
     * and branches.
     */
    private void createTestData() {
        
        // get root branch directory:
        BranchDirectory rootBranchDirectory = Bank.getInstance().getRootBranchDirectory();    
        
        // create country branches and countryBranchDirectory:
        BranchDirectory gerBranches = new BranchDirectory("Germany", "Berlin 1");
        BranchDirectory autBranches = (BranchDirectory) rootBranchDirectory.getBranches().get(0);
        BranchDirectory czBranches = new BranchDirectory("Czechia", "Prague 1");
        
        rootBranchDirectory.addBranch(gerBranches);
        rootBranchDirectory.addBranch(czBranches);
        
        // create CEO and VicePresident:
        Employee ceo = new Employee(1, "Albert", "Hutter", "Oneway 1, Alcala de Henares", null, 
                Employee.Position.CEO, 5000, "ceo@company.com");
        Bank.getInstance().setCEO(ceo);
        
        Employee vp = new Employee(2, "Jule", "Na", "Oneway 1, Alcala de Henares", null, 
                Employee.Position.VicePresident, 3000, "vice.president@company.com");
        Bank.getInstance().setVicePresident(vp);
        
        // set Bank balance:
        Bank.getInstance().setBalance(100000);
        
        // create Branchmanagers for Country BranchDirectories:
        Employee bm1 = new Employee(3, "Austria", "Manager", "Oneway 2, Vienna", autBranches, 
                Employee.Position.BranchManager, 4000, "bm1@company.com");
        
        Employee bm2 = new Employee(4, "Germany", "Manager", "Oneway 3, Berlin", gerBranches, 
                Employee.Position.BranchManager, 4000, "bm2@company.com");
        
        Employee bm3 = new Employee(5, "Czechia", "Manager", "Oneway 4, Prague", czBranches, 
                Employee.Position.BranchManager, 4000, "bm3@company.com");
        
        autBranches.addEmployee(bm1);
        gerBranches.addEmployee(bm2);
        czBranches.addEmployee(bm3);
        
        // create local branches
        for(int i = 1; i < 4; i++) {
            gerBranches.addBranch(new LocalBranch("German Branch " + i, "Germany Street " + i*2));
        }
        for(int i = 1; i < 4; i++) {
            autBranches.addBranch(new LocalBranch("Austrian Branch " + i, "Austria Street " + i*2));
        }
        for(int i = 1; i < 4; i++) {
            czBranches.addBranch(new LocalBranch("Czech Branch " + i, "Czechia Street " + i*2));
        }
         
         
         // create 15 happy clients with 15 healthy accounts for first branch:
        for(int i = 0; i <= 14; i++) {
            Customer customer = new Customer(i,"first" + i, "last" + i, "addr"+i, autBranches.getBranches().get(0), "+34 610 00 "+i,100);
            Account acc = new Account(i, 5500, Account.Type.Savings, Account.Currency.Euro, 3);
            acc.setOwner(customer);
            customer.addAccount(acc);            
            autBranches.getBranches().get(0).addCustomer(customer);
            autBranches.getBranches().get(0).addAccount(acc);
        }
             
         
        // create 15 unsatisfied clients with 15 poor accounts for second branch:
        for(int i = 15; i <= 29; i++) {
            Customer customer = new Customer(i,"first" + i, "last" + i, "addr"+i, autBranches.getBranches().get(1), "+34 610 00 "+i, 30);
            Account acc = new Account(i, 5, Account.Type.Savings, Account.Currency.Euro, 1.5);
            acc.setOwner(customer);
            customer.addAccount(acc);            
            autBranches.getBranches().get(1).addCustomer(customer);
            autBranches.getBranches().get(1).addAccount(acc);
        }
          
        // more accounts for german and czech branch:            
        Account acc1 = new Account(61, 1500, Account.Type.Savings, Account.Currency.Euro, 3);
        Account acc2 = new Account(62, 3500, Account.Type.Savings, Account.Currency.Euro, 3);
        Account acc3 = new Account(63, 9500, Account.Type.Savings, Account.Currency.Euro, 3);
        Customer c1 = new Customer(61, "Jenifer" , "Las", "Street 300", gerBranches.getBranches().get(0), "+49 610 55 ", 100);
        Customer c2 = new Customer(62, "Gilbert" , "Tor", "Street 300", czBranches.getBranches().get(0), "+23 610 32 ", 100);
        Customer c3 = new Customer(63, "Nina" , "Kraus", "Street 300", czBranches.getBranches().get(0), "+23 610 44 ", 100);
        c1.addAccount(acc1); acc1.setOwner(c1);
        c2.addAccount(acc2); acc2.setOwner(c2);
        c3.addAccount(acc3); acc3.setOwner(c3);
        gerBranches.getBranches().get(0).addCustomer(c1);
        czBranches.getBranches().get(0).addCustomer(c2);
        czBranches.getBranches().get(0).addCustomer(c3);
        gerBranches.getBranches().get(0).addAccount(acc1);
        czBranches.getBranches().get(0).addAccount(acc2);
        czBranches.getBranches().get(0).addAccount(acc3);
        
        // employees for first branch:
        for(int i = 3; i <= 13; i++) {
            Employee employee = new Employee(i,"Jen", "B1 " + i, "Alcala de Henares", autBranches.getBranches().get(0),
                    Employee.Position.Employee, 1000, "Jen.Normal"+i+"@company.com");
            
            autBranches.getBranches().get(0).addEmployee(employee);
        }
        // Branch Manager for first branch:
        Employee bm4 = new Employee(6, "Branch", "Manager1", "Steet 12", autBranches.getBranches().get(0),
                    Employee.Position.BranchManager, 1000, "bm-at@company.com");
        autBranches.getBranches().get(0).addEmployee(bm4);
        // employees for second branch:
        for(int i = 14; i <= 25; i++) {
            Employee employee = new Employee(i,"Jen" + i, "Doe", "Alcala de Henares", autBranches.getBranches().get(1),
                    Employee.Position.Employee, 1000+i, "Jen.Normal"+i+"@company.com");
            
            autBranches.getBranches().get(1).addEmployee(employee);
        } 
        // Branch Manager for second branch:
         Employee bm5 = new Employee(6, "Branch", "Manager2", "Steet 34", autBranches.getBranches().get(1),
                    Employee.Position.BranchManager, 1000, "bm-at@company.com");
        autBranches.getBranches().get(1).addEmployee(bm5);

        //set LocalBranch 2 LocalBranch of BranchManager:
        UserHandler.getInstance().setBranch(autBranches.getBranches().get(1));
    }
    
    /**
     * Sets the Banks strategy corresponding to the strategy name.
     * @param strategyName String of the strategy name to set.
     */
    public void setStrategyString(String strategyName)
    {
        Strategy strategy;
        
        switch(strategyName) {
            case "Easy-going Strategy":
                strategy = new EasyStrategy();
                break;
            case "Normal Strategy":
                strategy = new NormalStrategy();
                break;
            case "Strict Strategy":
                strategy = new StrictStrategy();
                break;
            default:
                strategy = new StrictStrategy();
                break;
        }
            
        Bank.getInstance().setStrategy(strategy);
        
    }
    
    /**
     * Adds Strategy names to strategy List.
     */
    private void buildStrategyList() {
        strategyList.add("Easy-going Strategy");
        strategyList.add("Normal Strategy");
        strategyList.add("Strict Strategy");
    }
    

    /**
    * Opens a dialog to edit details for a branch.Saves changes and returns true
    if user clicks ok in the dialog.
    * 
    * @param branch object to edit
     *@param labelText
    * @return true if the user clicks OK, if not false.
    */
   public boolean showBranchEditDialog(Branch branch, String labelText) {
       try {
           // Load the fxml file and create a new stage for the popup dialog.
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(BankManagement.class.getResource("views/BranchEditDialog.fxml"));
           AnchorPane page = (AnchorPane) loader.load();

           // Create the dialog Stage:
           Stage dialogStage = new Stage();
           dialogStage.setTitle("Edit Branch");
           dialogStage.initModality(Modality.WINDOW_MODAL);
           dialogStage.initOwner(primaryStage);
           Scene scene = new Scene(page);
           dialogStage.setScene(scene);

           // Load branch into controller, set title of dialog:
           BranchEditDialogController controller = loader.getController();
           controller.setDialogStage(dialogStage);
           controller.setBranch(branch);
           controller.setTitle(labelText);
           // Shows the dialog and wait:
           dialogStage.showAndWait();
           // notify Observers:
           branchSubject.notifyObservers();
           return controller.getclickedOk();
           } catch (IOException e) {
               e.printStackTrace();
               return false;
       }
    }
   
   /**
    * Opens a dialog to edit details for an employee.Saves changes and returns true
    if user clicks ok in the dialog.
    * 
    * @param employee object to edit
    * @param user The logged in user
    * @param branchDirectory The current branch directory of the country
    * @return true if the user clicks OK, if not false.
    */
   public boolean showEmployeeEditDialog(Employee employee, Employee user, BranchDirectory branchDirectory) {
       try {
           // Load the fxml file and create a new stage for the popup dialog.
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(BankManagement.class.getResource("views/EmployeeEditDialog.fxml"));
           AnchorPane page = (AnchorPane) loader.load();

           // Create the dialog Stage:
           Stage dialogStage = new Stage();
           dialogStage.setTitle("Edit Employee");
           dialogStage.initModality(Modality.WINDOW_MODAL);
           dialogStage.initOwner(primaryStage);
           Scene scene = new Scene(page);
           dialogStage.setScene(scene);

           // Load employee into controller:
           EmployeeEditDialogController controller = loader.getController();
           controller.setDialogStage(dialogStage);
           controller.setState(user, branchDirectory);
           controller.setEmployee(employee);
           
           // Shows the dialog and wait:
           dialogStage.showAndWait();

           return controller.getclickedOk();
           } catch (IOException e) {
               e.printStackTrace();
               return false;
       }
    }
   
    /**
    * Opens a dialog to edit details for an employee.Saves changes and returns true
    if user clicks ok in the dialog.
    * 
    * @param customer object to edit
    * @param user The logged in user
    * @param branchDirectory The current branch directory of the country
    * @return true if the user clicks OK, if not false.
    */
   public boolean showCustomerEditDialog(Customer customer, Employee user, BranchDirectory branchDirectory) {
       try {
           // Load the fxml file and create a new stage for the popup dialog.
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(BankManagement.class.getResource("views/CustomerEditDialog.fxml"));
           AnchorPane page = (AnchorPane) loader.load();

           // Create the dialog Stage:
           Stage dialogStage = new Stage();
           dialogStage.setTitle("Edit Customer");
           dialogStage.initModality(Modality.WINDOW_MODAL);
           dialogStage.initOwner(primaryStage);
           Scene scene = new Scene(page);
           dialogStage.setScene(scene);

           // Load customer into controller:
           CustomerEditDialogController controller = loader.getController();
           controller.setDialogStage(dialogStage);
           controller.setState(user, branchDirectory);
           controller.setCustomer(customer);
           
           // Shows the dialog and wait:
           dialogStage.showAndWait();

           return controller.getclickedOk();
           } catch (IOException e) {
               e.printStackTrace();
               return false;
       }
    }
    
   
    /**
    * Opens a dialog to edit details for an account.Saves changes and returns true
    if user clicks ok in the dialog.
    * 
    * @param account object to edit
    * @param user The logged in user
     *@param ownerBranch LocalBranch of account owner
    * @param branchDirectory The current branch directory of the country
    * @return true if the user clicks OK, if not false.
    */
   public boolean showAccountEditDialog(Account account, Employee user, Branch ownerBranch, BranchDirectory branchDirectory) {
       try {
           // Load the fxml file and create a new stage for the popup dialog.
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(BankManagement.class.getResource("views/AccountEditDialog.fxml"));
           AnchorPane page = (AnchorPane) loader.load();

           // Create the dialog Stage:
           Stage dialogStage = new Stage();
           dialogStage.setTitle("Edit Account");
           dialogStage.initModality(Modality.WINDOW_MODAL);
           dialogStage.initOwner(primaryStage);
           Scene scene = new Scene(page);
           dialogStage.setScene(scene);

           // Load account into controller:
           AccountEditDialogController controller = loader.getController();
           controller.setDialogStage(dialogStage);
           controller.setState(user, ownerBranch, branchDirectory);
           controller.setAccount(account);
           
           // Shows the dialog and wait:
           dialogStage.showAndWait();

           return controller.getclickedOk();
           } catch (IOException e) {
               e.printStackTrace();
               return false;
       }
    }
   
   /**
    * Getter for StrategyList
    * @return The StrategyList
    */
    public ObservableList<String> getStrategyList() {
        return strategyList;
    }
      
    
}
