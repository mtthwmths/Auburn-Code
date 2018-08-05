package pkg.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import pkg.client.Pair;
import pkg.exception.StockMarketException;
import pkg.market.Market;
import pkg.market.api.PriceSetter;

/**
 *
 * OrderBook is a java class to create maintain and process orderbooks for a 
 *  {@link #Market} in the StockMarketCA06 java project.
 * <p>
 * @author Matt
 * @version 1.0
 * @since 4-19-2015
 * 
 */
public class OrderBook {
	Market m;
	HashMap<String, ArrayList<Order>> buyOrders;
	HashMap<String, ArrayList<Order>> sellOrders;
	
	/**
	 * OrderBook: constructs an {@linkplain #OrderBook} to manage buy and sell 
	 * orders for a {@linkplain #Client} in a given {@linkplain Market} m.
	 * 
	 * @param {@linkplain Market} m
	 */
	public OrderBook(Market m) {
		this.m = m;
		buyOrders = new HashMap<String, ArrayList<Order>>();
		sellOrders = new HashMap<String, ArrayList<Order>>();
	}
	
	/**
	 * addToOrderBook: adds either a {@linkplain #BuyOrder} or a 
	 * {@linkplain #SellOrder} to the orders. <p>
	 * method will do nothing if input isn't a {@linkplain #BuyOrder} or a 
	 * {@linkplain #SellOrder}.
	 * @param order
	 */
	public void addToOrderBook(Order order) {
		String stockSymbol = order.getStockSymbol();
		if (order instanceof BuyOrder) {
			ArrayList<Order> buyOrderList = null;
			if (buyOrders.containsKey(stockSymbol)) {
				buyOrderList = buyOrders.remove(stockSymbol);
			} else {
				buyOrderList = new ArrayList<Order>();
			}
			buyOrderList.add(order);
			buyOrders.put(stockSymbol, buyOrderList);
		} else if (order instanceof SellOrder) {
			ArrayList<Order> sellOrderList = null;
			if (sellOrders.containsKey(stockSymbol)) {
				sellOrderList = sellOrders.remove(stockSymbol);
			} else {
				sellOrderList = new ArrayList<Order>();
			}
			sellOrderList.add(order);
			sellOrders.put(stockSymbol, sellOrderList);
		}
	}
	
	/**
	 * trade: performs the actions recorded in an {@linkplain #OrderBook}.
	 */
	@SuppressWarnings("unchecked")
	public void trade() {
		Set<String> stocksToBuy = buyOrders.keySet();

		for (String stockToBuy : stocksToBuy) {
			if (!sellOrders.containsKey(stockToBuy)) {
				continue;
			}
			ArrayList<Order> buyO = buyOrders.remove(stockToBuy);
			ArrayList<Order> sellO = sellOrders.remove(stockToBuy);
			TreeMap<Double, Pair<Integer, Integer>> orderCumulative = new TreeMap<Double, Pair<Integer, Integer>>();
			calculateCumulative(buyO, sellO, orderCumulative);
			double matchPrice = findMatchPrice(orderCumulative);
			// Perform trade
			ArrayList<Order> buyOrderTempArrayList = (ArrayList<Order>) buyO
					.clone();
			ArrayList<Order> sellOrderTempArrayList = (ArrayList<Order>) sellO
					.clone();
			if (matchPrice > 0.0) {
				PriceSetter ps = new PriceSetter();
				ps.registerObserver(m.getMarketHistory());
				m.getMarketHistory().setSubject(ps);
				ps.setNewPrice(m, stockToBuy, matchPrice);
			}

			for (Order buyOrder : buyOrderTempArrayList) {
				if (buyOrder.getPrice() >= matchPrice
						|| buyOrder.isMarketOrder()) {
					// May have to redo this
					buyO.remove(buyOrder);
					try {
						buyOrder.getTrader().tradePerformed(buyOrder,
								matchPrice);
					} catch (StockMarketException e) {
						e.printStackTrace();
					}
				}
			}
			buyOrders.put(stockToBuy, buyO);

			for (Order sellOrder : sellOrderTempArrayList) {
				if (sellOrder.getPrice() <= matchPrice
						|| sellOrder.isMarketOrder()) {
					// May have to redo this
					sellO.remove(sellOrder);
					try {
						sellOrder.getTrader().tradePerformed(sellOrder,
								matchPrice);
					} catch (StockMarketException e) {
						e.printStackTrace();
					}
				}
			}
			sellOrders.put(stockToBuy, sellO);
		}
	}

