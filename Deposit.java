
import java.util.Date;

// Public class for a Deposit
// Extends the Transaction class

public class Deposit extends Transaction {

    // class constructor for a deposit
    public Deposit(Account account, BankUser user, Currency value, Date date) {
        super(account, user, value, date);
    }

    // Deposit toString() method
    public String toString() {
        return "DEPOSIT " + super.toString();
    }

}