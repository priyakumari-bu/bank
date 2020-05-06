import java.util.Date;

// class for compilation purposes

public class CompileMe {
    public static void main(String[] args) {
        Bank bank = Bank.getInstance();
        PersistanceHandler p = new PersistanceHandler();
        p.loadState();
    }
}
