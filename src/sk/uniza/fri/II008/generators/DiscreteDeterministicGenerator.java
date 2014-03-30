package sk.uniza.fri.II008.generators;

public class DiscreteDeterministicGenerator implements IDiscreteGenerator
{
	private final int value;

	public DiscreteDeterministicGenerator(int value)
	{
		this.value = value;
	}

	@Override
	public int nextValue()
	{
		return value;
	}
}
