package test;

import static org.junit.Assert.*;

import org.junit.Test;

import prod.Market;
import prod.Stock;

public class MarketTest {
	
	
	
	@Test
	public void testAddStockNormal() {
		Market m = new Market("NASDAQ");
		Stock s1 =  new Stock("SBUX", "Starbucks", (float) 33.23);
		Stock s2 =  new Stock("GOOG", "Google", (float) 78.32);
		try{
			m.addStock(s1);
			m.addStock(s2);
		} catch (Exception e){
			fail("This should not have failed");
		}
	}
	@Test
	public void testAddStockBadSymbol() {
		Market m = new Market("NASDAQ");
		Stock s1 =  new Stock("SBUX", "Starbucks", (float) 33.23);
		Stock s2 =  new Stock("GOOG", "Google", (float) 78.32);
		try{
			m.addStock(s1);
			m.addStock(s2);
			m.addStock(s1);
		} catch (Exception e){
			String msg ="Tried to enter a stock that is already present ("
					+ s1.getSymbol() + ")";
			assertEquals(msg, e.getMessage());		
		}
	}
	@Test
	public void testAddStockNegativePrice() {
		Market m = new Market("NASDAQ");
		Stock s1 =  new Stock("SBUX", "Starbucks", (float) -33.23);
		Stock s2 =  new Stock("GOOG", "Google", (float) 78.32);
		try{
			m.addStock(s1);
			m.addStock(s2);
		} catch (Exception e){
			String msg = "Stock has a negative price (" +
				s1.getSymbol() + ", " + s1.getPrice() + ")";
			assertEquals(msg, e.getMessage());	
		}
	}
	
	@Test
	public void testgetStockForSymbolNormal() {
		Market m = new Market("NASDAQ");
		Stock s1 =  new Stock("SBUX", "Starbucks", (float) 33.23);
		Stock s2 =  new Stock("GOOG", "Google", (float) 78.32);
		try {
			m.addStock(s1);
			m.addStock(s2);
			assertEquals(s2, m.getStockForSymbol("GOOG"));
		} catch (Exception e){
			fail("This should not throw excpetion");
		}
		
	}
	@Test
	public void testgetStockForSymbolNotThereReturnsNull() {
		Market m = new Market("NASDAQ");
		assertEquals(null, m.getStockForSymbol("GOOG"));
	}
	
	@Test
	public void testremoveStockFromListNormal() {
		Market m = new Market("NASDAQ");
		Stock s1 =  new Stock("SBUX", "Starbucks", (float) 33.23);
		Stock s2 =  new Stock("GOOG", "Google", (float) 78.32);
		try{
			m.addStock(s1);
			m.addStock(s2);
			assertEquals(s1, m.removeStockFromStockList(s1.getSymbol()));
		} catch (Exception e){
			fail("This should not have failed");
		}
	}
	@Test
	public void testremoveStockFromListNotThereExpectError() {
		Market m = new Market("NASDAQ");
		Stock s1 =  new Stock("SBUX", "Starbucks", (float) 33.23);
		Stock s2 =  new Stock("GOOG", "Google", (float) 78.32);
		try{
			m.addStock(s1);
			m.addStock(s2);
			m.removeStockFromStockList("XXXX");
		} catch (Exception e){
			String msg = "Stock not present (XXXX)";
			assertEquals(msg, e.getMessage());	
		}
		
	}
	@Test
	public void testupdateStockPriceNormal() {
		Market m = new Market("NASDAQ");
		Stock s1 =  new Stock("SBUX", "Starbucks", (float) 33.23);
		Stock s2 =  new Stock("GOOG", "Google", (float) 78.32);
		try{
			m.addStock(s1);
			m.addStock(s2);
			m.updateStockPrice(s1.getSymbol(), (float) 0.0);
		} catch (Exception e){
			fail("This should not have failed");	
		}
	}
	@Test
	public void testupdateStockPriceBadSymbolExpectError() {
		Market m = new Market("NASDAQ");
		Stock s1 =  new Stock("SBUX", "Starbucks", (float) 33.23);
		Stock s2 =  new Stock("GOOG", "Google", (float) 78.32);
		try{
			m.addStock(s1);
			m.addStock(s2);
			m.updateStockPrice("XXXX", (float) 0.0);
		} catch (Exception e){
			String msg = "Stock not present (XXXX)";
			assertEquals(msg, e.getMessage());		
		}
		
	}
	
	
}
