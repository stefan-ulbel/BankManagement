package bankmanagement.adapter;

import bankmanagement.models.Account;

/**
 * Adapts EUForeignTransfer to fit the InternationalTransfer Interface.
 * @author stefan
 */
public class InternationalTransferAdapter implements InternationalTransfer{
    
    private EUForeignTransfer transfer; // the adaptee

    public InternationalTransferAdapter(EUForeignTransfer transfer) {
        this.transfer = transfer;
    }
    
    
    @Override
    public boolean makeTransaction(String accountNr, double amount, Account.Currency currency) {
        // Adapt account nr:
        String iban = "AT11" + accountNr;
        // call adaptee method:
        return (transfer.sendMoney(currency, amount, iban));
       
    }
    
}
