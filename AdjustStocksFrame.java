import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class AdjustStocksFrame extends JFrame implements ActionListener {
    private Bank bank; 
    private JPanel panel = new JPanel(new GridLayout(3, 1)); 
    private JLabel tickerLabel = new JLabel("Enter Stock Ticker: "); 
    private JLabel priceLabel = new JLabel("Enter New Price ($): "); 
    private JTextField tickerField = new JTextField(); 
    private JTextField priceField = new JTextField();
    private JButton adjustPrice = new JButton("Adjust Price");  

    public AdjustStocksFrame(Bank bank) {
        this.bank = bank;

        panel.add(tickerLabel); 
        panel.add(tickerField); 
        panel.add(priceLabel); 
        panel.add(priceField); 
        panel.add(adjustPrice); 

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        adjustPrice.addActionListener(this);

        add(panel, BorderLayout.CENTER); 
        setTitle("Stock Price Adjustment"  + " - " + Bank.date); 
        setSize(600, 150); 
        setVisible(true); 
    }

    public void actionPerformed(ActionEvent ae) {
        boolean status = false; 
        String stock = tickerField.getText(); 
        double price = Double.parseDouble(priceField.getText()); 
        for (Stock s : bank.getStockMarket().getStocks()) {
            if (s.getTicker().equals(stock)) {
                status = true; 
                s.setCurrentPrice(new Dollar(price));
                JOptionPane.showMessageDialog(rootPane, "The price of " + s.getTicker() + " stock has successfully been set to " + s.getCurrentPrice().toString() + ".");
                PersistanceHandler p = new PersistanceHandler();
                p.saveState();
            }
        }
        if (!status) {
            JOptionPane.showMessageDialog(rootPane, "This stock does not exist");
        }
    }
    
}