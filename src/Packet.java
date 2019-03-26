import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Packet implements Serializable
{
	private static final long serialVersionUID = -6199454131641366328L;

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
		return Serializer.deserialize(payloadBytes);
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

	public byte[] getPayloadBytes()
	{
		return payloadBytes;
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
