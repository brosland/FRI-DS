package sk.uniza.fri.II008;

public abstract class Simulation implements ISimulation
{
	public static final int UNLIMETED = -1;
	private ISimulation.ISimulationListener listener = new SimulationListener()
	{
	};
	private final long replicationCount, batchSize;
	private volatile long replication = 0;
	private volatile ISimulation.State state = ISimulation.State.STOPPED;

	public Simulation()
	{
		this(UNLIMETED, 10000);
	}

	public Simulation(long replicationCount, long batchSize)
	{
		this.replicationCount = replicationCount;
		this.batchSize = batchSize;
	}

	@Override
	public void setSimulationListener(ISimulation.ISimulationListener listener)
	{
		this.listener = listener;
	}

	@Override
	public void run()
	{
		replication = 0;
		state = ISimulation.State.RUNNING;

		listener.onStart();

		while (replicationCount == UNLIMETED || replication < replicationCount)
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
				listener.onReplicationEnd(replication, data);
			}
		}

		if (state != State.STOPPED)
		{
			stop();
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

		listener.onPause();
	}

	@Override
	public void stop()
	{
		state = ISimulation.State.STOPPED;

		listener.onStop();
	}

	@Override
	public ISimulation.State getState()
	{
		return state;
	}

	@Override
	public long getCurrentReplication()
	{
		return replication;
	}

	@Override
	public long getReplicationCount()
	{
		return replicationCount;
	}

	protected abstract void prepareReplication(long replication);

	protected abstract Object[] runReplication(long replication);
}
