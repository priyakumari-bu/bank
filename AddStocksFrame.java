import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class AddStocksFrame extends JFrame implements ActionListener {
    private Bank bank; 
    private JPanel panel = new JPanel(new GridLayout(5, 1)); 
    private JLabel companyLabel = new JLabel("Enter Company Name: "); 
    private JLabel tickerLabel = new JLabel("Enter Ticker Symbol: "); 
    private JLabel priceLabel = new JLabel("Enter Price: "); 
    private JLabel sharesLabel = new JLabel("Enter Number of Shares: "); 
    private JTextField companyField = new JTextField(); 
    private JTextField tickerField = new JTextField(); 
    private JTextField priceField = new JTextField(); 
    private JTextField sharesField = new JTextField(); 
    private JButton addStock = new JButton("Add Stock"); 

    public AddStocksFrame(Bank bank) {
        this.bank = bank; 

        panel.add(companyLabel); 
        panel.add(companyField); 
        panel.add(tickerLabel); 
        panel.add(tickerField); 
        panel.add(priceLabel); 
        panel.add(priceField); 
        panel.add(sharesLabel);
        panel.add(sharesField); 
        panel.add(addStock); 

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addStock.addActionListener(this);

        add(panel, BorderLayout.CENTER); 
        setTitle("Stock Price Adjustment"  + " - " + Bank.date); 
        setSize(600, 200); 
        setVisible(true); 
    }

    public void actionPerformed(ActionEvent ae) {
        String name = companyField.getText(); 
        String ticker = tickerField.getText(); 
        double price = Double.parseDouble(priceField.getText()); 
        int volume = Integer.parseInt(sharesField.getText()); 

        Stock newStock = new Stock(name, ticker, new Dollar(price), volume); 
        bank.getStockMarket().getStocks().add(newStock);
        PersistanceHandler p = new PersistanceHandler();
        p.saveState(); 
        JOptionPane.showMessageDialog(rootPane, "Stock " + ticker + " has successfully been added to the Stock Market!");
    }


    
}