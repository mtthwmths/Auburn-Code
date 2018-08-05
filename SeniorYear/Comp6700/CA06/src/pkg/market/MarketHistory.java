package pkg.market;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pkg.exception.StockMarketException;
import pkg.market.api.IObserver;
import pkg.market.api.ISubject;
import pkg.stock.Stock;

public class MarketHistory implements IObserver {
	private ISubject subject;
	Market m;
	Map<String, List<Double>> historyOfWhereTheBucksHaveGone;

	public MarketHistory(Market m) {
		super();
		this.m = m;
		historyOfWhereTheBucksHaveGone = new HashMap<String, List<Double>>();
	}

	@Override
	public void setSubject(ISubject priceSetter) {
		this.subject = priceSetter;
	}

	public void startHistoryWithPrice(String symbol, Double newPrice)
			throws StockMarketException {
		if (!historyOfWhereTheBucksHaveGone.containsKey(symbol)) {
			List<Double> priceList = new ArrayList<Double>();
			priceList.add(newPrice);
			historyOfWhereTheBucksHaveGone.put(symbol, priceList);
		}
	}

	@Override
	public void update() {
		Stock updatedStock = (Stock) subject.getUpdate();
		if (m.getStockForSymbol(updatedStock.getSymbol()) == null) {
			return;
		}
		if (historyOfWhereTheBucksHaveGone.containsKey(updatedStock.getSymbol())) {
			List<Double> priceList = historyOfWhereTheBucksHaveGone.get(updatedStock.getSymbol());
			priceList.add(updatedStock.getPrice());
			historyOfWhereTheBucksHaveGone.put(updatedStock.getSymbol(), priceList);
		} else {
			// New entry to history
			List<Double> priceList = new ArrayList<Double>();
			priceList.add(updatedStock.getPrice());
			historyOfWhereTheBucksHaveGone.put(updatedStock.getSymbol(), priceList);
		}
	}

	public ArrayList<Double> getPriceFor(String symbol) {
		if (historyOfWhereTheBucksHaveGone.containsKey(symbol)) {
			return (ArrayList<Double>) historyOfWhereTheBucksHaveGone.get(symbol);
		} else {
			return new ArrayList<Double>();
		}
	}
}
