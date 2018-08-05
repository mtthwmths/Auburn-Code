package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import pkg.order.BuyOrder;
import pkg.trader.Trader;

public class buyOrderTest {

	@Test
	public void createBuyOrderNormal() {
		try {
			Trader t = new Trader("Kara", 10000.00);
			new BuyOrder("SBUX", 100, 93.5, t);
		} 
		catch (Exception e){
			fail("buyOrderTest.createBuyOrderNormal: this Shouldn't fail");
		}
	}
	
	@Test
	public void createBuyOrderBool() {
		try{
			Trader t = new Trader("Matt", 10.00);
			new BuyOrder("SBUX", 1, true, t);
		} 
		catch (Exception e) {
			fail("buyOrderTest.createBuyOrderBool: this shouldn't fail");
		}
	}
	
	@Test
	public void badBuyOrderBool() {
		try {
			Trader t = new Trader("Kara", 100000.00);
			new BuyOrder("SBUX", 100, false, t);
		} 
		catch (Exception e) {
			assertEquals(e.getMessage(), "Buy order has been placed without a valid price");
		}
	}

}
