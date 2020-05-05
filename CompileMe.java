import java.util.Date;

// class for compilation purposes

public class CompileMe {
/*
    public static void main(String[] args) {
        /*
        Currency dollar = new Dollar(53.30);
        System.out.println(dollar.convertTo("euro"));
        System.out.println(dollar.convertTo("yen"));
        System.out.println(dollar);
        Currency euro = new Euro(492);
        System.out.println(euro);
        System.out.println(euro.convertTo("dollar"));
        System.out.println(euro.convertTo("yen")); */

        /*
        Transaction withdrawl = new Withdrawl(new Account(), new BankUser(), new Dollar(253.24), new Date(2020, 03, 11));
        Transaction deposit = new Deposit(new Account(), new BankUser(), new Yen(31044.24), new Date(2020, 03, 11));
        System.out.println(withdrawl.toString() + "\n" + deposit.toString());
        */

        /*
        Loan loan = new Loan(new Dollar(1200), new Customer(), new Date(), "Audi V8");

        System.out.println(loan);
        loan.accumulateInterest();
        System.out.println(loan);

        loan.exchangeTo("euro");
        loan.accumulateInterest();
        System.out.println(loan);

        loan.makePayment(new Dollar(785.32));
        System.out.println(loan);

        loan.makePayment(new Euro(408.53));
        System.out.println(loan);

        */
        
        /*
        CheckingAccount account1 = new CheckingAccount("dollar");
        
        account1.printAccountInfo();
        BankUser user1 = new BankUser("Priya", "password"); 

        System.out.println("Info after making a deposit: ");
        Currency firstDeposit = new Dollar(70.50); 
        Date date1 = new Date(2020, 4, 30); 
        Deposit deposit1 = new Deposit(account1, user1, firstDeposit, date1); 
        account1.deposit(deposit1); 

         
        account1.printAccountInfo(); 

        System.out.println("Info after making a withdrawl: "); 
        Currency firstWithdrawl = new Dollar(3.00); 
        Withdrawl withdrawl1 = new Withdrawl(account1, user1, firstWithdrawl, date1); 
        account1.withdraw(withdrawl1); 

        account1.printAccountInfo(); 

        System.out.println("Savings Account info: "); 
        SavingsAccount account2 = new SavingsAccount("dollar"); 
        account2.printAccountInfo();
        Currency firstTransfer = new Dollar(5.00); 
        Transfer transfer1 = new Transfer(account2, user1, firstTransfer, date1, account2); 
        account1.transfer(transfer1); 

        System.out.println("Info after making a transfer: "); 
        account1.printAccountInfo();
        account2.printAccountInfo();

        account1.exchangeTo("euro");
        account1.printAccountInfo();

        Currency secondDeposit = new Euro(20.00); 
        Deposit deposit2 = new Deposit(account1, user1, secondDeposit, date1); 
        account1.deposit(deposit2);
        account1.printAccountInfo();
        Currency thirdWithdrawl = new Dollar(100.00); 
        Transfer withdrawl3 = new Transfer(account1, user1, thirdWithdrawl, date1, account2);
        account2.transfer(withdrawl3); 
        */
    }
