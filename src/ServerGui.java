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
	private JLabel lblPort;
	private JTextField textFieldPort;
	private JLabel lblNachrichten;
	private JTextField textFieldNachricht;
	private JButton btnNachrichtSenden;
	private JButton btnStarten;
	private JButton btnBeenden;
	private JLabel labelStatus;
	private Boolean istAngemeldet = false;
	
	private ServerControl c;

	public ServerGui()
	{
		initialize();
		c = new ServerControl(textFieldPort, textFieldNachricht, labelStatus);
	}

	public void initialize()
	{
		setTitle("Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 389, 326);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(105, 105, 105));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblPort());
		contentPane.add(getTextFieldPort());
		contentPane.add(getLblNachrichten());
		contentPane.add(getTextFieldNachricht());
		contentPane.add(getBtnNachrichtSenden());
		contentPane.add(getBtnStarten());
		contentPane.add(getBtnBeenden());
		contentPane.add(getLabelStatus());
	}

	private JLabel getLblPort()
	{
		if (lblPort == null)
		{
			lblPort = new JLabel("Port:");
			lblPort.setForeground(new Color(255, 255, 255));
			lblPort.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			lblPort.setBounds(28, 39, 46, 14);
		}
		return lblPort;
	}

	private JTextField getTextFieldPort()
	{
		if (textFieldPort == null)
		{
			textFieldPort = new JTextField();
			textFieldPort.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			textFieldPort.setText("8008");
			textFieldPort.setBounds(84, 37, 53, 20);
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
			lblNachrichten.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			lblNachrichten.setBounds(28, 151, 86, 14);
		}
		return lblNachrichten;
	}

	private JTextField getTextFieldNachricht()
	{
		if (textFieldNachricht == null)
		{
			textFieldNachricht = new JTextField();
			textFieldNachricht.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
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
			btnNachrichtSenden.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			btnNachrichtSenden.setBounds(189, 236, 155, 23);
		}
		return btnNachrichtSenden;
	}

	private JButton getBtnStarten()
	{
		if (btnStarten == null)
		{
			btnStarten = new JButton("starten");
			btnStarten.setForeground(new Color(255, 255, 255));
			btnStarten.setBackground(new Color(51, 153, 153));
			btnStarten.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			btnStarten.setBounds(28, 88, 145, 23);
			
			btnStarten.addActionListener(e -> 
			{
				if(!istAngemeldet)
				{
					c.starteServer();
					istAngemeldet = true;
				}
				else
				{
					labelStatus.setText("Server bereits gestartet!");
				}
			});
		}
		return btnStarten;
	}

	private JButton getBtnBeenden()
	{
		if (btnBeenden == null)
		{
			btnBeenden = new JButton("beenden");
			btnBeenden.setForeground(new Color(255, 255, 255));
			btnBeenden.setBackground(new Color(51, 153, 153));
			btnBeenden.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			btnBeenden.setBounds(199, 89, 145, 23);

			btnBeenden.addActionListener(e -> 
			{
				if(istAngemeldet)
				{
					c.beendeServer();
					istAngemeldet = false;
				}
				else
				{
					labelStatus.setText("Server bereits abgemeldet!");
				}
			});
		}
		return btnBeenden;
	}
	private JLabel getLabelStatus() {
		if (labelStatus == null) {
			labelStatus = new JLabel("");
			labelStatus.setFont(new Font("Rockwell Condensed", Font.BOLD | Font.ITALIC, 12));
			labelStatus.setBounds(28, 199, 316, 14);
		}
		return labelStatus;
	}
}
