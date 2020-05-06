import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;

public class CustomerLoansFrame extends JFrame {
    private Bank bank; 
    private Customer customer; 
    private JPanel panel = new JPanel(new GridLayout(3,1)); 
    private JTable loansTable = new JTable(); 
    private JLabel titleLabel = new JLabel("Customer Loans"); 

    public static int FORM_WIDTH = 500;
    public static int FORM_Height = 500;

    public CustomerLoansFrame(Bank bank, Customer customer) {
        this.bank = bank;
        this.customer = customer; 
        String[] columns = new String[] {"ACCOUNT", "LOAN AMOUNT"}; 
        int numLoanAccounts = 0; 
        ArrayList<Account> loanAccounts = new ArrayList<>(); 
        for (Account a : customer.getAccounts()) {
            if (a instanceof LoanAccount) {
                loanAccounts.add(a);
            }
        }
        numLoanAccounts = loanAccounts.size(); 
        Object[][] data = new Object[numLoanAccounts][2];
        int i = 0; 
        for (Account a : loanAccounts) {
            data[i][0] = a.getID(); 
            data[i][1] = a.getAmount(); 
            i++; 
        }
        this.loansTable = new JTable(data, columns); 
        panel.add(titleLabel);  
        panel.add(new JScrollPane(loansTable)); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel, BorderLayout.CENTER);
        setTitle("Customer Loans"); 
        setSize(FORM_WIDTH, FORM_Height); 
        setVisible(true); 
        // JOptionPane.showMessageDialog(rootPane, numLoanAccounts);
    }
    
}