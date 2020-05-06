import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;

public class Login extends JFrame implements ActionListener {

    private Bank bank; 
    private JPanel panel = new JPanel(new GridLayout(2, 1));
    private JLabel usernameLabel = new JLabel("USERNAME: ");
    private JLabel passwordLabel = new JLabel("PASSWORD: ");
    private JTextField userTextField = new JTextField();
    private JPasswordField passwordTextField = new JPasswordField();
    private JButton submit = new JButton("Login");

    private JButton registerCustomer = new JButton("No Account");

    public Login(Bank bank) {
        this.bank = bank;

        PersistanceHandler p = new PersistanceHandler();
        p.loadDate();

        final JPanel panel0 = new JPanel();
        panel0.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel0.setBackground(new Color(-524801));
        panel0.add(usernameLabel, new GridConstraints(0, 0, 1, 1, GridBagConstraints.CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel0.add(userTextField, new GridConstraints(0, 1, 1, 1, GridBagConstraints.CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(40, -1), null, 0, false));
        panel0.add(passwordLabel, new GridConstraints(1, 0, 1, 1, GridBagConstraints.CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(40, -1), null, 0, false));
        panel0.add(passwordTextField, new GridConstraints(1, 1, 1, 1, GridBagConstraints.CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(40, -1), null, 0, false));
        panel.add(panel0, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(-524801));
        panel1.add(registerCustomer);
        panel1.add(submit);
        panel.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registerCustomer.addActionListener(this);
        submit.addActionListener(this);
        add(panel, BorderLayout.CENTER);
        setTitle("Bank Login" + " - " + Bank.date);
        setSize(500, 150);
        setVisible(true);

    }


    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == registerCustomer){
            CustomerRegistrationFrame frame = new CustomerRegistrationFrame(this,bank);
        }
        if(ae.getSource() == submit){
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
    }


    public static void main(String[] args) {
        Bank bank = Bank.getInstance();
        Login login = new Login(bank);
    }
}