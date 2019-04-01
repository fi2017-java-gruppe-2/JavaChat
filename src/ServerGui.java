import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;

public class ServerGui extends JFrame
{

	private JPanel contentPane;
	private JTextField textFieldLocalHost;
	private JLabel lblIp;
	private JLabel lblPort;
	private JTextField textFieldPort;
	private JLabel lblNachrichten;
	private JTextField textFieldNachricht;
	private JButton btnNachrichtSenden;
	private JButton btnConnect;
	private JButton btnDisconnect;
	private JLabel labelStatus;
	
	private ServerControl c;

	public ServerGui()
	{
		initialize();
		c = new ServerControl(textFieldPort, textFieldLocalHost, textFieldNachricht, labelStatus);
		contentPane.add(getLabelStatus());
	}

	public void initialize()
	{
		setTitle("Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 389, 281);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(105, 105, 105));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getTextFieldLocalHost());
		contentPane.add(getLblIp());
		contentPane.add(getLblPort());
		contentPane.add(getTextFieldPort());
		contentPane.add(getLblNachrichten());
		contentPane.add(getTextFieldNachricht());
		contentPane.add(getBtnNachrichtSenden());
		contentPane.add(getBtnConnect());
		contentPane.add(getBtnDisconnect());
	}

	private JTextField getTextFieldLocalHost()
	{
		if (textFieldLocalHost == null)
		{
			textFieldLocalHost = new JTextField();
			textFieldLocalHost.setFont(new Font("Rockwell", Font.PLAIN, 11));
			textFieldLocalHost.setText("127.0.0.1");
			textFieldLocalHost.setBounds(84, 37, 53, 20);
			textFieldLocalHost.setColumns(10);
		}
		return textFieldLocalHost;
	}

	private JLabel getLblIp()
	{
		if (lblIp == null)
		{
			lblIp = new JLabel("IP:");
			lblIp.setForeground(new Color(255, 255, 255));
			lblIp.setFont(new Font("Rockwell Condensed", Font.BOLD, 12));
			lblIp.setBounds(28, 40, 46, 14);
		}
		return lblIp;
	}

	private JLabel getLblPort()
	{
		if (lblPort == null)
		{
			lblPort = new JLabel("Port:");
			lblPort.setForeground(new Color(255, 255, 255));
			lblPort.setFont(new Font("Rockwell Condensed", Font.BOLD, 12));
			lblPort.setBounds(199, 40, 46, 14);
		}
		return lblPort;
	}

	private JTextField getTextFieldPort()
	{
		if (textFieldPort == null)
		{
			textFieldPort = new JTextField();
			textFieldPort.setFont(new Font("Rockwell", Font.PLAIN, 11));
			textFieldPort.setText("8008");
			textFieldPort.setBounds(255, 37, 53, 20);
			textFieldPort.setColumns(10);
		}
		return textFieldPort;
	}

	private JLabel getLblNachrichten()
	{
		if (lblNachrichten == null)
		{
			lblNachrichten = new JLabel("Nachricht:");
			lblNachrichten.setForeground(new Color(255, 255, 255));
			lblNachrichten.setFont(new Font("Rockwell Condensed", Font.BOLD, 12));
			lblNachrichten.setBounds(28, 151, 86, 14);
		}
		return lblNachrichten;
	}

	private JTextField getTextFieldNachricht()
	{
		if (textFieldNachricht == null)
		{
			textFieldNachricht = new JTextField();
			textFieldNachricht.setFont(new Font("Rockwell", Font.PLAIN, 11));
			textFieldNachricht.setBounds(104, 148, 240, 20);
			textFieldNachricht.setColumns(10);
		}
		return textFieldNachricht;
	}

	private JButton getBtnNachrichtSenden()
	{
		if (btnNachrichtSenden == null)
		{
			btnNachrichtSenden = new JButton("Nachricht senden");
			btnNachrichtSenden.setForeground(new Color(255, 255, 255));
			btnNachrichtSenden.setBackground(new Color(51, 153, 153));
			btnNachrichtSenden.setFont(new Font("Rockwell Condensed", Font.BOLD, 12));
			btnNachrichtSenden.setBounds(189, 189, 155, 23);
		}
		return btnNachrichtSenden;
	}

	private JButton getBtnConnect()
	{
		if (btnConnect == null)
		{
			btnConnect = new JButton("connect");
			btnConnect.setForeground(new Color(255, 255, 255));
			btnConnect.setBackground(new Color(51, 153, 153));
			btnConnect.setFont(new Font("Rockwell Condensed", Font.BOLD, 12));
			btnConnect.setBounds(28, 88, 145, 23);
			btnConnect.addActionListener(e -> c.starteServer());
		}
		return btnConnect;
	}

	private JButton getBtnDisconnect()
	{
		if (btnDisconnect == null)
		{
			btnDisconnect = new JButton("disconnect");
			btnDisconnect.setForeground(new Color(255, 255, 255));
			btnDisconnect.setBackground(new Color(51, 153, 153));
			btnDisconnect.setFont(new Font("Rockwell Condensed", Font.BOLD, 12));
			btnDisconnect.setBounds(199, 89, 145, 23);
			btnDisconnect.addActionListener(e -> c.beendeServer());
		}
		return btnDisconnect;
	}
	private JLabel getLabelStatus() {
		if (labelStatus == null) {
			labelStatus = new JLabel("");
			labelStatus.setFont(new Font("Rockwell Condensed", Font.BOLD | Font.ITALIC, 12));
			labelStatus.setBounds(28, 194, 46, 14);
		}
		return labelStatus;
	}
}
