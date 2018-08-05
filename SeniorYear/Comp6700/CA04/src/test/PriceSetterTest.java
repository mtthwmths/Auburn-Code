package test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import prod.Market;
import prod.PriceSetter;
import prod.Stock;

public class PriceSetterTest {
static Market m = new Market("NASDAQ");
static Stock s1 =  new Stock("SBUX", "Starbucks", (float) 33.23);
static Stock s2 =  new Stock("GOOG", "Google", (float) 78.32);
static Stock s3 =  new Stock("AAPL", "Apple", (float) 45.00);

@BeforeClass 
public static void onlyOnce() {
	try {
	m.addStock(s1);
	m.addStock(s2);
	m.addStock(s3);
	} catch (Exception e){
		//do nothing
	}
 }


	@Test
	public void testNormalPriceSetter() {
		PriceSetter ps = new PriceSetter();
		ps.setNewPrice(m, "GOOG", (float) 99.0);
	    //no error	
	}
	@Test
	public void testPriceSetterBadSymbol() {
		try {
		PriceSetter ps = new PriceSetter();
		ps.setNewPrice(m, "XXXX", (float) 99.0);
		} catch(Exception e ){
		    final String msg = "Stock Market Violation Exception\nStock not present (XXXX)";
		    assertEquals(msg, e.getMessage());
		}
	}
	
	@Test
	public void testPriceSetterNegativePrice() {
		try {
			PriceSetter ps = new PriceSetter();
			ps.setNewPrice(m, "GOOG", (float) -99.0);
		} catch(Exception e ){
		    final String msg = "Stock Market Violation Exception\nStock price cannot be set to a negative value (GOOG, 78.32) -> -99.0 X Not Allowed ";
		    assertEquals(msg, e.getMessage());
		}
	}
}
