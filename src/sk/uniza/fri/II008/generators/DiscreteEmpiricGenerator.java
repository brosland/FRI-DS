package sk.uniza.fri.II008.generators;

import java.util.TreeMap;

public class DiscreteEmpiricGenerator implements IDiscreteGenerator
{
	private final IContinuosGenerator intervalGenerator;
	private final TreeMap<Double, IDiscreteGenerator> valueGenerators;

	public DiscreteEmpiricGenerator(IContinuosGenerator intervalGenerator, TreeMap<Double, IDiscreteGenerator> valueGenerators)
	{
		if (valueGenerators.isEmpty())
		{
			throw new IllegalArgumentException("TreeMap of value generators is empty.");
		}

		this.intervalGenerator = intervalGenerator;
		this.valueGenerators = valueGenerators;
	}

	@Override
	public int nextValue()
	{
		double intervalValue = intervalGenerator.nextValue();

		for (double key : valueGenerators.keySet())
		{
			if (intervalValue < key)
			{
//				System.out.print(intervalValue + " => " + key + " => ");
				return valueGenerators.get(key).nextValue();
			}
		}

		throw new IllegalArgumentException("For interval value is not defined any value generator.");
	}
}