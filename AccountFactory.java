public class AccountFactory {

    public Account createAccount(String accountType, String currencyType) {
        switch (accountType) {
            case "checking":
                return new CheckingAccount(currencyType);
            case "saving":
                return new SavingsAccount(currencyType);
            case "securities":
                return new SecuritiesAccount(currencyType);
            case "loan":
                return new LoanAccount(currencyType);
        }
        return new CheckingAccount("dollar"); 
    }
    
}