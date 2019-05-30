package src.UI;

import src.Application.GameHandler;
import src.Domain.GameObjects.Background;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel {

    private static final int DELAY = 20;             // Milliseconds between screen and
    public static final int FPS   =                 // the resulting frame rate.
            Math.round(1000 / getDELAY());

    private static final int SCRAP_COUNT  = 2 * FPS;  // Timer counter starting values
    private final int HYPER_COUNT  = 3 * FPS;  // calculated using number of
    private static final int MISSLE_COUNT = 4 * FPS;  // seconds x frames per second.
    private static final int STORM_PAUSE  = 2 * FPS;

    // Off screen image.
    private Dimension offDimension;
    private Image     offImage;
    private Graphics offGraphics;
    private GameHandler myGameHandler;
    private Background myBackground;
    private static GameScreen myInstance;

    private GameScreen(Background myBackground){
        this.myBackground = myBackground;
    }

    public static GameScreen getInstance(Background myBackground){
        if(myInstance == null){
            myInstance = new GameScreen(myBackground);
        }
        return myInstance;
    }

    public void setUpScreen(){

        Dimension d = getSize();

        // Set the screen size.
        myBackground.setWidth(d.width);
        myBackground.setHeight(d.height);

        // Generate the starry background.
        myBackground.setUpStars();

        setBounds(0,0,1200,800);

        setFocusable(true);

    }


    public void update(Graphics g) {

        paint(g);
    }

    public void paint(Graphics g) {

        Dimension d = getSize();
        int i;
        int c;
        String s;
        int w, h;
        int x, y;

        // Create the off screen graphics context, if no good one exists.

        if (offGraphics == null || d.width != offDimension.width || d.height != offDimension.height) {
            offDimension = d;
            offImage = createImage(d.width, d.height);
            offGraphics = offImage.getGraphics();
        }

        // Fill in background and stars.

        offGraphics.setColor(Color.black);
        offGraphics.fillRect(0, 0, d.width, d.height);
        if (my) {
            offGraphics.setColor(Color.white);
            for (i = 0; i < numStars; i++)
                offGraphics.drawLine(stars[i].x, stars[i].y, stars[i].x, stars[i].y);
        }

        // Draw photon bullets.

        offGraphics.setColor(Color.white);
        for (i = 0; i < MAX_SHOTS; i++)
            if (photons[i].active)
                offGraphics.drawPolygon(photons[i].sprite);

        // Draw the guided missle, counter is used to quickly fade color to black
        // when near expiration.

        c = Math.min(missleCounter * 24, 255);
        offGraphics.setColor(new Color(c, c, c));
        if (missle.active) {
            offGraphics.drawPolygon(missle.sprite);
            offGraphics.drawLine(missle.sprite.xpoints[missle.sprite.npoints - 1], missle.sprite.ypoints[missle.sprite.npoints - 1],
                    missle.sprite.xpoints[0], missle.sprite.ypoints[0]);
        }

        // Draw the asteroids.

        for (i = 0; i < MAX_ROCKS; i++)
            if (asteroids[i].active) {
                if (detail) {
                    offGraphics.setColor(Color.black);
                    offGraphics.fillPolygon(asteroids[i].sprite);
                }
                offGraphics.setColor(Color.white);
                offGraphics.drawPolygon(asteroids[i].sprite);
                offGraphics.drawLine(asteroids[i].sprite.xpoints[asteroids[i].sprite.npoints - 1], asteroids[i].sprite.ypoints[asteroids[i].sprite.npoints - 1],
                        asteroids[i].sprite.xpoints[0], asteroids[i].sprite.ypoints[0]);
            }

        // Draw the flying saucer.

        if (ufo.active) {
            if (detail) {
                offGraphics.setColor(Color.black);
                offGraphics.fillPolygon(ufo.sprite);
            }
            offGraphics.setColor(Color.white);
            offGraphics.drawPolygon(ufo.sprite);
            offGraphics.drawLine(ufo.sprite.xpoints[ufo.sprite.npoints - 1], ufo.sprite.ypoints[ufo.sprite.npoints - 1],
                    ufo.sprite.xpoints[0], ufo.sprite.ypoints[0]);
        }

        // Draw the ship, counter is used to fade color to white on hyperspace.

        c = 255 - (255 / HYPER_COUNT) * hyperCounter;
        if (ship.active) {
            if (detail && hyperCounter == 0) {
                offGraphics.setColor(Color.black);
                offGraphics.fillPolygon(ship.sprite);
            }
            offGraphics.setColor(new Color(c, c, c));
            offGraphics.drawPolygon(ship.sprite);

            offGraphics.drawLine(ship.sprite.xpoints[ship.sprite.npoints - 1], ship.sprite.ypoints[ship.sprite.npoints - 1],
                    ship.sprite.xpoints[0], ship.sprite.ypoints[0]);

            // Draw thruster exhaust if thrusters are on. Do it randomly to get a
            // flicker effect.

            if (!paused && detail && Math.random() < 0.5) {
                if (up) {
                    offGraphics.drawPolygon(fwdThruster.sprite);
                    offGraphics.drawLine(fwdThruster.sprite.xpoints[fwdThruster.sprite.npoints - 1], fwdThruster.sprite.ypoints[fwdThruster.sprite.npoints - 1],
                            fwdThruster.sprite.xpoints[0], fwdThruster.sprite.ypoints[0]);
                }
                if (down) {
                    offGraphics.drawPolygon(revThruster.sprite);
                    offGraphics.drawLine(revThruster.sprite.xpoints[revThruster.sprite.npoints - 1], revThruster.sprite.ypoints[revThruster.sprite.npoints - 1],
                            revThruster.sprite.xpoints[0], revThruster.sprite.ypoints[0]);
                }
            }
        }

        // Draw any explosion debris, counters are used to fade color to black.

        for (i = 0; i < MAX_SCRAP; i++)
            if (explosions[i].active) {
                c = (255 / SCRAP_COUNT) * explosionCounter [i];
                offGraphics.setColor(new Color(c, c, c));
                offGraphics.drawPolygon(explosions[i].sprite);
            }

        // Display status and messages.

        offGraphics.setFont(font);
        offGraphics.setColor(Color.white);

        offGraphics.drawString("Score: " + score, fontWidth, fontHeight);
        offGraphics.drawString("Ships: " + shipsLeft, fontWidth, d.height - fontHeight);
        s = "High: " + highScore;
        offGraphics.drawString(s, d.width - (fontWidth + fm.stringWidth(s)), fontHeight);
        if (!sound) {
            s = "Mute";
            offGraphics.drawString(s, d.width - (fontWidth + fm.stringWidth(s)), d.height - fontHeight);
        }

        if (!playing) {
            s = copyName;
            offGraphics.drawString(s, (d.width - fm.stringWidth(s)) / 2, d.height / 2 - 2 * fontHeight);
            s = copyVers;
            offGraphics.drawString(s, (d.width - fm.stringWidth(s)) / 2, d.height / 2 - fontHeight);
            s = copyInfo;
            offGraphics.drawString(s, (d.width - fm.stringWidth(s)) / 2, d.height / 2 + fontHeight);
            s = copyLink;
            offGraphics.drawString(s, (d.width - fm.stringWidth(s)) / 2, d.height / 2 + 2 * fontHeight);
            if (!loaded) {
                s = "Loading sounds...";
                w = 4 * fontWidth + fm.stringWidth(s);
                h = fontHeight;
                x = (d.width - w) / 2;
                y = 3 * d.height / 4 - fm.getMaxAscent();
                offGraphics.setColor(Color.black);
                offGraphics.fillRect(x, y, w, h);
                offGraphics.setColor(Color.gray);
                if (clipTotal > 0)
                    offGraphics.fillRect(x, y, (int) (w * clipsLoaded / clipTotal), h);
                offGraphics.setColor(Color.white);
                offGraphics.drawRect(x, y, w, h);
                offGraphics.drawString(s, x + 2 * fontWidth, y + fm.getMaxAscent());
            }
            else {
                s = "Game Over";
                offGraphics.drawString(s, (d.width - fm.stringWidth(s)) / 2, d.height / 4);
                s = "'S' to Start";
                offGraphics.drawString(s, (d.width - fm.stringWidth(s)) / 2, d.height / 4 + fontHeight);
            }
        }
        else if (paused) {
            s = "Game Paused";
            offGraphics.drawString(s, (d.width - fm.stringWidth(s)) / 2, d.height / 4);
        }

        // Copy the off screen buffer to the screen.

        g.drawImage(offImage, 0, 0, this);
    }


    private FontData fontData;

    public static int getDELAY() {
        return DELAY;
    }

    public static int getScrapCount() {
        return SCRAP_COUNT;
    }

    public int getHyperCount() {
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

}
