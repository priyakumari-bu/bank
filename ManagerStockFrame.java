import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;

public class ManagerStockFrame extends JFrame implements ActionListener {
    private Bank bank; 
    private JPanel panel = new JPanel(new GridLayout(3, 1)); 
    private JLabel titleLabel = new JLabel("STOCK MARKET\n"); 
    private JButton viewMarket = new JButton("View the Stock Market"); 
    private JButton adjustPrice = new JButton("Adjust Stock Price"); 
    private JButton addStock = new JButton("Add a New Stock"); 

    public ManagerStockFrame(Bank bank) {
        this.bank = bank;

        panel.add(titleLabel); 
        panel.add(viewMarket); 
        panel.add(adjustPrice); 
        panel.add(addStock); 

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 

        viewMarket.addActionListener(this);
        adjustPrice.addActionListener(this);
        addStock.addActionListener(this); 

        add(panel, BorderLayout.CENTER); 
        setTitle("Stock Market Details"  + " - " + Bank.date); 
        setSize(600, 150); 
        setVisible(true); 
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == viewMarket) {
            StockMarketFrame frame = new StockMarketFrame(bank); 
        } else if (ae.getSource() == adjustPrice) {
            AdjustStocksFrame frame = new AdjustStocksFrame(bank); 
        } else if (ae.getSource() == addStock) {
            AddStocksFrame frame = new AddStocksFrame(bank); 
        }
    }
    
}