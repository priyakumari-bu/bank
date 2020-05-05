
public class Stock implements BankComponent, Exchangeable, Tradeable {
    
    private final ID id = new ID();
    private String name; 
    private String ticker; 
    private Currency currentPrice; 
    private Currency openPrice;
    private Currency highPrice;
    private Currency lowPrice;
    private Currency closePrice; 
    private int volume; 

    public Stock(String name, String ticker, Currency currentPrice, int volume) {
        this.name = name; 
        this.ticker = ticker; 
        this.currentPrice = currentPrice; 
        this.volume = volume; 
    }

    public ID getID() {
        return id;
    }

    public void exchangeTo(String currencyType) {
        openPrice.convertTo(currencyType); 
        highPrice.convertTo(currencyType); 
        lowPrice.convertTo(currencyType); 
        closePrice.convertTo(currencyType); 
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Currency getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Currency currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Currency getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Currency openPrice) {
        this.openPrice = openPrice;
    }

    public Currency getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(Currency highPrice) {
        this.highPrice = highPrice;
    }

    public Currency getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(Currency lowPrice) {
        this.lowPrice = lowPrice;
    }

    public Currency getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Currency closePrice) {
        this.closePrice = closePrice;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

}