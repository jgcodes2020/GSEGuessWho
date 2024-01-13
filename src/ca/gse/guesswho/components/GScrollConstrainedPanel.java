package ca.gse.guesswho.components;

import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

/**
 * An extension of {@link JPanel} that can be constrained along the X and/or Y
 * axes when placed in a {@link JScrollPane}.
 * The 'G' prefix represents both its authorship (GSE) and its genericity
 * compared to other components that were created
 * for this project.
 */
public class GScrollConstrainedPanel extends JPanel implements Scrollable, SwingConstants {
    // These two properties are named after their respective getters in the
    // Scrollable interface.
    private boolean scrollableTracksViewportWidth;
    private boolean scrollableTracksViewportHeight;

    /**
     * Creates a new scroll-constrained panel with a layout manager.
     * 
     * @param layout              the initial layout manager.
     * @param trackViewportWidth  If true, this panel's width will be locked to that
     *                            of its viewport.
     * @param trackViewportHeight If true, this panel's height will be locked to
     *                            that of its viewport.
     */
    public GScrollConstrainedPanel(LayoutManager layout, boolean trackViewportWidth,
            boolean trackViewportHeight) {
        super(layout);
        this.scrollableTracksViewportWidth = trackViewportWidth;
        this.scrollableTracksViewportHeight = trackViewportHeight;
    }

    /**
     * Creates a new scroll-constrained panel.
     * 
     * @param trackViewportWidth  If true, this panel's width will be locked to that
     *                            of its viewport.
     * @param trackViewportHeight If true, this panel's height will be locked to
     *                            that of its viewport.
     */
    public GScrollConstrainedPanel(boolean trackViewportWidth, boolean trackViewportHeight) {
        this(null, trackViewportWidth, trackViewportHeight);
    }

    /**
     * Returns the preferred scroll viewport size for this scroll-constrained panel.
     * As recommended in
     * the official docs for {@code Scrollable}, this simply returns the panel's
     * preferred size.
     * 
     * @return the preferred scroll viewport size
     */
    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return this.getPreferredSize();
    }

    /**
     * Checks whether this scroll-constrained panel's width should be locked to that
     * of its viewport.
     * 
     * @return true if this panel's width will be constrained to its viewport.
     */
    @Override
    public boolean getScrollableTracksViewportWidth() {
        return this.scrollableTracksViewportWidth;
    }

    /**
     * Sets whether this scroll-constrained panel's height should be locked to that
     * of its viewport.
     * 
     * @param value If true, this panel's width will be locked to that of its
     *              viewport.
     */
    public void setScrollableTracksViewportWidth(boolean value) {
        this.scrollableTracksViewportWidth = value;
    }

    /**
     * Checks whether this scroll-constrained panel's width should be locked to that
     * of its viewport.
     * 
     * @return true if this panel's height will be constrained to its viewport.
     */
    @Override
    public boolean getScrollableTracksViewportHeight() {
        return this.scrollableTracksViewportHeight;
    }

    /**
     * Sets whether this scroll-constrained panel's width should be locked to that
     * of its viewport.
     * 
     * @param value If true, this panel's height will be locked to that of its
     *              viewport.
     */
    public void setScrollableTracksViewportHeight(boolean value) {
        this.scrollableTracksViewportHeight = value;
    }

    /**
     * Gets the distance to scroll when the user requests a "block scroll" (e.g.
     * Page Up/Page Down keys).
     * 
     * @param visibleRect the area visible through a viewport
     * @param orientation Either {@link SwingConstants#HORIZONTAL} or
     *                    {@link SwingConstants#VERTICAL}.
     * @param direction   Less than 0 for up or left, greater than 0 for down or
     *                    left.
     */
    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        if (orientation == VERTICAL)
            return visibleRect.height;
        else
            return visibleRect.width;

    }

    /**
     * Gets the distance to scroll when the user requests a "block scroll" (e.g.
     * Page Up/Page Down keys).
     * 
     * @param visibleRect the area visible through a viewport
     * @param orientation Either {@link SwingConstants#HORIZONTAL} or
     *                    {@link SwingConstants#VERTICAL}.
     * @param direction   Less than 0 for up or left, greater than 0 for down or
     *                    left.
     */
    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 1;
    }

}
