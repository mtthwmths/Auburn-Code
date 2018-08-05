
package pkg.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import pkg.exception.StockMarketExpection;
import pkg.market.Market;
import pkg.market.api.PriceSetter;
import pkg.trader.Trader;

public class OrderBook implements Comparator<Order> {
	Market m;
	HashMap<String, ArrayList<Order>> buyOrders;
	HashMap<String, ArrayList<Order>> sellOrders;
	ArrayList<Order> stockArray;

	public OrderBook(Market m) {
		this.m = m;
		buyOrders = new HashMap<String, ArrayList<Order>>();
		sellOrders = new HashMap<String, ArrayList<Order>>();
		stockArray = new ArrayList<Order>();
	}

	public void addToOrderBook(Order order) {
		// Populate the buyOrders and sellOrders data structures, whichever
		// appropriate
		if (order instanceof BuyOrder) {
			if (buyOrders.get(order.getStockSymbol()) != null) {
				stockArray = buyOrders.get(order.getStockSymbol());
				stockArray.add(order);
			} else {
				stockArray = new ArrayList<Order>();
				stockArray.add(order);
			}
			buyOrders.put(order.getStockSymbol(), stockArray);
		} else {
			if (sellOrders.get(order.getStockSymbol()) != null) {
				stockArray = sellOrders.get(order.getStockSymbol());
				stockArray.add(order);
			} else {
				stockArray = new ArrayList<Order>();
				stockArray.add(order);
			}
			sellOrders.put(order.getStockSymbol(), stockArray);
		}

	}

	public int compare(Order o1, Order o2) {
		if (o1.getPrice() == o2.getPrice()) {
			return 0;
		}
		return o1.getPrice() > o2.getPrice() ? 1 : -1;
	}

	public void trade()  {
		// Complete the trading.
		// 1. Follow and create the orderbook data representation (see spec)
		// 2. Find the matching price
		// 3. Update the stocks price in the market using the PriceSetter.
		// Note that PriceSetter follows the Observer pattern. Use the pattern.
		// 4. Remove the traded orders from the orderbook
		// 5. Delegate to trader that the trade has been made, so that the
		// trader's orders can be placed to his possession (a trader's position
		// is the stocks he owns)
		// (Add other methods as necessary)
		String symbolForCompare = "";
		@SuppressWarnings("unchecked")
		ArrayList<Order> temp = (ArrayList<Order>) stockArray.clone();
		Iterator<Order> it = temp.iterator();
		while (it.hasNext()){
			Order stockSymbol = it.next();	
			if(symbolForCompare != stockSymbol.getStockSymbol()){
				symbolForCompare = stockSymbol.getStockSymbol();
				ArrayList<Order> buyOrdersArray = buyOrders.get(symbolForCompare);
				ArrayList<Order> sellOrdersArray = sellOrders.get(symbolForCompare);
				HashMap<Double, Double> cumBuyHash = new HashMap<Double, Double>();
				HashMap<Double, Double> cumSellHash = new HashMap<Double, Double>();
				ArrayList<Double> priceList = new ArrayList<Double>();
				double cumBuy = 0;
				double cumSell = 0;
				double maxNumberOfTrades = 0;
				double maxPrice = 0;
				PriceSetter ps = new PriceSetter();
				ps.registerObserver(m.getMarketHistory());
				m.getMarketHistory().setSubject(ps);
				Collections.sort(buyOrdersArray, this);
				Collections.sort(sellOrdersArray, this);
				Collections.reverse(sellOrdersArray);
				
		
				for (int i = 0; i < buyOrdersArray.size() && i < sellOrdersArray.size(); i++){
					if (buyOrdersArray.get(i).getPrice() >= sellOrdersArray.get(i).getPrice()){
						priceList.add(buyOrdersArray.get(i).getPrice());
					}
					else {
						priceList.add(sellOrdersArray.get(i).getPrice());
					}
					if (buyOrdersArray.get(i) != null){
						cumBuy += buyOrdersArray.get(i).getSize();
						cumBuyHash.put(buyOrdersArray.get(i).getPrice(), cumBuy);
					}
					if (sellOrdersArray.get(i) != null){
						cumSell += sellOrdersArray.get(i).getSize();
						cumSellHash.put(sellOrdersArray.get(i).getPrice(), cumSell);
					}
				}

				for (int j = 0; j < priceList.size(); j++){
					cumBuy = cumBuyHash.get(priceList.get(j));
					if(cumSellHash.containsKey(priceList.get(j))){
						cumSell = cumSellHash.get(priceList.get(j));
					}
					if(cumBuy > cumSell){
						if (cumSell > maxNumberOfTrades){
							maxNumberOfTrades = cumSell;
							maxPrice = priceList.get(j);
						}
					}
					else {
						if (cumBuy > maxNumberOfTrades){
							maxNumberOfTrades = cumBuy;
							maxPrice = priceList.get(j);

						}
					}
				}


				ps.setNewPrice(m, buyOrdersArray.get(0).getStockSymbol(), maxPrice);
				
				try{
					for(int k = 0; k < buyOrdersArray.size(); k++){
						if (buyOrdersArray.get(k).getPrice() >= maxPrice){
							Trader trader = buyOrdersArray.get(k).getTrader();
							if (trader != null)
							trader.tradePerformed(buyOrdersArray.get(k), maxPrice);
						} else if (buyOrdersArray.get(k).isMarketOrder){
							Trader trader = buyOrdersArray.get(k).getTrader();
							if (trader != null)
							trader.tradePerformed(buyOrdersArray.get(k), maxPrice);
							
						}
						
					}
					for (int k = 0;  k < sellOrdersArray.size(); k ++){
						if (sellOrdersArray.get(k).getPrice() <= maxPrice){
							Trader trader = sellOrdersArray.get(k).getTrader();
							if (trader != null)
							trader.tradePerformed(sellOrdersArray.get(k), maxPrice);
						} else if (sellOrdersArray.get(k).isMarketOrder){
							Trader trader = sellOrdersArray.get(k).getTrader();
							if (trader != null)
							trader.tradePerformed(sellOrdersArray.get(k), maxPrice);
							
						}
					}
				}catch(StockMarketExpection e){
					e.printStackTrace();
				}
			}
		}
	}
}



