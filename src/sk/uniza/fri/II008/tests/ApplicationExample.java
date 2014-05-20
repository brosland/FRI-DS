package sk.uniza.fri.II008.tests;

import java.util.Scanner;
import sk.uniza.fri.II008.ISimulation;
import sk.uniza.fri.II008.SimulationListener;

public class ApplicationExample
{
	public static void main(String[] args)
	{
		ISimulation simulation = new SimulationExample(SimulationExample.UNLIMITED, 10000000);
		simulation.setSimulationListener(new SimulationListener()
		{
			@Override
			public void onReplicationEnd(long replication, Object[] values)
			{
				System.out.print(".");
			}

			@Override
			public void onSimulationEnd()
			{
				System.out.println("Simulation stopped.");
			}
		});
		
		new Thread(simulation).start();
		
		Scanner scanner = new Scanner(System.in);
		
		while (true)
		{
			System.out.println("Press \"P\" to pause or unpause simulation.\n"
				+ "Press \"S\" to stop simulation.\n");
			String command = scanner.nextLine().toLowerCase();
			
			if (command.equals("p"))
			{
				simulation.pause();
			}
			else if (command.equals("s"))
			{
				simulation.stop();
				break;
			}
		}
	}
}
