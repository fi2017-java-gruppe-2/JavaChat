import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Spam_Protection implements Runnable
{
	private Thread t;
	private NavigableMap<String, Timestamp> spamliste;
	
	public Spam_Protection()
	{
		t = new Thread(this);
		spamliste = new TreeMap<String, Timestamp>();
		spamliste.put("Test", Timestamp.valueOf(LocalDateTime.now()));
		t.start();
	}
	@Override
	public void run()
	{
		try
		{
			Thread.sleep(5000);
			spamliste.clear();
		}
		catch(Exception e)
		{
			System.out.println("Exception in run Spam_Protection");
		}
	}
	public boolean checkSpam(String message, Timestamp ts, String ip )
	{
		if (!spamliste.isEmpty())
		{
			System.out.println("liste nicht ist leer");
			if (ts.getTime() - spamliste.lastEntry().getValue().getTime() >= 2000)
			{
				if (spamliste.get(message) != null)
				{
					System.out.println("die nachricht ist nicht leer");
					if (ts.getTime() - spamliste.get(message).getTime() >= 20000)
					{	
						spamliste.put(message, ts);
						return false;
					} else
					{
						System.out.println("spammt gleiche nachrichten");
						spamliste.put(message, ts);
						return true;
					}
				} else
				{
					spamliste.put(message, ts);
					return false;
				} 
			}
			else
			{
				System.out.println("spammt in kurzen zeiträumen");
				spamliste.put(message, ts);
				return true;
			}
		}
		else
		{
			spamliste.put(message, ts);
			return false;
		}
		
	}

}
