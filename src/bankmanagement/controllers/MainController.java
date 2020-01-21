package bankmanagement.controllers;
import bankmanagement.BankManagement;
import bankmanagement.builder.BranchBuilder;
import bankmanagement.builder.BranchDirectoryBuilder;
import bankmanagement.builder.BranchStructureDirector;
import bankmanagement.builder.LocalBranchBuilder;
import bankmanagement.models.Account;
import bankmanagement.singletons.Bank;
import bankmanagement.composite.Branch;
import bankmanagement.composite.BranchDirectory;
import bankmanagement.models.Customer;
import bankmanagement.models.Employee;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


/**
 * FXML Controller class
 * Handles interaction with user in MainView and correct display of data.
 * @author stefan
 */
public class MainController implements Initializable {

    /**
     * Reference to bankManagement app.
     */
    private BankManagement bankManagement;
    /**
     * The logged in user using the application. Needed to determine position.
     */
    private Employee user;
    /**
     * The currently selected BranchDirectory, containing branches of a country
     */
    private BranchDirectory currentBranchDirectory;
    
    
    // FXML GUI Fields:
    @FXML private TableView<Branch> branchTable;
    @FXML private TableColumn<Branch, String> branchNameColumn;
    @FXML private TableColumn<Branch, String> branchAddressColumn;
    @FXML private TableView<Employee> employeeTable;
    @FXML private TableColumn<Employee, String> empFirstNameColumn;
    @FXML private TableColumn<Employee, String> empLastNameColumn;
    @FXML private TableColumn<Employee, Integer> empSalaryColumn;
    @FXML private TableColumn<Employee, Employee.Position> empPositionColumn;
    @FXML private ComboBox<Branch> cusBranchCombx;
    @FXML private ComboBox<Branch> accBranchCombx;
    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, String> cusFirstNameColumn;
    @FXML private TableColumn<Customer, String> cusLastNameColumn;
    @FXML private TableColumn<Customer, Integer> cusSatisfactionColumn;
    @FXML private TableColumn<Customer, Double> cusBalanceColumn;
    @FXML private Tab transferTab;
    @FXML private Tab branchTab;
    @FXML private Tab fundingTab;
    @FXML private ComboBox<String> strategyCombx;
    @FXML private ComboBox<Branch> empBranchCombx;
    @FXML private Label alertBranchLabel;
    @FXML private Label alertEmployeeLabel;
    @FXML private Label alertCustomerLabel;
    @FXML private Label alertAccountLabel;
    @FXML private Button branchmanagertEditBranchButton;   
    @FXML private ComboBox<Branch> branchDirCombx;
    @FXML private Button newLocalBranchButton;
    @FXML private Button editLocalBranchButton;
    @FXML private Button newCountryBranchButton;
    @FXML private Button editCountryBranchButton;
    @FXML private Button deleteCountryBranchButton;
    @FXML private TableView<Branch> fundingTable;
    @FXML private TableColumn<Branch, String> funBranchColumn;
    @FXML private TableColumn<Branch, Double> funBalanceColumn;
    @FXML private TableView<Account> accountTable;
    @FXML private TableColumn<Account, Customer> accOwnerColumn;
    @FXML private TableColumn<Account, Double> accBalanceColumn;
    @FXML private TableColumn<Account, Account.Currency> accCurrencyColumn;
    @FXML private TableColumn<Account, Double> accInterestColumn;
    @FXML private TextField traAccnrTextField;
    @FXML private Slider traAmountSlider;
    @FXML private Label traAmountLabel;
    @FXML private ComboBox<Account.Currency> traCurrencyCombx;
    @FXML private Label traBankBalanceLabel;
    
    
    public MainController() {
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // link table columns with corresponding properties:
        branchNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        branchAddressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        
        empFirstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        empLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        empSalaryColumn.setCellValueFactory(cellData -> cellData.getValue().salaryProperty().asObject());
        empPositionColumn.setCellValueFactory(cellData -> cellData.getValue().positionProperty());     
        
        cusFirstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        cusLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        cusSatisfactionColumn.setCellValueFactory(cellData -> cellData.getValue().satisfactionProperty().asObject());
        cusBalanceColumn.setCellValueFactory(cellData -> cellData.getValue().balanceProperty().asObject());
        
        funBranchColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        funBalanceColumn.setCellValueFactory(cellData -> cellData.getValue().balanceProperty().asObject());
        
        accOwnerColumn.setCellValueFactory(cellData -> cellData.getValue().ownerProperty());
        accBalanceColumn.setCellValueFactory(cellData -> cellData.getValue().balanceProperty().asObject());
        accCurrencyColumn.setCellValueFactory(cellData -> cellData.getValue().currencyProperty());    
        accInterestColumn.setCellValueFactory(cellData -> cellData.getValue().interestRateProperty().asObject());
        
        traCurrencyCombx.setItems(FXCollections.observableArrayList(Account.Currency.values()));
        traAmountLabel.textProperty().bind(
            Bindings.format(
                "%.2f",
                traAmountSlider.valueProperty()
            )
        );
        
    }

