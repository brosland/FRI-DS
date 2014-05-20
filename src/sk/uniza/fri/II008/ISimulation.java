package sk.uniza.fri.II008;

public interface ISimulation extends Runnable
{
	public interface ISimulationListener
	{
		public void onReplicationEnd(long replication, Object[] values);

		public void onSimulationEnd();

		public void onStart();

		public void onPause();

		public void onStop();
	}

	public interface IReplicationListener extends ISimulationListener
	{
		public void onChange();
	}

	public enum State
	{
		STOPPED, RUNNING, PAUSED
	}

	public void setSimulationListener(ISimulationListener listener);

	public void setReplicationListener(ISimulation.IReplicationListener listener);

	public State getState();

	public void pause();

	public void stop();

	public long getCurrentReplicationId();

	public long getReplicationCount();
}
