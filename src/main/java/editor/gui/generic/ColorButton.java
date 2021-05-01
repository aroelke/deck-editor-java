package editor.gui.generic;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JButton;

/**
 * This class represents a button that displays a color rather than text.
 *
 * @author Alec Roelke
 */
public class ColorButton extends JButton
{
    /**
     * The amount of button to display outside the box of color.
     */
    private int border;
    /**
     * Color to display.
     */
    private Color color;

    /**
     * Create a new ColorButton with a random color and a border of 5.
     */
    public ColorButton()
    {
        this(null);
        Random rand = new Random();
        setColor(Color.getHSBColor(rand.nextFloat(), rand.nextFloat(), (float)Math.sqrt(rand.nextFloat())));
    }

    /**
     * Create a new ColorButton with the given color and a border of 5.
     *
     * @param col color to display
     */
    public ColorButton(Color col)
    {
        this(col, 5);
    }

    /**
     * Create a new ColorButton with the given color and border width.
     *
     * @param col color to display
     * @param b   border between color box and edge of button
     */
    public ColorButton(Color col, int b)
    {
        super(" ");
        setColor(col);
        border = b;
    }

    /**
     * Get this ColorButton's color.
     *
     * @return the color being displayed by this ColorButton.
     */
    public Color color()
    {
        return color;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(color());
        g.fillRect(border, border, getWidth() - 2 * border, getHeight() - 2 * border);
    }

    /**
     * Set the color of this ColorButton.
     *
     * @param col new color of the button
     */
    public void setColor(Color col)
    {
        color = col;
        repaint();
    }
}
