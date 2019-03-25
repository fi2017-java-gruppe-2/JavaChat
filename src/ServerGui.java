import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ServerGui extends JFrame
{

	private JPanel contentPane;
	private JTextField textFieldLocalHost;
	private JLabel lblNachrichten;
	private JLabel lblPortnr;
	private JList list;
	private JTextField textField_1;
	private JLabel labelStatus;
	private JTextField textFieldPort;
	private JButton buttonStart;
	private DefaultListModel<String> nachrichten = new DefaultListModel<>();
	private ServerControl c;
	private JButton btnBeenden;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public ServerGui()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 333, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 25, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);
		GridBagConstraints gbc_lblNachrichten = new GridBagConstraints();
		gbc_lblNachrichten.insets = new Insets(0, 0, 5, 5);
		gbc_lblNachrichten.gridx = 0;
		gbc_lblNachrichten.gridy = 0;
		contentPane.add(getLblNachrichten(), gbc_lblNachrichten);
		GridBagConstraints gbc_textFieldLocalHost = new GridBagConstraints();
		gbc_textFieldLocalHost.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldLocalHost.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldLocalHost.gridx = 0;
		gbc_textFieldLocalHost.gridy = 1;
		contentPane.add(getTextFieldLocalHost(), gbc_textFieldLocalHost);
		GridBagConstraints gbc_lblPortnr = new GridBagConstraints();
		gbc_lblPortnr.insets = new Insets(0, 0, 5, 0);
		gbc_lblPortnr.gridx = 1;
		gbc_lblPortnr.gridy = 1;
		contentPane.add(getLblPortnr(), gbc_lblPortnr);
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridheight = 4;
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 2;
		contentPane.add(getList(), gbc_list);
		GridBagConstraints gbc_textFieldPort = new GridBagConstraints();
		gbc_textFieldPort.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldPort.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPort.gridx = 1;
		gbc_textFieldPort.gridy = 2;
		contentPane.add(getTextFieldPort(), gbc_textFieldPort);
		GridBagConstraints gbc_buttonStart = new GridBagConstraints();
		gbc_buttonStart.fill = GridBagConstraints.BOTH;
		gbc_buttonStart.gridheight = 3;
		gbc_buttonStart.insets = new Insets(0, 0, 5, 0);
		gbc_buttonStart.gridx = 1;
		gbc_buttonStart.gridy = 3;
		contentPane.add(getButtonStart(), gbc_buttonStart);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 0;
		gbc_textField_1.gridy = 6;
		contentPane.add(getTextField_1(), gbc_textField_1);
		GridBagConstraints gbc_btnBeenden = new GridBagConstraints();
		gbc_btnBeenden.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnBeenden.insets = new Insets(0, 0, 5, 0);
		gbc_btnBeenden.gridx = 1;
		gbc_btnBeenden.gridy = 6;
		contentPane.add(getBtnBeenden(), gbc_btnBeenden);
		GridBagConstraints gbc_labelStatus = new GridBagConstraints();
		gbc_labelStatus.insets = new Insets(0, 0, 0, 5);
		gbc_labelStatus.gridx = 0;
		gbc_labelStatus.gridy = 7;
		contentPane.add(getLabelStatus(), gbc_labelStatus);

		c = new ServerControl(textFieldPort, textFieldLocalHost, labelStatus, list, nachrichten);
	}

	private JTextField getTextFieldLocalHost()
	{
		if (textFieldLocalHost == null)
		{
			textFieldLocalHost = new JTextField();
			textFieldLocalHost.setText("127.0.0.1");
			textFieldLocalHost.setColumns(10);
		}
		return textFieldLocalHost;
	}

	private JLabel getLblNachrichten()
	{
		if (lblNachrichten == null)
		{
			lblNachrichten = new JLabel("Nachrichten");
		}
		return lblNachrichten;
	}

	private JLabel getLblPortnr()
	{
		if (lblPortnr == null)
		{
			lblPortnr = new JLabel("PortNr");
		}
		return lblPortnr;
	}

	private JList getList()
	{
		if (list == null)
		{
			list = new JList(nachrichten);
		}
		return list;
	}

	private JTextField getTextField_1()
	{
		if (textField_1 == null)
		{
			textField_1 = new JTextField();
			textField_1.addActionListener(e -> System.out.println("der server schreibt keine nachrichten mehr ist as klar?"));
			textField_1.setColumns(10);
		}
		return textField_1;
	}

	private JLabel getLabelStatus()
	{
		if (labelStatus == null)
		{
			labelStatus = new JLabel("");
		}
		return labelStatus;
	}

	private JTextField getTextFieldPort()
	{
		if (textFieldPort == null)
		{
			textFieldPort = new JTextField();
			textFieldPort.setText("8008");
			textFieldPort.setColumns(10);
		}
		return textFieldPort;
	}

	private JButton getButtonStart()
	{
		if (buttonStart == null)
		{
			buttonStart = new JButton("start");
			buttonStart.addActionListener(e -> c.starteServer());
		}
		return buttonStart;
	}

	private JButton getBtnBeenden()
	{
		if (btnBeenden == null)
		{
			btnBeenden = new JButton("beenden");
			btnBeenden.addActionListener(e -> c.beendeServer());
		}
		return btnBeenden;
	}
}
