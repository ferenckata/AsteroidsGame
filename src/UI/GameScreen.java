package src.UI;

import src.Application.GameHandler;
import src.Domain.Data.FontData;
import src.Domain.GameObjects.*;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel {

    private static String copyName = "src.Asteroids";
    private static String copyVers = "Version 1.3";
    private static String copyInfo = "Copyright 1998-2001 by Mike Hall";
    private static String copyLink = "http://www.brainjar.com";

    private static final int DELAY = 20;             // Milliseconds between screen and
    public static final int FPS   =                 // the resulting frame rate.
            Math.round(1000 / getDELAY());

    private static final int SCRAP_COUNT  = 2 * FPS;  // Timer counter starting values
    private static final int HYPER_COUNT  = 3 * FPS;  // calculated using number of
    private static final int MISSILE_COUNT = 4 * FPS;  // seconds x frames per second.
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
        this.myGameHandler = GameHandler.getInstance();
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
        if (myGameHandler.isGameDetail()) {
            offGraphics.setColor(Color.white);
            Point[] stars = myBackground.getStars();
            for (i = 0; i < stars.length; i++)
                offGraphics.drawLine(stars[i].x, stars[i].y, stars[i].x, stars[i].y);
        }

        // Draw photon bullets.

        offGraphics.setColor(Color.white);
        Photon[] photons = myGameHandler.getPhotons();
        for (i = 0; i < myGameHandler.getMaxShots(); i++)
            if (photons[i].isActive())
                offGraphics.drawPolygon(photons[i].getSprite());

        // Draw the guided missle, counter is used to quickly fade color to black
        // when near expiration.

        c = Math.min(MISSILE_COUNT * 24, 255);
        offGraphics.setColor(new Color(c, c, c));
        if (myGameHandler.getMissile().isActive()) {
            Missile myMissile = myGameHandler.getMissile();
            offGraphics.drawPolygon(myMissile.getSprite());
            offGraphics.drawLine(myGameHandler.getMissile().getSprite().xpoints[myMissile.getSprite().npoints - 1], myMissile.getSprite().ypoints[myMissile.getSprite().npoints - 1],
                    myMissile.getSprite().xpoints[0], myMissile.getSprite().ypoints[0]);
        }

        // Draw the asteroids.

        Asteroid[] asteroids = myGameHandler.getAsteroids();
        for (i = 0; i < myGameHandler.getMaxRocks(); i++)
            if (asteroids[i].isActive()) {
                if (myGameHandler.isGameDetail()) {
                    offGraphics.setColor(Color.black);
                    offGraphics.fillPolygon(asteroids[i].getSprite());
                }
                offGraphics.setColor(Color.white);
                offGraphics.drawPolygon(asteroids[i].getSprite());
                offGraphics.drawLine(asteroids[i].getSprite().xpoints[asteroids[i].getSprite().npoints - 1], asteroids[i].getSprite().ypoints[asteroids[i].getSprite().npoints - 1],
                        asteroids[i].getSprite().xpoints[0], asteroids[i].getSprite().ypoints[0]);
            }

        // Draw the flying saucer.

        UFO myUfo = myGameHandler.getUfo();
        if (myUfo.isActive()) {
            if (myGameHandler.isGameDetail()) {
                offGraphics.setColor(Color.black);
                offGraphics.fillPolygon(myUfo.getSprite());
            }
            offGraphics.setColor(Color.white);
            offGraphics.drawPolygon(myUfo.getSprite());
            offGraphics.drawLine(myUfo.getSprite().xpoints[myUfo.getSprite().npoints - 1], myUfo.getSprite().ypoints[myUfo.getSprite().npoints - 1],
                    myUfo.getSprite().xpoints[0], myUfo.getSprite().ypoints[0]);
        }

        // Draw the ship, counter is used to fade color to white on hyperspace.

        c = 255 - (255 / HYPER_COUNT) * myGameHandler.getHyperCounter();
        Ship myShip = myGameHandler.getShip();
        if (myShip.isActive()) {
            if (myGameHandler.isGameDetail() && myGameHandler.getHyperCounter() == 0) {
                offGraphics.setColor(Color.black);
                offGraphics.fillPolygon(myShip.getSprite());
            }
            offGraphics.setColor(new Color(c, c, c));
            offGraphics.drawPolygon(myShip.getSprite());

            offGraphics.drawLine(myShip.getSprite().xpoints[myShip.getSprite().npoints - 1], myShip.getSprite().ypoints[myShip.getSprite().npoints - 1],
                    myShip.getSprite().xpoints[0], myShip.getSprite().ypoints[0]);

            // Draw thruster exhaust if thrusters are on. Do it randomly to get a
            // flicker effect.

            Thruster fwdThruster = myGameHandler.getFwdThruster();
            Thruster revThruster = myGameHandler.getRevThruster();
            if (!myGameHandler.isGamePaused() && myGameHandler.isGameDetail() && Math.random() < 0.5) {
                if (myGameHandler.isUpKeyInUse()) {
                    offGraphics.drawPolygon(fwdThruster.getSprite());
                    offGraphics.drawLine(fwdThruster.getSprite().xpoints[fwdThruster.getSprite().npoints - 1], fwdThruster.getSprite().ypoints[fwdThruster.getSprite().npoints - 1],
                            fwdThruster.getSprite().xpoints[0], fwdThruster.getSprite().ypoints[0]);
                }
                if (myGameHandler.isDownKeyInUse()) {
                    offGraphics.drawPolygon(revThruster.getSprite());
                    offGraphics.drawLine(revThruster.getSprite().xpoints[revThruster.getSprite().npoints - 1], revThruster.getSprite().ypoints[revThruster.getSprite().npoints - 1],
                            revThruster.getSprite().xpoints[0], revThruster.getSprite().ypoints[0]);
                }
            }
        }

        // Draw any explosion debris, counters are used to fade color to black.

        Explosion[] explosions = myGameHandler.getExplosions();
        for (i = 0; i < myGameHandler.getMaxScrap(); i++)
            if (explosions[i].isActive()) {
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

    public static int getMissileCount() {
        return MISSILE_COUNT;
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
