package bankmanagement.adapter;

import bankmanagement.models.Account;

/**
 * Interface to send money to a bank using the account number.
 * @author stefan
 */
public interface InternationalTransfer {
    
    /**
     * Executes a transaction.
     * @param accountNr The receiver account in account format.
     * @param amount Amount of money to send.
     * @param currency Type of currency to send in.
     * @return String indicating success or failure.
     */
    public boolean makeTransaction(String accountNr, double amount, Account.Currency currency);
    
}
