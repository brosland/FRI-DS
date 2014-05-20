package sk.uniza.fri.II008;

import java.util.HashMap;
import java.util.logging.Logger;
import sk.uniza.fri.II008.generators.IGenerator;

public abstract class Simulation implements ISimulation
{
	public static final int UNLIMITED = -1;
	protected ISimulationListener simulationListener = new SimulationListener()
	{
	};
	protected IReplicationListener replicationListener = new ReplicationListener()
	{
	};
	private final long replicationCount, batchSize;
	private volatile long replication = 0;
	private volatile ISimulation.State state = ISimulation.State.STOPPED;
	protected final HashMap<IGenerator.IGeneratorType, IGenerator> generators;
	public static final Logger LOGGER = Logger.getLogger(EventSimulation.class.getName());

	static
	{
		LOGGER.setUseParentHandlers(false);
	}

	public Simulation(long replicationCount, long batchSize)
	{
		this.replicationCount = replicationCount;
		this.batchSize = batchSize;
		this.generators = new HashMap<>();
	}

	@Override
	public void setSimulationListener(ISimulationListener listener)
	{
		this.simulationListener = listener;
	}

	@Override
	public void setReplicationListener(IReplicationListener listener)
	{
		this.replicationListener = listener;
	}

	public IGenerator getGenerator(IGenerator.IGeneratorType generator)
	{
		return generators.get(generator);
	}

	@Override
	public void run()
	{
		replication = 0;
		state = ISimulation.State.RUNNING;

		simulationListener.onStart();
		replicationListener.onStart();

		while (replicationCount == UNLIMITED || replication < replicationCount)
		{
			if (state == ISimulation.State.PAUSED)
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

			prepareReplication(replication);
			Object[] data = runReplication(replication);

			if (state == ISimulation.State.STOPPED)
			{
				break;
			}

			replication++;

			if (replication % batchSize == 0)
			{
				simulationListener.onReplicationEnd(replication, data);
				replicationListener.onReplicationEnd(replication, data);
			}
		}

		if (state != State.STOPPED)
		{
			stop();
		}

		simulationListener.onSimulationEnd();
		replicationListener.onSimulationEnd();
	}

	@Override
	public void pause()
	{
		if (state == ISimulation.State.STOPPED)
		{
			throw new IllegalStateException("Simulation is stopped.");
		}

		state = state == ISimulation.State.RUNNING ? ISimulation.State.PAUSED : ISimulation.State.RUNNING;

		simulationListener.onPause();
		replicationListener.onPause();
	}

	@Override
	public void stop()
	{
		state = ISimulation.State.STOPPED;

		simulationListener.onStop();
		replicationListener.onStop();
	}

	@Override
	public ISimulation.State getState()
	{
		return state;
	}

	@Override
	public long getCurrentReplicationId()
	{
		return replication;
	}

	@Override
	public long getReplicationCount()
	{
		return replicationCount;
	}

	public boolean isEnabledLogging()
	{
		return LOGGER.getHandlers().length > 0;
	}

	public void log(String message)
	{
		if (isEnabledLogging())
		{
			LOGGER.info(message);
		}
	}

	protected abstract void prepareReplication(long replication);

	protected abstract Object[] runReplication(long replication);
}
