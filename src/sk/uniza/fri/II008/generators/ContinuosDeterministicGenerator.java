package sk.uniza.fri.II008.generators;

import java.util.Random;

public class DiscreteGenerator implements IDiscreteGenerator
{
	private final Random random;
	private int min, max;

	public DiscreteGenerator(Random random, int max)
	{
		this(random, 0, max);
	}

	public DiscreteGenerator(Random random, int min, int max)
	{
		this.random = random;

		if (min >= max)
		{
			throw new IllegalArgumentException("Min value must be lower than max value.");
		}

		this.min = min;
		this.max = max;
	}

	@Override
	public int nextValue()
	{
		return min + random.nextInt(max - min);
	}
}