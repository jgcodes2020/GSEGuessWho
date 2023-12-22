package ca.gse.guesswho.components;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.JComponent;

public class CharacterImageDisplay extends JComponent {
	private Image image;
	private boolean crossedOut;

	public CharacterImageDisplay(Image image) {
		this.image = image;
		this.crossedOut = false;
	}
	
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public boolean isCrossedOut() {
		return crossedOut;
	}

	public void setCrossedOut(boolean crossedOut) {
		this.crossedOut = crossedOut;
	}
	
	@Override
	public boolean isOpaque() {
		return false;
	}
	
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
