package sk.uniza.fri.II008.generators;

import java.util.Random;

public final class ContinuousGenerator implements IContinuosGenerator
{
	private final Random random;
	private double min, max;

	public ContinuousGenerator(Random random, double max)
	{
		this(random, 0, max);
	}

	public ContinuousGenerator(Random random, double min, double max)
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
	public double nextValue()
	{
		return min + random.nextDouble() * (max - min);
	}
}