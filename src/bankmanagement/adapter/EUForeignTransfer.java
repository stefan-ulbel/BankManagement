package bankmanagement.adapter;

import bankmanagement.models.Account;

/**
 * Sends money to a bank using an IBAN.
 * @author stefan
 */
public class EUForeignTransfer {
    
    /**
     * Contacts bank and sends money with IBAN account format.
     * @param currency The used currency
     * @param amount Amount of money to send
     * @param iban The IBAN of the receiver account
     * @return True if successfull, false otherwise.
     */
    public boolean sendMoney(Account.Currency currency, double amount, String iban) {
        // Send money and return result...
        if(contactBank(currency, amount, iban))
            return true;
        else
            return false;
    }
    
    /**
     * Virtually "contacts a bank". Placeholder for a real function contacting
     * a bank.
     * @param currency The currency
     * @param amount Amount of money to send
     * @param iban The IBAN of the account
     * @return True
     */
    private boolean contactBank(Account.Currency currency, double amount, String iban) {
        return true;
    }
    
}
