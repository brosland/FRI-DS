package sk.uniza.fri.II008;

import sk.uniza.fri.II008.events.Event;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.logging.Logger;
import sk.uniza.fri.II008.events.PauseEvent;
import sk.uniza.fri.II008.generators.IGenerator;
import sk.uniza.fri.II008.generators.IGenerator.IGeneratorType;

public abstract class EventSimulation extends Simulation
{
	public interface IEventSimulationListener
	{
		public void onChange();
	}

	protected final HashMap<IGeneratorType, IGenerator> generators;
	private IEventSimulationListener listener = new EventSimulationListener()
	{
	};
	private final PriorityQueue<Event> events;
	private volatile PauseEvent pauseEvent;
	private final double maxTimestamp;
	private volatile double timestamp;
	private final Object[] emptyReplicationData = new Object[] {};
	private volatile boolean logging = false;
	public static final Logger LOGGER = Logger.getLogger(EventSimulation.class.getName());

	static
	{
		LOGGER.setUseParentHandlers(false);
	}

	public EventSimulation(long replicationCount, long batchSize, double maxTimestamp)
	{
		super(replicationCount, batchSize);

		this.maxTimestamp = maxTimestamp;
		generators = new HashMap<>();
		events = new PriorityQueue<>();
	}

	public void setEventSimulationListener(IEventSimulationListener listener)
	{
		this.listener = listener;
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

			if (maxTimestamp != UNLIMETED && timestamp > maxTimestamp)
			{
				timestamp = maxTimestamp;
				break;
			}

			event.run();

			listener.onChange();
		}

		return emptyReplicationData;
	}
	
	public boolean isEnabledLogging()
	{
		return logging;
	}

	public void setLogging(boolean logging)
	{
		this.logging = logging;
	}
}
