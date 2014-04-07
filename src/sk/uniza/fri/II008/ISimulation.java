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

	public enum State
	{
		STOPPED, RUNNING, PAUSED
	}

	public void setSimulationListener(ISimulationListener listener);

	public State getState();

	public void pause();

	public void stop();

	public long getCurrentReplication();

	public long getReplicationCount();
}
