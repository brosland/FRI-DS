package sk.uniza.fri.II008.events;

import java.util.logging.Level;
import java.util.logging.Logger;
import sk.uniza.fri.II008.EventSimulation;

public abstract class Event implements Runnable, Comparable<Event>
{
	private double timestamp;
	private final EventSimulation simulation;
	public static final Logger LOGGER = Logger.getLogger(Event.class.getName());

	static
	{
		LOGGER.setParent(EventSimulation.LOGGER);
		LOGGER.setLevel(Level.INFO);
	}

	public Event(double timestamp, EventSimulation simulation)
	{
		this.timestamp = timestamp;
		this.simulation = simulation;
	}

	public double getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(double timestamp)
	{
		this.timestamp = timestamp;
	}

	public EventSimulation getSimulation()
	{
		return simulation;
	}

	@Override
	public int compareTo(Event event)
	{
		return timestamp < event.getTimestamp() ? -1 : 1;
	}
}
