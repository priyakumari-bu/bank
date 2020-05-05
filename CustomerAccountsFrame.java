
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;


public class CustomerAccountsFrame extends JFrame implements ActionListener {

    public static int FORM_WIDTH = 450;
    public static int FORM_Height = 600;

    private JPanel wholePanel;
    private JLabel welcomeLabel;
    private JButton backButton;
    private JComboBox cmb;
    private JLabel account_num;
    private JButton open_button;
    private JTable account_table;
    private JButton delete_button;
    private JScrollPane account_scroll;

    private Customer customer;
    private String cmbValue = "checking";
    private CustomerFrame customerFrame;

    public Customer getCustomer(){
        return this.customer;
    }

    public CustomerAccountsFrame(CustomerFrame customerFrame , Customer customer ){
        initUI();
        this.customer = customer;
        this.customerFrame = customerFrame;

        open_button.addActionListener(this);
        delete_button.addActionListener(this);
        backButton.addActionListener(this);

        add(wholePanel, BorderLayout.CENTER);
        setTitle("Bank - Account"  + " - " + Bank.date);
        setSize(FORM_WIDTH, FORM_Height);
        setVisible(true);

        welcomeLabel.setText(customer.getUsername() + " ,here are your accounts.");
        account_num.setText(Integer.toString(customer.getCheckingNum()));
        refreshTables("checking");


        cmb.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    switch (e.getItem().toString()){
                        case "checking":
                            cmbValue = "checking";
                            account_num.setText(Integer.toString(getCustomer().getCheckingNum()));
                            refreshTables("checking");
                            break;
                        case "savings":
                            cmbValue = "savings";
                            account_num.setText(Integer.toString(getCustomer().getCheckingNum()));
                            refreshTables("savings");
                            break;
                        case "securities":
                            cmbValue = "securities";
                            account_num.setText(Integer.toString(getCustomer().getCheckingNum()));
                            refreshTables("securities");
                            break;
                        case "loan":
                            cmbValue = "loan";
                            account_num.setText(Integer.toString(getCustomer().getCheckingNum()));
                            refreshTables("loan");
                            break;
                    }
                }
            }
        });

    }



    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == backButton) {
            this.dispose();
            customerFrame.setVisible(true);
        }

        if (e.getSource() == open_button) {
            int result = 1;
            switch (cmbValue){
                case "checking":
                    CheckingAccount checkingAccount = new CheckingAccount("dollar");
                    result = JOptionPane.showConfirmDialog(null,
                            "Do you want to open a checking account?\nThis operation will charge $" + checkingAccount.getOpeningFee().getValue() + " for service fee",
                            "Confirm",
                            JOptionPane.YES_NO_OPTION);
                    if (result == 0) {//YES
                        customer.openAccount(checkingAccount);
                        refreshTables(cmbValue);
                    }
                    break;
                case "savings":
                    SavingsAccount savingsAccount = new SavingsAccount("dollar");
                    result = JOptionPane.showConfirmDialog(null,
                            "Do you want to open a savings account?\nThis operation will charge $" + savingsAccount.getOpeningFee().getValue() + " for service fee",
                            "Confirm",
                            JOptionPane.YES_NO_OPTION);
                    if (result == 0) {//YES
                        customer.openAccount(savingsAccount);
                        refreshTables(cmbValue);
                    }
                    break;
                case "securities":
                    SecuritiesAccount securitiesAccount = new SecuritiesAccount("dollar");
                    result = JOptionPane.showConfirmDialog(null,
                            "Do you want to open a securities account?\nThis operation will charge $" + securitiesAccount.getOpeningFee().getValue() + " for service fee",
                            "Confirm",
                            JOptionPane.YES_NO_OPTION);
                    if (result == 0) {//YES
                        customer.openAccount(securitiesAccount);
                        refreshTables(cmbValue);
                    }
                    break;
                case "loan":
                    LoanAccount loanAccount = new LoanAccount("dollar");
                    result = JOptionPane.showConfirmDialog(null,
                            "Do you want to open a loan account?\nThis operation will charge $" + loanAccount.getOpeningFee().getValue() + " for service fee",
                            "Confirm",
                            JOptionPane.YES_NO_OPTION);
                    if (result == 0) {//YES
                        customer.openAccount(loanAccount);
                        refreshTables(cmbValue);
                    }
                    break;
            }
            PersistanceHandler p = new PersistanceHandler();
            p.saveState();
        }

        if (e.getSource() == delete_button) {
            int row = account_table.getSelectedRow();
            System.out.println(account_table.getRowCount());
            if (row != -1) {
                String accountNum = account_table.getValueAt(row, 0).toString();
                System.out.println(accountNum);
                int result = 1;
                int index = -1;
                switch (cmbValue){
                    case "checking":
                        CheckingAccount checkingAccount = new CheckingAccount("dollar");
                        result = JOptionPane.showConfirmDialog(null,
                                "Close this account?\nThis operation will charge $" + checkingAccount.getOpeningFee().getValue() + " service fee",
                                "Confirm",
                                JOptionPane.YES_NO_OPTION);
                        if (result == 0) {//YES
                            index = customer.findAccount(accountNum);
                            customer.closeAccount(index);//TODO alert msg
                            refreshTables(cmbValue);
                        }
                        break;
                    case "savings":
                        SavingsAccount savingsAccount = new SavingsAccount("dollar");
                        result = JOptionPane.showConfirmDialog(null,
                                "Close this account?\nThis operation will charge $" + savingsAccount.getOpeningFee().getValue() + " service fee",
                                "Confirm",
                                JOptionPane.YES_NO_OPTION);
                        if (result == 0) {//YES
                            index = customer.findAccount(accountNum);
                            customer.closeAccount(index);//TODO alert msg
                            refreshTables(cmbValue);
                        }
                        break;
                    case "securities":
                        SecuritiesAccount securitiesAccount = new SecuritiesAccount("dollar");
                        result = JOptionPane.showConfirmDialog(null,
                                "Close this account?\nThis operation will charge $" + securitiesAccount.getOpeningFee().getValue() + " service fee",
                                "Confirm",
                                JOptionPane.YES_NO_OPTION);
                        if (result == 0) {//YES
                            index = customer.findAccount(accountNum);
                            customer.closeAccount(index);//TODO alert msg
                            refreshTables(cmbValue);
                        }
                        break;
                    case "loan":
                        LoanAccount loanAccount = new LoanAccount("dollar");
                        result = JOptionPane.showConfirmDialog(null,
                                "Close this account?\nThis operation will charge $" + loanAccount.getOpeningFee().getValue() + " service fee",
                                "Confirm",
                                JOptionPane.YES_NO_OPTION);
                        if (result == 0) {//YES
                            index = customer.findAccount(accountNum);
                            customer.closeAccount(index);//TODO alert msg
                            refreshTables(cmbValue);
                        }
                        break;
                }
            }
        }
        PersistanceHandler p = new  PersistanceHandler ();
        p.saveState();
    }





    public void refreshAccountNum(String type){
        switch (type){
            case "checking":
                account_num.setText(Integer.toString(customer.getCheckingNum()));
                break;
            case "savings":
                account_num.setText(Integer.toString(customer.getSavingNum()));
                break;
            case "securities":
                account_num.setText(Integer.toString(customer.getSecurititesNum()));
                break;
            case "loan":
                account_num.setText(Integer.toString(customer.getLoanNum()));
                break;
        }
    }

    public void refreshTables(String type) {

        refreshAccountNum(cmbValue);

        String[] columnNames = {"Account Num", "Balance/ $"};
        DefaultTableModel model;
        switch (type){
            case "checking":
                model = (DefaultTableModel) account_table.getModel();
                model.setDataVector(initializeData(customer,type), columnNames);
                account_scroll = new JScrollPane(new JTable(model));
                break;
            case "savings":
                model = (DefaultTableModel) account_table.getModel();
                model.setDataVector(initializeData(customer,type), columnNames);
                account_scroll = new JScrollPane(new JTable(model));
                break;
            case "securities":
                model = (DefaultTableModel) account_table.getModel();
                model.setDataVector(initializeData(customer,type), columnNames);
                account_scroll = new JScrollPane(new JTable(model));
                break;
            case "loan":
                model = (DefaultTableModel) account_table.getModel();
                model.setDataVector(initializeData(customer,type), columnNames);
                account_scroll = new JScrollPane(new JTable(model));
                break;
        }

    }

    private Object[][] initializeData(Customer customer,String accountType) {
        int count = 0;
        Object[][] data = new Object[0][];
        ArrayList<Account> accounts = customer.getAccounts();
        switch (accountType){
            case "checking":
                data = new Object[customer.getCheckingNum()][2];
                for (int i = 0; i < accounts.size(); i++) {
                    if (accounts.get(i).getAccountType().equals("checking")) {
                        data[count][0] = accounts.get(i).getID();
                        data[count][1] = accounts.get(i).getAmount();
                        count++;
                    }
                }
                break;
            case "savings":
                data = new Object[customer.getSavingNum()][2];
                for (int i = 0; i < accounts.size(); i++) {
                    if (accounts.get(i).getAccountType().equals("savings")) {
                        data[count][0] = accounts.get(i).getID();
                        data[count][1] = accounts.get(i).getAmount();
                        count++;
                    }
                }
                break;
            case "securities":
                data = new Object[customer.getSecurititesNum()][2];
                for (int i = 0; i < accounts.size(); i++) {
                    if (accounts.get(i).getAccountType().equals("securities")) {
                        data[count][0] = accounts.get(i).getID();
                        data[count][1] = accounts.get(i).getAmount();
                        count++;
                    }
                }
                break;
            case "loan":
                data = new Object[customer.getLoanNum()][2];
                for (int i = 0; i < accounts.size(); i++) {
                    if (accounts.get(i).getAccountType().equals("loan")) {
                        data[count][0] = accounts.get(i).getID();
                        data[count][1] = accounts.get(i).getAmount();
                        count++;
                    }
                }
                break;
        }
        return data;
    }





    public void initUI(){
        wholePanel = new JPanel();
        wholePanel.setLayout(new GridLayoutManager(12, 1, new Insets(0, 0, 0, 0), -1, -1));
        wholePanel.setBackground(new Color(-524801));
        final Spacer spacer1 = new Spacer();
        wholePanel.add(spacer1, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));

        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 9, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-524801));
        wholePanel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        welcomeLabel = new JLabel();
        welcomeLabel.setForeground(new Color(-16777216));
        welcomeLabel.setText("XXX, here are your accounts:");


        panel1.add(welcomeLabel, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel1.add(spacer2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel1.add(spacer3, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        backButton = new JButton();
        backButton.setBackground(new Color(-1118482));
        backButton.setForeground(new Color(-16777216));
        backButton.setText("Back");
        panel1.add(backButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(40, -1), null, 0, false));


        final Spacer spacer4 = new Spacer();
        panel1.add(spacer4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel1.add(spacer5, new GridConstraints(0, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        panel1.add(spacer6, new GridConstraints(0, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        panel1.add(spacer7, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer8 = new Spacer();
        panel1.add(spacer8, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));


        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 10, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-524801));
        panel2.setForeground(new Color(-16777216));
        wholePanel.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setForeground(new Color(-16777216));
        label1.setText("Checking Account:");
        panel2.add(label1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        final Spacer spacer9 = new Spacer();
        panel2.add(spacer9, new GridConstraints(0, 9, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer10 = new Spacer();
        panel2.add(spacer10, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer11 = new Spacer();
        panel2.add(spacer11, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer12 = new Spacer();
        panel2.add(spacer12, new GridConstraints(0, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer13 = new Spacer();
        panel2.add(spacer13, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer14 = new Spacer();
        panel2.add(spacer14, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));

        account_num = new JLabel();
        account_num.setForeground(new Color(-16777216));
        account_num.setText("checking Label");
        panel2.add(account_num, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        open_button = new JButton();
        open_button.setBackground(new Color(-1118482));
        open_button.setForeground(new Color(-16777216));
        open_button.setText("+");
        panel2.add(open_button, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(16, 20), null, 0, false));


        delete_button = new JButton();
        delete_button.setBackground(new Color(-1118482));
        delete_button.setForeground(new Color(-16777216));
        delete_button.setText("-");
        panel2.add(delete_button, new GridConstraints(0, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(16, 20), null, 0, false));


        JLabel label111=new JLabel("Account type：");
        cmb = new JComboBox();
        cmb.addItem("--Select--");
        cmb.addItem("checking");
        cmb.addItem("savings");
        cmb.addItem("securities");
        cmb.addItem("loan");
        panel2.add(label111, new GridConstraints(0, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(16, 20), null, 0, false));
        panel2.add(cmb, new GridConstraints(0, 9, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(16, 20), null, 0, false));

        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-524801));
        wholePanel.add(panel3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        account_scroll = new JScrollPane();
        account_scroll.setEnabled(true);
        panel3.add(account_scroll, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        account_table  = new JTable();
        account_table.setEnabled(true);
        account_scroll.setViewportView(account_table);


        final Spacer spacer22 = new Spacer();
        panel3.add(spacer22, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer23 = new Spacer();
        panel3.add(spacer23, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));


    }



}
