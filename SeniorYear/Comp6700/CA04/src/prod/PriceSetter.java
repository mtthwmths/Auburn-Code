package prod;


public class PriceSetter {

	public void setNewPrice(Market m, String symbol, float newPrice){
		try {
			m.updateStockPrice(symbol, newPrice);
		}catch (StockMarketException e){
			System.out.println("Stock Market Violation Exception");
			System.out.println(e.getMessage());
		}
	}
}
