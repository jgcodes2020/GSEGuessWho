package ca.gse.guesswho.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;

import javax.swing.GrayFilter;
import javax.swing.JComponent;

/**
 * A display for an image, optionally displaying some kind of
 * "crossed out" graphic.
 */
public class CharacterImageDisplay extends JComponent {
	private Image image;
	private Image grayImage;
	private boolean crossedOut;

	public CharacterImageDisplay(Image image) {
		this.image = image;
		this.crossedOut = false;
		
		updateGrayImage();
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
		updateGrayImage();
	}
	
	private void updateGrayImage() {
		// this is an object which applies a graying filter
		ImageProducer producer = new FilteredImageSource(
			image.getSource(), new GrayFilter(true, 50)
		);
		// this applies the filter
		grayImage = Toolkit.getDefaultToolkit().createImage(producer);
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
		
		Image curImage;
		if (crossedOut)
			curImage = grayImage;
		else
			curImage = image;
		
		if (curImage == null)
			return;
		
		// calculate the bounds needed to preserve the icon's aspect ratio
		Dimension panelSize = getSize();
		Dimension imageSize = new Dimension(curImage.getWidth(this), curImage.getHeight(this));
		Rectangle scaledRect = Geometry.sizeToFitBounds(panelSize, imageSize);
		
		
        
		// Scale and draw the image
		g2d.drawImage(
			curImage.getScaledInstance(scaledRect.width, scaledRect.height, Image.SCALE_AREA_AVERAGING), 
			scaledRect.x, scaledRect.y, scaledRect.width, scaledRect.height, null, this);
	}
}
