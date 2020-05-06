// Class that represents a securities account, held only by rich customers 
import java.util.*;
public class SecuritiesAccount extends Account {

    // a collection that stores all the stocks owned
    private ArrayList<Stock> stocks = new ArrayList<Stock>();

    // a collection that stores all the open positions
    private ArrayList<Stock> openPositions = new ArrayList<Stock>();

    public SecuritiesAccount(String currencyType) {
        super(currencyType);
        setAccountType("securities");
    }

    // method to buy shares of a certain stock for the account 
    public boolean buy(Stock stock, int numShares) {
        boolean transactionSuccessful = false;
        if (getAmount().getValue() >= stock.getCurrentPrice().convertTo(getAmount().getStringType()).getValue()*numShares) { // if account has enough money to buy stock
            Currency convertedCurrency = stock.getCurrentPrice().convertTo(getCurrencyType());
            double totalAmount = getAmount().getValue() - convertedCurrency.getValue()*numShares; // deduct price of total purchase from account's balance

            Currency updatedCurrency = new Dollar(totalAmount);
            switch(getCurrencyType()) {
                case "euro":
                    updatedCurrency = new Euro(totalAmount);
                case "yen":
                    updatedCurrency = new Yen(totalAmount);
            }
            setAmount(updatedCurrency); // update the account's new balance
            //TODO
            int i = findStocksByName(stock.getName());
            if(i<0){
                stocks.add(stock); // add the stock to the account's stocks
            }else {
                Integer num = stocks.get(i).getVolume()+numShares;
                stocks.get(i).setVolume(num);
            }
            //TODO
            transactionSuccessful = true;
        } else {
            System.out.println("This account does not have enough money for this transaction");
        }
        return transactionSuccessful;
    }

    // method to sell shares of a certain stock back to the stock market 
    public boolean sell(Stock stock, int numShares) {
        boolean transactionSuccessful = false;

        //start
        Currency convertedCurrency = stock.getCurrentPrice().convertTo(getCurrencyType());
        double totalAmount = getAmount().getValue() + convertedCurrency.getValue()*numShares; // add price of total sale to account's balance
        Currency updatedCurrency = new Dollar(totalAmount);
        switch(getCurrencyType()) {
            case "euro":
                updatedCurrency = new Euro(totalAmount);
            case "yen":
                updatedCurrency = new Yen(totalAmount);
        }
        setAmount(updatedCurrency); // update the account's new balance
        int i = findStocksByName(stock.getName());
        if(i<0){
            System.out.println("This account does not own this stock.");
        }else {
            Integer num = stocks.get(i).getVolume()-numShares;
            stocks.get(i).setVolume(num);
        }
        transactionSuccessful = true;
        //end


//        if (stocks.contains(stock)) { // if the account actually owns the stock attempted to be sold
//            Currency convertedCurrency = stock.getCurrentPrice().convertTo(getCurrencyType());
//            double totalAmount = getAmount().getValue() + convertedCurrency.getValue()*numShares; // add price of total sale to account's balance
//
//            Currency updatedCurrency = new Dollar(totalAmount);
//            switch(getCurrencyType()) {
//                case "euro":
//                    updatedCurrency = new Euro(totalAmount);
//                case "yen":
//                    updatedCurrency = new Yen(totalAmount);
//            }
//            setAmount(updatedCurrency); // update the account's new balance
//            stocks.remove(stock); // add the stock to the account's stocks
//            transactionSuccessful = true;
//        } else {
//            System.out.println("This account does not own this stock.");
//        }
        return transactionSuccessful;
    }


    public ArrayList<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(ArrayList<Stock> stocks) {
        this.stocks = stocks;
    }


    // method to find the stock of a certain company
    public int findStocksByName(String name){
        int length = stocks.size();
        for(int i=0;i<length;i++){
            if(stocks.get(i).getName().equals(name)){
                return i;
            }
        }
        return -1;
    }

}
