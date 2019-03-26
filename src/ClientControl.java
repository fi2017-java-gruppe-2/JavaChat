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

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

public class ClientControl extends Thread
{
	private JLabel labelGesendet;
	private JTextField textFieldLocalhost;
	private JTextField textFieldNachricht;
	private JList list;
	private DefaultListModel<String> chat;
	private Socket client;

	public ClientControl(JLabel labelGesendet, JTextField textFieldLocalhost, JTextField textFieldNachricht, JList list,
			DefaultListModel<String> chat)
	{
		this.labelGesendet = labelGesendet;
		this.textFieldLocalhost = textFieldLocalhost;
		this.textFieldNachricht = textFieldNachricht;
		this.list = list;
		this.chat = chat;
	}

	public void verbindeZuServer()
	{
		try
		{
			client = new Socket(textFieldLocalhost.getText(), 8008);
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
			// handle msg
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
			while (!this.isInterrupted())
			{
				// erste 4 bytes lesen -> zu int
				// die rest bytes auslesen und bei createPacket einsetzen

				Packet p = ProtocolHelper.createPacket(null /* hier kommen die gelesenen bytes rein */);
				empfangeNachricht(p);

				Thread.sleep(10);
			}
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
