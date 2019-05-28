package src.UI;

import src.Domain.FontData;

import javax.swing.*;
import java.awt.*;

public abstract class Screen extends JPanel {

    static final int DELAY = 20;             // Milliseconds between screen and
    public static final int FPS   =                 // the resulting frame rate.
            Math.round(1000 / DELAY);


    static final int SCRAP_COUNT  = 2 * FPS;  // Timer counter starting values
    static final int HYPER_COUNT  = 3 * FPS;  // calculated using number of
    static final int MISSLE_COUNT = 4 * FPS;  // seconds x frames per second.
    static final int STORM_PAUSE  = 2 * FPS;


    // Off screen image.

    Dimension offDimension;
    Image     offImage;
    Graphics  offGraphics;


    FontData fontData;

}
