package sk.uniza.fri.II008.events;

import sk.uniza.fri.II008.EventSimulation;

public abstract class Event implements Runnable, Comparable<Event>
{
	private long timestamp;
	private final EventSimulation simulation;

	public Event(long timestamp, EventSimulation simulation)
	{
		this.timestamp = timestamp;
		this.simulation = simulation;
	}

	public long getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(long timestamp)
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
		long result = getTimestamp() - event.getTimestamp();

		return result == 0 ? 0 : (result < 0 ? -1 : 1);
	}
}
