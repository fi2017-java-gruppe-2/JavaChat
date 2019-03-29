
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class ClientGui extends JFrame
{

	private JPanel contentPane;
	private JLabel lblServer;
	private JButton bntConnect;
	private JLabel lblTextEingeben;
	private JButton btnSenden;
	private JLabel labelGesendet;
	private JTextField textFieldLocalhost;
	private JTextField textFieldNachricht;
	private ClientControl c;
	private DefaultListModel<String> chat = new DefaultListModel<>();
	private JButton btnDisconnect;
	private JScrollPane scrollPane;
	private JList list;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public ClientGui()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 558);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 287, 129, 0 };
		gbl_contentPane.rowHeights = new int[] { 28, 28, 28, 28, 28, 28, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);
		GridBagConstraints gbc_lblServer = new GridBagConstraints();
		gbc_lblServer.insets = new Insets(0, 0, 5, 5);
		gbc_lblServer.gridx = 0;
		gbc_lblServer.gridy = 0;
		contentPane.add(getLblServer(), gbc_lblServer);
		GridBagConstraints gbc_textFieldLocalhost = new GridBagConstraints();
		gbc_textFieldLocalhost.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldLocalhost.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldLocalhost.anchor = GridBagConstraints.ABOVE_BASELINE;
		gbc_textFieldLocalhost.gridx = 0;
		gbc_textFieldLocalhost.gridy = 1;
		contentPane.add(getTextFieldLocalhost(), gbc_textFieldLocalhost);
		GridBagConstraints gbc_bntConnect = new GridBagConstraints();
		gbc_bntConnect.insets = new Insets(0, 0, 5, 0);
		gbc_bntConnect.fill = GridBagConstraints.HORIZONTAL;
		gbc_bntConnect.gridx = 1;
		gbc_bntConnect.gridy = 1;
		contentPane.add(getBntConnect(), gbc_bntConnect);
		GridBagConstraints gbc_lblTextEingeben = new GridBagConstraints();
		gbc_lblTextEingeben.insets = new Insets(0, 0, 5, 5);
		gbc_lblTextEingeben.gridx = 0;
		gbc_lblTextEingeben.gridy = 2;
		contentPane.add(getLblTextEingeben(), gbc_lblTextEingeben);
		GridBagConstraints gbc_btnDisconnect = new GridBagConstraints();
		gbc_btnDisconnect.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDisconnect.insets = new Insets(0, 0, 5, 0);
		gbc_btnDisconnect.gridx = 1;
		gbc_btnDisconnect.gridy = 2;
		contentPane.add(getBtnDisconnect(), gbc_btnDisconnect);
		GridBagConstraints gbc_textFieldNachricht = new GridBagConstraints();
		gbc_textFieldNachricht.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldNachricht.gridwidth = 2;
		gbc_textFieldNachricht.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNachricht.gridx = 0;
		gbc_textFieldNachricht.gridy = 3;
		contentPane.add(getTextFieldNachricht(), gbc_textFieldNachricht);
		GridBagConstraints gbc_btnSenden = new GridBagConstraints();
		gbc_btnSenden.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSenden.insets = new Insets(0, 0, 5, 0);
		gbc_btnSenden.gridx = 1;
		gbc_btnSenden.gridy = 4;
		contentPane.add(getBtnSenden(), gbc_btnSenden);
		GridBagConstraints gbc_labelGesendet = new GridBagConstraints();
		gbc_labelGesendet.insets = new Insets(0, 0, 5, 5);
		gbc_labelGesendet.gridx = 0;
		gbc_labelGesendet.gridy = 5;
		contentPane.add(getLabelGesendet(), gbc_labelGesendet);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 6;
		contentPane.add(getScrollPane(), gbc_scrollPane);

		c = new ClientControl(labelGesendet, textFieldLocalhost, textFieldNachricht, list, chat);
	}

	private JLabel getLblServer()
	{
		if (lblServer == null)
		{
			lblServer = new JLabel("Server");
		}
		return lblServer;
	}

	private JTextField getTextFieldLocalhost()
	{
		if (textFieldLocalhost == null)
		{
			textFieldLocalhost = new JTextField();
			textFieldLocalhost.setText("localhost");
			textFieldLocalhost.setColumns(10);
		}
		return textFieldLocalhost;
	}

	private JButton getBntConnect()
	{
		if (bntConnect == null)
		{
			bntConnect = new JButton("connect");
			bntConnect.addActionListener(e -> c.verbindeZuServer());
		}
		return bntConnect;
	}

	private JLabel getLblTextEingeben()
	{
		if (lblTextEingeben == null)
		{
			lblTextEingeben = new JLabel("Text eingeben");
		}
		return lblTextEingeben;
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
			btnSenden.addActionListener(e-> c.sendeNachricht());
		}
		return btnSenden;
	}

	private JLabel getLabelGesendet()
	{
		if (labelGesendet == null)
		{
			labelGesendet = new JLabel("");
		}
		return labelGesendet;
	}
	private JButton getBtnDisconnect() {
		if (btnDisconnect == null) {
			btnDisconnect = new JButton("disconnect");
			btnDisconnect.addActionListener(e -> c.theEnd());
		}
		return btnDisconnect;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getList_1());
		}
		return scrollPane;
	}
	private JList getList_1() {
		if (list == null) {
			list = new JList();
		}
		return list;
	}
}
