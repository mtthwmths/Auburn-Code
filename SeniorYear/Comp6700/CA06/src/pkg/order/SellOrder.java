package pkg.order;

import pkg.exception.StockMarketException;
import pkg.trader.Trader;

public class SellOrder extends Order {
	public SellOrder(String stockSymbol, int size, double price, Trader trader) {
		this.orderNumber = getNextOrderNumber();
		setStockSymbol(stockSymbol);
		setSize(size);
		setPrice(price);
		setTrader(trader);
	}

	public SellOrder(String stockSymbol, int size, boolean isMarketOrder,
			Trader trader) throws StockMarketException {
		this.orderNumber = getNextOrderNumber();
		setStockSymbol(stockSymbol);
		setSize(size);
		if (isMarketOrder == true) {
			setMarketOrder(isMarketOrder);
			setPrice((float) 0.00);
		} else {
			throw new StockMarketException("Sell order for stock "
					+ stockSymbol + " placed without a valid price.");
		}
		setTrader(trader);
	}

	// @Override
	// public int compareTo(Order obj) {
	// SellOrder o = (SellOrder) obj;
	// if (o.isMarketOrder() && this.isMarketOrder()
	// || o.getPrice() == this.getPrice()) {
	// if (this.getOrderNumber() < o.getOrderNumber())
	// return 1;
	// else if (this.getOrderNumber() == o.getOrderNumber())
	// return 0;
	// else
	// return -1;
	// } else {
	// if (this.isMarketOrder())
	// return -1;
	// if (o.isMarketOrder())
	// return 1;
	// if (this.getPrice() < o.getPrice())
	// return 1;
	// else
	// return -1;
	// }
	// }

	public void printOrder() {
		System.out.println("Stock: " + ss + " $" + d + " x "
				+ size + " (Sell)");
	}
}
