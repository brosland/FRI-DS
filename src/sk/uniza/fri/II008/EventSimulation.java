package sk.uniza.fri.II008;

import sk.uniza.fri.II008.events.Event;
import java.util.HashMap;
import java.util.LinkedList;
import sk.uniza.fri.II008.events.PauseEvent;
import sk.uniza.fri.II008.generators.IGenerator;
import sk.uniza.fri.II008.generators.IGenerator.IGeneratorType;

public abstract class EventSimulation extends Simulation
{
	protected final HashMap<IGeneratorType, IGenerator> generators;
	private final LinkedList<Event> events;
	private final long maxTimestamp;
	private volatile long timestamp;
	private volatile PauseEvent pauseEvent;

	public EventSimulation(long maxReplication, long batchSize, long maxSimulationTime)
	{
		super(maxReplication, batchSize);

		this.maxTimestamp = maxSimulationTime;
		generators = new HashMap<>();
		events = new LinkedList<>();
	}

	public long getTimestamp()
	{
		return timestamp;
	}

	public long getMaxTimestamp()
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
			pauseEvent = new PauseEvent(timestamp, this, interval, paused);
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
		for (int i = 0; i < events.size(); i++)
		{
			if (event.getTimestamp() < events.get(i).getTimestamp())
			{
				events.add(i, event);
				return;
			}
		}

		events.addLast(event);
	}

	@Override
	protected void prepareReplication(long replication)
	{
		timestamp = 0;
		events.clear();
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

			Event event = events.removeFirst();
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
