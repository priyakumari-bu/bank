
public class SavingsAccount extends Account implements InterestTaxable {
    // the fee charged to withdraw money from the account
    private double withdrawalFee;

    // the interest rate that determines how much interest the holder of the account 
    // will earn on their savings 
    private double interestRate = 1.03;

    public SavingsAccount(String currencyType) {
        super(currencyType); 
        setAccountType("savings"); 
    }

    public double getWithdrawalFee() {
        return withdrawalFee;
    }

    public void setWithdrawalFee(double withdrawalFee) {
        this.withdrawalFee = withdrawalFee;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    // checks if the amount is the account is greater than $5000 
    // to check if the holder of the account is eligible to create a securities account
    public boolean isEligibleForSecuritiesAccountCreation() {
        return (getAmount().convertTo("dollar").getValue() > 5000); 
    }

    // checks if the amount in the account is at least $2500 
    // as this is the minimum balance required to maintain a securities account
    public boolean hasMinBalanceForSecuritiesAccount() {
        return (getAmount().convertTo("dollar").getValue() >= 2500); 
    }

    // accumulates interest on the current balance based on the interest rate
    // returns the amount of interested that was currently accumulated
    public Currency accumulateInterest() {
        double interestToCharge = getAmount().getValue() * (interestRate/100);
        Currency charge;
        switch (getCurrencyType()) {
            case "dollar":
                charge = new Dollar(interestToCharge);
                return charge;
            case "euro":
                charge = new Euro(interestToCharge);
                return charge;
            case "yen":
                charge = new Yen(interestToCharge);
                return charge;
        }
        return new Dollar(0);
    }

}