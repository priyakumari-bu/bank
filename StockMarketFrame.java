
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// Customer side stock market GUI interface show stocks price and other infomatation
public class StockMarketFrame extends JFrame {
    private Bank bank;
    private JTable table_1 = new JTable();
    private JTable table_2 = new JTable();
    private JScrollPane scroll_1 = new JScrollPane();
    private JScrollPane scroll_2 = new JScrollPane();
    private JLabel titleLabel = new JLabel("Stock Market");
    private JPanel panel = new JPanel(new GridLayout(3, 1));
    private JComboBox choose_stock_cmb;
    private JComboBox choose_SecuritiesAccount_cmb;
    private JButton buyButton;
    private JButton sellButton;
    private JButton backButton = new JButton("Back");
    private JTextField amount_text;

    private Customer customer;
    private CustomerFrame customerFrame;
    private ManagerStockFrame managerStockFrame;
    public static int FORM_WIDTH = 850;
    public static int FORM_Height = 800;

    public StockMarketFrame(Bank bank) {
        this.bank = bank;
        String[] columns = new String[] {"NAME", "TICKER", "PRICE", "# SHARES"};
        Object[][] data = new Object[bank.getStockMarket().getStocks().size()][4];
        int i = 0;
        for (Stock s : bank.getStockMarket().getStocks()) {
            data[i][0] = s.getName();
            data[i][1] = s.getTicker();
            data[i][2] = s.getCurrentPrice();
            data[i][3] = s.getVolume();
            i++;
        }
        this.table_1 = new JTable(data,columns);
        panel.add(titleLabel);
        panel.add(new JScrollPane(table_1));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel, BorderLayout.CENTER);
        setTitle("Stock Market Details");
        setSize(FORM_WIDTH, FORM_Height);
        setVisible(true);
    }


    public StockMarketFrame(CustomerFrame customerFrame, Bank bank,  final Customer customer ) {
        this.bank = bank;
        this.customer=customer;
        this.customerFrame = customerFrame;
        initUI();
        initializeStock_cmb(bank);
        initMySecuritiesAccount(customer);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel, BorderLayout.CENTER);
        setTitle("Stock Market Details");
        setSize(FORM_WIDTH, FORM_Height);
        setVisible(true);
        refreshTables1();
        refreshTables2();

        // buy stock action
        buyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                String stock_ticker = choose_stock_cmb.getSelectedItem().toString();
                String accountNum = choose_SecuritiesAccount_cmb.getSelectedItem().toString().split(" ")[1];
                String amount = amount_text.getText();

                ArrayList<Account> savingsAccounts = customer.getAllAccountsByType("savings");
                Double total = 0.0;
                for (Account account :savingsAccounts){
                    total = total+account.getAmount().getValue();
                }
                if(total<2500){
                    JOptionPane.showMessageDialog(null, "Your savings account amount must be greater than 2500!");
                }else {
                    if(!amount.equals("")){
                        if(customer.getAllAccountsByType("securities").size()<=0){
                            JOptionPane.showMessageDialog(null, "Please create an account first!");
                        }else {
                            SecuritiesAccount securitiesAccount = (SecuritiesAccount) customer.getAccounts().get(customer.findAccount(accountNum));

                            ArrayList<Stock> stocks = bank.getStockMarket().getStocks();

                            Stock buyStock = null;
                            Stock original = null;
                            for(Stock stock:stocks){
                                if(stock.getTicker().equals(stock_ticker)){
                                    original = stock;
                                    buyStock = new Stock(stock.getName(), stock.getTicker(),stock.getCurrentPrice() , Integer.valueOf(amount));
                                }
                            }
                            if((securitiesAccount.getAmount().getValue() <= (buyStock.getCurrentPrice().convertTo(securitiesAccount.getAmount().getStringType())).getValue()*Integer.parseInt(amount))){
                                JOptionPane.showMessageDialog(null, "This account does not have enough money for this transaction!");
                            }else {
                                original.setVolume(original.getVolume() - Integer.valueOf(amount));
                                securitiesAccount.buy(buyStock,Integer.parseInt(amount));
                                refreshTables1();
                                refreshTables2();
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please fill in the purchase quantity!");
                    }
                }

            }
        });

        //sell stock action
        sellButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                String stock_ticker = choose_stock_cmb.getSelectedItem().toString();
                String accountNum = choose_SecuritiesAccount_cmb.getSelectedItem().toString().split(" ")[1];
                String amount = amount_text.getText();

                ArrayList<Account> savingsAccounts = customer.getAllAccountsByType("savings");
                Double total = 0.0;
                for (Account account :savingsAccounts){
                    total = total+account.getAmount().getValue();
                }
                if(total<2500){
                    JOptionPane.showMessageDialog(null, "Your savings account amount must be greater than 2500!");
                }else {

                    if (customer.getAllAccountsByType("securities").size() <= 0) {
                        JOptionPane.showMessageDialog(null, "Please create an account first.");
                    } else {
                        SecuritiesAccount securitiesAccount = (SecuritiesAccount) customer.getAccounts().get(customer.findAccount(accountNum));

                        ArrayList<Stock> stocks = bank.getStockMarket().getStocks();

                        Stock sellStock = null;

                        ArrayList<Stock> myStock = securitiesAccount.getStocks();
                        for (Stock stock : myStock) {
                            if (stock.getTicker().equals(stock_ticker)) {
                                sellStock = new Stock(stock.getName(), stock.getTicker(), stock.getCurrentPrice(), stock.getVolume());
                            }
                        }

                        if (null == sellStock) {
                            JOptionPane.showMessageDialog(null, "This account does not own this stock.");
                        } else if (sellStock.getVolume() < Integer.valueOf(amount)) {
                            JOptionPane.showMessageDialog(null, "This stock is not enough.");
                        } else {
                            for (Stock stock2 : stocks) {
                                if (stock2.getTicker().equals(sellStock.getTicker())) {
                                    stock2.setVolume(stock2.getVolume() + Integer.parseInt(amount));
                                }
                            }
                            securitiesAccount.sell(sellStock, Integer.parseInt(amount));
                            refreshTables1();
                            refreshTables2();
                        }
                    }
                }
            }
        });
        //back
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                PersistanceHandler p = new PersistanceHandler();
                p.saveState();
                closeFrame();
                getCustomerFrame().setVisible(true);
            }
        });
    }

    public StockMarketFrame(ManagerStockFrame managerStockFrame, Bank bank) {

        this.bank = bank;
        this.managerStockFrame = managerStockFrame;


//        String[] columns = new String[] {"NAME", "TICKER", "PRICE", "# SHARES"};
//        Object[][] data = new Object[bank.getStockMarket().getStocks().size()][4];
//        int i = 0;
//        for (Stock s : bank.getStockMarket().getStocks()) {
//            data[i][0] = s.getName();
//            data[i][1] = s.getTicker();
//            data[i][2] = s.getCurrentPrice();
//            data[i][3] = s.getVolume();
//            i++;
//        }
//        this.table_1 = new JTable(data,columns);
//        panel.add(titleLabel);

//        panel.add(new JScrollPane(table_1));
//        panel.add(backButton);
        initUI2();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel, BorderLayout.CENTER);
        setTitle("Stock Market Details");
        setSize(FORM_WIDTH, FORM_Height);
        setVisible(true);

        refreshTables1();
        //back
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                closeFrame();
                getManagerStockFrame().setVisible(true);
            }
        });
    }


    public void closeFrame() {
        this.dispose();
    }
    private CustomerFrame getCustomerFrame(){
        return this.customerFrame;
    }
    private ManagerStockFrame getManagerStockFrame(){
        return this.managerStockFrame;
    }

    private Customer getCustomer() {
        return customer;
    }

    private void initMySecuritiesAccount(Customer customer) {
        choose_SecuritiesAccount_cmb.removeAllItems();
        if(getCustomer().getAllAccountsByType("securities").size()>0){
            for (Account account : getCustomer().getAllAccountsByType("securities")){
                choose_SecuritiesAccount_cmb.addItem("(" + (account.getAmount()).toString() + ") " + account.getID());
            }
        }else {
            choose_SecuritiesAccount_cmb.addItem("Please create an account first.");
        }
    }



    private void initializeStock_cmb(Bank bank) {
        for (Stock s : bank.getStockMarket().getStocks()) {
            String ticker = s.getTicker();
            choose_stock_cmb.addItem(ticker);
        }
    }

    public void refreshTables1() {
        String[] columns = new String[] {"NAME", "TICKER", "PRICE", "# SHARES"};
        Object[][] data = new Object[Bank.getStockMarket().getStocks().size()][4];
        int i = 0;
        for (Stock s : Bank.getStockMarket().getStocks()) {
            data[i][0] = s.getName();
            data[i][1] = s.getTicker();
            data[i][2] = s.getCurrentPrice();
            data[i][3] = s.getVolume();
            i++;
        }
        DefaultTableModel  model = (DefaultTableModel) table_1.getModel();
        model.setDataVector(data, columns);
        scroll_1 = new JScrollPane(new JTable(model));
        initMySecuritiesAccount(customer);
    }

    public void refreshTables2() {
        String[] columns = new String[] {"name", "ticker", "privce", "volume"};
        Object[][] data = new Object[bank.getStockMarket().getStocks().size()][4];
        String accountNum = choose_SecuritiesAccount_cmb.getSelectedItem().toString().split(" ")[1];
        if(customer.findAccount(accountNum)!=-1){
            SecuritiesAccount securitiesAccount = (SecuritiesAccount) customer.getAccounts().get(customer.findAccount(accountNum));
            ArrayList<Stock> myStock = securitiesAccount.getStocks();
            int i = 0;
            for (Stock s : myStock) {

                data[i][0] = s.getName();
                data[i][1] = s.getTicker();
                data[i][2] = s.getCurrentPrice();
                data[i][3] = s.getVolume();
                i++;
            }
            DefaultTableModel  model = (DefaultTableModel) table_2.getModel();
            model.setDataVector(data, columns);
            scroll_2 = new JScrollPane(new JTable(model));
            initMySecuritiesAccount(customer);
        }
    }

    private void initUI(){
        panel.setBackground(new Color(-16777216));
        panel =  new JPanel();
        panel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel.setBackground(new Color(-524801));

        final JPanel panel4 = new JPanel();
        scroll_1 = new JScrollPane();
        scroll_1.setEnabled(true);
        panel4.add(scroll_1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table_1  = new JTable();
        table_1.setEnabled(true);
        scroll_1.setViewportView(table_1);
        panel.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));

        final JPanel panel3 = new JPanel();
        scroll_2 = new JScrollPane();
        scroll_2.setEnabled(true);
        panel3.add(scroll_2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table_2  = new JTable();
        table_2.setEnabled(true);
        scroll_2.setViewportView(table_2);
        panel.add(panel3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));


        final JPanel panel1 = new JPanel();
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-524801));
        panel.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel2.add( panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        panel1.setLayout(new GridLayoutManager(1, 9, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-524801));

        JLabel choose_stock_label = new JLabel();
        choose_stock_label.setBackground(new Color(-524801));
        choose_stock_label.setForeground(new Color(-16777216));
        choose_stock_label.setText("Choose stock:");
        panel1.add(choose_stock_label, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        choose_stock_cmb =  new JComboBox();
        panel1.add(choose_stock_cmb, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        JLabel choose_mySecuritiesAccount_label = new JLabel();
        choose_mySecuritiesAccount_label.setBackground(new Color(-524801));
        choose_mySecuritiesAccount_label.setForeground(new Color(-16777216));
        choose_mySecuritiesAccount_label.setText("My SecuritiesAccount:");
        panel1.add(choose_mySecuritiesAccount_label, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        choose_SecuritiesAccount_cmb =  new JComboBox();
        panel1.add(choose_SecuritiesAccount_cmb, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        JLabel num = new JLabel();
        num.setBackground(new Color(-524801));
        num.setForeground(new Color(-16777216));
        num.setText("Amount:");
        panel1.add(num, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        amount_text = new JTextField();
        amount_text.setBackground(new Color(-1118482));
        amount_text.setForeground(new Color(-4473925));
        amount_text.setText("");
        panel1.add(amount_text, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(30, -1), null, 0, false));

        buyButton = new JButton();
        buyButton.setBackground(new Color(-1118482));
        buyButton.setForeground(new Color(-16777216));
        buyButton.setText("BUY");
        panel1.add(buyButton, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(40, -1), null, 0, false));

        sellButton = new JButton();
        sellButton.setBackground(new Color(-1118482));
        sellButton.setForeground(new Color(-16777216));
        sellButton.setText("SELL");
        panel1.add(sellButton, new GridConstraints(0, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(40, -1), null, 0, false));

        backButton = new JButton();
        backButton.setBackground(new Color(-1118482));
        backButton.setForeground(new Color(-16777216));
        backButton.setText("Back");
        panel1.add(backButton, new GridConstraints(0, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(40, -1), null, 0, false));
    }


    private void initUI2(){
        panel.setBackground(new Color(-16777216));
        panel =  new JPanel();
        panel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel.setBackground(new Color(-524801));

        final JPanel panel4 = new JPanel();
        scroll_1 = new JScrollPane();
        scroll_1.setEnabled(true);
        panel4.add(scroll_1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table_1  = new JTable();
        table_1.setEnabled(true);
        scroll_1.setViewportView(table_1);
        panel.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));

//        final JPanel panel3 = new JPanel();
//        scroll_2 = new JScrollPane();
//        scroll_2.setEnabled(true);
//        panel3.add(scroll_2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
//        table_2  = new JTable();
//        table_2.setEnabled(true);
//        scroll_2.setViewportView(table_2);
//        panel.add(panel3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));


        final JPanel panel1 = new JPanel();
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-524801));
        panel.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel2.add( panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        panel1.setLayout(new GridLayoutManager(1, 9, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-524801));

//        JLabel choose_stock_label = new JLabel();
//        choose_stock_label.setBackground(new Color(-524801));
//        choose_stock_label.setForeground(new Color(-16777216));
//        choose_stock_label.setText("Choose stock:");
//        panel1.add(choose_stock_label, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
//        choose_stock_cmb =  new JComboBox();
//        panel1.add(choose_stock_cmb, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
//        JLabel choose_mySecuritiesAccount_label = new JLabel();
//        choose_mySecuritiesAccount_label.setBackground(new Color(-524801));
//        choose_mySecuritiesAccount_label.setForeground(new Color(-16777216));
//        choose_mySecuritiesAccount_label.setText("My SecuritiesAccount:");
//        panel1.add(choose_mySecuritiesAccount_label, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
//        choose_SecuritiesAccount_cmb =  new JComboBox();
//        panel1.add(choose_SecuritiesAccount_cmb, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
//
//        JLabel num = new JLabel();
//        num.setBackground(new Color(-524801));
//        num.setForeground(new Color(-16777216));
//        num.setText("Amount:");
//        panel1.add(num, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
//
//        amount_text = new JTextField();
//        amount_text.setBackground(new Color(-1118482));
//        amount_text.setForeground(new Color(-4473925));
//        amount_text.setText("");
//        panel1.add(amount_text, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(30, -1), null, 0, false));
//
//        buyButton = new JButton();
//        buyButton.setBackground(new Color(-1118482));
//        buyButton.setForeground(new Color(-16777216));
//        buyButton.setText("BUY");
//        panel1.add(buyButton, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(40, -1), null, 0, false));
//
//        sellButton = new JButton();
//        sellButton.setBackground(new Color(-1118482));
//        sellButton.setForeground(new Color(-16777216));
//        sellButton.setText("SELL");
//        panel1.add(sellButton, new GridConstraints(0, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(40, -1), null, 0, false));

        backButton = new JButton();
        backButton.setBackground(new Color(-1118482));
        backButton.setForeground(new Color(-16777216));
        backButton.setText("Back");
        panel1.add(backButton, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(40, -1), null, 0, false));
    }

}
