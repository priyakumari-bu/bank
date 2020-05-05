import java.util.*;
import java.io.*;

public class PersistanceHandler {

    public void saveState() {
        File index = new File("StoredData/");
        for (File file : index.listFiles()) {
            if (file != null) {
                if (file.getName().equals("StockData") || file.getName().equals("Report") || file.getName().equals("date.txt") || file.getName().equals("report.txt")) {
                    continue;
                }
                if (file.isDirectory()) {
                    for (File inner : file.listFiles()) {
                        inner.delete();
                    }
                    file.delete();
                } else {
                    file.delete();
                }
            }
        }
        Bank bank = Bank.getInstance();
        Manager manager = bank.getManager();
        StockMarket stockMarket = bank.getStockMarket();
        persistStockMarket(stockMarket.getStocks());
        ArrayList<Customer> customers = bank.getCustomers();
        for (Customer customer : customers) {
            persistCustomer(customer);
        }
    }

    private void persistStockMarket(ArrayList<Stock> stocks) {
        String path = "StoredData/StockData/stocks.txt";
        try {
            FileWriter writer = new FileWriter(path);
            for (Stock stock : stocks) {
                // String name, String ticker, Currency currentPrice, int volume
                String name = stock.getName();
                String ticker = stock.getTicker();
                Currency currentPrice = stock.getCurrentPrice();
                int volume = stock.getVolume();
                writer.write(name + " " + ticker + " " + currentPrice.getStringType() + " " + Double.toString(currentPrice.getValue()) + " " + Integer.toString(volume) + "\n");
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("error has occured");
            System.out.println(e);
        }
    }

    private void persistCustomer(Customer customer) {
        String path = "StoredData/" + customer.getUsername() + "_" + customer.getPassword();
        File file = new File(path);
        file.mkdir();
        String userInfoPath = path + "/accounts.txt";
        File userInfoFile = new File(userInfoPath);
        try {
            FileWriter writer = new FileWriter(userInfoFile);
            int count = 0;
            for (Account account : customer.getAccounts()) {
                writer.write("ACCOUNT " + Integer.toString(count) + "\n");
                writer.write(account.getAccountType() + " " + account.getCurrencyType() + "\n");
                writer.write(account.getAmount().getStringType() + " " + Double.toString(account.getAmount().getValue()) + "\n");
                writer.write("START TRANSACTIONS\n");
                for (Transaction transaction : account.getTransactions()) {
                    if (transaction instanceof Deposit) {
                        writer.write("deposit " + transaction.getValue().getStringType() + " " + Double.toString(transaction.getValue().getValue()) + " " + transaction.getDate().toString());
                    } else if (transaction instanceof Withdrawl) {
                        writer.write("withdrawl " + transaction.getValue().getStringType() + " " + Double.toString(transaction.getValue().getValue()) + " " + transaction.getDate().toString());
                    } else {
                        writer.write("transfer " + transaction.getValue().getStringType() + " " + Double.toString(transaction.getValue().getValue()) + " " + transaction.getDate().toString());
                    }
                    writer.write("\n");
                }
                if (account instanceof LoanAccount) {
                    writer.write("loan start\n");
                    LoanAccount loanAccount = (LoanAccount) account;
                    for (Loan loan : loanAccount.getLoans()) {
                        // public Loan(Currency principal, Customer customer, Date date, String collateral) 
                        writer.write(loan.getPrincipal().getStringType() + " " + Double.toString(loan.getPrincipal().getValue()) + " " + loan.getCollateral() + " " + loan.getDate() + "\n");
                        writer.write(loan.getRemainingBalance().getStringType() + " " + Double.toString(loan.getRemainingBalance().getValue()));
                        writer.write(" " + loan.getInterestAccumulated().getStringType() + " " + Double.toString(loan.getInterestAccumulated().getValue()) + "\n");
                        if (loan.getInterestCharges().size() != 0) {
                            for (Currency charge : loan.getInterestCharges()) {
                                writer.write(charge.getStringType() + "-" + Double.toString(charge.getValue()) + " ");
                            }
                        } else {
                            writer.write("interest skip");
                        }
                        writer.write("\n");
                        if (loan.getPayments().size() != 0) {
                            for (Currency charge : loan.getPayments()) {
                                writer.write(charge.getStringType() + "-" + Double.toString(charge.getValue()) + " ");
                            }
                        } else {
                            writer.write("payment skip");
                        }
                        writer.write("\n");
                    }
                    writer.write("loan end\n");
                }
                writer.write("END TRANSACTIONS\n");
                writer.write("");
                count += 1;
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public void loadState() {
        loadDate();
        File[] files = new File("StoredData/").listFiles();
        for (File file : files) {
            if (!(file.getName().equals(".DS_Store"))) {
                if (file.getName().equals("StockData")) {
                    ArrayList<Stock> stocks = loadStocks(file);
                    Bank.getInstance().getStockMarket().setStocks(stocks);;
                } else if (file.getName().equals("report.txt")) {
                    
                } else if (file.getName().equals("date.txt")) {
                    loadDate();
                } else if (file.getName().equals("Report")) {

                }else {
                    Customer current_customer = new Customer(file.getName().split("_")[0], file.getName().split("_")[1], new Dollar(5));
                    current_customer.getAccounts().remove(0);
                    current_customer.accounts = parseAccounts(file, current_customer);
                    Bank.getInstance().getCustomers().add(current_customer);
                }
            }
        }
    }

    private ArrayList<Stock> loadStocks(File file) {
        ArrayList<Stock> stocks = new ArrayList<Stock>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file.getPath() + "/stocks.txt"));
            String data = reader.readLine();
            while (data != null) {
                String[] split = data.split(" ");
                String name = split[0];
                String ticker = split[1];
                Currency currentPrice = parseCurrency(split[2], split[3]);
                int volume = Integer.parseInt(split[4]);
                stocks.add(new Stock(name, ticker, currentPrice, volume));
                data = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error");
        }
        return stocks;
    }

    private ArrayList<Account> parseAccounts(File file, Customer customer) {
        ArrayList<Account> new_accounts = new ArrayList<Account>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file.getPath() + "/accounts.txt"));
            String data = reader.readLine();
            while (data != null) {
                data = reader.readLine();
                String[] split = data.split(" ");
                Account account = new CheckingAccount("dollar");
                switch (split[0]) {
                    case "checkings":
                        account = new CheckingAccount(split[1]);
                        break;
                    case "savings":
                        account = new SavingsAccount(split[1]);
                        break;
                    case "securities":
                        account = new SecuritiesAccount(split[1]);
                        break;
                    case "loan":
                        account = new LoanAccount(split[1]);
                        break;
                }
                data = reader.readLine();
                split = data.split(" ");
                Currency currentValue = new Dollar(1);
                switch (split[0]) {
                    case "dollar":
                        currentValue = new Dollar(Double.parseDouble(split[1]));
                        break;
                    case "euro":
                        currentValue = new Euro(Double.parseDouble(split[1]));
                        break;
                    case "yen":
                        currentValue = new Euro(Double.parseDouble(split[1]));
                        break;
                }
                account.setAmount(currentValue);
                data = reader.readLine();
                if (data.equals("START TRANSACTIONS")) {
                    data = reader.readLine();
                    while (!data.equals("END TRANSACTIONS")) {
                        if (!data.equals("loan start")) {
                            Transaction transaction = parseTransaction(customer, account, data);
                            account.getTransactions().add(transaction);
                            data = reader.readLine();
                        } else {
                            ArrayList<Loan> loans = parseLoans(customer, account, reader);
                            data = reader.readLine();
                        }
                    }
                }
                data = reader.readLine();
                new_accounts.add(account);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
        return new_accounts;
    }

    private Transaction parseTransaction(Customer customer, Account account, String line) {
        String[] split = line.split(" ");
        Currency currency = new Dollar(1);
        switch (split[1]) {
            case "dollar":
                currency = new Dollar(Double.parseDouble(split[2]));
                break;
            case "yen":
                currency = new Yen(Double.parseDouble(split[2]));
                break;
            case "euro":
                currency = new Euro(Double.parseDouble(split[2]));
                break;
        }
        if (split[0].equals("deposit")) {
            return new Deposit(account, customer, currency, parseDate(split));
        } else if (split[0].equals("withdrawl")) {
            return new Withdrawl(account, customer, currency, parseDate(split));
        } else {
            return new Transfer(account, customer, currency, parseDate(split), account);
        }
    }

    private ArrayList<Loan> parseLoans(Customer customer, Account account, BufferedReader reader) {
        ArrayList<Loan> loans = new ArrayList<Loan>();
        try {
            String data = reader.readLine();
            while (!data.equals("loan end")) {
                String[] constructorData = data.split(" ");
                Currency principal = parseCurrency(constructorData[0], constructorData[1]);
                String collateral = constructorData[2];
                Date date = parseDate(constructorData);
                Loan toAdd = new Loan(principal, customer, date, collateral);
                data = reader.readLine();
                String[] split = data.split(" ");
                Currency remainingBalance = parseCurrency(split[0], split[1]);
                Currency interstAccumulated = parseCurrency(split[2], split[3]);
                toAdd.setBalance(remainingBalance);
                toAdd.setAccumulatedInterest(interstAccumulated);
                data = reader.readLine();
                ArrayList<Currency> interestCharges = new ArrayList<Currency>();
                if (!data.equals("interest skip")) {
                    String[] charges = data.split(" ");
                    for (String string : charges) {
                        String[] interior = string.split("-");
                        interestCharges.add(parseCurrency(interior[0], interior[1]));
                    }
                }
                data = reader.readLine();
                ArrayList<Currency> payments = new ArrayList<Currency>();
                if (!data.equals("payment skip")) {
                    String[] charges = data.split(" ");
                    for (String string : charges) {
                        String[] interior = string.split("-");
                        payments.add(parseCurrency(interior[0], interior[1]));
                    }
                }
                toAdd.setPayments(payments);
                toAdd.interestChargest(interestCharges);
                data = reader.readLine();
                loans.add(toAdd);
            }
        } catch (Exception e) {};
        ((LoanAccount) account).setLoans(loans);
        return loans;
    }

    private Date parseDate(String[] split) {
        return new Date(split[3] + " " + split[4] + " " + split[5] + " " + split[6] + " " + split[7] + " " + split[8]);
    }

    private Currency parseCurrency(String split_one, String split_two) {
        Currency currentValue = new Dollar(1);
        switch (split_one) {
            case "dollar":
                currentValue = new Dollar(Double.parseDouble(split_two));
                break;
            case "euro":
                currentValue = new Euro(Double.parseDouble(split_two));
                break;
            case "yen":
                currentValue = new Euro(Double.parseDouble(split_two));
                break;
        }
        return currentValue;
    }

    public void persistReport(String reportString, int mode) {
        String path;
        if (mode == 3) {
            path = "StoredData/Report/HISTORICAL/allTransactions_" + Bank.date.toString() + ".txt";
        }
        else {
            path = "StoredData/Report/" + Bank.date.toString() + ".txt";
        }
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(reportString);
            writer.close();
        } catch (Exception e) {
            System.out.println("error has occured");
        }
    }

    public void saveDate() {
        Date date = Bank.date;
        String path = "StoredData/date.txt";
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(date.toString());
            writer.close();
        } catch (Exception e) {
            System.out.println("error has occured");
            System.out.println(e);
        }
    }

    public void loadDate() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("StoredData/date.txt"));
            String data = reader.readLine();
            if (data == null) {
                saveDate();
                loadDate();
            } else {
                Bank.date = new Date(data);
                reader.close();
            }
        } catch (Exception e) {}
    }

}