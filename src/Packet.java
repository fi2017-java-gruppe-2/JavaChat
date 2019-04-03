import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public final class Packet implements Serializable, Comparable<Packet>
{
	private static final long serialVersionUID = 6916696322277477805L;

	private String id;

	private LocalDateTime creationDateTime;
	private String header;

	private Class<?> payloadClass;
	private byte[] payloadBytes;

	private Packet(String header, Class<?> payloadClass, byte[] payloadBytes)
	{
		this.id = UUID.randomUUID().toString();
		this.creationDateTime = LocalDateTime.now();
		this.header = header;
		this.payloadClass = payloadClass;
		this.payloadBytes = payloadBytes;
	}

	public <T extends Serializable> T unpack(Class<T> c)
	{
		if (c != null && c.equals(payloadClass))
		{
			return Serializer.deserialize(payloadBytes);
		} 
		else
		{
			throw new IllegalArgumentException("Die übergebene Klasse entspricht nicht der des Packets oder ist null");
		}
	}

	public String getId()
	{
		return id;
	}

	public LocalDateTime getCreationDateTime()
	{
		return creationDateTime;
	}

	public String getHeader()
	{
		return header;
	}

	public Class<?> getPayloadClass()
	{
		return payloadClass;
	}

	@Override
	public int compareTo(Packet packet)
	{
		if (packet == null)
		{
			throw new IllegalArgumentException("Das übergebene Packet Objekt ist null");
		}
		return packet.payloadBytes.length - payloadBytes.length;
	}

	public static <T extends Serializable> Packet create(String header, T obj)
	{
		if (header == null || header.isEmpty() || obj == null)
		{
			throw new IllegalArgumentException();
		}
		return new Packet(header, obj.getClass(), Serializer.serialize(obj));
	}
}