    /**
     * Handles clicks on refresh button.
     */
    @FXML
    private void onRefreshClick(ActionEvent event) {
        System.out.println("Refreshing");
    }
    
    /**
     * Handles click on start strategy button, calls to execute the selected
     * strategy.
     */
    @FXML
    private void onStrategyButton(ActionEvent event) {        
        Branch branch = branchTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Strategy result");
        // Set result of strategy execution to alert content:   
        if(branch != null)
           alert.setHeaderText(Bank.getInstance().executeStrategy(branch));
       else
           alert.setHeaderText("Please select a branch");
       // show alert:
       alert.showAndWait();
            
                
    }
   
    /**
     * Handles changes of strategy combo box selection.
     */
    @FXML
    private void onStrategyComboChange(ActionEvent event) {
        bankManagement.setStrategyString(strategyCombx.getSelectionModel().getSelectedItem());
    }
    
    /**
     * Handles changes of branch combo box selection in the employee tab
     * to display employees of selected branch.
     */
    @FXML
    private void onEmpBranchComboChange(ActionEvent event) {
        // change employee data according to branch:
        if(!empBranchCombx.getSelectionModel().isEmpty())
            employeeTable.setItems(empBranchCombx.getValue().getEmployees());
    }

    /**
     * Handles changes of branch combo box selection in the customer tab
     * to display customers of selected branch.
     */
    @FXML
    private void onCusBranchComboChange(ActionEvent event) {
        // change customer data according to branch:
        if(!cusBranchCombx.getSelectionModel().isEmpty())
            customerTable.setItems(cusBranchCombx.getValue().getCustomers());
    }
    
    /**
     * Handles changes of branch combo box selection in the account tab
     * to display accounts of selected branch.
     */
    @FXML
    private void onAccBranchComboChange(ActionEvent event) {
        // change account data according to branch:
        if(!accBranchCombx.getSelectionModel().isEmpty())
            accountTable.setItems(accBranchCombx.getValue().getAccounts());
    }
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param bankManagement The bank management application
     */
    public void setBankManagement(BankManagement bankManagement) {
        this.bankManagement = bankManagement;
    }
    
    /**
     * Fills up tables and combo boxes.
     *
     */
    public void setData() {
        // Set current Branch Directory to root branch directory:
            this.currentBranchDirectory = (BranchDirectory) Bank.getInstance().getRootBranchDirectory().getBranches().get(0); 
        if(isAdmin()) {
            branchDirCombx.setItems(Bank.getInstance().getRootBranchDirectory().getBranches());
            
            accountTable.setItems(currentBranchDirectory.getAccounts());
        }
        else {
            if(user.getBranch() != null) {
                accountTable.setItems(user.getBranch().getAccounts());
                employeeTable.setItems(user.getBranch().getEmployees());
                customerTable.setItems(user.getBranch().getCustomers());
            }
        }
        
        // Add branch data to the table:
        branchTable.setItems(currentBranchDirectory.getBranches());
        //Connect list to strategy names to dropdown menu/combo box and selected default:
        strategyCombx.setItems(bankManagement.getStrategyList());
        strategyCombx.getSelectionModel().select(1);
        bankManagement.setStrategyString(strategyCombx.getSelectionModel().getSelectedItem());
        branchDirCombx.setValue(currentBranchDirectory);
        
        fundingTable.setItems(Bank.getInstance().getRootBranchDirectory().getBranches());
        traBankBalanceLabel.setText(String.valueOf(Bank.getInstance().getBalance()));
        
    }
    
