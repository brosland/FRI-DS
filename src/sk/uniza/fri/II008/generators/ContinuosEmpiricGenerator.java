package sk.uniza.fri.II008.generators;

import java.util.TreeMap;

public class ContinuosEmpiricGenerator implements IContinuosGenerator
{
	private final IContinuosGenerator intervalGenerator;
	private final TreeMap<Double, IContinuosGenerator> valueGenerators;

	public ContinuosEmpiricGenerator(IContinuosGenerator intervalGenerator, TreeMap<Double, IContinuosGenerator> valueGenerators)
	{
		if (valueGenerators.isEmpty())
		{
			throw new IllegalArgumentException("TreeMap of value generators is empty.");
		}

		this.intervalGenerator = intervalGenerator;
		this.valueGenerators = valueGenerators;
	}

	@Override
	public double nextValue()
	{
		double intervalValue = intervalGenerator.nextValue();

		double lastKey = 0;
		
		for (double key : valueGenerators.keySet())
		{
			if (intervalValue < key)
			{
//				System.out.printf("%.6f => <%.6f, %.6f)\n", intervalValue, lastKey, key);
				return valueGenerators.get(key).nextValue();
			}
			
			lastKey = key;
		}

		throw new IllegalArgumentException("For interval value is not defined any value generator.");
	}
}