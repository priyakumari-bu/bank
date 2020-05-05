import java.util.Date;

// Public class for a Transfer
// Extends the Transaction abstract class

public class Transfer extends Transaction {

    // additional data member for transfers
    private Account accountTo;

    // class constructor for a transfer
    public Transfer(Account account, BankUser user, Currency value, Date date, Account accountTo) {
        super(account, user, value, date);
        this.accountTo = accountTo;
    }

    // returns destination account for the transfer
    public Account getAccountTo() {
        return accountTo;
    }

    // Transfer toString() method
    public String toString() {
        return "TRANSFER TO " + accountTo.toString() + "" + super.toString();
    }

}