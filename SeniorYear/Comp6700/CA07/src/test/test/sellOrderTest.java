package test;

import static org.junit.Assert.*;

import org.junit.Test;

import pkg.exception.StockMarketExpection;
import pkg.order.SellOrder;
import pkg.trader.Trader;


public class sellOrderTest {

	@Test
	public void createSellOrderNormal() {
		try {
			Trader t = new Trader("Kara", 10000.00);
			new SellOrder("SBUX", 100, 93.5, t);
		} catch (Exception e){
			fail("SellOrderTest.createSellOrderNormal: this Shouldn't fail");
		}
	}
	
	@Test
	public void createSellOrderBool() {
		try{
			Trader t = new Trader("Matt", 10.00);
			new SellOrder("SBUX", 1, true, t);
		} catch (Exception e) {
			fail("SellOrderTest.createSellOrderBool: this shouldn't fail");
		}
	}
	
	@Test(expected = StockMarketExpection.class)
	public void badSellOrderBool() {
		try {
			Trader t = new Trader("Kara", 100000.00);
			new SellOrder("SBUX", 100, false, t);
		} catch (StockMarketExpection e) {

			//assertEquals(e.getMessage(), "Sell order has been placed without a valid price");
		}
	}

}
