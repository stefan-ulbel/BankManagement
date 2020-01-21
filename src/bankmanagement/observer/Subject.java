package bankmanagement.observer;

import bankmanagement.composite.BranchDirectory;

/**
 *
 * @author stefan
 */
public interface Subject {
    
    /**
     * Set value of subject.
     * @param value The BranchDirectory to hold.
     */
    public void setValue(BranchDirectory value);
    
    /**
     * Get current value.
     * @return The branch directory.
     */
    public BranchDirectory getValue();
    
    /**
     * Add observer to list.
     * @param observer The observer.
     */
    public void attachObserver(Observer observer);
    
    /**
     * Remove observer from list.
     * @param observer The observer to remove.
     */
    public void detachObserver(Observer observer);
    
    /**
     * Notifies all observers in list.
     */
    public void notifyObservers();
    
}
