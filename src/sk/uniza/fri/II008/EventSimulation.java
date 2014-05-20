package sk.uniza.fri.II008;

import sk.uniza.fri.II008.events.Event;
import java.util.PriorityQueue;
import sk.uniza.fri.II008.events.PauseEvent;

public abstract class EventSimulation extends Simulation
{
	private final PriorityQueue<Event> events;
	private volatile PauseEvent pauseEvent;
	private final double maxTimestamp;
	private volatile double timestamp;
	private final Object[] emptyReplicationData = new Object[] {};

	public EventSimulation(long replicationCount, long batchSize, double maxTimestamp)
	{
		super(replicationCount, batchSize);

		this.maxTimestamp = maxTimestamp;
		
		events = new PriorityQueue<>();
	}

	public double getTimestamp()
	{
		return timestamp;
	}

	public double getMaxTimestamp()
	{
		return maxTimestamp;
	}

	public boolean hasPauseEvent()
	{
		return pauseEvent != null;
	}

	public void setPauseEvent(long interval, long duration)
	{
		if (pauseEvent == null)
		{
			double nextTimestamp = PauseEvent.getNextTimestamp(timestamp, interval);
			pauseEvent = new PauseEvent(nextTimestamp, this, interval, duration);

			addEvent(pauseEvent);
		}
		else
		{
			pauseEvent.setInterval(interval);
			pauseEvent.setPaused(duration);
		}
	}

	public void removePauseEvent()
	{
		this.pauseEvent = null;
	}

	public void addEvent(Event event)
	{
		events.add(event);
	}

	@Override
	protected void prepareReplication(long replication)
	{
		timestamp = 0;
		events.clear();

		if (hasPauseEvent())
		{
			double nextTimestamp = PauseEvent.getNextTimestamp(timestamp, pauseEvent.getInterval());
			pauseEvent.setTimestamp(nextTimestamp);
			addEvent(pauseEvent);
		}
	}

	@Override
	protected Object[] runReplication(long replication)
	{
		while (!events.isEmpty() && getState() != ISimulation.State.STOPPED)
		{
			if (getState() == ISimulation.State.PAUSED)
			{
				try
				{
					Thread.sleep(100);
				}
				catch (InterruptedException ex)
				{
				}

				continue;
			}

			Event event = events.remove();
			timestamp = event.getTimestamp();

			if (maxTimestamp != UNLIMITED && timestamp > maxTimestamp)
			{
				timestamp = maxTimestamp;
				break;
			}

			event.run();

			replicationListener.onChange();
		}

		return emptyReplicationData;
	}
}
