import java.util.*;
public class LoanAccount extends Account {
    // a collection that stores all of the loans that the holder of 
    // the account has taken out
    private ArrayList<Loan> loans = new ArrayList<Loan>();

    public LoanAccount(String currencyType) {
        super(currencyType);
        setAccountType("loan"); 
    }

    public Currency getTotalBalance() {
        double value = 0;
        for (Loan loan : loans) {
            double toAdd = loan.getRemainingBalance().convertTo(getCurrencyType()).getValue();
            value += toAdd;
        }
        switch (getCurrencyType()) {
            case "dollar":
                return new Dollar(value);
            case "yen":
                return new Yen(value);
            case "euro":
                return new Euro(value);
        }
        return new Dollar(value);
    }

    public ArrayList<Loan> getLoans() {
        return loans;
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    public void accrueInterestOnLoanAtIndex(int i) {
        loans.get(i).accumulateInterest();
    }

    public Loan getLoanAtIndex(int i) {
        return loans.get(i);
    }

    public void setLoans(ArrayList<Loan> loans) {
        this.loans = loans;
    }

    // overrides method of parent class to also print loans
    public void printAccountInfo() {
        super.printAccountInfo();
        System.out.println("Loans: "); 
        for (Loan l : loans) {
            System.out.println(l); 
        }
    }

}