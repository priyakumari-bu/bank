import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.DefaultTableModel;

//Customer side loans  GUI  interface, show loans infomations
public class CustomerLoansFrame extends JFrame implements ActionListener {
    private Bank bank; 
    private Customer customer; 
    private JPanel panel = new JPanel(new GridLayout(3,1)); 
    private JTable loansTable = new JTable(); 
    private JLabel titleLabel = new JLabel("Customer Loans"); 
    private JButton pay = new JButton("Pay Loan");
    private JButton back_button = new JButton("Back");
    private CustomerFrame customerFrame;


    public static int FORM_WIDTH = 500;
    public static int FORM_Height = 500;

    public CustomerLoansFrame(CustomerFrame customerFrame,Bank bank, Customer customer) {
        this.bank = bank;
        this.customer = customer;
        this.customerFrame=customerFrame;

        String[] columns = new String[] {"ID", "PRINCIPAL","REMAINING BALANCE", "PAYMENT TOTAL","INTEREST ACCRUED", "COLLATERAL"}; 
        int numLoanAccounts = 0; 
        ArrayList<Loan> loans = new ArrayList<>(); 
        for (Account a : customer.getAccounts()) {
            if (a instanceof LoanAccount) {
                loans.addAll(((LoanAccount) a).getLoans());
            }
        }
        numLoanAccounts = loans.size(); 
        Object[][] data = new Object[numLoanAccounts][6];
        int i = 0; 
        for (Loan a : loans) {
            data[i][0] = a.getID(); 
            data[i][1] = a.getPrincipal().toString(); 
            data[i][2] = a.getRemainingBalance().toString();
            data[i][3] = a.computeAllPayments().toString();
            data[i][4] = a.getInterestAccumulated();
            data[i][5] = a.getCollateral();
            i++; 
        }
        pay.setMaximumSize(new DimensionUIResource(200, 200));

        this.loansTable = new JTable(data, columns); 
        panel.add(titleLabel);  
        panel.add(new JScrollPane(loansTable));
        JPanel panel2 = new JPanel();
        panel2.add(back_button);
        panel2.add(pay);

        panel.add(panel2);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panel, BorderLayout.CENTER);
        setTitle("Customer Loans - " + Bank.date); 
        setSize(FORM_WIDTH, FORM_Height); 
        setVisible(true); 
        // JOptionPane.showMessageDialog(rootPane, numLoanAccounts);
        pay.addActionListener(this);
        back_button .addActionListener(this);
    }
    
    
    //Customer side loans  GUI  interface action performed Controls
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back_button) {
            this.dispose();
            this.customerFrame.setVisible(true);
        }else if(e.getSource() == pay){
            new PayLoanFrame(this, customer);
            setVisible(false);
        }
    }
    
}
