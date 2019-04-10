

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class RegistrierenControl
{

	private String nutzername;
	private String passwort1;
	private String passwort2;
	private String nutzernameVorhanden;
	private JLabel lblStatus;
	private boolean vorhanden;
	private JFrame registrierenGui;

	public RegistrierenControl(JFrame registrierenGui)
	{
		this.registrierenGui = registrierenGui;
	}
	
	public void registrieren(String nutzername, String passwort1, String passwort2, JLabel lblStatus)
	{
		this.nutzername = nutzername;
		this.passwort1 = passwort1;
		this.passwort2 = passwort2;
		this.lblStatus = lblStatus;
		
		if(vorhanden == false && passwortPruefen(passwort1, passwort2) == true)
		{
			if(nutzername.equals("") || passwort1.equals(""))
			{
				lblStatus.setText("Nutzername oder Passwort zu kurz!");
			}
			else if(nutzername.length() > 20 || passwort1.length() > 20)
			{
				lblStatus.setText("Nutzername oder Passwort zu lang (max. 20 Zeichen)!");
			}
			else if(nutzername.contains(";") || passwort1.contains(";"))
			{
				lblStatus.setText("Nutzername und Passwort darf kein ; enthalten!");
			}
			else
			{
				speichern(nutzername, passwort1);
				System.out.println("Registriert!");
				registrierenGui.setVisible(false);
			}
		}
		
	}

	private void speichern(String nutzername, String passwort)
	{
		Path path = Paths.get("Anmeldedaten.txt");
		try
		{
			BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
			writer.write("\n" + nutzername + "~" + passwort);
			writer.close();
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

	private boolean passwortPruefen(String passwort1, String passwort2)
	{
		if(passwort1.compareTo(passwort2) != 0)
		{
			lblStatus.setText("Passwörter stimmen nicht überein!");
			return false;		
		}	
		else
		{
			return true;
		}
	}

	public boolean nutzernamePruefen(String nutzername)
	{
		Path path = Paths.get("Anmeldedaten.txt");
		vorhanden = false;
		try
		{
			String zeile = null;
			BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);

			while ((zeile = reader.readLine()) != null)
			{
				String[] s = zeile.split("~");
				nutzernameVorhanden = s[0];
				if (nutzername.equals(nutzernameVorhanden))
				{				
					vorhanden = true;
				}
			}
			reader.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return vorhanden;
	}

}
