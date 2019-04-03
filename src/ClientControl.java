
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
	private Socket client;
	
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

	public void sendeNachricht()
	{
		Packet packet = Packet.create("Message", textFieldNachricht.getText());
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

	private void empfangeNachricht(Packet packet)
	{
		switch (packet.getHeader())
		{
		case "Message":
			String msg = packet.unpack(String.class);
			System.out.println(msg);
			listNachrichten.addItem(msg);
			break;
		case "Disconnect":
			String discon = packet.unpack(String.class);
			theEnd();
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
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ClientGui frame = new ClientGui();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}
