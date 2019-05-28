package src.Domain;

import java.awt.*;

public class Background {

    // Fields:

    private int width;          // Dimensions of the graphics area.
    private int height;


    // Background stars.

    private int     numStars;
    private Point[] stars;

    // getters and setters


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getNumStars() {
        return numStars;
    }

    public void setNumStars(int numStars) {
        this.numStars = numStars;
    }

    public Point[] getStars() {
        return stars;
    }

    public void setStars(Point[] stars) {
        this.stars = stars;
    }

    public void setUpStars() {
        numStars = width * height / 5000;
        stars = new Point[numStars];
        for (int i = 0; i < numStars; i++)
            stars[i] = new Point((int) (Math.random() * width), (int) (Math.random() * height));
    }
}
