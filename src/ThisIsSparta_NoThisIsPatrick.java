import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class ThisIsSparta_NoThisIsPatrick implements Runnable //because Patrick is the real threat
{
	private HashMap<String, LocalDateTime> verbindungsListe;
	private ArrayList<String> bannliste;
	private Thread t;
	
	public ThisIsSparta_NoThisIsPatrick()
	{
		verbindungsListe = new HashMap<>();
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
			verbindungsListe.clear();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	private Boolean detectDDos(String IPadresse)
	{
		
		if(verbindungsListe.containsKey(IPadresse))
		{
			if(verbindungsListe.containsKey(IPadresse))
			{
				LocalDateTime letzterLogin = verbindungsListe.get(IPadresse);
				LocalDateTime now = LocalDateTime.now();
				
				if(now.getSecond() - letzterLogin.getSecond() <= 2)
				{
					verbindungsListe.put(IPadresse,now);
					return false;
				}
				else
				{
					bannliste.add(IPadresse);
					verbindungsListe.put(IPadresse,now);
					return true;
				}
			}
			else
			{
				verbindungsListe.put(IPadresse, LocalDateTime.now());
				return false;
			}
		}
		else
		{
			return true;
		}
	}
	
}
