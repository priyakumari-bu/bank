// interface implemented by Stock that represents a stock's ability to be bought and sold (traded) 
// created so that future derivatives like bonds or options can also be traded in the market 
interface Tradeable {

    public int getVolume();
    
    public void setVolume(int volume);
    
}
