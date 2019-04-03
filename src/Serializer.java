
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Serializer
{
	public static <T extends Serializable> byte[] serialize(T obj)
	{
		try
		{
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(obj);
			objectOutputStream.flush();
			objectOutputStream.close();
			byteArrayOutputStream.close();
			return byteArrayOutputStream.toByteArray();
		} catch (Exception e)
		{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T deserialize(byte[] bytes)
	{
		try
		{
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
			ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			objectInputStream.close();
			byteArrayInputStream.close();
			return (T) objectInputStream.readObject();
		} catch (Exception e)
		{
			return null;
		}
	}
}
