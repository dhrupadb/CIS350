import java.util.*;


public class LoggerSubject {
	protected List<LogObserver> observers = new LinkedList<LogObserver>();
	
	public void addObserver(LogObserver obs)
	{
		observers.add(obs);
	}
	
	public void removeObserver(LogObserver obs)
	{
		observers.remove(obs);
	}
	
	protected void notifyObservers(String s, boolean stampStatus)
	{
		for(LogObserver obs: observers) obs.notify(s, stampStatus);
	}
	
	public void logWithTimestamp(String s)
	{
		notifyObservers(s, true);
	}
	
	public void logWithoutTimestamp(String s)
	{
		notifyObservers(s, false);
	}
	
	public void closeLog()
	{
		for(LogObserver obs: observers) obs.endLog();
	}
}
