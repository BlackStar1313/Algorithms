package design.patterns.observers.clock.observable;

import java.io.Serializable;
import java.util.Vector;

import clock.observers.Observer;

public class Observable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 15625664351152946L;


	private static final int DEFAULT_SIZE = 1 << 4;// aka 16


	/**
	 * Internal storage to maintain a list of observers.
	 */
	private Vector<Observer> observers;
	
	private boolean changed;

	public Observable() {
		observers = new Vector<>(DEFAULT_SIZE);
	}


	public void setChanged() {
		changed = true;
	}

	public void clearChanged() {
		changed = false;
	}

	public boolean hasChanged() {
		return changed;
	}


	/**
	 * Add a given observer in a list
	 * @param o	The observer to be added.
	 */
	public void addObserver(Observer o) {
		if(o == null) {
			throw new IllegalArgumentException("An observer cannot be null!!");
		}
		if(!observers.contains(o)) {
			observers.addElement(o);
		}
	}

	/**
	 * Remove a given observer
	 * @param o	the observer to be removed from the list.
	 */
	public void removeObserver(Observer o) {
		if(o == null) {
			throw new IllegalArgumentException("An observer cannot be null!!");
		}

		if(observers.contains(o)) {
			observers.removeElement(o);
		}
	}

	/**
	 * Remove all observers
	 */
	public void removeAllObservers() {
		observers.removeAllElements();
	}

	public void notifyObservers(int h, int m, int s) {

		if(hasChanged()) {
			for(Observer obs : observers) {
				obs.update(h, m, s);
			}
			clearChanged();
		}
		return;
	}
	
	/**
	 * Get the number of observers of this {@code Observable} object
	 * 
	 * @return	the number of observers of this object.
	 */
	public int observers() {
		return observers.size();
	}


}
