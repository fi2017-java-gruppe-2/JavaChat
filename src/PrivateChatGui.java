
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Color;

public class PrivateChatGui extends JFrame
{

	private JPanel contentPane;
	private ChatListe<String> list;
	private JTextField textFieldNachricht;
	private JButton btnSenden;
	private String nutzername;
	private String chatpartner;
	private ClientControl cc;

	/**
	 * Create the frame.
	 */
	public PrivateChatGui(String nutzername, String chatpartner, ClientControl cc)
	{
		this.nutzername = nutzername;
		this.chatpartner = chatpartner;
		this.cc = cc;
		initialize();
	}

	private void initialize()
	{
		setTitle("Privater Chat mit " + chatpartner);
		setBounds(100, 100, 419, 322);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(51, 153, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridwidth = 2;
		gbc_list.insets = new Insets(0, 0, 5, 0);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 0;
		contentPane.add(getList(), gbc_list);
		GridBagConstraints gbc_textFieldNachricht = new GridBagConstraints();
		gbc_textFieldNachricht.insets = new Insets(0, 0, 0, 5);
		gbc_textFieldNachricht.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNachricht.gridx = 0;
		gbc_textFieldNachricht.gridy = 1;
		contentPane.add(getTextFieldNachricht(), gbc_textFieldNachricht);
		GridBagConstraints gbc_btnSenden = new GridBagConstraints();
		gbc_btnSenden.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSenden.gridx = 1;
		gbc_btnSenden.gridy = 1;
		contentPane.add(getBtnSenden(), gbc_btnSenden);
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing()
			{
				fensterSchlieﬂen();
			}

		});
	}

	private void fensterSchlieﬂen()
	{
		this.setVisible(false);
	}

	public ChatListe<String> getList()
	{
		if (list == null)
		{
			list = new ChatListe<String>();
			list.getList().setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
		}
		return list;
	}

	private JTextField getTextFieldNachricht()
	{
		if (textFieldNachricht == null)
		{
			textFieldNachricht = new JTextField();
			textFieldNachricht.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			textFieldNachricht.setColumns(10);
		}
		return textFieldNachricht;
	}

	private JButton getBtnSenden()
	{
		if (btnSenden == null)
		{
			btnSenden = new JButton("senden");
			btnSenden.setBackground(SystemColor.controlDkShadow);
			btnSenden.setForeground(Color.WHITE);
			btnSenden.setFont(new Font("Rockwell Condensed", Font.PLAIN, 17));
			btnSenden.addActionListener(
					e -> cc.sendePrivateNachricht(textFieldNachricht.getText(), chatpartner, nutzername));
		}
		return btnSenden;
	}

	public String getChatpartner()
	{
		return chatpartner;
	}
}
