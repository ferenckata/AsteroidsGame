package src.UI;

import src.Domain.Data.FontData;

import javax.swing.*;
import java.awt.*;

public abstract class Screen extends JPanel {

    private static final int DELAY = 20;             // Milliseconds between screen and
    public static final int FPS   =                 // the resulting frame rate.
            Math.round(1000 / getDELAY());


    private static final int SCRAP_COUNT  = 2 * FPS;  // Timer counter starting values
    private static final int HYPER_COUNT  = 3 * FPS;  // calculated using number of
    private static final int MISSLE_COUNT = 4 * FPS;  // seconds x frames per second.
    private static final int STORM_PAUSE  = 2 * FPS;

    private static final double MIN_ROCK_SPEED =  40.0 / FPS;
    private static final double MAX_ROCK_SPEED = 240.0 / FPS;
    private static final double MAX_ROCK_SPIN  = Math.PI / FPS;

    // Ship's rotation and acceleration rates and maximum speed.

    private static final double SHIP_ANGLE_STEP = Math.PI / FPS;
    private static final double SHIP_SPEED_STEP = 15.0 / FPS;
    private static final double MAX_SHIP_SPEED  = 1.25 * MAX_ROCK_SPEED;

    // Probablility of flying saucer firing a missle during any given frame
    // (other conditions must be met).

    private static final double MISSLE_PROBABILITY = 0.45 / FPS;


    // Off screen image.

    private Dimension offDimension;
    private Image     offImage;
    private Graphics  offGraphics;


    private FontData fontData;

    public static int getDELAY() {
        return DELAY;
    }

    public static int getScrapCount() {
        return SCRAP_COUNT;
    }

    public static int getHyperCount() {
        return HYPER_COUNT;
    }

    public static int getMissleCount() {
        return MISSLE_COUNT;
    }

    public static int getStormPause() {
        return STORM_PAUSE;
    }

    public Dimension getOffDimension() {
        return offDimension;
    }

    public void setOffDimension(Dimension offDimension) {
        this.offDimension = offDimension;
    }

    public Image getOffImage() {
        return offImage;
    }

    public void setOffImage(Image offImage) {
        this.offImage = offImage;
    }

    public Graphics getOffGraphics() {
        return offGraphics;
    }

    public void setOffGraphics(Graphics offGraphics) {
        this.offGraphics = offGraphics;
    }

    public FontData getFontData() {
        return fontData;
    }

    public void setFontData(FontData fontData) {
        this.fontData = fontData;
    }

    public static double getMinRockSpeed() {
        return MIN_ROCK_SPEED;
    }

    public static double getMaxRockSpeed() {
        return MAX_ROCK_SPEED;
    }

    public static double getMaxRockSpin() {
        return MAX_ROCK_SPIN;
    }

    public static double getShipAngleStep() {
        return SHIP_ANGLE_STEP;
    }

    public static double getShipSpeedStep() {
        return SHIP_SPEED_STEP;
    }

    public static double getMaxShipSpeed() {
        return MAX_SHIP_SPEED;
    }

    public static double getMissleProbability() {
        return MISSLE_PROBABILITY;
    }
}
