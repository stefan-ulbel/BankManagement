package bankmanagement.controllers;

import bankmanagement.models.Account;
import bankmanagement.composite.Branch;
import bankmanagement.composite.BranchDirectory;
import bankmanagement.models.Customer;
import bankmanagement.models.Employee;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

/**
 * Controller for dialog window to edit properties of an account.
 *
 * @author stefan
 */
public class AccountEditDialogController implements Initializable {
    
    /**
     * The currently selected BranchDirectory, containing branches of a country
     */
    private BranchDirectory currentBranchDirectory;
    
    /**
     * The logged in user
     */
    private Employee user;
    private Stage dialogStage;
    private Account account;
    private Branch ownerBranch;
    private boolean clickedOk = false;
    
    // FXML fields:
    @FXML private ComboBox<Customer> ownerCombx;          
    @FXML private Slider balanceSlider;
    @FXML private Label balanceLabel;
    @FXML private Slider interestSlider;
    @FXML private Label interestLabel;
    @FXML private ComboBox<Account.Type> typeCombx;
    @FXML private ComboBox<Account.Currency> currencyCombx;
    


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
     * Sets account for editing. Sets text fields and combo boxes.
     * 
     * @param account
     */
    public void setAccount(Account account) {
        this.account = account;

        ownerCombx.setItems(ownerBranch.getCustomers());
        ownerCombx.setValue(account.getOwner());
        
        typeCombx.setItems(FXCollections.observableArrayList(Account.Type.values()));
        typeCombx.getSelectionModel().select(account.getType());
        currencyCombx.setItems(FXCollections.observableArrayList(Account.Currency.values()));
        currencyCombx.getSelectionModel().select(account.getCurrency());
        
        balanceSlider.setValue(account.getBalance());
        balanceLabel.setText(Double.toString(balanceSlider.getValue()));
        // bind balance label to slider value so changes are visible immediately:
        balanceLabel.textProperty().bind(
            Bindings.format(
                "%.2f",
                balanceSlider.valueProperty()
            )
        );

        interestSlider.setValue(account.getInterest_rate());
        interestLabel.setText(Double.toString(interestSlider.getValue()));
        // bind interest label to slider value so changes are visible immediately:
        interestLabel.textProperty().bind(
            Bindings.format(
                "%.2f",
                interestSlider.valueProperty()
            )
        );
       
        
    }

     /**
     * Handles the action of the user clicking ok. Saves new/changed employee.
     */
    @FXML
    private void onOk() {
        if (isInputValid()) { 
            account.setOwner(ownerCombx.getValue());
            account.setBalance((int) balanceSlider.getValue());
            account.setType(typeCombx.getValue());
            account.setCurrency(currencyCombx.getValue());
            account.setInterest_rate(interestSlider.getValue());
            ownerCombx.getValue().addAccount(account);
            ownerBranch.addAccount(account);
            
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
     * Is called by the main application to set important context states.Disables branch selection of employee.
     * @param user The user using the application
     * @param ownerBranch Branch of account owner
     * @param branchDirectory Current branch directory of country.
     */
    public void setState(Employee user, Branch ownerBranch, BranchDirectory branchDirectory) {
        this.user = user;
        this.ownerBranch = ownerBranch;
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
        
        if (ownerCombx.getSelectionModel().isEmpty())
            errorMsg += "No valid owner!\n";
        
        if(typeCombx.getSelectionModel().isEmpty())
            errorMsg += "No valid type!\n";
        
        if(currencyCombx.getSelectionModel().isEmpty())
            errorMsg += "No valid currency!\n";
        

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
    
}
