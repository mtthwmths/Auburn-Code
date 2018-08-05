package prod;

	public class Client {
		public static void main(String[] args) {
		Market m = new Market("NASDAQ");
		IPO.enterNewStock(m, "SBUX", "Starbucks Corp.", (float) 92.86);
		IPO.enterNewStock(m, "TWTR", "Twitter Inc.", (float) 47.88);
		IPO.enterNewStock(m, "VSLR", "Vivint Solar", (float) 16.44);
		IPO.enterNewStock(m, "GILD", "Gilead Sciences", (float) 93.33);
		IPO.enterNewStock(m, "FOO", "Gilead Sciences", (float) -93.33);
		// Exception
		// thrown
		// here
		m.printStocks();
		Market m1 = new Market("Nikkei");
		IPO.enterNewStock(m1, "BABA", "Alibaba", (float) 84.88);
		IPO.enterNewStock(m1, "BDU", "Baidu", (float) 253.66);
		m1.printStocks();
		PriceSetter ps = new PriceSetter();
		// This is the part that happens in real time.
		ps.setNewPrice(m, "SBUX", (float) 93.33);
		ps.setNewPrice(m, "SBUX", (float) 94.08);
		ps.setNewPrice(m, "SBUX", (float) 93.21);
		ps.setNewPrice(m, "SBUX", (float) 92.99);
		ps.setNewPrice(m, "SBUX", (float) 93.33);
		ps.setNewPrice(m, "GILD", (float) 16.33);
		ps.setNewPrice(m1, "BABA", (float) 85.55);
		ps.setNewPrice(m, "VSLR", (float) 16.00);
		ps.setNewPrice(m1, "BABA", (float) 86.71);
		ps.setNewPrice(m, "VSLR", (float) 16.21);
		m.printHistoryFor("SBUX");
		m1.printHistoryFor("BABA");
		m.printHistoryFor("BABA"); // Should not print anything
		m.printHistoryFor("VSLR");

		}

}
