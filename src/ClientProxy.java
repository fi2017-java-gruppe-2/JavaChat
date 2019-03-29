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

public class ClientProxy extends Thread
{
	private Socket socket;
	private ServerControl server;
//	private ObjectOutputStream writer;
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
			in = socket.getInputStream();
			out = socket.getOutputStream();

			while (!this.isInterrupted())
			{
				// erste 4 bytes lesen -> zu int
				// die rest bytes auslesen und bei createPacket einsetzen
				byte[] lengthBytes = receive(4);
				int length = ByteBuffer.wrap(lengthBytes).getInt();
				

				Packet p = ProtocolHelper.createPacket(receive(length) /* hier kommen die gelesenen bytes rein */);
				server.verarbeiteNachricht(p);

				Thread.sleep(10);
			}
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
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

		}
	}
}