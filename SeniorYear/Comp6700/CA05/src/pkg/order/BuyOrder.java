package pkg.order;

import pkg.exception.StockMarketException;
import pkg.trader.Trader;

public class BuyOrder extends Order {

	private String type = "Buy";

	public BuyOrder(String stockSymbol, int size, double price, Trader trader) {
		this.stockSymbol = stockSymbol;
		this.size = size;
		this.price = price;
		this.trader = trader;
	}

	public BuyOrder(String stockSymbol, int size, boolean isMarketOrder,
			Trader trader) throws StockMarketException {
		if (isMarketOrder == false) {
			throw new StockMarketException("Buy order has been placed without a valid price");
		}
		this.stockSymbol = stockSymbol;
		this.size = size;
		this.isMarketOrder = true;
		this.trader = trader;
		this.price = 0.0;
	}

	public void printOrder() {
		System.out.println("Stock: " + stockSymbol + " $" + price + " x "
				+ size + " (Buy)");
	}
	
	public String getType() {
		return this.type;
	}

}
