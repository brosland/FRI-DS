package sk.uniza.fri.II008;

public class Utils
{
	public static String formatTime(double timestamp)
	{
		double x = timestamp;

		int days = (int) (x / (3600 * 24));
		x -= days * 3600 * 24;

		int hours = (int) (x / 3600);
		x -= hours * 3600;

		int minutes = (int) (x / 60);
		x -= minutes * 60;

		int seconds = (int) (x % 60);

		if (days > 0)
		{
			return String.format("%d. %02d:%02d:%02d", days, hours, minutes, seconds);
		}
		else
		{
			return String.format("%02d:%02d:%02d", hours, minutes, seconds);
		}
	}

	public static double createTimestampFromTime(String time)
	{
		String[] parts = time.split(":");

		try
		{
			int hours = Integer.parseInt(parts[0]);
			int minutes = Integer.parseInt(parts[1]);
			int seconds = Integer.parseInt(parts[2]);

			if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59
				|| seconds < 0 || seconds > 59)
			{
				throw new NumberFormatException();
			}

			return hours * 60 * 60 + minutes * 60 + seconds;
		}
		catch (Exception ex)
		{
			throw new IllegalArgumentException("Time must have format HH:MM:SS.");
		}
	}
}
