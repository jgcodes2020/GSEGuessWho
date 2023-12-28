package ca.gse.guesswho.components;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.JComponent;

/**
 * A display for an image, optionally displaying some kind of
 * "crossed out" graphic.
 */
public class CharacterImageDisplay extends JComponent {
	private Image image;
	private boolean crossedOut;

	public CharacterImageDisplay(Image image) {
		this.image = image;
		this.crossedOut = false;
	}
	
	/**
	 * Gets the image used by this CharacterImageDisplay.
	 * @return the image used
	 */
	public Image getImage() {
		return image;
	}
	/**
	 * Sets the image used by this CharacterImageDisplay.
	 * @param image the image used by this CharacterImageDisplay.
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * If true, the image is crossed out (i.e. eliminated).
	 * @return whether the image is crossed out.
	 */
	public boolean isCrossedOut() {
		return crossedOut;
	}

	/**
	 * Sets whether the image is "crossed out" (i.e. eliminated).
	 * @param crossedOut true if the image should be "crossed out", false otherwise.
	 */
	public void setCrossedOut(boolean crossedOut) {
		this.crossedOut = crossedOut;
	}
	
	/**
	 * A CharacterImageDisplay leaves a transparent area on either side of it;
	 * it is usually not fully opaque. This must be overridden to ensure that
	 * Swing doesn't do anything suspicious to it.
	 * @return false
	 * @see java.awt.Component#isOpaque()
	 */
	@Override
	public boolean isOpaque() {
		return false;
	}
	
	/**
	 * Paints the CharacterImageDisplay. This draws the image and
	 * adds the "crossed out" graphic if necessary.
	 * @see JComponent#paintComponents(Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		// this cast *shouldn't* fail
		Graphics2D g2d = (Graphics2D) g;
		
		if (image == null)
			return;
		
		// calculate the bounds needed to preserve the icon's aspect ratio
		Dimension panelSize = getSize();
		Dimension imageSize = new Dimension(image.getWidth(this), image.getHeight(this));
		Rectangle scaledRect = Geometry.sizeToFitBounds(panelSize, imageSize);
        
		// Scale and draw the image
		g2d.drawImage(
			image.getScaledInstance(scaledRect.width, scaledRect.height, Image.SCALE_AREA_AVERAGING), 
			scaledRect.x, scaledRect.y, scaledRect.width, scaledRect.height, null, this);
		
		// if it's crossed out draw a big ol' X over it.
		if (crossedOut) {
			g2d.setStroke(new BasicStroke(5.0f));
			g2d.drawLine(scaledRect.x, scaledRect.y, scaledRect.x + scaledRect.width, scaledRect.y + scaledRect.height);
			g2d.drawLine(scaledRect.x + scaledRect.width, scaledRect.y, scaledRect.x, scaledRect.y + scaledRect.height);
		}
	}
}
