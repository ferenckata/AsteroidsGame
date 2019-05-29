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
}
