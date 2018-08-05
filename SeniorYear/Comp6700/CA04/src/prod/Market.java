package prod;
import java.util.*;
import java.util.Map.Entry;


public class Market {
	private String marketName;
	private HashMap <String, Stock> stockList;
	private MarketHistory marketHistory;
	
	public Market(String name){
		marketName = name;
		stockList = new HashMap<String, Stock>();
		marketHistory = new MarketHistory(this);
	}
	
	public void addStock(Stock stock) throws StockMarketException{
		if(stockList.containsKey(stock.getSymbol())){
			throw new StockMarketException(
				"Tried to enter a stock that is already present ("
				+ stock.getSymbol() + ")");
		}
		if (stock.getPrice() < 0) {
			throw new StockMarketException("Stock has a negative price (" +
				stock.getSymbol() + ", " + stock.getPrice() + ")");
		}
		
		stockList.put(stock.getSymbol(), stock);
		marketHistory.startHistoryWithPrice(stock.getSymbol(), stock.getPrice());		
	}
	
	public Stock getStockForSymbol(String symbol){
		return stockList.get(symbol);
	}
	
	public Stock removeStockFromStockList(String symbol) throws StockMarketException{	
		if (!stockList.containsKey(symbol)){
			throw new StockMarketException ("Stock not present (" + symbol + ")");
		}
		return stockList.remove(symbol);
	}
	
	public void updateStockPrice(String symbol, float newPrice)
			throws StockMarketException	{
		if (!stockList.containsKey(symbol)){
			throw new StockMarketException ("Stock not present (" + symbol + ")");
		}
		if (newPrice < 0) {
			throw new StockMarketException(
				"Stock price cannot be set to a negative value (" +
				symbol + ", " + getStockForSymbol(symbol).getPrice()
				+ ") -> " + newPrice + " X Not Allowed ");
		}
		
		Stock thisStock = stockList.remove(symbol);
		thisStock.setPrice(newPrice);
		stockList.put(symbol, thisStock);
		marketHistory.updatePriceHistory(symbol, newPrice);		
	}
	
	public void printStocks(){
		System.out.println(marketName);
		for (Entry<String, Stock> entry : stockList.entrySet())
		{
			Stock thisStock = entry.getValue();
			System.out.print("(" + thisStock.getSymbol() + ", " + thisStock.getPrice() + ") ");
		}	
		System.out.println();
	}
	
	public void printHistoryFor(String symbol){
		if (!stockList.containsKey(symbol)){
			//do nothing. This stock is not present
		} else {
		ArrayList<Float> prices = marketHistory.getPriceFor(symbol);
		System.out.println("Stock Name: " + symbol + " in Market: " + marketName);
		for (int i = 0; i < prices.size(); i++){
			if (i == prices.size() - 1){ //Don't print the "-" because it's the last one
				System.out.print(prices.get(i));
			} else {
				System.out.print(prices.get(i) + " - ");
			}	
		}
		System.out.println();
		}
	}
	
	public void setMarketName(String name){
		marketName = name;
	}
	public String getMarketName(){
		return marketName;
	}
	
}
