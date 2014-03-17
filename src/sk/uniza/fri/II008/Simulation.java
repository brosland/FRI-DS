package sk.uniza.fri.II008;

public abstract class Simulation implements ISimulation
{
	public static final int UNLIMETED = -1;
	private ISimulation.ISimulationListener listener = new ISimulation.ISimulationListener()
	{
		@Override
		public void onReplicationEnd(long replication, Object[] values)
		{
		}

		@Override
		public void onSimulationEnd()
		{
		}
	};
	private final long maxReplication, batchSize;
	private volatile long replication = 0;
	private volatile ISimulation.State state = ISimulation.State.STOPPED;

	public Simulation()
	{
		this(UNLIMETED, 10000);
	}

	public Simulation(long maxReplication, long batchSize)
	{
		this.maxReplication = maxReplication;
		this.batchSize = batchSize;
	}

	@Override
	public void setListener(ISimulation.ISimulationListener listener)
	{
		this.listener = listener;
	}

	@Override
	public void run()
	{
		state = ISimulation.State.RUNNING;

		while (state != ISimulation.State.STOPPED
			&& (maxReplication == UNLIMETED || replication < maxReplication))
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

			replication++;

			if (replication % batchSize == 0)
			{
				listener.onReplicationEnd(replication, data);
			}
		}

		listener.onSimulationEnd();
	}

	@Override
	public void pause()
	{
		if (state == ISimulation.State.STOPPED)
		{
			throw new IllegalStateException("Simulation is stopped.");
		}

		state = state == ISimulation.State.RUNNING ? ISimulation.State.PAUSED : ISimulation.State.RUNNING;
	}

	@Override
	public void stop()
	{
		state = ISimulation.State.STOPPED;
	}

	@Override
	public ISimulation.State getState()
	{
		return state;
	}

	protected abstract void prepareReplication(long replication);

	protected abstract Object[] runReplication(long replication);
}