     /**
     * Is called by the main application to set the user using the application.
     * Hides and enables view items for admin.
     * @param user The user using the application
     */
    public void setUser(Employee user) {
        this.user = user;
        
        if(isAdmin()) {
            // enable admin, branch and funding tab:
            transferTab.setDisable(false);
            branchTab.setDisable(false);
            fundingTab.setDisable(false);
            // hide branch manager branch-edit button:
            branchmanagertEditBranchButton.setVisible(false);        
            // show country branch delete button:
            deleteCountryBranchButton.setVisible(true);
            // enable county branch directory selector:
            branchDirCombx.setDisable(false);
        }            
    }

    
    /**
    * Reloads branch data into combo boxes and tables.
    * Non-CEOs only see their own branch. Called by observer.
    */
    public void updateBranch() {
        branchDirCombx.setItems(null);
        branchDirCombx.setItems(Bank.getInstance().getRootBranchDirectory().getBranches());
        branchDirCombx.getSelectionModel().select(currentBranchDirectory);
        refreshFunding();
        if(isAdmin()) {
            // reload combo boxes:
            empBranchCombx.setItems(null);
            empBranchCombx.setItems(currentBranchDirectory.getBranches());
            
            cusBranchCombx.setItems(null);
            cusBranchCombx.setItems(currentBranchDirectory.getBranches());
            
            accBranchCombx.setItems(null);
            accBranchCombx.setItems(currentBranchDirectory.getBranches());
            
        }            
        else if(user.getBranch() != null) // non-ceo action
        {
            empBranchCombx.setValue(null);
            empBranchCombx.setValue(user.getBranch());
            
            
            cusBranchCombx.setValue(null);
            cusBranchCombx.setValue(user.getBranch());
            
            accBranchCombx.setValue(null);
            accBranchCombx.setValue(user.getBranch());
        }   
    }
    
    /**
     * Updates tables when Country Branch Directory is changed.
     * combo box changes.
     * @param event 
     */
    @FXML
    private void onBranchDirComboChange(ActionEvent event) {
        // get new selected branch directory:        
        if(branchDirCombx.getSelectionModel().getSelectedItem() != null)
            currentBranchDirectory = (BranchDirectory) branchDirCombx.getSelectionModel().getSelectedItem(); 
        
        // fill tables with local data:
        empBranchCombx.setItems(currentBranchDirectory.getBranches());
        
         if(!empBranchCombx.getSelectionModel().isEmpty()) {
             empBranchCombx.getSelectionModel().select(0);
             employeeTable.setItems(empBranchCombx.getValue().getEmployees());
         } else
            employeeTable.setItems(null);
         
        
        cusBranchCombx.setItems(currentBranchDirectory.getBranches());
        cusBranchCombx.getSelectionModel().select(0);
        
        accBranchCombx.setItems(currentBranchDirectory.getBranches());
        accBranchCombx.getSelectionModel().select(0);
        
        if(!accBranchCombx.getSelectionModel().isEmpty())
            accountTable.setItems(accBranchCombx.getValue().getAccounts());
        else
            accountTable.setItems(null);
        
        if(!cusBranchCombx.getSelectionModel().isEmpty())
            customerTable.setItems(cusBranchCombx.getValue().getCustomers());
        else
            customerTable.setItems(null);
        
        branchTable.setItems(currentBranchDirectory.getBranches());
    }
    
