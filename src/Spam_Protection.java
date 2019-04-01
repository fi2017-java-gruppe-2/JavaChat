import java.time.LocalDateTime;
import java.util.HashMap;

public class Spam_Protection implements Runnable
{
	private Thread t;
	private HashMap<String, LocalDateTime> spamliste;
	
	public Spam_Protection()
	{
		t = new Thread(this);
		spamliste = new HashMap<>();
		t.start();
	}
	@Override
	public void run()
	{
		try
		{
			Thread.sleep(200);
		}
		catch(Exception e)
		{
			System.out.println("Exception in run Spam_Protection");
		}
		
	}
	private boolean checkSpam(String message, LocalDateTime sendTime, String ip )
	{
		return true;	
	}

}
