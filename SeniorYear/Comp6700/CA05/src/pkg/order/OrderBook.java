
package pkg.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;

import pkg.market.Market;
import pkg.stock.Stock;

public class OrderBook implements Comparator<Order> {
	Market m;
	HashMap<String, ArrayList<Order>> buyOrders;
	HashMap<String, ArrayList<Order>> sellOrders;
	ArrayList<Order> stockArray;
	

	public OrderBook(Market m) {
		this.m = m;
		buyOrders = new HashMap<String, ArrayList<Order>>();
		sellOrders = new HashMap<String, ArrayList<Order>>();
	}

	public void addToOrderBook(Order order) {
		// Populate the buyOrders and sellOrders data structures, whichever
		// is appropriate
		
		if (order.getType().equals("Buy")) {
			if (buyOrders.get(order.getStockSymbol()) != null) {
				stockArray = buyOrders.get(order.getStockSymbol());
				stockArray.add(order);
			} else {
				stockArray = new ArrayList<Order>();
				stockArray.add(order);
			}
			buyOrders.put(order.getStockSymbol(), stockArray);
		} 
		
		else if (order.getType().equals("Sell")) {
			if (sellOrders.get(order.getStockSymbol()) != null) {
				stockArray = sellOrders.get(order.getStockSymbol());
				stockArray.add(order);
			} else {
				stockArray = new ArrayList<Order>();
				stockArray.add(order);
			}
			sellOrders.put(order.getStockSymbol(), stockArray);
		}
		else {
			System.out.println("An invalid Order Type was used. Program only accepts Buy or Sell Orders!");
		}
	}
	
	// Sorts by price from lowest to highest
	public int compare(Order o1, Order o2) {
		if (o1.getPrice() == o2.getPrice()) {
			return 0;
		}
	    return o1.getPrice() > o2.getPrice() ? 1 : -1;
	}

	public void trade()  {
		// Complete the trading.
		// 1. Follow and create the orderbook data representation (see spec)
		
		// get all of the stocks from the market
		HashMap<String, Stock> stocksHash = m.getStockList();
		Set<String> stocks = stocksHash.keySet(); //a set of all the stocks
		ArrayList<Order> sellStock = null;
		ArrayList<Order> buyStock = null;
		
		for(String curStock: stocks){
			if(buyOrders.containsKey(curStock)){
				// get every buy request from buyOrders for this stock if any
				// exist and sort it
				buyStock = buyOrders.get(curStock);
				Collections.sort(buyStock, this);
				// Reverse buyStock to be highest to lowest
				Collections.reverse(buyStock); 
			}
			else {
				System.out.printf("no buy orders for stocks of type %s", curStock);
			}
			if(sellOrders.containsKey(curStock)){
				// get every sell request from sellOrders for this stock if any 
				// exist and sort it
				sellStock = sellOrders.get(curStock);
				Collections.sort(sellStock, this);				
			}
			else {
				System.out.printf("no sell orders for stocks of type %s", curStock);
			}
			
			
			// 2. Find the matching price
			
			int sumSell = 0;
			int sumBuy = 0;
			
			HashMap<Double, Integer> sellChart = new HashMap<Double, Integer>();
			HashMap<Double, Integer> buyChart = new HashMap<Double, Integer>();
			ArrayList<Double> prices = new ArrayList<Double>();
			
			for(int s = 0; s < sellStock.size(); s++){
				Order temp = sellStock.get(s);
				
				sellChart.put(temp.getPrice(), temp.getSize() + sumSell);
				
				
				sumSell += temp.getSize();
				
				if (!prices.contains(temp.getPrice())){
					prices.add(temp.getPrice());
				}
				
				s++;
			}
			
			for(int b=0; b < buyStock.size(); b++){
				Order temp = buyStock.get(b);
				
				buyChart.put(temp.getPrice(), temp.getSize() + sumBuy);
				
				sumBuy += temp.getSize();
				
				if(!prices.contains(temp.getPrice())){
					prices.add(temp.getPrice());
				}
				
				b++;
				
				
			}
			// 3. Update the stocks price in the market using the PriceSetter.
			// Note that PriceSetter follows the Observer pattern. Use the pattern.
			// 4. Remove the traded orders from the orderbook
			// 5. Delegate to trader that the trade has been made, so that the
			// trader's orders can be placed to his possession (a trader's position
			// is the stocks he owns)
			// (Add other methods as necessary)	}

		}
		
}
}
	