    /**
     * Handles creation and editing of local branches and country branches.
     * Gets called by for different buttons.
     * @param event 
     */
    @FXML
    private void onBranchButtons(ActionEvent event) {
        if (event.getSource() instanceof Button) {
        Button clickedButton = (Button) event.getSource();
        // The branch parent:
        BranchDirectory base;
        // Create director to build branch:                                                     
        BranchStructureDirector director = new BranchStructureDirector();
        // The Builder:
        BranchBuilder builder;
        
        alertBranchLabel.setText(""); // Remove alert warnings if existed
        if (clickedButton.equals(newLocalBranchButton)) {
            // create a new local branch
            // set base to selected country branch:
            base = currentBranchDirectory;            
            // Use a new local branch builder:
            builder = new LocalBranchBuilder();
            // Set builder of director:
            director.setBranchBuilder(builder);        
            // create new branch and save:
            director.createBranch(null, base, bankManagement);
            
        } else if (clickedButton.equals(editLocalBranchButton) || clickedButton.equals(branchmanagertEditBranchButton)) {
            // edit an existing local branch            
            Branch branch;
            // check if branch selected and user is admin
            if(branchTable.getSelectionModel().getSelectedItem() == null && isAdmin()) {
                alertBranchLabel.setText("Please select a branch");
                return;
            }
            // Non-CEOs only edit their own branch:
            if(isAdmin())
                branch = branchTable.getSelectionModel().getSelectedItem();
            else
                branch = user.getBranch();
            // set base to selected country branch:
            base = currentBranchDirectory;            
            // Use a new local branch builder:
            builder = new LocalBranchBuilder();
            // Set builder of director:
            director.setBranchBuilder(builder);        
            // edit branch and save:
            director.createBranch(branch, base, bankManagement);
            
            // set combo box items null again if not admin:
            if(!isAdmin()) {
                empBranchCombx.setItems(null);
                empBranchCombx.setValue(user.getBranch());
                cusBranchCombx.setItems(null);
                cusBranchCombx.setValue(user.getBranch());
                accBranchCombx.setItems(null);
                accBranchCombx.setValue(user.getBranch());
            }
                
            
        } else if (clickedButton.equals(newCountryBranchButton)) {
            // create new country branch directory
            // Use a new local branch builder:
            builder = new BranchDirectoryBuilder();
            // Set builder of director:
            director.setBranchBuilder(builder);        
            // create new branch and save:
            director.createBranch(null, null, bankManagement);
            
        } else if(clickedButton.equals(editCountryBranchButton)) {
            // edit an existing country branch directory
            // Use a new local branch builder:
            builder = new BranchDirectoryBuilder();
            // Set builder of director:
            director.setBranchBuilder(builder);        
            // create new branch and save:
            director.createBranch(currentBranchDirectory, null, bankManagement);
        }
    }
    }
    
    
    /**
    * Handles the click on the delete local branch button
    */
   @FXML
   private void onDeleteLocalBranch() {
       int selectedIndex = branchTable.getSelectionModel().getSelectedIndex();
       if (selectedIndex >= 0) {
           Branch branch = branchTable.getSelectionModel().getSelectedItem(); //LocalBranch
           // remove association of employees and customers:
           branch.deleteBranch();
           // remove branch from list:
           branchTable.getItems().remove(selectedIndex);
           
       } else {
           // Nothing selected.
           alertBranchLabel.setText("Please select a branch");
       }
   }
   
   /**
    * Handles the click on the delete country branch button. Deletes current
    * country branch if there is more than one country branch.
    */
   @FXML
   private void onDeleteCountryBranch() {
       if(Bank.getInstance().getRootBranchDirectory().getBranches().size() > 1) {
        currentBranchDirectory.removeAllBranches();
       Bank.getInstance().getRootBranchDirectory().removeBranch(currentBranchDirectory);
       branchDirCombx.getSelectionModel().select(0);
       }
   }

    
    /**
    * Handles the click on the delete employee button. Removes employee from table.
    */
   @FXML
   private void onDeleteEmployee() {
       int selectedIndex = employeeTable.getSelectionModel().getSelectedIndex();
       if (selectedIndex >= 0) {
           // get employee:
           Employee employee = employeeTable.getSelectionModel().getSelectedItem();
           // remove from branch:
           employee.getBranch().removeEmployee(employee);           
       } else {
           // Nothing selected.
           alertEmployeeLabel.setText("Please select an employee");
       }
   }
    
    /**
    * Called when the user clicks the new employee button. Calls to open a dialog
    * to edit details for a new employee.
    */
   @FXML
   private void onNewEmployee() {
       Employee newEmployee = new Employee();
       bankManagement.showEmployeeEditDialog(newEmployee, user, currentBranchDirectory);      
       
   }

    /**
     * Called when edit button clicked. Opens a dialog to edit
     * the selected employee.
     */
    @FXML
    private void onEditEmployee() {
        Employee employee = employeeTable.getSelectionModel().getSelectedItem();
        if (employee != null) {
            bankManagement.showEmployeeEditDialog(employee, user, currentBranchDirectory);
        } else {
            alertEmployeeLabel.setText("Please select an employee");
        }
    }
    
    /**
    * Handles the click on the delete customer button. Removes customer from table.
    */
   @FXML
   private void onDeleteCustomer() {
       int selectedIndex = customerTable.getSelectionModel().getSelectedIndex();
       if (selectedIndex >= 0) {
           // get customer:
           Customer customer = customerTable.getSelectionModel().getSelectedItem();
           // remove custmer and accounts from branch:
           customer.removeAccounts();
           customer.getBranch().removeCustomer(customer);           
       } else {
           // Nothing selected.
           alertBranchLabel.setText("Please select a customer");
       }
   }
    
