
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

public class PrivateChatGui extends JFrame
{

	private JPanel contentPane;
	private ChatListe<String> list;
	private JTextField textFieldNachricht;
	private JButton btnSenden;
	private JLabel lblChatMit;
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
		setBounds(100, 100, 379, 271);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);
		GridBagConstraints gbc_lblChatMit = new GridBagConstraints();
		gbc_lblChatMit.anchor = GridBagConstraints.WEST;
		gbc_lblChatMit.insets = new Insets(0, 0, 5, 5);
		gbc_lblChatMit.gridx = 0;
		gbc_lblChatMit.gridy = 0;
		contentPane.add(getLblChatMit(), gbc_lblChatMit);
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridwidth = 2;
		gbc_list.insets = new Insets(0, 0, 5, 0);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 1;
		contentPane.add(getList(), gbc_list);
		GridBagConstraints gbc_textFieldNachricht = new GridBagConstraints();
		gbc_textFieldNachricht.insets = new Insets(0, 0, 0, 5);
		gbc_textFieldNachricht.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNachricht.gridx = 0;
		gbc_textFieldNachricht.gridy = 2;
		contentPane.add(getTextFieldNachricht(), gbc_textFieldNachricht);
		GridBagConstraints gbc_btnSenden = new GridBagConstraints();
		gbc_btnSenden.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSenden.gridx = 1;
		gbc_btnSenden.gridy = 2;
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
		}
		return list;
	}

	private JTextField getTextFieldNachricht()
	{
		if (textFieldNachricht == null)
		{
			textFieldNachricht = new JTextField();
			textFieldNachricht.setColumns(10);
		}
		return textFieldNachricht;
	}

	private JButton getBtnSenden()
	{
		if (btnSenden == null)
		{
			btnSenden = new JButton("senden");
			btnSenden.addActionListener(
					e -> cc.sendePrivateNachricht(textFieldNachricht.getText(), chatpartner, nutzername));
		}
		return btnSenden;
	}

	private JLabel getLblChatMit()
	{
		if (lblChatMit == null)
		{
			lblChatMit = new JLabel("Chat mit ...");
		}
		return lblChatMit;
	}

	public String getChatpartner()
	{
		return chatpartner;
	}
}
