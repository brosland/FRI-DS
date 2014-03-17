package sk.uniza.fri.II008.generators;

import java.util.Random;

public final class ContinuousExponencialGenerator implements IContinuosGenerator
{
	private final Random random;
	private final double lambda;

	public ContinuousExponencialGenerator(Random random, double lambda)
	{
		this.random = random;
		this.lambda = lambda;
	}

	@Override
	public double nextValue()
	{
		return -1 / lambda * Math.log(random.nextDouble());
	}
}