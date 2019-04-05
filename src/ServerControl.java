
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

public class ServerControl implements Runnable
{
	private JTextField textFieldPort;
	private JTextField textFieldLocalHost;
	private JLabel labelStatus;
	private JTextField textFieldNachricht;
	private ArrayList<ClientProxy> proxyList = new ArrayList<>();
	private ServerSocket server;
	private Thread t;
	private Socket client = null;
	private ThisIsSparta_NoThisIsPatrick dDosProtection = new ThisIsSparta_NoThisIsPatrick();
	private ClientProxy c;


	public ServerControl(JTextField textFieldPort, JTextField textFieldNachricht, JLabel labelStatus)
	{
		this.textFieldPort = textFieldPort;
		this.textFieldNachricht = textFieldNachricht;
		this.labelStatus = labelStatus;
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
			server.setSoTimeout(5000);
			while (!Thread.currentThread().isInterrupted())
			{
				try
				{
<<<<<<< HEAD
					Packet packet = Packet.create("Reply", "");
					byte[] bytes = ProtocolHelper.createBytes(packet);
					client.getOutputStream().write(bytes);
					client.close();
					System.out.println("DDOS1");
				}
				else
				{
					if (client.isBound())
=======
					client = server.accept();
					if(dDosProtection.detectDDos(client.getInetAddress().toString()))
>>>>>>> branch 'master' of https://github.com/fi2017-java-gruppe-2/JavaChat.git
					{
						client.close();
						System.out.println("DDOS1");
					}
					else
					{
						if (client.isBound())
						{
							labelStatus.setText("Client verbunden");
							c = new ClientProxy(client, this, textFieldNachricht);
							System.out.println("bla");
							proxyList.add(c);
						} 
						else
						{
							System.out.println("Client nicht verbunden");
						}
					}
				}
				catch(SocketTimeoutException e)
				{
				
				}
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
			broadcastMessage(p);
			break;
		case "Disconnect":
			try
			{
				c.getSocket().close();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
			break;
		case "Nutzername":	
			String nutzer = p.unpack(String.class);
			proxyList.get(proxyList.size() - 1).setNutzername(nutzer);
			String[] array = new String[proxyList.size()];
			for (int i = 0; i < proxyList.size(); i++)
			{
				array[i] = proxyList.get(i).getNutzername();
			}
			broadcastMessage(Packet.create("Nutzerliste", array));
			break;
		case "PrivateMessage":
			for (int i = 0; i < proxyList.size(); i++)
			{
				if (proxyList.get(i).getNutzername().compareTo(p.unpack(String[].class)[1]) == 0)
				{
					proxyList.get(i).writeMessage(p);
				}
			}
			break;
		case "Image":
			broadcastMessage(p);
			break;
		default:
			break;
		}

	}
	//geht noch nicht
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
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

}