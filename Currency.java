
// Abstract class Currency: Implementation for the multiple currencies used in the bank
// Implements the BankComponent interface

abstract class Currency implements BankComponent {

    // Monetary value of the currency
    private double value;
    private final ID id = new ID();

    // class constructor
    public Currency(double value) {
        this.value = (double) Math.round(value * 100) / 100;
    }

    // returns the monetary value
    public double getValue() {
        return value;
    }

    // get unique ID of the Currency
    public ID getID() {
        return id;
    }

    // toString method for the currency
    public String toString() {
        String str_repr = Double.toString(value);
        if (str_repr.charAt(str_repr.length() - 2) == '.') {
            return Double.toString(value) + "0";
        } else {
            return Double.toString(value);
        }
    }

    // equals methods between two currency objects.
    public boolean equals(Currency other) {
        return this.getID().id.equals(other.getID().id) && this.value == other.value;
    }

    // Abstract methods, specifying conversions between Currency objects and symbol of the currency
    abstract Currency convertTo(String currency);

    abstract String getSymbol();

    abstract String getStringType();

}