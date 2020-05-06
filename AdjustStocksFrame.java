import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
// GUI class used to produce frame where Manager can adjust the stock price of an existing stock
public class AdjustStocksFrame extends JFrame implements ActionListener {
    private Bank bank; 
    private JPanel panel = new JPanel(new GridLayout(3, 1)); 
    private JLabel tickerLabel = new JLabel("Enter Stock Ticker: "); 
    private JLabel priceLabel = new JLabel("Enter New Price ($): "); 
    private JTextField tickerField = new JTextField(); 
    private JTextField priceField = new JTextField();
    private JButton adjustPrice = new JButton("Adjust Price");
    private JButton backButton = new JButton("Back");
    private ManagerStockFrame managerStockFrame;
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

    public AdjustStocksFrame(ManagerStockFrame managerStockFrame, Bank bank) {
        this.bank = bank;
        this.managerStockFrame = managerStockFrame;
        panel.add(tickerLabel);
        panel.add(tickerField);
        panel.add(priceLabel);
        panel.add(priceField);
        panel.add(backButton);
        panel.add(adjustPrice);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        adjustPrice.addActionListener(this);
        backButton.addActionListener(this);
        add(panel, BorderLayout.CENTER);
        setTitle("Stock Price Adjustment"  + " - " + Bank.date);
        setSize(600, 150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == backButton){
            this.dispose();
            managerStockFrame.setVisible(true);
        }
        if(ae.getSource() == adjustPrice){
            boolean status = false;
            String stock = tickerField.getText();
            String priceText = priceField.getText();
            if("".equals(stock)){
                JOptionPane.showMessageDialog(rootPane, "Please enter the ticker."  );
            } else if("".equals(priceText)){
                JOptionPane.showMessageDialog(rootPane, "Please enter the price."  );
            }else {
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

    }


    
}
