import java.util.*; 

public class StockMarket implements BankComponent {
    private final ID id = new ID();
    private ArrayList<Stock> stocks; 

    public StockMarket() {
        this.stocks = new ArrayList<>(); 
    }

    public ID getID() {
        return id;
    }

    public ArrayList<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(ArrayList<Stock> stocks) {
        this.stocks = stocks;
    }

    public ArrayList<Stock> addDefaultStocks() {
        Stock amzn = new Stock("Amazon", "AMZN", new Dollar(2474.00), 100); 
        Stock fb = new Stock("Facebook", "FB", new Dollar(204.71), 100); 
        Stock msft = new Stock("Microsoft", "MSFT", new Dollar(174.57), 100); 
        Stock googl = new Stock("Alphabet", "GOOGL", new Dollar(1317.32), 100); 
        Stock baba = new Stock("Alibaba", "BABA", new Dollar(194.48), 100); 
        Stock fis = new Stock("Fidelity", "FIS", new Dollar(26.01), 100); 
        Stock crm = new Stock("Salesforce", "CRM", new Dollar(156.37), 100); 
        Stock ma = new Stock("Mastercard", "MA", new Dollar(268.74), 100); 
        Stock v = new Stock("Visa", "V", new Dollar(175.57), 100); 
        Stock nflx = new Stock("Netflix", "NFLX", new Dollar(415.27), 100); 

        stocks.add(amzn); 
        stocks.add(fb); 
        stocks.add(msft); 
        stocks.add(googl); 
        stocks.add(baba); 
        stocks.add(fis);
        stocks.add(crm); 
        stocks.add(ma); 
        stocks.add(v); 
        stocks.add(nflx); 

        return stocks; 
    }

    public String toString() {
        StringBuilder str = new StringBuilder(); 
        str.append("NAME\t TICKER\t PRICE\t # SHARES\t\n"); 
        str.append(String.format("%1$-" + 15 + "s", "NAME") +
                    String.format("%1$-" + 15 + "s", "TICKER") +
                    String.format("%1$-" + 15 + "s", "PRICE") +
                    String.format("%1$-" + 15 + "s", "# SHARES")); 
        /*
        for (Stock s : stocks) {
            str.append(String.format("%1$-" + 15 + "s", s.getName()) +
                        String.format("%1$-" + 15 + "s", s.getTicker()) + 
                        String.format("%1$-" + 15 + "s", s.getCurrentPrice().toString()) + 
                        String.format("%1$-" + 15 + "s", s.getVolume())); 
            str.append("\n");
        }
        */
        return str.toString(); 
    }




}