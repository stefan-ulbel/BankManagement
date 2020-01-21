package bankmanagement.observer;

import bankmanagement.controllers.MainController;

/**
 * This observer observes a subject / branch subject and calls to update
 * the corresponding view. 
 * @author stefan
 */
public class BranchObserver implements Observer {
    
    private Subject subject;
    private MainController mainController;
    
    /**
     * Constructor for BranchObserver. Sets necessary fields.
     * @param subject Subject to observe.
     * @param mainController Reference to mainController.
     */
    public BranchObserver(Subject subject, MainController mainController) {
        this.mainController = mainController;
        this.subject = subject;
        subject.attachObserver(this);
    }

    @Override
    public void update() {
        if(mainController != null)
            mainController.updateBranch();
    }
    
    
    
}
