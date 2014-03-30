package sk.uniza.fri.II008.events;

import sk.uniza.fri.II008.EventSimulation;

public abstract class Event implements Runnable, Comparable<Event>
{
	private double timestamp;
	private final EventSimulation simulation;

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
