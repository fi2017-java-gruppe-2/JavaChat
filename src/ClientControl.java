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
	private ObjectOutputStream writer;
	private JList list;
	private DefaultListModel<String> chat;
	private Socket client;
	
	public ClientControl(JLabel labelGesendet, JTextField textFieldLocalhost, JTextField textFieldNachricht, JList list, DefaultListModel<String> chat)
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
			OutputStream out = client.getOutputStream();
			writer = new ObjectOutputStream(out);
			this.start();
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void sendeNachricht()
	{
		Protokoll p = new Protokoll();
		p.setMsg(true, textFieldNachricht.getText());
		try
		{
			System.out.println(p.getMessage());
			writer.writeObject(p);
			writer.flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void empfangeNachricht(Protokoll p)
	{
		System.out.println("client empfängt: " + p);
		if(p.isMsg())
		{
			chat.addElement(p.getMessage());
		}
	}
	
	public void run()
	{
		try
		{
			InputStream in = client.getInputStream();
			ObjectInputStream reader = new ObjectInputStream(in);
			while(!this.isInterrupted())
			{
				Object o = reader.readObject();
				Protokoll p = (Protokoll) o;
				empfangeNachricht(p);
				Thread.sleep(10);
			}
		} catch (IOException e)
		{
			System.out.println("komischer fehler");
			e.printStackTrace();
		} catch (InterruptedException e)
		{
			
			e.printStackTrace();
			this.interrupt();
		} catch (ClassNotFoundException e)
		{
			System.out.println("nicht vom typ Protokoll");
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
