package bankmanagement.controllers;

import bankmanagement.composite.Branch;
import bankmanagement.composite.BranchDirectory;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for dialog window to edit properties of an employee.
 *
 * @author stefan
 */
public class EmployeeEditDialogController implements Initializable {
    
    /**
     * The currently selected BranchDirectory, containing branches of a country
     */
    private BranchDirectory currentBranchDirectory;
    
    /**
     * The logged in user
     */
    private Employee user;
    private Stage dialogStage;
    private Employee employee;
    private boolean clickedOk = false;
    
    
    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField emailTextField;
    @FXML private Slider salarySlider;
    @FXML private Label salaryLabel;
    @FXML private ComboBox<Employee.Position> positionCombx;
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
     * Sets employee for editing.
     * 
     * @param employee
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;

        firstNameTextField.setText(employee.getFirstName());
        lastNameTextField.setText(employee.getLastName());
        addressTextField.setText(employee.getAddress());
        emailTextField.setText(employee.getEmail());
        salarySlider.setValue(employee.getSalary());
        salaryLabel.setText(Double.toString(salarySlider.getValue()));
        // bind slider label to slider value so changes are visible immediately:
        salaryLabel.textProperty().bind(
            Bindings.format(
                "%.2f",
                salarySlider.valueProperty()
            )
        );
        
        branchCombx.setItems(currentBranchDirectory.getBranches());
        branchCombx.getSelectionModel().select(employee.getBranch());
        positionCombx.setItems(FXCollections.observableArrayList(Employee.Position.values()));
        positionCombx.getSelectionModel().select(employee.getPosition());
    }

     /**
     * Handles the action of the user clicking ok. Saves new/changed employee.
     */
    @FXML
    private void onOk() {
        if (isInputValid()) {
            employee.setFirstName(firstNameTextField.getText());
            employee.setLastName(lastNameTextField.getText());
            employee.setAddress(addressTextField.getText());
            employee.setPosition(positionCombx.getValue());
            employee.setEmail(emailTextField.getText());
            employee.setSalary((int) salarySlider.getValue());
            
            // only CEO can change branch:
            if(user.getPosition() != Employee.Position.CEO) {
                // set employee branch to user branch:
                if(user.getBranch() != null)
                    employee.setBranch(user.getBranch());
                
                user.getBranch().addEmployee(employee);
            }   
            else // user is CEO:
            {    // check if branch was changed, remove employee from old branch:
                if(branchCombx.getSelectionModel().getSelectedItem() != employee.getBranch() &&
                    employee.getBranch() != null)
                        employee.getBranch().removeEmployee(employee);
                
                employee.setBranch(branchCombx.getSelectionModel().getSelectedItem());
                branchCombx.getSelectionModel().getSelectedItem().addEmployee(employee);
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
        
        if(user.getPosition() != Employee.Position.CEO) {
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
          
        if (emailTextField.getText() == null || emailTextField.getText().length() == 0) 
            errorMsg += "No valid email!\n"; 
        
        if (user.getPosition() == Employee.Position.CEO && branchCombx.getSelectionModel().isEmpty())
            errorMsg += "No valid branch!\n";
        
        if(positionCombx.getSelectionModel().isEmpty())
            errorMsg += "No valid position!\n";
        

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
