package sk.uniza.fri.II008.generators;

import java.util.Random;

public final class ContinuousTriangularGenerator implements IContinuosGenerator
{
	private final Random random;
	private double min, max, modus;

	public ContinuousTriangularGenerator(Random random, double min, double max, double modus)
	{
		if (min >= max)
		{
			throw new IllegalArgumentException("Min value must be lower than max value.");
		}

		if (modus < min || modus > max)
		{
			throw new IllegalArgumentException("Modus is out of range.");
		}

		this.random = random;
		this.min = min;
		this.max = max;
		this.modus = modus;
	}

	@Override
	public double nextValue()
	{
		double x = random.nextDouble();
		double f = (modus - min) / (max - min);

		if (f > x)
		{
			return min + Math.sqrt(x * (max - min) * (modus - min));
		}
		else
		{
			return max - Math.sqrt((1 - x) * (max - min) * (max - modus));
		}
	}
}
