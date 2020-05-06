import java.util.*;
// Class that represents the Bank 
public class Bank {

    private static Bank single_instance = null; 
    private static StockMarket stockMarket = new StockMarket(); 
    static private Manager manager = new Manager("Mister Manager", "123456");
    static private ArrayList<Customer> customers = new ArrayList();
    static Date date = new Date();
    
    // private constructor
    private Bank() {
        new Login(this);
    }

    // public getInstance method to enforce Singleton Pattern and only produce one bank
    public static Bank getInstance() {
        if (single_instance == null) {
            single_instance = new Bank();
        }
        return single_instance;
    }

    // method to control time in the bank
    public static void pushDate() {
        PersistanceHandler p = new PersistanceHandler();
        p.saveState();
        p.loadDate();
        Calendar c = Calendar.getInstance();
        c.setTime(Bank.date);
        c.add(Calendar.DATE, 1);
        date = c.getTime();
        p.saveDate();
    }

    public Manager getManager() {
        return manager;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }
    
    public static StockMarket getStockMarket() {
        return stockMarket;
    }

    public static void setStockMarket(StockMarket stockMarket) {
        Bank.stockMarket = stockMarket;
    }

}
