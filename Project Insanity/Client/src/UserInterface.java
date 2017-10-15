import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

public class UserInterface extends client implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7349508809360287138L;

	public static UserInterface _instance;

	public static JPanel gamePanel;

	public UserInterface(String args[]) {
		super();
		try {
			// UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			initUI();
			init();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void initUI() {
		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			JPopupMenu.setDefaultLightWeightPopupEnabled(false);
			frame = new JFrame(clientName);
			frame.setLayout(new BorderLayout());
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			gamePanel = new JPanel();
			gamePanel.setLayout(new BorderLayout());
			gamePanel.add(this);
			gamePanel.setPreferredSize(new Dimension(REGULAR_WIDTH, REGULAR_HEIGHT));
			frame.getContentPane().add(gamePanel, BorderLayout.CENTER);
			// initMenuBar();
			frame.pack();
			frame.setVisible(true);
			frame.setResizable(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initMenuBar() {
		menuBar = new JMenuBar();
		/** File menu **/
		fileMenu = new JMenu("File");
		String[] fileMenuItems = { "Help", "-", "Quit" };
		for (String name : fileMenuItems) {
			if (name.equals("-")) {
				fileMenu.addSeparator();
			} else {
				JMenuItem fileMenuItem = new JMenuItem(name);
				fileMenuItem.addActionListener(this);
				fileMenu.add(fileMenuItem);
			}
		}
		menuBar.add(fileMenu);
		/** Mod menu **/
		modMenu = new JMenu("Moderator CP");
		modMenu.setToolTipText("A menu for easy access to moderator commands.");
		modMenu.setEnabled(false);
		String[] modMenuItems = { "Mute", "Kick", "Ban" };
		for (String name : modMenuItems) {
			if (name.equals("-")) {
				modMenu.addSeparator();
			} else {
				JMenuItem modMenuItem = new JMenuItem(name);
				modMenuItem.addActionListener(this);
				modMenu.add(modMenuItem);
			}
		}
		menuBar.add(modMenu);
		/** Admin menu **/
		adminMenu = new JMenu("Admin CP");
		adminMenu
				.setToolTipText("A menu for easy access to administrator commands.");
		adminMenu.setEnabled(false);
		String[] adminMenuItems = { "Mute", "Unmute", "Kick", "-", "Ban",
				"Unban", "IP Ban", "-", "Open bank", "Tele-to", "Tele-to-me" };
		for (String name : adminMenuItems) {
			if (name.equals("-")) {
				adminMenu.addSeparator();
			} else {
				JMenuItem adminMenuItem = new JMenuItem(name);
				adminMenuItem.addActionListener(this);
				adminMenu.add(adminMenuItem);
			}
		}
		menuBar.add(adminMenu);

		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
	}

	public URL getCodeBase() {
		try {
			return new URL("http://" + server + "/cache");
		} catch (Exception e) {
			return super.getCodeBase();
		}
	}

	public URL getDocumentBase() {
		return getCodeBase();
	}

	public void loadError(String s) {
		System.out.println("loadError: " + s);
	}

	public String getParameter(String key) {
		return "";
	}

	public void actionPerformed(ActionEvent evt) {
		String cmd = evt.getActionCommand();
		try {
			if (cmd != null) {
				if (cmd.equals("Quit")) {
					System.exit(0);
				}
			}
		} catch (Exception e) {
		}
	}
}