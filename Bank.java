import java.util.*;

public class Bank {

    private static Bank single_instance = null; 
    private static StockMarket stockMarket = new StockMarket(); 
    static private Manager manager = new Manager("John Doe", "123456");
    static private ArrayList<Customer> customers = new ArrayList();
    static Date date = new Date();

    private Bank() {
        new Login(this);
    }

    public static Bank getInstance() {
        if (single_instance == null) {
            single_instance = new Bank(); 
            stockMarket.addDefaultStocks();
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

    // a method to add a few customers to the bank to start off with 
    public void addCustomers() {

        Currency currency1 = new Dollar(5000.00); 
        Currency currency2 = new Euro(140.00); 
        Currency currency3 = new Yen(160.00); 

        Customer customer1 = new Customer("Priya Kumari", "123", currency1);
        Customer customer2 = new Customer("Jorge Jimenez", "123", currency2); 
        Customer customer3 = new Customer("Ziyu Shen", "123", currency3); 

        customers.add(customer1); 
        customers.add(customer2); 
        customers.add(customer3); 

        Account ac1 = new SavingsAccount("dollar");
        Account ac2 = new CheckingAccount("yen");
        Account ac3 = new SavingsAccount("euro");

        customers.get(0).openAccount(ac1);
        customers.get(0).openAccount(ac2);
        customers.get(0).openAccount(ac3);

        ac1.deposit(new Deposit(ac1, customers.get(0), new Dollar(100), new Date()));
        ac1.deposit(new Deposit(ac1, customers.get(0), new Yen(100), new Date()));
        ac1.deposit(new Deposit(ac1, customers.get(0), new Euro(550), new Date()));
        ac1.deposit(new Deposit(ac1, customers.get(0), new Euro(420.34), new Date()));
        ac1.deposit(new Deposit(ac1, customers.get(0), new Dollar(5.30), new Date()));
        ac1.withdraw(new Withdrawl(ac1, customers.get(0), new Dollar(5.30), new Date()));
        ac1.withdraw(new Withdrawl(ac1, customers.get(0), new Euro(14.30), new Date()));
        ac1.withdraw(new Withdrawl(ac1, customers.get(0), new Dollar(12.30), new Date()));

    }

    public static StockMarket getStockMarket() {
        return stockMarket;
    }

    public static void setStockMarket(StockMarket stockMarket) {
        Bank.stockMarket = stockMarket;
    }

}