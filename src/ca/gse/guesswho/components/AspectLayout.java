package ca.gse.guesswho.components;

import java.awt.*;

/**
 * A layout manager that fixes a single child to a specific aspect ratio.
 */
public class AspectLayout implements LayoutManager {
	private int ratioWidth;
	private int ratioHeight;

	/**
	 * Creates a new {@link AspectLayout} that obeys the given aspect ratio.
	 * For example, to use a 16:9 aspect ratio, use {@code new AspectLayout(16, 9)}.
	 * @param w the width of the ratio.
	 * @param h the height of the ratio.
	 */
	public AspectLayout(int w, int h) {
		if (w <= 0 || h <= 0)
			throw new IllegalArgumentException("Width and height must be positive");
		
		// I was originally going to reduce the fraction to lowest terms
		// but then I realized how hard it is to explain the algorithm 
		// for computing the GCF.
		this.ratioWidth = w;
		this.ratioHeight = h;
	}

	/**
	 * Gets the single child component of a container, or throws if it has more.
	 * @param parent the container
	 * @exception IllegalStateException if the parent container contains more than one child
	 * @return the single child component, or null if there is no component
	 */
	private static Component getSingleChild(Container parent) {
		int count = parent.getComponentCount();
		if (count == 0)
			return null;
		else if (count > 1)
			throw new IllegalStateException("AspectLayout may only manage one child");

		return parent.getComponent(0);
	}

	/**
	 * Takes the bounds of non-margin space and margin widths and computes the bounds of
	 * the child control so that it fits within the insets.
	 * @param boxWidth the width of the non-margin space
	 * @param boxHeight the height of the non-margin space
	 * @param insets the insets of the area
	 * @param fill if true, sizes the child to fill instead of fit.
	 * @return The computed child position.
	 */
	private Rectangle sizeToRatio(int boxWidth, int boxHeight, Insets insets, boolean fill) {
		int scaledWidth, scaledHeight, relativeX, relativeY;

		// In this code, I adjusted the math to avoid floating-point values.
		// a/b < c/d is equivalent to a*d < c*b. We use long to prevent overflow.
		if (fill == (((long) this.ratioWidth * boxHeight) > ((long) this.ratioHeight * boxWidth))) {
			// The area is too wide. Fill the whole height, then determine width based on that.
			scaledHeight = boxHeight;
			scaledWidth = (int) (((long) boxHeight * this.ratioWidth) / this.ratioHeight);
			// We know that it fills the whole height, so relativeY = 0.
			relativeY = 0;
			relativeX = (boxWidth - scaledWidth) / 2;
		} else {
			// The area is too tall. Fill the whole width, then determine height based on that.
			scaledWidth = boxWidth;
			scaledHeight = (int) (((long) boxWidth * this.ratioHeight) / this.ratioWidth);
			// We know that it fills the whole width, so relativeX = 0.
			relativeX = 0;
			relativeY = (boxHeight - scaledHeight) / 2;
		}
		
		relativeX += insets.left;
		relativeY += insets.top;

		return new Rectangle(relativeX, relativeY, scaledWidth, scaledHeight);
	}

	/**
	 * Called when a parameter is added. Currently does absolutely nothing.
	 * @param name unused
	 * @param comp unused
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	/**
	 * Called when a parameter is removed. Currently does absolutely nothing.
	 * @param comp unused
	 */
	@Override
	public void removeLayoutComponent(Component comp) {
	}
	
	/**
	 * Lays out the container. Errors if the container contains more than one child.
	 * @throws IllegalStateException if the container has more than one child
	 */
	@Override
	public void layoutContainer(Container parent) {
		Component child = getSingleChild(parent);
		if (child == null)
			return;
		Insets insets = parent.getInsets();
		int boxWidth = parent.getWidth() - insets.left - insets.right;
		int boxHeight = parent.getHeight() - insets.top - insets.bottom;

		child.setBounds(sizeToRatio(boxWidth, boxHeight, insets, false));
	}

	/**
	 * Computes the container's minimum size. Errors if the container contains more than one child.
	 * @throws IllegalStateException if the container has more than one child
	 */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		Component child = getSingleChild(parent);
		if (child == null)
			return new Dimension(0, 0);
		
		Insets insets = parent.getInsets();
		Dimension minSize = parent.getMinimumSize();
		
		int boxWidth = (int) minSize.getWidth() - insets.left - insets.right;
		int boxHeight = (int) minSize.getHeight() - insets.top - insets.bottom;

		return sizeToRatio(boxWidth, boxHeight, insets, true).getSize();
	}

	/**
	 * Computes the container's preferred size. Errors if the container contains more than one child.
	 * @throws IllegalStateException if the container has more than one child
	 */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Component child = getSingleChild(parent);
		if (child == null)
			return new Dimension(0, 0);
		
		Insets insets = parent.getInsets();
		Dimension minSize = parent.getPreferredSize();
		
		int boxWidth = (int) minSize.getWidth() - insets.left - insets.right;
		int boxHeight = (int) minSize.getHeight() - insets.top - insets.bottom;

		return sizeToRatio(boxWidth, boxHeight, insets, true).getSize();
	}
}
