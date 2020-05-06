// interface that represents that an object's ability to be taxed with a certain interest rate
interface InterestTaxable {
    
    public double getInterestRate();


    public Currency accumulateInterest();

}
