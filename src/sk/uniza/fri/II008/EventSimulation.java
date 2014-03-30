package sk.uniza.fri.II008;

import sk.uniza.fri.II008.events.Event;
import java.util.HashMap;
import java.util.PriorityQueue;
import sk.uniza.fri.II008.events.PauseEvent;
import sk.uniza.fri.II008.generators.IGenerator;
import sk.uniza.fri.II008.generators.IGenerator.IGeneratorType;

public abstract class EventSimulation extends Simulation
{
	protected final HashMap<IGeneratorType, IGenerator> generators;
	private final PriorityQueue<Event> events;
	private PauseEvent pauseEvent;
	private final double maxTimestamp;
	private volatile double timestamp;

	public EventSimulation(long maxReplication, long batchSize, double maxSimulationTime)
	{
		super(maxReplication, batchSize);

		this.maxTimestamp = maxSimulationTime;
		generators = new HashMap<>();
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

	public IGenerator getGenerator(IGeneratorType generator)
	{
		return generators.get(generator);
	}

	public boolean hasPauseEvent()
	{
		return pauseEvent != null;
	}

	public void setPauseEvent(long interval, long paused)
	{
		if (pauseEvent == null)
		{
			double nextTimestamp = PauseEvent.getNextTimestamp(timestamp, interval);
			pauseEvent = new PauseEvent(nextTimestamp, this, interval, paused);

			addEvent(pauseEvent);
		}
		else
		{
			pauseEvent.setInterval(interval);
			pauseEvent.setPaused(paused);
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
			addEvent(pauseEvent);
		}
	}

	@Override
	protected Object[] runReplication(long replication)
	{
		while (getState() != ISimulation.State.STOPPED)
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

			if (timestamp > maxTimestamp)
			{
				break;
			}

			event.run();
		}

		return new Object[]
		{
			replication
		};
	}
}
