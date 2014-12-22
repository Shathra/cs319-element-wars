package edu.ew.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class CardViewPanel extends JPanel {

	JLabel picture;
	
	public CardViewPanel() {
		
		setPreferredSize( ViewConstants.cardViewPanelSize);
		setBackground( ViewConstants.backgroundColor);
		setLayout( new BoxLayout( this, BoxLayout.Y_AXIS));
		
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File( "assets/card-images/1.png"));
		} catch (IOException e) {
			giveWarning();
			e.printStackTrace();
		}
		JLabel picture = new JLabel(new ImageIcon(myPicture));
		add(picture);
	}
	
	public void changePicture( int id) {
		
		System.out.println( "here");
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File( "assets/card-images/" + id + ".png"));
		} catch (IOException e) {
			giveWarning();
			e.printStackTrace();
		}

		removeAll();
		picture = new JLabel(new ImageIcon(myPicture));
		add(picture);
		MainFrame frame = (MainFrame) SwingUtilities.getWindowAncestor( this);
		frame.pack();
	}
	
	public void giveWarning() {
		
		JOptionPane.showMessageDialog( SwingUtilities.getWindowAncestor( this), "Error: File is corrupted");
	}
}