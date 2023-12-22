package ca.gse.guesswho.components;

import java.awt.*;

/**
 * Utility class for geometric operations.
 */
public class Geometry {
	public static Rectangle sizeToFitBounds(Dimension boxSize, Dimension imageSize) {
		// Compare the aspect ratio of the two boxes.
		// (a / b > c / d) is equivalent to (a * d > b * c), and the multiply version
		// saves me the use of floating-point numbers.
		if (boxSize.width * imageSize.height > boxSize.height * imageSize.width) {
			// the box is wider than the image
			// fill height and compute width based on aspect ratio
			int matchingWidth = boxSize.height * imageSize.width / imageSize.height;
			int xOffset = (boxSize.width - matchingWidth) / 2;
			
			return new Rectangle(xOffset, 0, matchingWidth, boxSize.height);
		}
		else {
			// the box is skinnier than the image
			// fill width and compute height based on aspect ratio
			int matchingHeight = boxSize.width * imageSize.height / imageSize.width;
			int yOffset = (boxSize.height - matchingHeight) / 2;
			
			return new Rectangle(0, yOffset, boxSize.width, matchingHeight);
		}
	}
}
