package bankmanagement.controllers;

import bankmanagement.composite.Branch;
import bankmanagement.composite.BranchDirectory;
import bankmanagement.models.Customer;
import bankmanagement.models.Employee;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for dialog window to edit properties of a customer.
 *
 * @author stefan
 */
public class CustomerEditDialogController implements Initializable {
    
    /**
     * The currently selected BranchDirectory, containing branches of a country
     */
    private BranchDirectory currentBranchDirectory;
    
    /**
     * The logged in user
     */
    private Employee user;
    private Stage dialogStage;
    private Customer customer;
    private boolean clickedOk = false;
    
    
    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField telephoneNrTextField;
    @FXML private TextField addressTextField;
    @FXML private ComboBox<Branch> branchCombx;
    


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          
    }

    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets customer for editing.
     * 
     * @param customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;

        firstNameTextField.setText(customer.getFirstName());
        lastNameTextField.setText(customer.getLastName());
        addressTextField.setText(customer.getAddress());
        telephoneNrTextField.setText(customer.getTelephone_nr());
        
        if(isAdmin())
            branchCombx.setItems(currentBranchDirectory.getBranches());
        
        branchCombx.getSelectionModel().select(customer.getBranch());
    }

     /**
     * Handles the action of the user clicking ok. Saves new/changed customer.
     */
    @FXML
    private void onOk() {
        if (isInputValid()) {
            customer.setFirstName(firstNameTextField.getText());
            customer.setLastName(lastNameTextField.getText());
            customer.setAddress(addressTextField.getText());
            customer.setTelephone_nr(telephoneNrTextField.getText());
            
            // only CEO can change branch:
            if(user.getPosition() != Employee.Position.CEO) {
                // set employee branch to user branch:
                if(user.getBranch() != null)
                    customer.setBranch(user.getBranch());
                
                user.getBranch().addCustomer(customer);
            }   
            else // user is CEO:
            {    // check if branch was changed, remove employee from old branch:
                if(branchCombx.getSelectionModel().getSelectedItem() != customer.getBranch() &&
                    customer.getBranch() != null)
                        customer.getBranch().removeCustomer(customer);
                
            customer.setBranch(branchCombx.getSelectionModel().getSelectedItem());
            branchCombx.getSelectionModel().getSelectedItem().addCustomer(customer);
            }
            
            clickedOk = true;
            dialogStage.close();
        }
    }
    
     /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean getclickedOk() {
        return clickedOk;
    }
    
    /**
     * Is called by the main application to set important context states.
     * Disables branch selection of employee.
     * @param user The user using the application
     * @param branchDirectory Current branch directory of country.
     */
    public void setState(Employee user, BranchDirectory branchDirectory) {
        this.user = user;
        
        if(!isAdmin()) {
            branchCombx.setDisable(true);       
        }
        this.currentBranchDirectory = branchDirectory;
    }
    
    /**
     * Handles the action of the user clicking cancel.
     */
    @FXML
    private void onCancel() {
        dialogStage.close();
    }
    
    /**
     * Checks if input values are valid and displays an error if not.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMsg = "";
        
        if (firstNameTextField.getText() == null || firstNameTextField.getText().length() == 0) 
            errorMsg += "No valid first name!\n"; 
         
        if (lastNameTextField.getText() == null || lastNameTextField.getText().length() == 0) 
            errorMsg += "No valid last name!\n"; 
        
        if (addressTextField.getText() == null || addressTextField.getText().length() == 0) 
            errorMsg += "No valid address!\n"; 
          
        if (telephoneNrTextField.getText() == null || telephoneNrTextField.getText().length() == 0) 
            errorMsg += "No valid telephone nr!\n"; 
        
        if (isAdmin() && branchCombx.getSelectionModel().isEmpty())
            errorMsg += "No valid branch!\n";
        
        
        if (errorMsg.length() == 0) {
            return true;
        } else {
            // Display error:
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Incorrect input");
            alert.setHeaderText("Please change input");
            alert.setContentText(errorMsg);
            
            alert.showAndWait();
            
            return false;
        }
    }
    
    /**
     * Determines if user is admin.
     * @return True if user is CEO, false if not.
     */
    private boolean isAdmin() {
        return user.getPosition() == Employee.Position.CEO;
    }
    
}
