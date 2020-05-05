import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class CustomerRegistrationFrame extends JFrame implements ActionListener {
    private Bank bank; 
    private JPanel panel = new JPanel(new GridLayout(5, 1));
    private JLabel messageLabel = new JLabel("Enter Customer Information: "); 
    private JLabel blankLabel = new JLabel(""); 
    private JLabel usernameLabel = new JLabel("USERNAME: "); 
    private JLabel passwordLabel = new JLabel("PASSWORD: "); 
    private JLabel confirmPasswordLabel = new JLabel("CONFIRM PASSWORD: "); 
    private JTextField userTextField = new JTextField(); 
    private JTextField passwordTextField = new JTextField(); 
    private JTextField confirmPasswordTextField = new JTextField(); 
    private JButton register = new JButton("Register"); 

    public CustomerRegistrationFrame(Bank bank) {
        this.bank = bank; 

        panel.add(messageLabel); 
        panel.add(blankLabel); 
        panel.add(usernameLabel); 
        panel.add(userTextField); 
        panel.add(passwordLabel); 
        panel.add(passwordTextField); 
        panel.add(confirmPasswordLabel); 
        panel.add(confirmPasswordTextField); 
        panel.add(register); 

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        register.addActionListener(this);
        add(panel, BorderLayout.CENTER);
        setTitle("New Customer Registration");
        setSize(600, 200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String username = userTextField.getText(); 
        String password = passwordTextField.getText(); 
        String confirmPassword = confirmPasswordTextField.getText(); 

        if (!(password.equals(confirmPassword))) {
            JOptionPane.showMessageDialog(rootPane, "The passwords do not match.");
        } else {
            Currency initialDeposit = new Dollar(0.00); 
            Customer newCustomer = new Customer(username, password, initialDeposit); 
            bank.getCustomers().add(newCustomer);
            PersistanceHandler p = new PersistanceHandler();
            p.saveState();
            JOptionPane.showMessageDialog(rootPane, username + " has been successfuly registered!");
        }
    }

    
}