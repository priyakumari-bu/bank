import java.util.*;

public class Bank {

    private static Bank single_instance = null; 
    private static StockMarket stockMarket = new StockMarket(); 
    static private Manager manager = new Manager("Mister Manager", "123456");
    static private ArrayList<Customer> customers = new ArrayList();
    static Date date = new Date();

    private Bank() {
        new Login(this);
    }

    public static Bank getInstance() {
        if (single_instance == null) {
            single_instance = new Bank();
        }
        return single_instance;
    }

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