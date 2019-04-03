
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JLabel;

public class AnmeldeControl
{
	private JLabel lblFehlermeldung;
	private AnmeldeGui ag;

	public AnmeldeControl(AnmeldeGui ag, JLabel lblFehlermeldung)
	{
		this.ag = ag;
		this.lblFehlermeldung = lblFehlermeldung;
	}

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					AnmeldeGui anmeldeGui = new AnmeldeGui();
					anmeldeGui.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	private String nutzername;
	private String passwort;
	private String nutzername2;
	private String passwort2;

	public void anmelden(String nutzername, String passwort)
	{
		this.nutzername = nutzername;
		this.passwort = passwort;

		Path path = Paths.get("H:\\git\\JavaChat\\src\\Anmeldedaten.txt");

		try
		{
			String zeile = null;
			BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);

			boolean anmelden = false;

			while ((zeile = reader.readLine()) != null)
			{
				String[] s = zeile.split(";");
				nutzername2 = s[0];
				passwort2 = s[1];
				if (nutzername.equals(nutzername2) && passwort.equals(passwort2))
				{
					ClientGui frame = new ClientGui(nutzername);
					frame.setVisible(true);

					anmelden = true;
				}
			}
			reader.close();

			if (anmelden == false)
			{
				lblFehlermeldung.setText("Falscher Nutzername oder Passwort");
			} else
			{
				ag.setVisible(false);
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
