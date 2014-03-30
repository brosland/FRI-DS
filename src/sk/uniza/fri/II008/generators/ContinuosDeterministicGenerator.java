package sk.uniza.fri.II008.generators;

public class ContinuosDeterministicGenerator implements IContinuosGenerator
{
	private final double value;

	public ContinuosDeterministicGenerator(double value)
	{
		this.value = value;
	}

	@Override
	public double nextValue()
	{
		return value;
	}
}
