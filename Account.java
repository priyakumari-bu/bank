import java.util.*;

abstract class Account implements BankComponent, Exchangeable {

    private final ID id = new ID();

    // the type of account
    private String accountType; 

    // the base currency that all the money in the acount is in the form of 
    private String currencyType; 

    // the total amount of money stored in the account in the base currency 
    // (i.e. if there are $100 total in the account, amount is a Dollar object with value 100) 
    private Currency amount; 

    // the fee to open the account
    private Currency openingFee = new Dollar(-50);

    // the fee to close the account 
    private Currency closingFee = new Dollar(-50);

    // a collection of all the transactions this account has been involved in 
    private ArrayList<Transaction> transactions = new ArrayList<Transaction>(); 

    public Account(String currencyType) {
        setCurrencyType(currencyType); 
        this.amount = openingFee.convertTo(currencyType);
    }

    public ID getID() {
        return id;
    }

    public String getAccountType() {
        return accountType;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public Currency getAmount() {
        return amount;
    }

    public void setAmount(Currency amount) {
        this.amount = amount;
    }

    public Currency getOpeningFee() {
        return openingFee;
    }

    public Currency getClosingFee() {
        return closingFee;
    }

    // a method that prints all the info and details of the account 
    public void printAccountInfo() {
        System.out.println("Account ID: " + id); 
        System.out.println("Account Type: " + accountType); 
        System.out.println("Base Currency: " + currencyType); 
        System.out.println("Total Amount: " + amount);
        
        // prints all the transactions 
        System.out.println("Transactions: "); 
        for (Transaction t : transactions) {
            System.out.println(t);
        }
        System.out.println();  
    }

    // a method to make a deposit to an account 
    public boolean deposit(Deposit deposit) {
        // convert the deposited currency to the currency type of the account
        Currency convertedCurrency = deposit.getValue().convertTo(currencyType);
        
        // get the new total amount after making the deposit 
        double totalAmount = amount.getValue() + convertedCurrency.getValue(); 

        // set the account's currency to the new amount 
        Currency updatedCurrency = new Dollar(totalAmount); 
        switch(currencyType) {
            case "euro": 
                updatedCurrency = new Euro(totalAmount); 
            case "yen":
                updatedCurrency = new Yen(totalAmount); 
        } 
        setAmount(updatedCurrency); 
        transactions.add(deposit); // add this transaction to the account's transactions
        return true; // indicates the transaction successfully took place (will always be successful)
    }

    // a method to withdraw from an account
    public boolean withdraw(Withdrawl withdrawl) {
        boolean transactionSuccessful = false; // indicates if the transaction successfully took place 
        // convert the amount the withdraw to the currency type of the account
        Currency convertedCurrency = withdrawl.getValue().convertTo(currencyType); 

        // check if the account has enough money for the withdrawl to take place 
        if (hasAtLeast(convertedCurrency.getValue())) {
            // get the new total amount after making the withdrawl
            double totalAmount = amount.getValue() - convertedCurrency.getValue(); 

            Currency updatedCurrency = new Dollar(totalAmount); 
            switch(currencyType) {
                case "euro":
                    updatedCurrency = new Euro(totalAmount);
                case "yen": 
                    updatedCurrency = new Yen(totalAmount); 
            }
            setAmount(updatedCurrency); 
            transactions.add(withdrawl); // add this transaction to the account's transactions
            transactionSuccessful = true; 
        } else { // if the account does not have enough money for the withdrawl to take place
            System.out.println("This account does not have enough money for this transaction."); 
        }
        return transactionSuccessful; 
    }

    // a method to check if an account has at least a certain amount of money
    public boolean hasAtLeast(double value) {
        return amount.getValue() > value; 
    }

    // a method to transfer from this account to a different account 
    public void transfer(Transfer transfer) {
        // conduct a transfer by creating a deposit and withdrawl object 
        Withdrawl withdrawl = new Withdrawl(transfer.getAccount(), transfer.getUser(), transfer.getValue(), transfer.getDate()); 
        if (withdraw(withdrawl)) { // check if the account has enough money for this withdrawl to take place 
            Deposit deposit = new Deposit(transfer.getAccount(), transfer.getUser(), transfer.getValue(), transfer.getDate()); 
            transfer.getAccountTo().deposit(deposit); // deposit the money to the other account 
            transactions.add(transfer); // add this transaction to the account's transactions
        }
    }

    // a method to exhchange the account to a different type of currency
    public void exchangeTo(String currencyType) {
        this.currencyType = currencyType;
        amount = amount.convertTo(currencyType); 
        openingFee = openingFee.convertTo(currencyType);
        closingFee = closingFee.convertTo(currencyType);
    }

    public String toString() {
        String name = accountType.substring(0,1).toUpperCase() + accountType.substring(1) + " Account "; 
        return name; 
    }

}