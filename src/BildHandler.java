import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;

public class BildHandler implements Serializable
{
	private String dateiname;
	private byte[] bildBytes;

	public BildHandler(File f)
	{
		dateiname = f.getName();
		try
		{
			bildBytes = Files.readAllBytes(f.toPath());
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getDateiname()
	{
		return dateiname;
	}

	public byte[] getBildBytes()
	{
		return bildBytes;
	}

}