import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class ThisIsSparta_NoThisIsPatrick implements Runnable //because Patrick is the real threat
{
	private HashMap<String, Timestamp> verbindungsListe;
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
	
	public Boolean detectDDos(String IPadresse)
	{
		System.out.println(IPadresse);
		if(!bannliste.contains(IPadresse))
		{
			System.out.println("test");
			if(verbindungsListe.containsKey(IPadresse))
			{
				Timestamp letzterLogin = verbindungsListe.get(IPadresse);
				Timestamp now = Timestamp.valueOf(LocalDateTime.now());
				
				System.out.println(now.getTime() - letzterLogin.getTime());
				
				if(now.getTime() - letzterLogin.getTime() >= 5000)
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
				verbindungsListe.put(IPadresse, Timestamp.valueOf(LocalDateTime.now()));
				return false;
			}
		}
		else
		{
			return true;
		}
	}
}
