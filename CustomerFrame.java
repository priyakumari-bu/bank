
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
    private JButton transfer = new JButton("Transfer"); 
    private JButton requestLoan = new JButton("Request a Loan"); 
    private JButton viewLoans = new JButton("View/Pay Loans"); 
    private JButton tradeStocks = new JButton("Trade Stocks"); 
    private JButton logOut = new JButton("Log Out");
    private JButton viewAll = new JButton("View All Transactions");  

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
        panel3.add(transfer);
        panel3.add(requestLoan);
        panel3.add(tradeStocks);
        panel3.add(viewLoans); 
        panel3.add(viewAll);
        panel3.add(logOut);
        panel.add(panel1);
        panel.add(panel3);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        viewAccount.addActionListener(this);
        deposit.addActionListener(this);
        withdraw.addActionListener(this);
        requestLoan.addActionListener(this);
        tradeStocks.addActionListener(this);
        transfer.addActionListener(this);
        viewLoans.addActionListener(this);
        viewAll.addActionListener(this);
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
        } else if (ae.getSource() == transfer) {
            this.dispose();
            new TransferFrame(this, customer);
        } else if (ae.getSource() == viewAll) {
            JTextArea textArea = new JTextArea(20,45);
            String disp = "Viewing detailed report for " + customer.getUsername() + "(" + customer.getID().toString() + "):\n\n\n";
            for (Account account : customer.getAccounts()) {
                disp += account.getAccountType() + " account (" + account.getID() + ") : " + account.getAmount().toString() + "\n\n";
                for (Transaction transaction : account.getTransactions()) {
                    disp += transaction.toString() + "\n";
                }
                if (account instanceof LoanAccount) {
                    for (Loan loan : ((LoanAccount) account).getLoans()) {
                        disp += loan.toString() + "\n\n";
                    }
                }
                if (account instanceof SecuritiesAccount) {
                    disp += "\n";
                    for (Stock stock : ((SecuritiesAccount) account).getStocks()) {
                        disp += Integer.toString(stock.getVolume()) + " " + stock.getName() + " (" + stock.getTicker() + ") stocks at " + stock.getCurrentPrice().toString() + " per stock ";
                        switch (stock.getCurrentPrice().getStringType()) {
                            case "dollar":
                            disp += " (" + (new Dollar(stock.getCurrentPrice().getValue() * stock.getVolume())).toString() + ")";
                            break;
                            case "euro":
                            disp += " (" + (new Euro(stock.getCurrentPrice().getValue() * stock.getVolume())).toString() + ")";
                            break;
                            case "yen":
                            disp += " (" + (new Yen(stock.getCurrentPrice().getValue() * stock.getVolume())).toString() + ")";
                            break;
                        }
                        disp += ".\n";
                    }
                }
                disp += "\n\n";
            }
            textArea.setText(disp);
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            JOptionPane.showMessageDialog(rootPane, scrollPane);        
        }else if (ae.getSource() == deposit) {
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
                    this.dispose();
                    new LoanFrame(this, customer, (LoanAccount) account);
                    found = true;
                    break;
                }
            }
            if (!found) {
                JOptionPane.showMessageDialog(rootPane, "You do not have a Loan account yet. Please open one first.");
            }
        } else if (ae.getSource() == tradeStocks) {
            this.dispose();
            new StockMarketFrame(this,bank, customer);
        } else if (ae.getSource() == viewLoans) {
            this.dispose();
            CustomerLoansFrame frame = new CustomerLoansFrame(this,bank, customer);
        } else if (ae.getSource() == logOut) {
            JOptionPane.showMessageDialog(rootPane, "You have logged out.");
            login.setTitle("Bank Login" + " - " + Bank.date);
            this.dispose();
            login.setVisible(true);
        } 
    }

}