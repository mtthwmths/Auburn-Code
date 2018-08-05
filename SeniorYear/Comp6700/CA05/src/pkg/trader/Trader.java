package pkg.trader;

import java.util.ArrayList;

import pkg.exception.StockMarketException;
import pkg.market.Market;
import pkg.order.BuyOrder;
import pkg.order.Order;
import pkg.order.OrderType;
import pkg.order.SellOrder;

public class Trader {
	// Name of the trader
	String name;
	// Cash left in the trader's hand
	double cashInHand;
	// Stocks owned by the trader
	ArrayList<Order> position;
	// Orders placed by the trader
	ArrayList<Order> ordersPlaced;

	public Trader(String name, double cashInHand) {
		super();
		this.name = name;
		this.cashInHand = cashInHand;
		this.position = new ArrayList<Order>();
		this.ordersPlaced = new ArrayList<Order>();
	}

	//TODO rearrange
	public void buyFromBank(Market m, String symbol, int volume)
			throws StockMarketException {
		// Buy stock straight from the bank
		// Need not place the stock in the order list
		double price = m.getStockForSymbol(symbol).getPrice();
		// If the stock's price is larger than the cash possessed, then an
		// exception is thrown
		if ((price*volume) > this.cashInHand) {
		throw new StockMarketException("Cannot place order for stock: " + symbol + " since there is not enough money."
				+ " Trader: " + this.name);
		}
		this.position.add(new BuyOrder(symbol, volume, price, this));
		// Adjust cash possessed since the trader spent money to purchase a
		// stock.
		this.cashInHand -= (price*volume);
		
	}

	//TODO rearrange
	public void placeNewOrder(Market m, String symbol, int volume,
			double price, OrderType orderType) throws StockMarketException {
		if (orderType == OrderType.BUY) {
			// If the stock's price is larger than the cash possessed, then an
			// exception is thrown
			if ((price*volume) > this.cashInHand) {
			throw new StockMarketException("Cannot place order for stock: " + symbol + " since there is not enough money."
						+ " Trader: " + this.name);
			}
			// A trader cannot place two orders for the same stock, throw an
			// exception if there are multiple orders for the same stock.
			for(int i = 0; i < this.ordersPlaced.size(); i++) {
				if (this.ordersPlaced.get(i).getStockSymbol().equals(symbol)) {
					throw new StockMarketException("Cannot place order for stock: " + symbol + " since this trader already has"
							+ "an order for this stock. Trader: " + this.name);
				}
			}
			Order order = new BuyOrder(symbol, volume, price, this);
			// Place a new order and add to the orderlist
			this.ordersPlaced.add(order);
			// Also enter the order into the orderbook of the market.
			m.addOrder(order);
			// Note that no trade has been made yet. The order is in suspension
			// until a trade is triggered.
			//
		}
		// Also a person cannot place a sell order for a stock that he does not
		// own. Or he cannot sell more stocks than he possesses. Throw an
		// exception in these cases.
		if (orderType == OrderType.SELL) {
			boolean owns = false;
			int numberStocksOwned = 0;
			for (int i = 0; i < this.position.size(); i++) {
				if (this.position.get(i).getStockSymbol() == symbol) {
					owns = true;
					numberStocksOwned = this.position.get(i).getSize();
				}
			}
			if (!owns) {
				throw new StockMarketException("Cannot place order for stock: " + symbol + " since this trader does not"
						+ "own this stock. Trader: " + this.name);
			}
			if (numberStocksOwned < volume){
				throw new StockMarketException("Cannot place order for stock: " + symbol + " since this trader does not"
						+ "own enough shares of this stock. Trader: " + this.name);
			}
			Order order = new SellOrder(symbol, volume, price, this);
			this.ordersPlaced.add(order);
			m.addOrder(order);
		}
	}

	public void placeNewMarketOrder(Market m, String symbol, int volume,
			double price, OrderType orderType) throws StockMarketException {
		// Similar to the other method, except the order is a market order
		if (orderType == OrderType.BUY) {
			// If the stock's price is larger than the cash possessed, then an
			// exception is thrown
			if ((price*volume) > this.cashInHand) {
			throw new StockMarketException("Cannot place order for stock: " + symbol + " since there is not enough money."
						+ " Trader: " + this.name);
			}
			// A trader cannot place two orders for the same stock, throw an
			// exception if there are multiple orders for the same stock.
			for(int i = 0; i < this.ordersPlaced.size(); i++) {
				if (this.ordersPlaced.get(i).getStockSymbol().equals(symbol)) {
					throw new StockMarketException("Cannot place order for stock: " + symbol + " since this trader already has"
							+ "an order for this stock. Trader: " + this.name);
				}
			}
			Order order = new BuyOrder(symbol, volume, true, this);
			// Place a new order and add to the orderlist
			this.ordersPlaced.add(order);
			// Also enter the order into the orderbook of the market.
			m.addOrder(order);
			// Note that no trade has been made yet. The order is in suspension
			// until a trade is triggered.
			//
		}
		// Also a person cannot place a sell order for a stock that he does not
		// own. Or he cannot sell more stocks than he possesses. Throw an
		// exception in these cases.
		if (orderType == OrderType.SELL) {
			boolean owns = false;
			int numberStocksOwned = 0;
			for (int i = 0; i < this.position.size(); i++) {
				if (this.position.get(i).getStockSymbol() == symbol) {
					owns = true;
					numberStocksOwned = this.position.get(i).getSize();
				}
			}
			if (!owns) {
				throw new StockMarketException("Cannot place order for stock: " + symbol + " since this trader does not"
						+ "own this stock. Trader: " + this.name);
			}
			if (numberStocksOwned < volume){
				throw new StockMarketException("Cannot place order for stock: " + symbol + " since this trader does not"
						+ "own enough shares of this stock. Trader: " + this.name);
			}
			Order order = new SellOrder(symbol, volume, true, this);
			this.ordersPlaced.add(order);
			m.addOrder(order);
		}
	}

	public void tradePerformed(Order o, double matchPrice)
			throws StockMarketException {
		// Notification received that a trade has been made, the parameters are
		// the order corresponding to the trade, and the match price calculated
		// in the order book. Note than an order can sell some of the stocks he
		// bought, etc. Or add more stocks of a kind to his position. Handle
		// these situations.

		// Update the trader's orderPlaced, position, and cashInHand members
		// based on the notification.
		for (int i = 0; i < this.ordersPlaced.size(); i++) {
			if (this.ordersPlaced.get(i).equals(o)) {
				this.position.add(o);
				this.ordersPlaced.remove(i);
			}
		}
		this.cashInHand -= o.getPrice();
	}

	public void printTrader() {
		System.out.println("Trader Name: " + name);
		System.out.println("=====================");
		System.out.println("Cash: " + cashInHand);
		System.out.println("Stocks Owned: ");
		for (Order o : position) {
			o.printStockNameInOrder();
		}
		System.out.println("Stocks Desired: ");
		for (Order o : ordersPlaced) {
			o.printOrder();
		}
		System.out.println("+++++++++++++++++++++");
		System.out.println("+++++++++++++++++++++");
	}
}
