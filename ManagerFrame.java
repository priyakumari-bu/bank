import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class ManagerFrame extends JFrame implements ActionListener {
    private Bank bank;
    private Login login;
    private JPanel panel = new JPanel(new GridLayout(3, 1));
    private JLabel messageLabel = new JLabel("Service Select: "); 
    private JButton registerCustomer = new JButton("Register a New Customer"); 
    private JButton generateReport = new JButton("Generate Report");
    private JButton checkCustomer = new JButton("Check on a Customer"); 
    private JButton adjustStocks = new JButton("Adjust Stocks"); 
    private JButton advanceDate = new JButton("Advance Date");
    private JButton allReports = new JButton("Transaction History");
    private JButton logOut = new JButton("Log Out"); 

    public ManagerFrame(Bank bank, Login login) {
        this.bank = bank; 
        this.login = login;
        panel.add(messageLabel);
        panel.add(registerCustomer); 
        panel.add(generateReport);
        panel.add(checkCustomer);
        panel.add(adjustStocks);
        panel.add(advanceDate);
        panel.add(allReports);
        panel.add(logOut); 
    
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        registerCustomer.addActionListener(this);
        generateReport.addActionListener(this);
        checkCustomer.addActionListener(this);
        adjustStocks.addActionListener(this);
        logOut.addActionListener(this);
        advanceDate.addActionListener(this);
        allReports.addActionListener(this);

        add(panel, BorderLayout.CENTER);
        setTitle("Manager Login"  + " - " + Bank.date);
        setSize(600, 150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == registerCustomer) { // done! 
            CustomerRegistrationFrame frame = new CustomerRegistrationFrame(bank); 
        } else if (ae.getSource() == generateReport) { // done! 
            generateBankReport(1); 
        }else if (ae.getSource() == checkCustomer) { // done! 
            CustomerInfoFrame frame = new CustomerInfoFrame(bank); 
        } else if (ae.getSource() == adjustStocks) {
            ManagerStockFrame frame = new ManagerStockFrame(bank); 
        } else if (ae.getSource() == logOut) {
            JOptionPane.showMessageDialog(rootPane, "You have logged out.");
            this.dispose();
            login.setTitle("Bank Login" + " - " + Bank.date);
            login.setVisible(true);
        } else if (ae.getSource() == advanceDate) {
            ArrayList<Deposit> interestPaid = new ArrayList<Deposit>();
            for (Customer customer : bank.getCustomers()) {
                for (Account account : customer.getAccounts()) {
                    if (account instanceof SavingsAccount) {
                        if (account.getAmount().convertTo("dollar").getValue() > 2500) {
                            Currency interest = ((SavingsAccount) account).accumulateInterest();
                            Deposit dep = new Deposit(account,customer, interest,Bank.date);
                            account.deposit(dep);
                            interestPaid.add(dep);
                        }
                    } else if (account instanceof LoanAccount) {
                        for (Loan loan : ((LoanAccount) account).getLoans()) {
                            System.out.println(loan.accumulateInterest());
                        }
                    }
                }
            }
            if (interestPaid.size() == 0) {
                JOptionPane.showMessageDialog(rootPane, "No interest was paid today! Wealthy!");
            } else {
                String msg = "Sad! You had to pay interest to your customers!\n\n";
                for (Deposit deposit : interestPaid) {
                    msg += deposit.getUser().getUsername() + ": Savings Account (" + deposit.getAccount().getID().toString() + ") received " + deposit.getValue().toString() + " (" + deposit.getAccount().getAmount().toString() + ").\n";
                }
                JOptionPane.showMessageDialog(rootPane, msg);
            }
            generateBankReport(0);
            Bank.pushDate();
            JOptionPane.showMessageDialog(rootPane, "Date has been advanced forward to " + Bank.date.toString());
            setTitle("Manager Login"  + " - " + Bank.date);
        } else if (ae.getSource() == allReports) {
            generateBankReport(3);
        }
    }

    public void generateBankReport(int MODE) {
        StringBuilder str = new StringBuilder(); 
        switch (MODE) {
            case 0:
            str.append("END OF DATE REPORT OF ALL TRANSACTIONS FOR: " + Bank.date.toString() + "\n"); 
            break;
            case 1:
            str.append("MID-DAY BANK REPORT OF ALL TRANSACTIONS FOR: " + Bank.date.toString() + "\n");
            break; 
            case 3:
            str.append("VIEWING TRANSACTION HISTORY PER CUSTOMER\n"); 
            break;
        }
        str.append("Number of Customers: " + bank.getCustomers().size() + "\n\n"); 

        for (Customer c : bank.getCustomers()) {
            int count = 0;
            str.append("Customer: " + c.getUsername() + "\n"); 
            for (Transaction t : c.getAllTransactions()) {
                if ((t.getDate().getTime() == Bank.date.getTime())) {
                    str.append(t.toString() + "\n"); 
                    count += 1;
                } else if (MODE == 3) {
                    str.append(t.toString() + "\n"); 
                    count += 1;
                }
            }
            if (count == 0) {
                str.append("no transactions for the current date");
            }
            str.append("\n\n"); 
        }
        PersistanceHandler p = new PersistanceHandler();
        p.persistReport(str.toString(), MODE);
        JOptionPane.showMessageDialog(rootPane, str.toString());
    }

}