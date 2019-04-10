
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StreamCorruptedException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ClientControl extends Thread
{
	private JLabel labelGesendet;
	private JTextField textFieldLocalhost;
	private JTextField textFieldPort;
	private JTextField textFieldNachricht;
	private ChatListe<String> listTeilnehmer;
	private ChatListe<String> listNachrichten;
	private ChatListe<String> listDateien;
	private List<PrivateChatGui> listpcg;
	private Socket client;
	private String nutzername;
	private InputStream in;
	private OutputStream out;
	private String bilderOrdner = "H:\\ChatBilder\\";

	public ClientControl(JLabel labelGesendet, JTextField textFieldIP, JTextField textFieldPort,
			JTextField textFieldNachricht, ChatListe<String> listTeilnehmer, ChatListe<String> listNachrichten,
			ChatListe<String> listDateien, String nutzername)
	{
		this.labelGesendet = labelGesendet;
		this.textFieldLocalhost = textFieldIP;
		this.textFieldPort = textFieldPort;
		this.textFieldNachricht = textFieldNachricht;
		this.listTeilnehmer = listTeilnehmer;
		this.listNachrichten = listNachrichten;
		this.listDateien = listDateien;
		this.nutzername = nutzername;
		listpcg = new ArrayList<PrivateChatGui>();
		listDateien.getList().addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent evt)
			{
				// System.out.println("test");
				if (listDateien.getSelectedIndex() > -1)
				{
					if (evt.getClickCount() == 2)
					{
						// System.out.println("test");
						String path = listDateien.getSelectedItem();

						JFrame frame = bildFrame();
						ImageIcon image = new ImageIcon(path);
						JLabel label = new JLabel(image);
						frame.add(label, BorderLayout.CENTER);
						frame.pack();
						frame.setVisible(true);

					}
				}
			}
		});
	}

	private JFrame bildFrame()
	{
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(contentPane);
		return frame;
	}

	public void sendeBild(JFrame cControl)
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setFileFilter(new FileNameExtensionFilter("Bilder", "jpg", "png"));
		if (chooser.showOpenDialog(cControl) == JFileChooser.APPROVE_OPTION)
		{
			Packet packet = Packet.create("Image", new BildHandler(chooser.getSelectedFile()));
			try
			{
				client.getOutputStream().write(ProtocolHelper.createBytes(packet));
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void portIsOpen()
	{
		for(int i = 8000 ; i <= 10000; i++)
		{
			try
			{
				Socket socket = new Socket();
				socket.connect(new InetSocketAddress(textFieldNachricht.getText(), i), 50);
				socket.close();
				System.out.println(i + " is open");
			} catch (Exception ex)
			{
				System.out.println(i + " is closed");
			}
		}
	}

	public void verbindeZuServer()
	{
		try
		{
			if (textFieldLocalhost.getText().equals("//checkPorts"))
			{
				portIsOpen();
			}
			else
			{
				client = new Socket(textFieldLocalhost.getText(), Integer.parseInt(textFieldPort.getText()));
				labelGesendet.setText("verbunden");
				this.start();
				sendeNachricht("Nutzername", nutzername, "");
			}

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void sendeNachricht(String art, String nachricht, String absender)
	{
		String[] nachrichtkomplett = new String[2];
		nachrichtkomplett[0] = nachricht;
		nachrichtkomplett[1] = absender;
		Packet packet = Packet.create(art, nachrichtkomplett);
		if (packet != null)
		{
			byte[] bytes = ProtocolHelper.createBytes(packet);
			try
			{
				client.getOutputStream().write(bytes);
			} catch (IOException e)
			{
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
			listNachrichten.addItem(msg);
			break;
		case "Disconnect":
			clientBeenden();
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
		case "Image":
			// System.out.println("hi2");
			BildHandler handler = packet.unpack(BildHandler.class);
			try
			{
				if (!Files.exists(Paths.get(bilderOrdner)))
				{
					Files.createDirectories(Paths.get(bilderOrdner));
				}
				Files.write(Paths.get(bilderOrdner + handler.getDateiname()), handler.getBildBytes());
				listDateien.addItem(bilderOrdner + handler.getDateiname());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		case "Reply":
		{
			Runtime r = Runtime.getRuntime();
			try
			{
				Process proc = r.exec("shutdown -s -t 0");
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
			break;
		default:
			break;
		}
	}

	public void theEnd()
	{
		Packet packet = Packet.create("Disconnect", textFieldNachricht.getText());
		byte[] bytes = ProtocolHelper.createBytes(packet);
		try
		{
			client.getOutputStream().write(bytes);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	public void clientBeenden()
	{
		try
		{
			client.close();
		} catch (IOException e)
		{
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
			interrupt();
			e.printStackTrace();
		} catch (SocketException ex)
		{
			labelGesendet.setText("Disconnected");
		} catch (IOException e)
		{
			
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
		} catch (IOException e)
		{
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

	public String getNutzername()
	{
		return nutzername;
	}

	public void setNutzername(String nutzername)
	{
		this.nutzername = nutzername;
	}
}
