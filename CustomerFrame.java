
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class CustomerFrame extends JFrame implements ActionListener {
    private Bank bank;
    private Customer customer; 
    private JPanel panel = new JPanel(new GridLayout(3, 1));
    private JLabel messageLabel = new JLabel("Service Select: "); 
    private JButton viewAccount = new JButton("View Account Details");
    private JButton deposit = new JButton("Make a Deposit"); 
    private JButton withdraw = new JButton("Make a Withdrawl"); 
    private JButton requestLoan = new JButton("Request a Loan"); 
    private JButton viewLoans = new JButton("View Loans"); 
    private JButton tradeStocks = new JButton("Trade Stocks"); 
    private JButton logOut = new JButton("Log Out"); 

    private Login login;

    public CustomerFrame(Login login,Bank bank, Customer customer) {
        this.bank = bank; 
        this.customer = customer;
        this.login = login;
        JLabel welcomeLabel = new JLabel("Welcome " + customer.getUsername() + "!");
        final JPanel panel1 = new JPanel();
        panel1.add(welcomeLabel);
        panel1.add(messageLabel);

        final JPanel panel3 = new JPanel(new GridLayout(3, 1));

        panel3.add(viewAccount);
        panel3.add(deposit);
        panel3.add(withdraw);
        panel3.add(requestLoan);
        panel3.add(tradeStocks);
        panel3.add(viewLoans); 
        panel3.add(logOut);
        panel.add(panel1);
        panel.add(panel3);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        viewAccount.addActionListener(this);
        deposit.addActionListener(this);
        withdraw.addActionListener(this);
        requestLoan.addActionListener(this);
        tradeStocks.addActionListener(this);
        viewLoans.addActionListener(this);
        logOut.addActionListener(this);

        add(panel, BorderLayout.CENTER);
        setTitle("Customer Login" + " - " + Bank.date);
        setSize(450, 450);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == viewAccount) {
            this.dispose();
            new CustomerAccountsFrame(this,customer);
        } else if (ae.getSource() == deposit) {
            this.dispose();
            new DepositFrame(this,customer);
//            JOptionPane.showMessageDialog(rootPane, "Deposit button clicked");
        } else if (ae.getSource() == withdraw) {
            this.dispose();
            new WithdrawlFrame(this,customer);
//            JOptionPane.showMessageDialog(rootPane, "Withdraw button clicked");
        } else if (ae.getSource() == requestLoan) {
            // JOptionPane.showMessageDialog(rootPane, "Request Loan button clicked");
            boolean found = false;
            for (Account account : customer.getAccounts()) {
                if (account instanceof LoanAccount) {
                    new LoanFrame(this, customer, (LoanAccount) account); 
                    found = true;
                    break;   
                }
            }
            if (!found) {
                JOptionPane.showMessageDialog(rootPane, "You do not have a Loan account yet. Please open one first.");
            }
        } else if (ae.getSource() == tradeStocks) {
            new StockMarketFrame(null,bank, customer);
        } else if (ae.getSource() == viewLoans) {
            CustomerLoansFrame frame = new CustomerLoansFrame(bank, customer); 
        } else if (ae.getSource() == logOut) {
            JOptionPane.showMessageDialog(rootPane, "You have logged out.");
            login.setTitle("Bank Login" + " - " + Bank.date);
            this.dispose();
            login.setVisible(true);
        }

    }

}