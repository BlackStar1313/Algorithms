package design.patterns.observers.clock.observable;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Logger;
import clock.observers.Observer;

/**
 * <code>ClockTimer</code> is a subject for storing and maintaining the time of 
 * day. It notifies its observers every second. The class provides an interface 
 * for retrieving individual time units such as hour, minute and second.
 * 
 * @author Peguy Njoyim
 */
public class ClockTimer extends Observable implements Runnable{
	
	private static final long serialVersionUID = -5739326229140232700L;

	private long time;
    
    private Calendar calendar;
    
    // delay in milliseconds
    private long delay = 1000;
    
    private Thread thread;

	private static Logger loggingService = Logger.getLogger("ClockTimer");
	
	/**
	 * 
	 * @uml.property name="analogClock"
	 * @uml.associationEnd multiplicity="(1 1)"
	 */

	
    /**
     * Creates a new instance of <code>ClockTimer</code>.
     */
    public ClockTimer(Observer...observers) {
		// TODO Remove the analog clock argument from the constructor, once the observer pattern is used.
    	
    	if(observers.length == 0 || observers == null) {
    		throw new IllegalArgumentException("Cannot have empty or null observers!");
    	}
    	
    	for(Observer obs : observers) {
    		addObserver(obs);
    	}
        calendar = new GregorianCalendar();
        
    }

    /**
     * Returns the current hour.
     */
    private int getHour() {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * Returns the current minute.
     */
    private int getMinute() {
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * Returns the current second.
     */
    private int getSecond() {
        return calendar.get(Calendar.SECOND);
    }
    
    

    /**
     * Notifies the observers every second.
     */
    public void run() {
        while (thread != null) {
            try {
                time = System.currentTimeMillis();
                calendar.setTimeInMillis(time);
                // TODO Notify observers here, instead of setting the time directly.
				// Program against the observer interface, instead of programming against the analog clock class!
                setChanged();
                notifyObservers(getHour(), getMinute(), getSecond());
                Thread.sleep(delay);
            } catch (InterruptedException e) {
            	loggingService.severe("Timer got interrupted");
            }
        }
    }

    /**
     * Starts the timer.
     */
    public void start() {
        if (!isRunning()) {
            thread = new Thread(this);
            thread.start();
        }
    }
    
    
    /**
     * Check whether the clock is running or not.
     * @return	{@code true} if the {@code Timer} is running, false otherwise
     */
    private boolean isRunning() {
    	return thread != null;
    }
    
}
