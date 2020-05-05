import java.util.Date;

// Public class for a Withdrawl
// Extends the Transaction abstract class

public class Withdrawl extends Transaction {

    // class constructor for a withdrawl
    public Withdrawl(Account account, BankUser user, Currency value, Date date) {
        super(account, user, value, date);
    }

    // Withdrawl toString() method
    public String toString() {
        return "WITHDRAWL " + super.toString();
    }

}