package sk.uniza.fri.II008.events;

import sk.uniza.fri.II008.EventSimulation;

public abstract class Event implements Runnable
{
	protected long timestamp;
	protected final EventSimulation simulation;

	public Event(long timestamp, EventSimulation simulation)
	{
		this.timestamp = timestamp;
		this.simulation = simulation;
	}

	public long getTimestamp()
	{
		return timestamp;
	}

	public EventSimulation getSimulation()
	{
		return simulation;
	}
}
