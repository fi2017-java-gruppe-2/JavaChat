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

public class ClientProxy extends Thread
{
	private Socket socket;
	private ServerControl server;
	private ObjectOutputStream writer;
	private InputStream in;
	private OutputStream out;

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
			while (!this.isInterrupted())
			{
				// erste 4 bytes lesen -> zu int
				// die rest bytes auslesen und bei createPacket einsetzen

				Packet p = ProtocolHelper.createPacket(null /* hier kommen die gelesenen bytes rein */);
				server.verarbeiteNachricht(p);

				Thread.sleep(10);
			}
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			interrupt();
		}
	}

	public void writeMessage(Packet p)
	{
		byte[] bytes = ProtocolHelper.createBytes(p);
		try
		{
			socket.getOutputStream().write(bytes);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}