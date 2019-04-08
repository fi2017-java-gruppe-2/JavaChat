
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.swing.JTextField;

public class ClientProxy extends Thread
{
	private Socket socket;
	private ServerControl server;
//	private ObjectOutputStream writer;
	private InputStream in;
	private OutputStream out;
	private Spam_Protection spamProtect = new Spam_Protection();
	private String nutzername;

	public String getNutzername()
	{
		return nutzername;
	}

	public void setNutzername(String nutzername)
	{
		this.nutzername = nutzername;
	}

	public ClientProxy(Socket socket, ServerControl server)
	{
		this.socket = socket;
		this.server = server;
		this.start();

	}

	@Override
	public void run()
	{
		try
		{
			in = socket.getInputStream();
			out = socket.getOutputStream();
			spamProtect = new Spam_Protection();

			while (!this.isInterrupted())
			{
				// erste 4 bytes lesen -> zu int
				// die rest bytes auslesen und bei createPacket einsetzen
				byte[] lengthBytes = receive(4);
				int length = ByteBuffer.wrap(lengthBytes).getInt();
				

				Packet p = ProtocolHelper.createPacket(receive(length) /* hier kommen die gelesenen bytes rein */);
				if (p.getPayloadClass() == Integer.class) 
				{
					server.verarbeiteNachricht(p);
				}
				else if(p.getPayloadClass() == BildHandler.class)
				{
					server.verarbeiteNachricht(p);
				}
				else
				{
					if(p.getHeader() == "Message") {
						String msg = p.unpack(String.class);
						if(spamProtect.checkSpam(p.unpack(String.class), Timestamp.valueOf(LocalDateTime.now()), socket.getInetAddress().toString()))
						{
							System.out.println("da spammt "  + socket.getInetAddress().toString());
						}
						else
						{
							server.verarbeiteNachricht(p);
						}
					} else {
						server.verarbeiteNachricht(p);
					}
				}
				Thread.sleep(10);
			}
			clientBeenden();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			interrupt();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			interrupt();
			e.printStackTrace();
		}
	}

	public void writeMessage(Packet p)
	{
		byte[] bytes = ProtocolHelper.createBytes(p);
		try
		{
			socket.getOutputStream().write(bytes);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void clientBeenden()
	{
		try
		{
			socket.close();
			Packet packet = Packet.create("Disconnect", "beenden");
			byte[] bytes = ProtocolHelper.createBytes(packet);
			try
			{
				socket.getOutputStream().write(bytes);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}	
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public Socket getSocket()
	{
		return socket;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

		}
	}
}