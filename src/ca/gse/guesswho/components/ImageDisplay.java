package ca.gse.guesswho.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.JComponent;

public class ImageDisplay extends JComponent {
	private Image image;

	public ImageDisplay(Image image) {
		this.image = image;
		repaint();
	}
	
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
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
		
		// do some math shenanigans to size the image properly
		Dimension panelSize = getSize();
		Dimension imageSize = new Dimension(image.getWidth(this), image.getHeight(this));
		Rectangle scaledRect = Geometry.sizeToFitBounds(panelSize, imageSize);
		
		Image scaledImage = image.getScaledInstance(scaledRect.width, scaledRect.height, Image.SCALE_AREA_AVERAGING);
        
		g2d.drawImage(scaledImage, scaledRect.x, scaledRect.y, scaledRect.width, scaledRect.height, null, this);
	}
}
