import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextPane;

public class ClientGui extends JFrame
{
	private JLabel lblIp;
	private JLabel lblPort;
	private JTextField textFieldIP;
	private JTextField textFieldPort;
	private JButton btnConnect;
	private JButton btnDisconnect;
	private JLabel lblTeilnehmer;
	private JLabel lblNachrichten;
	private JLabel lblDateien;
	private ChatListe<String> listNachrichten;
	private ChatListe<String> listDateien;
	private JLabel lblNachricht;
	private JTextField textFieldNachricht;
	private JButton btnNachrichtSenden;
	private JButton btnDateiSenden;
	private JButton btnJoin;
	private JTextField textFieldDatei;
	private JLabel lblDatei;
	private ChatListe<String> listTeilnehmer;
	private Boolean istAngemeldet = false;
	
	private ClientControl c;
	private JLabel labelGesendet;

	public ClientGui()
	{
		initialize();
		c = new ClientControl(labelGesendet, textFieldIP, textFieldPort, textFieldNachricht, textFieldDatei, listTeilnehmer, listNachrichten, listDateien);
		getContentPane().add(getLabelGesendet());		
	}
	
	public void initialize()
	{
		getContentPane().setBackground(new Color(95, 158, 160));
		setForeground(new Color(0, 0, 0));
		setTitle("Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 633, 545);
		getContentPane().setLayout(null);
		getContentPane().add(getLblIp());
		getContentPane().add(getLblPort());
		getContentPane().add(getTextFieldIP());
		getContentPane().add(getTextFieldPort());
		getContentPane().add(getBtnConnect());
		getContentPane().add(getBtnDisconnect());
		getContentPane().add(getLblTeilnehmer());
		getContentPane().add(getLblNachrichten());
		getContentPane().add(getLblDateien());
		getContentPane().add(getListNachrichten());
		getContentPane().add(getListDateien());
		getContentPane().add(getLblNachricht());
		getContentPane().add(getTextFieldNachricht());
		getContentPane().add(getBtnNachrichtSenden());
		getContentPane().add(getBtnDateiSenden());
		getContentPane().add(getBtnJoin());
		getContentPane().add(getTextFieldDatei());
		getContentPane().add(getLblDatei());
		getContentPane().add(getListTeilnehmer());
	}

	private JLabel getLblIp()
	{
		if (lblIp == null)
		{
			lblIp = new JLabel("IP:");
			lblIp.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			lblIp.setBounds(22, 42, 46, 14);
		}
		return lblIp;
	}

	private JLabel getLblPort()
	{
		if (lblPort == null)
		{
			lblPort = new JLabel("Port:");
			lblPort.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			lblPort.setBounds(212, 42, 46, 14);
		}
		return lblPort;
	}

	private JTextField getTextFieldIP()
	{
		if (textFieldIP == null)
		{
			textFieldIP = new JTextField();
			textFieldIP.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			textFieldIP.setBounds(54, 39, 136, 20);
			textFieldIP.setColumns(10);
			textFieldIP.setText("localhost");
		}
		return textFieldIP;
	}

	private JTextField getTextFieldPort()
	{
		if (textFieldPort == null)
		{
			textFieldPort = new JTextField();
			textFieldPort.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			textFieldPort.setBounds(250, 39, 86, 20);
			textFieldPort.setColumns(10);
			textFieldPort.setText("8008");
		}
		return textFieldPort;
	}

	private JButton getBtnConnect()
	{
		if (btnConnect == null)
		{
			btnConnect = new JButton("connect");
			btnConnect.setForeground(new Color(255, 255, 255));
			btnConnect.setBackground(new Color(105, 105, 105));
			btnConnect.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			btnConnect.setBounds(365, 38, 110, 23);
			if(!istAngemeldet)
			{
				btnConnect.addActionListener(e -> c.verbindeZuServer());
				istAngemeldet = true;
			}
			else
			{
				labelGesendet.setText("Client bereits angemeldet!");
			}
		}
		return btnConnect;
	}

	private JButton getBtnDisconnect()
	{
		if (btnDisconnect == null)
		{
			btnDisconnect = new JButton("disconnect");
			btnDisconnect.setForeground(new Color(255, 255, 255));
			btnDisconnect.setBackground(new Color(105, 105, 105));
			btnDisconnect.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			btnDisconnect.setBounds(497, 38, 110, 23);
			if(istAngemeldet)
			{
				btnDisconnect.addActionListener(e -> c.theEnd());
				istAngemeldet = true;
			}
			else
			{
				labelGesendet.setText("Client bereits abgemeldet!");
			}
		}
		return btnDisconnect;
	}

	private JLabel getLblTeilnehmer()
	{
		if (lblTeilnehmer == null)
		{
			lblTeilnehmer = new JLabel("Teilnehmer:");
			lblTeilnehmer.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			lblTeilnehmer.setBounds(22, 97, 141, 14);
		}
		return lblTeilnehmer;
	}

	private JLabel getLblNachrichten()
	{
		if (lblNachrichten == null)
		{
			lblNachrichten = new JLabel("Nachrichten:");
			lblNachrichten.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			lblNachrichten.setBounds(167, 97, 141, 14);
		}
		return lblNachrichten;
	}

	private JLabel getLblDateien()
	{
		if (lblDateien == null)
		{
			lblDateien = new JLabel("Dateien:");
			lblDateien.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			lblDateien.setBounds(403, 97, 141, 14);
		}
		return lblDateien;
	}

	private ChatListe<String> getListNachrichten()
	{
		if (listNachrichten == null)
		{
			listNachrichten = new ChatListe<String>();
			listNachrichten.getList().setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			listNachrichten.setBounds(167, 122, 216, 278);
		}
		return listNachrichten;
	}

	private ChatListe<String> getListDateien()
	{
		if (listDateien == null)
		{
			listDateien = new ChatListe<String>();
			listDateien.getList().setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			listDateien.setBounds(403, 122, 204, 278);
		}
		return listDateien;
	}

	private JLabel getLblNachricht()
	{
		if (lblNachricht == null)
		{
			lblNachricht = new JLabel("Nachricht:");
			lblNachricht.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			lblNachricht.setBounds(169, 420, 89, 14);
		}
		return lblNachricht;
	}

	private JTextField getTextFieldNachricht()
	{
		if (textFieldNachricht == null)
		{
			textFieldNachricht = new JTextField();
			textFieldNachricht.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			textFieldNachricht.setBounds(236, 417, 147, 20);
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
			btnNachrichtSenden.setBackground(new Color(105, 105, 105));
			btnNachrichtSenden.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			btnNachrichtSenden.setBounds(236, 455, 147, 23);
			btnNachrichtSenden.addActionListener(e-> c.sendeNachricht());
		}
		return btnNachrichtSenden;
	}

	private JButton getBtnDateiSenden()
	{
		if (btnDateiSenden == null)
		{
			btnDateiSenden = new JButton("Datei senden");
			btnDateiSenden.setForeground(new Color(255, 255, 255));
			btnDateiSenden.setBackground(new Color(105, 105, 105));
			btnDateiSenden.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			btnDateiSenden.setBounds(460, 455, 147, 23);
		}
		return btnDateiSenden;
	}

	private JButton getBtnJoin()
	{
		if (btnJoin == null)
		{
			btnJoin = new JButton("join");
			btnJoin.setForeground(new Color(255, 255, 255));
			btnJoin.setBackground(new Color(105, 105, 105));
			btnJoin.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			btnJoin.setBounds(10, 416, 136, 23);
		}
		return btnJoin;
	}

	private JTextField getTextFieldDatei()
	{
		if (textFieldDatei == null)
		{
			textFieldDatei = new JTextField();
			textFieldDatei.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			textFieldDatei.setColumns(10);
			textFieldDatei.setBounds(460, 417, 147, 20);
		}
		return textFieldDatei;
	}

	private JLabel getLblDatei()
	{
		if (lblDatei == null)
		{
			lblDatei = new JLabel("Datei:");
			lblDatei.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			lblDatei.setBounds(403, 420, 58, 14);
		}
		return lblDatei;
	}

	private ChatListe<String> getListTeilnehmer()
	{
		if (listTeilnehmer == null)
		{
			listTeilnehmer = new ChatListe<String>();
			listTeilnehmer.getList().setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			listTeilnehmer.setBounds(10, 122, 136, 278);
		}
		return listTeilnehmer;
	}
	private JLabel getLabelGesendet() {
		if (labelGesendet == null) {
			labelGesendet = new JLabel("");
			labelGesendet.setFont(new Font("Rockwell Condensed", Font.BOLD | Font.ITALIC, 12));
			labelGesendet.setBounds(10, 460, 136, 14);
		}
		return labelGesendet;
	}
}
