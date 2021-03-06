package pkg.market.api;

public interface ISubject {
	public void registerObserver(IObserver observer);

	public void notifyObserver();
	
	public Object getUpdate();

	// Abstract method
	public void unRegisterObserver(IObserver observer);

}
