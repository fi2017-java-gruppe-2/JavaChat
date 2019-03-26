import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

public class ServerControl implements Runnable
{
	private JTextField textFieldPort;
	private JTextField textFieldLocalHost;
	private JLabel labelStatus;
	private JList list;
	private DefaultListModel<String> nachrichten;
	private ArrayList<ClientProxy> proxyList = new ArrayList<>();
	private ServerSocket server;
	private Thread t;
	private Socket client = null;

	public ServerControl(JTextField textFieldPort, JTextField textFieldLocalHost, JLabel labelStatus, JList list,
			DefaultListModel<String> nachrichten)
	{
		this.textFieldPort = textFieldPort;
		this.textFieldLocalHost = textFieldLocalHost;
		this.labelStatus = labelStatus;
		this.list = list;
		this.nachrichten = nachrichten;
		
	}

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ServerGui frame = new ServerGui();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	public void run()
	{
		try
		{
			server = new ServerSocket(Integer.parseInt(textFieldPort.getText()));
			while (!Thread.currentThread().isInterrupted())
			{
				client = server.accept();
				if (client.isBound())
				{
					labelStatus.setText("Client verbunden");
					ClientProxy c = new ClientProxy(client, this);
					System.out.println("bla");
					proxyList.add(c);
				} 
				else
					System.out.println("blubb");
			}

		}
		catch(SocketException e)
		{
			System.out.println("Laut Luwe dem CodeGod!!!!!");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void verarbeiteNachricht(Packet p)
	{

		switch (p.getHeader())
		{
		case "Message":
			String msg = p.unpack(String.class);
			// handle msg
			break;
		case "Disconnect":
			String discon = p.unpack(String.class);
			beendeServer();
		default:
			break;
		}

	}

	public void broadcastMessage(Packet p)
	{
		for (ClientProxy cp : proxyList)
		{
			cp.writeMessage(p);
		}
	}

	public void starteServer()
	{
		t = new Thread(this);
		t.start();
	}

	public void beendeServer()
	{
		
		try
		{

			t.interrupt();

			server.close();
			t.join();
			for(ClientProxy cp : proxyList)
			{
				cp.interrupt();
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

	}

}