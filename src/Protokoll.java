import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Protokoll implements Serializable
{
	private static final long serialVersionUID = 4917782496779156038L;
	private boolean msg;
	private boolean logout;
	private boolean changeNickname;
	private String message;
	private String ip;
	private Timestamp zeitstempel;
	
	public Protokoll()
	{
		msg = false;
		logout = false;
		zeitstempel = Timestamp.valueOf(LocalDateTime.now()); 
	}
	
	
	public boolean isMsg()
	{
		return msg;
	}

	public void setMsg(boolean msg, String message)
	{
		this.msg = msg;
		setMessage(message);
	}

	public boolean isLogout()
	{
		return logout;
	}

	public void setLogout(boolean logout)
	{
		this.logout = logout;
	}
	
	public boolean isChangeNickname()
	{
		return changeNickname;
	}

	public void setChangeNickname(boolean changeNickname)
	{
		this.changeNickname = changeNickname;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public Timestamp getZeitstempel()
	{
		return zeitstempel;
	}

	public void setZeitstempel(Timestamp zeitstempel)
	{
		this.zeitstempel = zeitstempel;
	}
}
