package test;

import static org.junit.Assert.*;

import org.junit.Test;

import prod.Market;
import prod.MarketHistory;

public class MarketHistoryTest {

	@Test
	public void testStartHistoryWithPrice() {
		Market market = new Market("NASDAQ");
		MarketHistory mh = new MarketHistory(market);
		mh.startHistoryWithPrice("SBUX", (float) 12.12);
		assertEquals(12.12, mh.getPriceFor("SBUX").get(0), 0.0001);		
	}
	@Test
	public void testupdatePriceHistory() {
		Market market = new Market("NASDAQ");
		MarketHistory mh = new MarketHistory(market);
		mh.startHistoryWithPrice("SBUX", (float) 12.12);
		mh.updatePriceHistory("SBUX", (float) 34.32);
		assertEquals(34.32, mh.getPriceFor("SBUX").get(0), 0.0001);	
		assertEquals(12.12, mh.getPriceFor("SBUX").get(1), 0.0001);	
	}
	@Test
	public void testgetPriceFor() {
		Market market = new Market("NASDAQ");
		MarketHistory mh = new MarketHistory(market);
		mh.startHistoryWithPrice("SBUX", (float) 11.0);
		mh.updatePriceHistory("SBUX", (float) 13.0);
		mh.updatePriceHistory("SBUX", (float) 15.0);
		mh.updatePriceHistory("SBUX", (float) 100.30);
		assertEquals(11.0, mh.getPriceFor("SBUX").get(3), 0.0001);	
		assertEquals(13.0, mh.getPriceFor("SBUX").get(2), 0.0001);	
		assertEquals(15.0, mh.getPriceFor("SBUX").get(1), 0.0001);	
		assertEquals(100.30, mh.getPriceFor("SBUX").get(0), 0.0001);		
	}

}
