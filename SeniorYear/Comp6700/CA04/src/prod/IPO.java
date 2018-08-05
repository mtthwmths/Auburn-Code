package prod;


public class IPO {

	
	public static void enterNewStock(Market m, String symbol, String name, float ipoPrice){
		try{
			Stock stock = new Stock(symbol, name, ipoPrice);
			m.addStock(stock);
			
		} catch (StockMarketException e){
			System.out.println("Stock Market Violation Exception");
			System.out.println(e.getMessage());
		}
	}
}
