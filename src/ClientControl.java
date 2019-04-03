

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

public class ClientControl extends Thread
{
	private JLabel labelGesendet;
	private JTextField textFieldLocalhost;
	private JTextField textFieldPort;
	private JTextField textFieldNachricht;
	private JTextField textFieldDatei;
	private ChatListe<String> listTeilnehmer;
	private ChatListe<String> listNachrichten;
	private ChatListe<String> listDateien;
	private List<PrivateChatGui> listpcg;
	private Socket client;
	private String nutzername;
	private InputStream in;
	private OutputStream out;

	
	public ClientControl(JLabel labelGesendet, JTextField textFieldIP, JTextField textFieldPort,
			JTextField textFieldNachricht, JTextField textFieldDatei, ChatListe<String> listTeilnehmer,
			ChatListe<String> listNachrichten, ChatListe<String> listDateien)
	{
		this.labelGesendet = labelGesendet;
		this.textFieldLocalhost = textFieldIP;
		this.textFieldPort = textFieldPort;
		this.textFieldNachricht = textFieldNachricht;
		this.textFieldDatei = textFieldDatei;
		this.listTeilnehmer = listTeilnehmer;
		this.listNachrichten = listNachrichten;
		this.listDateien = listDateien;
		listpcg = new ArrayList<PrivateChatGui>();
	}

	public void verbindeZuServer()
	{
		try
		{
			client = new Socket(textFieldLocalhost.getText(), Integer.parseInt(textFieldPort.getText()));
			labelGesendet.setText("verbunden");
			this.start();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void sendeNachricht(String art, String nachricht)
	{
		Packet packet = Packet.create(art, nachricht);
		if (packet != null)
		{
			byte[] bytes = ProtocolHelper.createBytes(packet);
			try
			{
				client.getOutputStream().write(bytes);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void sendePrivateNachricht(String nachricht, String empfaenger, String absender)
	{
		String[] nachrichtkomplett = new String[3];
		nachrichtkomplett[0] = nachricht;
		nachrichtkomplett[1] = empfaenger;
		nachrichtkomplett[2] = absender;
		Packet packet = Packet.create("PrivateMessage", nachrichtkomplett);
		for (int i = 0; i < listpcg.size(); i++)
		{
			if (listpcg.get(i).getChatpartner().compareTo(empfaenger) == 0)
			{
				listpcg.get(i).getList().addItem(absender + ": " + nachricht);
			}
		}
		if (packet != null)
		{
			byte[] bytes = ProtocolHelper.createBytes(packet);
			try
			{
				client.getOutputStream().write(bytes);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void empfangeNachricht(Packet packet)
	{
		switch (packet.getHeader())
		{
		case "Message":
			String msg = packet.unpack(String.class);
			System.out.println(msg);
			listTeilnehmer.addItem(msg);
			break;
		case "Disconnect":
			String discon = packet.unpack(String.class);
			theEnd();
			break;
		case "Nutzerliste":
			String[] nutzerl = packet.unpack(String[].class);
			listTeilnehmer.clear();
			for (int i = 0; i < nutzerl.length; i++)
			{
				listTeilnehmer.addItem(nutzerl[i]);
			}
			break;
		case "PrivateMessage":
			String[] msgp = packet.unpack(String[].class);
			for (int i = 0; i < listpcg.size(); i++)
			{
				if (listpcg.get(i).getChatpartner().compareTo(msgp[2]) == 0)
				{
					listpcg.get(i).getList().addItem(msgp[2] + ": " + msgp[0]);
					return;
				}
			}
			neuenPrivatChatStarten(msgp[2]);
			listpcg.get(listpcg.size() - 1).getList().addItem(msgp[2] + ": " + msgp[0]);
			break;
		default:
			break;
		}
	}
	
	public void theEnd()
	{
		try
		{
			client.close();
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void run()
	{
		try
		{
			in = client.getInputStream();
			out = client.getOutputStream();
			
			while (!this.isInterrupted())
			{
				// erste 4 bytes lesen -> zu int
				// die rest bytes auslesen und bei createPacket einsetzen

				byte[] lengthBytes = receive(4);
				int length = ByteBuffer.wrap(lengthBytes).getInt();
				

				Packet p = ProtocolHelper.createPacket(receive(length) /* hier kommen die gelesenen bytes rein */);
				empfangeNachricht(p);

				Thread.sleep(10);
			}
		} 
		
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			interrupt();
			e.printStackTrace();
		} 
		catch(SocketException ex)
		{
			labelGesendet.setText("Disconnected");
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			interrupt();
			e.printStackTrace();
		}
	}

	private byte[] receive(int length)
	{
		try
		{
			ByteBuffer byteBuffer = ByteBuffer.allocate(length);
			byte[] buffer = (length > 4096) ? new byte[4096] : new byte[length];
			int bytesRead = 0;
			for (int i = 0; i < length; i += bytesRead)
			{
				bytesRead = in.read(buffer, 0, buffer.length);
				byteBuffer.put(buffer, 0, bytesRead);
			}
			return byteBuffer.array();
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

		}
	}
	
	public void neuenPrivatChatStarten(String selectedItem)
	{
		for (int i = 0; i < listpcg.size(); i++)
		{
			if (listpcg.get(i).getChatpartner().compareTo(selectedItem) == 0)
			{
				listpcg.get(i).setVisible(true);
				return;
			}
		}
		listpcg.add(new PrivateChatGui(nutzername, selectedItem, this));
		listpcg.get(listpcg.size() - 1).setVisible(true);
	}
	
	public String getNutzername() {
		return nutzername;
	}
	
	public void setNutzername(String nutzername) {
		this.nutzername = nutzername;
	}
}
