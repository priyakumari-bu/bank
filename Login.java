import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;

public class Login extends JFrame implements ActionListener {

    private Bank bank; 
    private JPanel panel = new JPanel(new GridLayout(3, 1));
    private JLabel usernameLabel = new JLabel("USERNAME: ");
    private JLabel passwordLabel = new JLabel("PASSWORD: ");
    private JTextField userTextField = new JTextField();
    private JPasswordField passwordTextField = new JPasswordField();
    private JButton submit = new JButton("Login");

    public Login(Bank bank) {
        this.bank = bank;

        PersistanceHandler p = new PersistanceHandler();
        p.loadDate();

        panel.add(usernameLabel);
        panel.add(userTextField);
        panel.add(passwordLabel);
        panel.add(passwordTextField);
        panel.add(submit);
    
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        submit.addActionListener(this);
        add(panel, BorderLayout.CENTER);
        setTitle("Bank Login" + " - " + Bank.date);
        setSize(500, 150);
        setVisible(true);

    }


    public void actionPerformed(ActionEvent ae) {
        boolean status = false;
        String userName = userTextField.getText();
        String password = passwordTextField.getText();
        Manager manager = bank.getManager();
        ArrayList<Customer> customers = bank.getCustomers(); 
        if (userName.trim().equals(manager.getUsername()) && password.trim().equals(manager.getPassword())) {
            setVisible(false);
            dispose();
            JOptionPane.showMessageDialog(null, "You have logged in as a manager!");
            status = true;
            ManagerFrame frame = new ManagerFrame(bank, this); 
        } else {
           for (Customer customer : customers) {
             if (userName.trim().equals(customer.getUsername()) && password.trim().equals(customer.getPassword())) {
                setVisible(false);
                dispose();
                JOptionPane.showMessageDialog(null, "You have logged in as a user!");
                status = true;
                CustomerFrame frame = new CustomerFrame(this, bank, customer); 
             }
           }
        }
        if (!status) {
            JOptionPane.showMessageDialog(null, "Authentication failed! The user and password do not exist.");
        }
    }

    public static void main(String[] args) {
        Bank bank = Bank.getInstance();
        PersistanceHandler p = new PersistanceHandler();
        p.loadState();
    }

}