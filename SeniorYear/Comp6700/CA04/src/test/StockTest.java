package test;

import static org.junit.Assert.*;
import org.junit.Test;

import prod.Stock;

public class StockTest {

	@Test
	public void testgetName() {
		Stock stock = new Stock("SBUX", "Starbucks", (float)12.21);
		assertSame(stock.getName(), "Starbucks");
	}
	@Test
	public void testsetName() {
		Stock stock = new Stock("SBUX", "Starbucks", (float)12.21);
		stock.setName("NotStarbucks");
		assertSame(stock.getName(), "NotStarbucks");
	}
	@Test
	public void testgetSymbol() {
		Stock stock = new Stock("SBUX", "Starbucks", (float)12.21);
		assertSame(stock.getSymbol(), "SBUX");
	}
	@Test
	public void testsetSymbol() {
		Stock stock = new Stock("SBUX", "Starbucks", (float)12.21);
		stock.setSymbol("XXX");
		assertSame(stock.getSymbol(), "XXX");
	}
	@Test
	public void testgetPrice() {
		Stock stock = new Stock("SBUX", "Starbucks", (float)12.21);
		assertEquals(12.21, stock.getPrice(), 0.0001);

	}
	@Test
	public void testsetPrice() {
		Stock stock = new Stock("SBUX", "Starbucks", (float)12.21);
		stock.setPrice((float)0.0);
		assertEquals(0.0, stock.getPrice(), 0.0001);
	}
	@Test
	public void testsetPriceNegative() {
		Stock stock = new Stock("SBUX", "Starbucks", (float)12.21);
		stock.setPrice((float) -12.0);
		assertEquals(-12.00, stock.getPrice(), 0.0001);
	}
}
