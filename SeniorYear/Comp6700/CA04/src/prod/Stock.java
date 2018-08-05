package prod;

public class Stock {
private String symbol;
private String name;
private float price;
	
	
	public Stock(String symbol, String name, float price){
		this.symbol = symbol;
		this.name = name;
		this.price = price;
	}
	
	public String getSymbol(){
		return symbol;
	}
	
	public void setSymbol(String symbol){
		this.symbol = symbol;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public float getPrice(){
		return price;
	}
	
	public void setPrice(float price){
		this.price = price;
	}
	
	
}
