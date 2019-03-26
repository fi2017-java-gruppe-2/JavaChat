import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class ThisIsSparta_NoThisIsPatrick implements Runnable //because Patrick is the real threat
{
	private HashMap<String, LocalDateTime> verbindungsliste;
	private ArrayList<String> bannliste;
	private Thread t;
	
	public ThisIsSparta_NoThisIsPatrick()
	{
		verbindungsliste = new HashMap<>();
		bannliste = new ArrayList<>();
		t = new Thread(this);
		t.setName("leereBlacklist");
		t.start();
	}
	
	@Override
	public void run()
	{
		try
		{
			Thread.sleep(2000);
			verbindungsliste.clear();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	private Boolean detectDDos(String IPadresse)
	{
		
		if(verbindungsliste.containsKey(IPadresse))
		{
			f(connectionProtocol.containsKey(IPadresse))
			{
				LocalDateTime letzterLogin = connectionProtocol.get(IPadresse);
				LocalDateTime now = LocalDateTime.now();
				
				if(now.getSecond() - letzterLogin.getSecond() <= 2)
				{
					connectionProtocol.put(IPadresse,now);
					return false;
				}
				else
				{
					connectionBanList.add(IPadresse);
					connectionProtocol.put(IPadresse,now);
					return true;
				}
			}
			else
			{
				connectionProtocol.put(IPadresse, LocalDateTime.now());
				return false;
			}
		}
		else
		{
			return true;
		}
	}
	
}
