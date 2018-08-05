package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import pkg.exception.StockMarketException;
import pkg.market.Market;
import pkg.stock.Stock;
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

		Trader t = new Trader("Kara", 10000.00);
		Market m = new Market("AU");

		try {
			m.addStock(new Stock("SBUX", "Starbucks", 33.33));
			m.addStock(new Stock("GOOG", "Google", 52.56));

			t.buyFromBank(m, "GOOG", 10);
		} catch (StockMarketException e){
			fail("traderTest.testBuyFromBankSuccess: this Shouldn't fail");
		}
	}

	@Test
	public void testBuyFromBankFail() {

		Trader t = new Trader("Kara", 10000.00);
		Market m = new Market("AU");

		try {
			m.addStock(new Stock("SBUX", "Starbucks", 33.33));
			m.addStock(new Stock("GOOG", "Google", 52.56));
			t.buyFromBank(m, "GOOG", 10000000);
		} catch (Exception e){
			assertEquals(e.getMessage(), "Cannot place order for stock: GOOG since there is not enough money." 
					+ " Trader: Kara");
		}
	}

}
