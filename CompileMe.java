import java.util.Date;

// class for compilation purposes
// contains the main method
// creates an instance of the bank and persistancehandler for persistance
public class CompileMe {
    public static void main(String[] args) {
        Bank bank = Bank.getInstance();
        PersistanceHandler p = new PersistanceHandler();
        p.loadState();
    }
}
