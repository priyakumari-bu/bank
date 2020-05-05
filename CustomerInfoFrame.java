import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class CustomerInfoFrame extends JFrame implements ActionListener {

    private Bank bank; 
    private JPanel panel = new JPanel(new GridLayout(3, 1)); 
    private JLabel customerField = new JLabel("Enter Customer Username: "); 
    private JTextField customerTextField = new JTextField(); 
    private JButton checkInfo = new JButton("Check Info"); 

    public CustomerInfoFrame(Bank bank) {
        this.bank = bank; 

        panel.add(customerField); 
        panel.add(customerTextField); 
        panel.add(checkInfo); 

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        checkInfo.addActionListener(this);

        add(panel, BorderLayout.CENTER); 
        setTitle("Customer Details" + " - " + Bank.date); 
        setSize(600, 150); 
        setVisible(true); 
    }

    public void actionPerformed(ActionEvent ae) {
        boolean status = false; 
        String username = customerTextField.getText(); 
        ArrayList<Customer> customers = bank.getCustomers();

        for (Customer customer : customers) {
            if (username.trim().equals(customer.getUsername().trim())) {
                // JOptionPane.showMessageDialog(rootPane, "CUSTOMER IDENTIFIED");
                status = true; 
                StringBuilder str = new StringBuilder(); 
                String name = customer.getUsername(); 
                String totalBalance = customer.showCurrentBalances("dollar").toString(); 

                str.append("Username: " + name + "\n"); 
                str.append("Total Balance ($): " + totalBalance + "\n\n"); 

                str.append("Account Information: \n"); 
                for (Account account : customer.getAccounts()) {
                    str.append("(" + account.getID() + "): " + account.getAmount() + "\n"); 
                }

                String strString = str.toString(); 

                JTextArea accountInfo = new JTextArea(strString); 
                JOptionPane.showMessageDialog(rootPane, accountInfo);
            }
        }

        if (!status) {
            JOptionPane.showMessageDialog(rootPane, "This customer does not exist!");
        }
    }

    
}