	/**
	 * findMatchPrice: given a {@linkplain #TreeMap}<{@linkplain #Double}, 
	 * {@linkplain #Pair}<{@linkplain #Integer}, {@linkplain #Integer}>>,
	 * retrns a double representing the best price.
	 * @param orderCumulative
	 * @return {@linkplain #Double}
	 */
	private double findMatchPrice(
			TreeMap<Double, Pair<Integer, Integer>> orderCumulative) {
		// Initialize match price to 0
		double matchPrice = (float) 0.0;
		// No stocks matched/ traded
		int matchQuantity = 0;
		for (double orderPrice : orderCumulative.keySet()) {
			Pair<Integer, Integer> orderPair = orderCumulative.get(orderPrice);
			if (orderPair.getLeft() <= orderPair.getRight()) {
				if (orderPair.getLeft() > matchQuantity) {
					matchPrice = orderPrice;
					matchQuantity = orderPair.getLeft();
				}
			} else if (orderPair.getLeft() > orderPair.getRight()) {
				if (orderPair.getRight() > matchQuantity) {
					matchPrice = orderPrice;
					matchQuantity = orderPair.getRight();
				}
			}
		}
		return matchPrice;
	}

	/**
	 * calculateCumulative: gives the total number of orders to buy/sell for 
	 * this {@linkplain #orderBook}.
	 * @param buyOrderList
	 * @param sellOrderList
	 * @param orderCumulative
	 */
	private void calculateCumulative(ArrayList<Order> buyOrderList,
			ArrayList<Order> sellOrderList,
			TreeMap<Double, Pair<Integer, Integer>> orderCumulative) {
		enterBuyCumulativeOrders(buyOrderList, orderCumulative);

		int totalSells = 0;
		for (Order sellOrder : sellOrderList) {
			totalSells += sellOrder.getSize();
			double sellPrice = sellOrder.getPrice();
			// If it is a sell market order, put it in the end
			if (orderCumulative.containsKey(sellPrice)) {
				Pair<Integer, Integer> cumulativePair = orderCumulative
						.remove(sellPrice);
				orderCumulative.put(sellPrice, new Pair<Integer, Integer>(
						cumulativePair.getLeft(), totalSells));
			} else {
				if (sellOrder.isMarketOrder()) {
					orderCumulative.put(sellPrice, new Pair<Integer, Integer>(
							0, totalSells));
				} else {
					Double nearestHighKey = orderCumulative
							.ceilingKey(sellPrice);
					if (nearestHighKey != null) {
						Pair<Integer, Integer> cumulativePair = orderCumulative
								.remove(nearestHighKey);
						orderCumulative.put(
								nearestHighKey,
								new Pair<Integer, Integer>(cumulativePair
										.getLeft(), totalSells));
					} else {
						orderCumulative.put(sellPrice,
								new Pair<Integer, Integer>(0, totalSells));
					}
				}
			}
		}
	}

	
	/**
	 * enterBuyCumulativeOrders: no idea what this does... (Matt)
	 * 
	 * @param buyOrderList
	 * @param orderCumulative
	 */
	private void enterBuyCumulativeOrders(ArrayList<Order> buyOrderList,
			TreeMap<Double, Pair<Integer, Integer>> orderCumulative) {
		int totalBuys = 0;
		for (Order buyOrder : buyOrderList) {
			totalBuys += buyOrder.getSize();
			Pair<Integer, Integer> cumulativePair = new Pair<Integer, Integer>(
					totalBuys, 0);
			double price = buyOrder.getPrice();
			orderCumulative.put(price, cumulativePair);
		}
		// Reset the buy market order
		if (orderCumulative.containsKey(0.0)) {
			Pair<Integer, Integer> cumulativePair = orderCumulative.remove(0.0);
			Double d = orderCumulative.lastKey();
			orderCumulative.put((Double) (d + 1.00),
					new Pair<Integer, Integer>(cumulativePair.getLeft(), 0));
		}
	}
}
