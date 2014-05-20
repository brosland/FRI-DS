package sk.uniza.fri.II008.generators;

import java.util.Random;

public final class ContinuousExponencialGenerator implements IContinuosGenerator
{
	private final Random random;
	private final double lambda, offset;

	public ContinuousExponencialGenerator(Random random, double lambda)
	{
		this(random, lambda, 0.0);
	}

	public ContinuousExponencialGenerator(Random random, double lambda, double offset)
	{
		this.random = random;
		this.lambda = lambda;
		this.offset = offset;
	}

	@Override
	public double nextValue()
	{
		return offset + -Math.log(1.0 - random.nextDouble()) / lambda;
	}
}