    /**
    * Called when the user clicks the new customer button. Calls to open a dialog
    * to edit details for a new customer.
    */
   @FXML
   private void onNewCustomer() {
       Customer newCustomer = new Customer();
       bankManagement.showCustomerEditDialog(newCustomer, user, currentBranchDirectory);      
       
   }

    /**
     * Called when edit customer button clicked. Opens a dialog to edit
     * the selected customer.
     */
    @FXML
    private void onEditCustomer() {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        if (customer != null) {
            bankManagement.showCustomerEditDialog(customer, user, currentBranchDirectory);
        } else {
            alertCustomerLabel.setText("Please select a customer");
        }
    }
    
    
    
    /**
    * Handles the click on the delete account button. Removes customer from table.
    */
   @FXML
   private void onDeleteAccount() {
       int selectedIndex = accountTable.getSelectionModel().getSelectedIndex();
       
       Branch branch;
       if(isAdmin())
            branch = accBranchCombx.getSelectionModel().getSelectedItem();
       else
           branch = user.getBranch();
       
       if (selectedIndex >= 0 && branch != null) {
           // get account:
           Account account = accountTable.getSelectionModel().getSelectedItem();
           // remove from branch:
           branch.removeAccount(account);           
       } else {
           // Nothing selected
           alertBranchLabel.setText("Please select an account and branch");
       }
   }
    
    /**
    * Called when the user clicks the new account button. Calls to open a dialog
    * to edit details for a new account.
    */
   @FXML
   private void onNewAccount() {
       Account newAccount = new Account();
       Branch branch = accBranchCombx.getSelectionModel().getSelectedItem();
       if(branch != null)
            bankManagement.showAccountEditDialog(newAccount, user, branch, currentBranchDirectory);    
       else
           alertAccountLabel.setText("Please select a branch first");
       
   }

    /**
     * Called when edit account button clicked. Opens a dialog to edit
     * the selected account.
     */
    @FXML
    private void onEditAccount() {
        Account account = accountTable.getSelectionModel().getSelectedItem();
        Branch branch = accBranchCombx.getSelectionModel().getSelectedItem();
        
        if (account != null && branch != null) {
            bankManagement.showAccountEditDialog(account, user, branch, currentBranchDirectory);
        } else {
            alertAccountLabel.setText("Please select an account and branch");
        }
    }
    
    
    /**
    * Called when the user clicks the liquidity check button. Calls the liquidity check
    * on selected branch and displays an alert.
    */
   @FXML
   private void onLiquidityCheckButton() {
    // get selected branch:   
    BranchDirectory branch = (BranchDirectory) fundingTable.getSelectionModel().getSelectedItem();
    // create the alert:   
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Liquidity check");
    // Set result of liquidity check to alert content:        
       if(branch != null)
           alert.setHeaderText(branch.liquidityCheck());
       else
           alert.setHeaderText("Please select a branch");
       // show alert:
       alert.showAndWait();
   }
   
   /**
    * Refreshes the funding table in funding tab.
    */
   @FXML
   private void onFundingRefresh() {
       refreshFunding();
   }
   
   /** 
    * Calculates funding for each branch in root branch directory.
    */
   private void refreshFunding() {
       for(Branch b : Bank.getInstance().getRootBranchDirectory().getBranches())
           b.calculateFunding();
   }
   
   /**
    * Handle click on send funds button. Collects values and sends funds.
    */
   @FXML
   private void onSendFunds() {
       // check if correct input
       if(traCurrencyCombx.getSelectionModel().isEmpty() || 
               (traAccnrTextField.getText() == null || traAccnrTextField.getText().length() == 0) ) {
           // wrong input, display error:
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect input");
            alert.setHeaderText("Please change input");
            alert.setContentText("Please provide receiver, amount and currency");
            alert.showAndWait();
       }
       else { // good input, gather properties:
           String accnr = traAccnrTextField.getText();
            Account.Currency curr = traCurrencyCombx.getValue();
            double amount = Math.round(traAmountSlider.getValue() * 100) / 100;
            // send funds:
            String result = Bank.getInstance().sendFunds(accnr, amount, curr);
            // display result in alert:
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Sent funds");
            alert.setContentText(result);
            alert.showAndWait();
            traBankBalanceLabel.setText(String.valueOf(Bank.getInstance().getBalance()));
       }
       
   }
    
    
    /**
     * Utility function to determine quickly if logged in user is CEO / admin.
     * @return True if user is CEO, false otherwise.
     */    
    private boolean isAdmin() {
        return (user.getPosition() == Employee.Position.CEO);
    }
    
}
