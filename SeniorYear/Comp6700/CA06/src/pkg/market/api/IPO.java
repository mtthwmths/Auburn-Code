package pkg.market.api;
import pkg.exception.StockMarketException;
import pkg.market.Market;
import pkg.stock.Stock;

public class IPO {
	public static void enterNewStock(Market m, String s, String ss,
			double sss) {
		Stock ssss = new Stock(s, ss, sss);
		try {
			m.addStock(ssss);
		} catch (StockMarketException e) {
			e.printStackTrace();
		}
	}
}
