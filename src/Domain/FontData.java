package src.Domain;

import java.awt.*;

public class FontData {


    // Data for the screen font.

    Font font      = new Font("Helvetica", Font.BOLD, 12);
    FontMetrics fm = getFontMetrics(font);
    int fontWidth  = fm.getMaxAdvance();
    int fontHeight = fm.getHeight();
}
