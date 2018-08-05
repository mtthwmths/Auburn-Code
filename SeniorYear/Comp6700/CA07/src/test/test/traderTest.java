package test;

import static org.junit.Assert.*;

import org.junit.Test;

import pkg.market.Market;
import pkg.stock.Stock;
import pkg.exception.StockMarketExpection;
import pkg.trader.Trader;

public class traderTest {

	@Test
	public void createTraderNormal() {
		try {
			new Trader("Kara", 10000.00);
		} catch (Exception e){
			fail("traderTest.createTraderNormal: this Shouldn't fail");
		}
	}
	
	@Test
	public void testBuyFromBankSuccess() {
		try{
		Trader t = new Trader("Kara", 10000.00);
		Market m = new Market("AU");
		m.addStock(new Stock("SBUX", "Starbucks", 33.33));
		m.addStock(new Stock("GOOG", "Google", 52.56));
		
		
			t.buyFromBank(m, "GOOG", 10);
		} catch (Exception e){
			fail("traderTest.testBuyFromBankSuccess: this Shouldn't fail");
		}
	}
	
	@Test(expected = StockMarketExpection.class)
	public void testBuyFromBankFail() {
		try{
		Trader t = new Trader("Kara", 10000.00);
		Market m = new Market("AU");
		m.addStock(new Stock("SBUX", "Starbucks", 33.33));
		m.addStock(new Stock("GOOG", "Google", 52.56));
		
		
			t.buyFromBank(m, "GOOG", 10000000);
		} catch (StockMarketExpection e){
			//assertEquals(e.getMessage(), "Cannot place order for stock: GOOG since there is not enough money." 
				//	+ " Trader: Kara");
		}
	}
	
}
