package test;

import static org.junit.Assert.*;

import org.junit.Test;

import prod.IPO;
import prod.Market;

public class IPOTest {

	
	
	@Test
	public void testEnterNewStockNormal() {
		try {
		Market m = new Market("NASDAQ");
		IPO.enterNewStock(m, "SBUX", "Starbucks", (float) 33.23);
		IPO.enterNewStock(m, "GOOG", "Google", (float) 78.32);
		IPO.enterNewStock(m, "AAPL", "Apple", (float) 45.00);
		} catch (Exception e){
			fail ("This should not fail");
		}
	}
	@Test
	public void testEnterNewStockAlreadyExists() {
		try {
			Market m = new Market("NASDAQ");
			IPO.enterNewStock(m, "SBUX", "Starbucks", (float) 33.23);
			IPO.enterNewStock(m, "GOOG", "Google", (float) 78.32);
			IPO.enterNewStock(m, "AAPL", "Apple", (float) 45.00);
			IPO.enterNewStock(m, "SBUX", "Starbucks", (float) 33.23);
			} catch (Exception e){
				String msg = "Stock Market Violation Exception";
				assertTrue(e.getMessage().contains(msg));
			}
		
	}
	@Test
	public void testEnterNewStockNegativePrice() {
		try {
			Market m = new Market("NASDAQ");
			IPO.enterNewStock(m, "SBUX", "Starbucks", (float) 33.23);
			IPO.enterNewStock(m, "GOOG", "Google", (float) 78.32);
			IPO.enterNewStock(m, "AAPL", "Apple", (float) -45.00);
			} catch (Exception e){
				String msg = "Stock Market Violation Exception";
				assertTrue(e.getMessage().contains(msg));
			}
		
	}

}
