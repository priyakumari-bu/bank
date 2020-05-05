import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;


public class LoanFrame extends JFrame  implements ActionListener {

    public static int FORM_WIDTH = 450;
    public static int FORM_Height = 250;

    private JPanel wholePanel;
    private JLabel choose_account_label;
    private JLabel choose_currency_label;
    private JLabel collateral_label;
    private JTextField collateral_text;
    private JLabel amount_label;
    private JButton backButton;
    private JButton enterButton;
    private JComboBox choose_account_cmb;
    private JComboBox choose_currency_cmb;
    private JTextField amount_text;
    private CustomerFrame customerFrame;
    private Customer customer;

    private LoanAccount account;


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            closeFrame();
            customerFrame.setVisible(true);
        }
    }


    public LoanFrame(final CustomerFrame customerFrame , Customer customer, LoanAccount account){
        this.customer = customer;
        this.customerFrame = customerFrame;

        initUI();

        backButton.addActionListener(this);
        add(wholePanel, BorderLayout.CENTER);
        setTitle("Bank - Loan");
        setSize(FORM_WIDTH, FORM_Height);
        setVisible(true);

        initializeAccountComboBox(customer);



        enterButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                String currency = choose_currency_cmb.getSelectedItem().toString();
                String account = choose_account_cmb.getSelectedItem().toString();
                String amount = amount_text.getText();

                Customer c = getCustomer();
                LoanAccount acc = (LoanAccount) c.getAccounts().get(c.findAccount(account.split("—")[1]));

                Loan loan = null;
                switch (currency){
                    case "USD":
                        Dollar dollar = new Dollar(Double.valueOf(amount));
                        loan = new Loan(dollar, customer, Bank.date, collateral_text.getText());
                        break;
                    case "EUR":
                        Euro euro = new Euro(Double.valueOf(amount));
                        loan = new Loan(euro, customer, Bank.date, collateral_text.getText());
                        break;
                    case "CNY":
                        Yen yen = new Yen(Double.valueOf(amount));
                        loan = new Loan(yen, customer, Bank.date, collateral_text.getText());
                        break;
                }
                acc.addLoan(loan);
                loan.makePayment(new Dollar (50));
                acc.setAmount(acc.getTotalBalance());
                PersistanceHandler p = new PersistanceHandler();
                p.saveState();
                JOptionPane.showMessageDialog(rootPane, "You have succesfully created a loan.\n" + loan.toString());
                closeFrame();
                customerFrame.setVisible(true);
            }
        });

    }


    public void closeFrame() {
        this.dispose();
    }
    public Customer getCustomer() {
        return customer;
    }


    private void initializeAccountComboBox(Customer customer) {
        if (customer.getAccounts().size() == 0) {
            choose_account_cmb.addItem("Please create an account first");
        }
        for (int i = 0; i < customer.getAccounts().size(); i++) {
            if (customer.getAccounts().get(i) instanceof LoanAccount) {
                String accountNum = customer.getAccounts().get(i).getID().toString();
                String accountType = customer.getAccounts().get(i).getAccountType();
                String balance = customer.getAccounts().get(i).getAmount().toString();
                choose_account_cmb.addItem(accountType + "—" + accountNum + "—" + balance);
            }
        }
    }



    public void initUI(){
        wholePanel =  new JPanel();
        wholePanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        wholePanel.setBackground(new Color(-524801));

        final JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(-524801));
        wholePanel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));

        final JLabel label1 = new JLabel();
        label1.setBackground(new Color(-524801));
        label1.setForeground(new Color(-16777216));
        label1.setText("Loan");
        panel1.add(label1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(4, 8, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-524801));
        wholePanel.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));

        choose_currency_label = new JLabel();
        choose_currency_label.setBackground(new Color(-524801));
        choose_currency_label.setForeground(new Color(-16777216));
        choose_currency_label.setText("Currency:");
        panel2.add(choose_currency_label, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        choose_currency_cmb =  new JComboBox();
        choose_currency_cmb.addItem("USD");
        choose_currency_cmb.addItem("CNY");
        choose_currency_cmb.addItem("EUR");
        panel2.add(choose_currency_cmb, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        choose_account_label = new JLabel();
        choose_account_label.setBackground(new Color(-524801));
        choose_account_label.setForeground(new Color(-16777216));
        choose_account_label.setText("Account:");
        panel2.add(choose_account_label, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        choose_account_cmb =  new JComboBox();
        panel2.add(choose_account_cmb, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        amount_label = new JLabel();
        amount_label.setBackground(new Color(-524801));
        amount_label.setForeground(new Color(-16777216));
        amount_label.setText("Amount:");
        panel2.add(amount_label, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        amount_text = new JTextField();
        amount_text.setBackground(new Color(-1118482));
        amount_text.setForeground(new Color(-4473925));
        amount_text.setText("");
        panel2.add(amount_text, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(30, -1), null, 0, false));

        collateral_label  = new JLabel();
        collateral_label.setBackground(new Color(-524801));
        collateral_label.setForeground(new Color(-16777216));
        collateral_label.setText("Enter collateral:");
        panel2.add(collateral_label, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        collateral_text = new JTextField();
        collateral_text.setBackground(new Color(-1118482));
        collateral_text.setForeground(new Color(-4473925));
        collateral_text.setText("");
        panel2.add(collateral_text, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(30, -1), null, 0, false));

        final JPanel panel3 = new JPanel();
        panel3.setBackground(new Color(-524801));
        wholePanel.add(panel3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));

        backButton = new JButton();
        backButton.setBackground(new Color(-1118482));
        backButton.setForeground(new Color(-16777216));
        backButton.setText("Back");
        panel3.add(backButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(40, -1), null, 0, false));

        enterButton = new JButton();
        enterButton.setBackground(new Color(-1118482));
        enterButton.setForeground(new Color(-16777216));
        enterButton.setText("Enter");
        panel3.add(enterButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(40, -1), null, 0, false));

    }



    public static void main(String[] args) {
//        Currency currency1 = new Dollar(120.00);
//        Customer customer4 = new Customer("admin", "admin", currency1);
//        new DepositFrame(customer4);
//        Yen y = new Yen(7.0);
//        Double s = y.convertTo("dollar").getValue();
//        System.out.println(s);
    }






}