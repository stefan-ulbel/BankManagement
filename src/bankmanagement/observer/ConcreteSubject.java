package bankmanagement.observer;

import bankmanagement.composite.BranchDirectory;
import java.util.ArrayList;

/**
 * ConrecreteSubject, holds as value a BranchDirectory and has observers
 * attached to it.
 * @author stefan
 */
public class ConcreteSubject implements Subject {
    // List of observers:
    private ArrayList<Observer> observers = new ArrayList<>();
    //Monitored value:
    private BranchDirectory value;
    
    
    @Override
    public void setValue(BranchDirectory value) {
        this.value = value;
    }

    @Override
    public BranchDirectory getValue() {
        return value;

    }

    @Override
    public void attachObserver(Observer observer) {
        if(observers.contains(observer) == false)
            observers.add(observer);
        
    }

    @Override
    public void detachObserver(Observer observer) {
        observers.remove(observer);
        
    }

    @Override
    public void notifyObservers() {
        Object[] obArray = observers.toArray();

        for (int i = 0; i < obArray.length; ++i) {
            Observer observer = (Observer) obArray[i];
            observer.update();
        }
        
    }
    
}
