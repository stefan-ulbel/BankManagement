package bankmanagement.controllers;

import bankmanagement.composite.Branch;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for dialog window to edit properties of a branch.
 *
 * @author stefan
 */
public class BranchEditDialogController implements Initializable {
    
    @FXML private TextField nameTextField;
    @FXML private TextField addressTextField;
    @FXML private Label titleLabel;
    
    private Stage dialogStage;
    private Branch branch;
    private boolean clickedOk = false;

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
     * Sets branch for editing.
     * 
     * @param branch
     */
    public void setBranch(Branch branch) {
        this.branch = branch;

        nameTextField.setText(branch.getName());
        addressTextField.setText(branch.getAddress());
    }    
    
    /**
     * Sets title of dialog.
     * @param title String to set title to.
     */
    public void setTitle(String title) {
        titleLabel.setText(title);
    }
    
    /**
     * Handles the action of the user clicking ok.
     */
    @FXML
    private void onOk() {
        if (isInputValid()) {
            branch.setName(nameTextField.getText());
            branch.setAddress(addressTextField.getText());
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
     * Handles the action of the user clicking cancel.
     */
    @FXML
    private void onCancel() {
        dialogStage.close();
    }
    
    /**
     * Checks if input values are valid. Displays an error message if not.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMsg = "";

        if (nameTextField.getText() == null || nameTextField.getText().length() == 0) {
            errorMsg += "No valid first name!\n"; 
        }
        if (addressTextField.getText() == null || addressTextField.getText().length() == 0) {
            errorMsg += "No valid address!\n"; 
        }

     

        if (errorMsg.length() == 0) {
            return true;
        } else {
            // Display error:
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Incorrect input");
            alert.setHeaderText("Please change input");
            alert.setContentText(errorMsg);
            
            alert.showAndWait();
            
            return false;
        }
    }
    
    
    
}
