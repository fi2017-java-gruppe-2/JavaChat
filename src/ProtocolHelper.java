import java.nio.ByteBuffer;

public class ProtocolHelper
{
	public static byte[] createBytes(Packet packet)
	{
		byte[] packetBytes = Serializer.serialize(packet);
		ByteBuffer buffer = ByteBuffer.allocate(4 + packetBytes.length);
		buffer.putInt(packetBytes.length);
		buffer.put(packetBytes);
		return buffer.array();
	}

	public static Packet createPacket(byte[] bytes)
	{
		Packet packet = Serializer.deserialize(bytes);
		return packet;
	}
}
