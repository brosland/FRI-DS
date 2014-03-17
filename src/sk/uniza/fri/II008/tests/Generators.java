package sk.uniza.fri.II008.tests;

import java.util.Random;
import java.util.TreeMap;
import sk.uniza.fri.II008.generators.ContinuousGenerator;
import sk.uniza.fri.II008.generators.DiscreteEmpiricGenerator;
import sk.uniza.fri.II008.generators.DiscreteGenerator;
import sk.uniza.fri.II008.generators.IDiscreteGenerator;

public class Generators
{
	public static void main(String[] args)
	{
		final Random seedGen = new Random();

		DiscreteGenerator dg = new DiscreteGenerator(new Random(seedGen.nextInt()), 10);
		System.out.println("DiscreteGenerator: max 10");

		for (int i = 0; i < 10; i++)
		{
			System.out.println(dg.nextValue());
		}

		DiscreteGenerator dg2 = new DiscreteGenerator(new Random(seedGen.nextInt()), 5, 10);
		System.out.println("\nDiscreteGenerator: min: 5, max 10");

		for (int i = 0; i < 10; i++)
		{
			System.out.println(dg2.nextValue());
		}

		ContinuousGenerator cg = new ContinuousGenerator(new Random(seedGen.nextInt()), 10.0);
		System.out.println("\nContinuousGenerator: max 10.0");

		for (int i = 0; i < 10; i++)
		{
			System.out.println(cg.nextValue());
		}

		ContinuousGenerator cg2 = new ContinuousGenerator(new Random(seedGen.nextInt()), 5.0, 10.0);
		System.out.println("\nContinuousGenerator: min: 5.0, max 10.0");

		for (int i = 0; i < 10; i++)
		{
			System.out.println(cg2.nextValue());
		}

		ContinuousGenerator cg3 = new ContinuousGenerator(new Random(seedGen.nextInt()), 0.0, 1.0);

		TreeMap<Double, IDiscreteGenerator> valueGenerators = new TreeMap<>();
		valueGenerators.put(0.2, new DiscreteGenerator(new Random(seedGen.nextInt()), 170, 196));
		valueGenerators.put(1.0, new DiscreteGenerator(new Random(seedGen.nextInt()), 196, 281));
		DiscreteEmpiricGenerator eg = new DiscreteEmpiricGenerator(cg3, valueGenerators);
		System.out.println("\nEmpiricalGenerator:");

		for (int i = 0; i < 10; i++)
		{
			System.out.println(eg.nextValue());
		}
	}
}