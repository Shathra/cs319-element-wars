package edu.ew.view.playground;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.ew.controller.ControllerConnector;
import edu.ew.model.Card;
import edu.ew.model.Deck;
import edu.ew.model.Hand;
import edu.ew.model.Player;
import edu.ew.model.Player.Side;
import edu.ew.view.ViewConstants;

/**
 * 
 * @author Selcuk Gulcan
 *
 */
@SuppressWarnings("serial")
public class HandPanel extends JPanel implements Observer{
	
	private static Dimension SIZE = new Dimension( 
			ViewConstants.frameWidth - ViewConstants.cardViewPanelSize.width, 150);
	
	private static Dimension DECK_SIZE = new Dimension( 100, SIZE.height);
	private static Dimension HAND_SIZE = new Dimension( SIZE.width - DECK_SIZE.width, SIZE.height);
	private static String CARD_SMALL_PATH = "assets/card-images-small/";
	private static String CARD_SMALL_EXTENSION = ".png";
	
	private JLabel noOfCards;
	JPanel hand, deck;

	public HandPanel() {
		
		super();
		
		setPreferredSize( SIZE);
		setMaximumSize( getPreferredSize());
		setLayout( new BoxLayout( this, BoxLayout.X_AXIS));
		setBackground( PlaygroundConstants.background);
		
		hand = new JPanel();
		((FlowLayout)hand.getLayout()).setVgap(0);
		hand.setPreferredSize( HAND_SIZE);
		hand.setMaximumSize( hand.getPreferredSize());
		hand.setBackground( Color.white);
		
		deck = new JPanel();
		((FlowLayout)deck.getLayout()).setVgap(0);
		deck.setPreferredSize( DECK_SIZE);
		deck.setMaximumSize( deck.getPreferredSize());
		deck.setBackground( Color.black);
		
	    noOfCards=new JLabel( "20");
	    noOfCards.setFont( PlaygroundConstants.bigFont);
	    noOfCards.setForeground( Color.white);

	    noOfCards.setIcon( ViewConstants.cardBack);
	    noOfCards.setIconTextGap(-62);
	    noOfCards.setOpaque(true);
	    noOfCards.setLayout(null);
	    noOfCards.setBackground( Color.white);
		deck.add( noOfCards);
		
		add( hand);
		add( deck);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		
		if( arg0 instanceof Hand) {
			hand.removeAll();
			
			Iterator<Card> it = ((Hand)arg0).iterator();
			
			while( it.hasNext()) {
				
				hand.add( new OneCard( it.next()));
			}
			
			hand.revalidate();
			hand.repaint();
		}
		
		else if( arg0 instanceof Deck) {
			
			Deck p = (Deck)arg0;
			noOfCards.setText( "" + p.getNoOfCards());
			
			update( getGraphics());
		}
	}
	
	public class OneCard extends JPanel{
		
		public OneCard( Card card) {
			
			((FlowLayout)this.getLayout()).setVgap(0);
			setBackground( Color.white);
			addMouseListener( new CardListener( card));
			
			BufferedImage myPicture = null;
			try {
				myPicture = ImageIO.read(new File( CARD_SMALL_PATH + card.getId() + CARD_SMALL_EXTENSION));
			} catch (IOException e) {
				e.printStackTrace();
			}
			add( new JLabel( new ImageIcon( myPicture)));
		}
	}
	
	public class CardListener implements MouseListener {
		
		private Card card;
		
		public CardListener( Card card) {
			
			this.card = card;
		}

		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {
			
			PlaygroundPanel.cardViewPanel.changePicture( card.getId());
		}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {
			
			ControllerConnector.playCard( card, Side.WHITE);
		}
	}
}
