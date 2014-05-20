package sk.uniza.fri.II008.tests;

import sk.uniza.fri.II008.Simulation;

public class SimulationExample extends Simulation
{
	public SimulationExample()
	{
		this(UNLIMITED, 1000);
	}

	public SimulationExample(long maxReplication, long batchSize)
	{
		super(maxReplication, batchSize);
	}

	@Override
	protected void prepareReplication(long replication)
	{

	}

	@Override
	protected Object[] runReplication(long replication)
	{
		return new Object[]
		{
		};
	}
}
