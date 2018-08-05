package prod;
import java.util.*;


public class MarketHistory {

	private Market m;
	private Map<String, List<Float>> history;
	
	public MarketHistory(Market m){
		this.setMarket(m);
		history = new HashMap <String, List<Float>>();
	}
	
	public void startHistoryWithPrice(String symbol, float newPrice){
		ArrayList<Float> newHistory = new ArrayList<Float>();
		newHistory.add(newPrice);
		history.put(symbol, newHistory);	
	}
	public void updatePriceHistory(String symbol, float newPrice){
		ArrayList<Float> thisList = (ArrayList<Float>) history.get(symbol);
		thisList.add(0, newPrice);
		history.put(symbol, thisList);
		
	}
	public ArrayList<Float> getPriceFor(String symbol){
		return (ArrayList<Float>) history.get(symbol);
	}

	public Market getMarket() {
		return m;
	}

	public void setMarket(Market m) {
		this.m = m;
	}
}
