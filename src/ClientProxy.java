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
			InputStream in = socket.getInputStream();
			ObjectInputStream reader = new ObjectInputStream(in);
			while(!this.isInterrupted())
			{
				Object o = reader.readObject();
				Protokoll p = (Protokoll) o;
				System.out.println(p + "1");
				server.verarbeiteNachricht(p);
				Thread.sleep(10);
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
			this.interrupt();
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public void writeMessage(Protokoll p) throws SocketException
	{
		try
		{
			if(out == null)
			{
				out = socket.getOutputStream();
				writer = new ObjectOutputStream(out);
			}
			writer.writeObject(p);
			writer.flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		};
	}
}