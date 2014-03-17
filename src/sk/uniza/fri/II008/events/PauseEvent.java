package sk.uniza.fri.II008.events;

import java.util.logging.Level;
import java.util.logging.Logger;
import sk.uniza.fri.II008.EventSimulation;

public class PauseEvent extends Event
{
	private final long interval, paused;

	public PauseEvent(long timestamp, EventSimulation simulation, long interval, long paused)
	{
		super(timestamp, simulation);

		this.interval = interval;
		this.paused = paused;
	}

	@Override
	public void run()
	{
		try
		{
			Thread.sleep(paused);

			Event event = new PauseEvent(timestamp + interval, simulation, interval, paused);
			simulation.addEvent(event);
		}
		catch (InterruptedException ex)
		{
			Logger.getLogger(PauseEvent.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
