import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;

public class Spam_Protection implements Runnable
{
	private Thread t;
	private HashMap<String, Timestamp> spamliste;
	
	public Spam_Protection()
	{
		t = new Thread(this);
		spamliste = new HashMap<>();
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
			if (spamliste.get(message) != null)
			{
				if (ts.getTime() - spamliste.get(message).getTime() >= 20000)
				{
					spamliste.put(message, ts);
					
					return false;
				} else
				{
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
			spamliste.put(message, ts);
			
			return false;
		}
		
	}

}
