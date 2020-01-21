package bankmanagement.controllers;

import bankmanagement.BankManagement;
import bankmanagement.singletons.UserHandler;
import bankmanagement.models.Employee;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Handles login interaction with user.
 * @author stefan
 */
public class LoginController implements Initializable {
    
    private BankManagement bankManagement;
    
    @FXML
    private TextField username;
    
    @FXML
    private PasswordField password;
    
    @FXML
    private Label labelInfo;
  
    @FXML
    private Button loginButton;
    
    
    /**
     * Handles login button press, calls show main overview method or shows
     * incorrect password-username information label.
     * @param event 
     */
    @FXML
    private void onLoginButton(ActionEvent event) {        
        
        Employee user = UserHandler.getInstance().authenticate(username.getText(), password.getText());
        if(user != null)
        {  
            // change view:
            bankManagement.showMainOverview(user);
        }
        else
            labelInfo.setVisible(true);
        
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
     /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param bankManagement
     */
    public void setBankManagement(BankManagement bankManagement) {
        this.bankManagement = bankManagement;
    }    
    
}
