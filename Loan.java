import java.util.Date;
import java.util.ArrayList;

public class Loan implements BankComponent, Exchangeable, InterestTaxable {

    // data members for the Loan object
    private final double interestRate = 1.02;

    private Currency principal;
    private Currency interestAccumulated;
    private Currency remainingBalance;

    private ArrayList<Currency> payments = new ArrayList<Currency>();
    private ArrayList<Currency> interestCharges = new ArrayList<Currency>();

    private Customer customer;
    
    private String collateral;

    private final Date date;

    private ID id = new ID();

    public Loan(Currency principal, Customer customer, Date date, String collateral) {
        this.principal = principal;
        if (principal instanceof Dollar) {
            this.interestAccumulated = new Dollar(0);
            this.remainingBalance = new Dollar(principal.getValue());
        } else if (principal instanceof Euro) {
            this.interestAccumulated = new Euro(0);
            this.remainingBalance = new Euro(principal.getValue());
        } else {
            this.interestAccumulated = new Yen(0);
            this.remainingBalance = new Yen(principal.getValue());
        }
        this.customer = customer;
        this.date = date;
        this.collateral = collateral;
    }

    // getter mthods

    public double getInterestRate() {
        return interestRate;
    }

    public Currency getPrincipal() {
        return principal;
    }

    public Currency getInterestAccumulated() {
        return interestAccumulated;
    }

    public Currency getRemainingBalance() {
        return remainingBalance;
    }

    public ArrayList<Currency> getPayments() {
        return payments;
    }

    public ArrayList<Currency> getInterestCharges() {
        return interestCharges;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Date getDate() {
        return date;
    }

    public ID getID() {
        return id;
    }

    public String getCollateral() {
        return collateral;
    }

    public void setBalance(Currency balance) {
        this.remainingBalance = balance;
    }

    public void setAccumulatedInterest(Currency acc) {
        this.interestAccumulated = acc;
    }

    public void setPayments(ArrayList<Currency> newP) {
        this.payments = newP;
    }

    public void interestChargest(ArrayList<Currency> interestCharges) {
        this.interestCharges = interestCharges;
    }

    // changes the currency of a loan
    public void exchangeTo(String currencyType) {
        principal = principal.convertTo(currencyType);
        remainingBalance = remainingBalance.convertTo(currencyType);
        interestAccumulated = interestAccumulated.convertTo(currencyType);
    }

    // returns true if loan is paid off
    public boolean isPaidOff() {
        return remainingBalance.getValue() <= 0;
    }

    // accumulates interest on the current balance based on the interest rate
    // returns the amount of interested that was currently accumulated
    public Currency accumulateInterest() {
        double interestToCharge = remainingBalance.getValue() * (interestRate/100);
        Currency charge;
        switch (principal.getStringType()) {
            case "dollar":
                charge = new Dollar(interestToCharge);
                interestAccumulated = new Dollar(interestAccumulated.getValue() + charge.getValue());
                remainingBalance = new Dollar(remainingBalance.getValue() + charge.getValue());
                interestCharges.add(charge);
                return charge;
            case "euro":
                charge = new Euro(interestToCharge);
                interestAccumulated = new Euro(interestAccumulated.getValue() + charge.getValue());
                remainingBalance = new Euro(remainingBalance.getValue() + charge.getValue());
                interestCharges.add(charge);
                return charge;
            case "yen":
                charge = new Yen(interestToCharge);
                interestAccumulated = new Yen(interestAccumulated.getValue() + charge.getValue());
                remainingBalance = new Yen(remainingBalance.getValue() + charge.getValue());
                interestCharges.add(charge);
                return charge;
        }
        return new Dollar(0);
    }

    // makes a payment to the loan
    public boolean makePayment(Currency payment) {
        payments.add(payment);
        switch (principal.getStringType()) {
            case "dollar":
                remainingBalance = new Dollar(remainingBalance.getValue() - payment.convertTo("dollar").getValue());
                return true;
            case "euro":
                remainingBalance = new Euro(remainingBalance.getValue() - payment.convertTo("euro").getValue());
                return true;
            case "yen":
                remainingBalance = new Yen(remainingBalance.getValue() - payment.convertTo("yen").getValue());
                return true;
        }
        return false;
    }

    public String toString() {
        String strRepr = "Loan (" + id.toString() + "):";
        strRepr += "\n  Principal: " + principal.toString();
        strRepr += "\n  Remaining Balance: " + remainingBalance.toString();
        strRepr += "\n  Interest Accumulated To Date: " + interestAccumulated.toString();
        strRepr += "\n  Date Created: " + date.toString();
        strRepr += "\n  Collateral: " + collateral;
        strRepr += "\n  Interest Charges: ";
        for (Currency currency : interestCharges) {
            strRepr += currency.toString() + " ";
        }
        strRepr += "\n  Payments: ";
        for (Currency currency : payments) {
            strRepr += currency.toString() + " ";
        }
        return strRepr;
    }

}