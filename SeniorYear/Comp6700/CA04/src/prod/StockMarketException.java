package prod;

@SuppressWarnings("serial")
public class StockMarketException extends Exception {

	  private String message = null;
	  
	    public StockMarketException() {
	        super();
	    }
	 
	    public StockMarketException(String message) {
	        super(message);
	        this.message = message;
	    }
	    
	    public String getMessage(){
	    	return message;
	    }
	
}
