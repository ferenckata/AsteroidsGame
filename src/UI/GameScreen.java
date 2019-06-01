package src.UI;

import src.Application.GameHandler;
import src.Domain.GameObjects.*;
import src.Main;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel {

    private  String copyName = "src.Main";
    private  String copyVers = "Version 1.3";
    private  String copyInfo = "Copyright 1998-2001 by Mike Hall";
    private  String copyLink = "http://www.brainjar.com";

    private  final int DELAY = 20;             // Milliseconds between screen and
    public  final int FPS   =                 // the resulting frame rate.
            Math.round(1000 / getDELAY());

    private  final int SCRAP_COUNT  = 2 * FPS;  // Timer counter starting values
    private  final int HYPER_COUNT  = 3 * FPS;  // calculated using number of
    private  final int MISSILE_COUNT = 4 * FPS;  // seconds x frames per second.
    private  final int STORM_PAUSE  = 2 * FPS;

    private  final double MIN_ROCK_SPEED =  40.0 / FPS;
    private  final double MAX_ROCK_SPEED = 240.0 / FPS;
    private  final double MAX_ROCK_SPIN  = Math.PI / FPS;

    // Ship's rotation and acceleration rates and maximum speed.

    private  final double SHIP_ANGLE_STEP = Math.PI / FPS;
    private  final double SHIP_SPEED_STEP = 15.0 / FPS;
    private  final double MAX_SHIP_SPEED  = 1.25 * MAX_ROCK_SPEED;

    // Probablility of flying saucer firing a missle during any given frame
    // (other conditions must be met).

    private  final double MISSILE_PROBABILITY = 0.45 / FPS;


    private Font font      = new Font("Helvetica", Font.BOLD, 12);
    private FontMetrics fm = getFontMetrics(font);
    private int fontWidth  = fm.getMaxAdvance();
    private int fontHeight = fm.getHeight();

    // Off screen image.
    private Dimension offDimension;
    private Image     offImage;
    private Graphics offGraphics;
    private Background myBackground;
    private static GameScreen myInstance = null;

    private GameScreen(){
        System.out.println("GAMESCREEN");
        this.myBackground = Background.getMyInstance();
    }

    public static GameScreen getInstance(){
        if(myInstance == null){
            myInstance = new GameScreen();
        }
        return myInstance;
    }

    public void setUpScreen(){

        setBounds(0,0,1200,800);
        Dimension d = getSize();

        //ToDo: this was moved from Main, remove if unnecessary here
        // Display copyright information.
        System.out.println(Main.getAppletInfo());

        setFocusable(true);

        // Set the screen size.
        myBackground.setWidth(d.width);
        myBackground.setHeight(d.height);

        // Generate the starry background.
        myBackground.setUpStars();

    }

    public void update(Graphics g) {
        System.out.println("inUpdateGameScreen");

        paint(g);
    }

    public void paint(Graphics g) {
        GameHandler myGameHandler = GameHandler.getInstance();
        Dimension d = getSize();
        int i;
        int c;
        String s;
        int w, h;
        int x, y;

        System.out.println("InPaint");

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
        for (i = 0; i < photons.length; i++){
            if (photons[i].isActive())
                offGraphics.drawPolygon(photons[i].getSprite());
        }

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
        for (i = 0; i < asteroids.length; i++){
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
                c = (255 / SCRAP_COUNT) * myGameHandler.getExplosionCounter()[i];
                offGraphics.setColor(new Color(c, c, c));
                offGraphics.drawPolygon(explosions[i].getSprite());
            }

        // Display status and messages.

        offGraphics.setFont(font);
        offGraphics.setColor(Color.white);

        offGraphics.drawString("Score: " + myGameHandler.getScore(), fontWidth, fontHeight);
        offGraphics.drawString("Ships: " + myGameHandler.getShipsLeft(), fontWidth, d.height - fontHeight);
        s = "High: " + myGameHandler.getHighScore();
        offGraphics.drawString(s, d.width - (fontWidth + fm.stringWidth(s)), fontHeight);
        if (!myGameHandler.isSoundActive()) {
            s = "Mute";
            offGraphics.drawString(s, d.width - (fontWidth + fm.stringWidth(s)), d.height - fontHeight);
        }

        if (!myGameHandler.isGameActive()) {
            s = copyName;
            offGraphics.drawString(s, (d.width - fm.stringWidth(s)) / 2, d.height / 2 - 2 * fontHeight);
            s = copyVers;
            offGraphics.drawString(s, (d.width - fm.stringWidth(s)) / 2, d.height / 2 - fontHeight);
            s = copyInfo;
            offGraphics.drawString(s, (d.width - fm.stringWidth(s)) / 2, d.height / 2 + fontHeight);
            s = copyLink;
            offGraphics.drawString(s, (d.width - fm.stringWidth(s)) / 2, d.height / 2 + 2 * fontHeight);
            if (!myGameHandler.isSoundLoaded()) {
                s = "Loading sounds...";
                w = 4 * fontWidth + fm.stringWidth(s);
                h = fontHeight;
                x = (d.width - w) / 2;
                y = 3 * d.height / 4 - fm.getMaxAscent();
                offGraphics.setColor(Color.black);
                offGraphics.fillRect(x, y, w, h);
                offGraphics.setColor(Color.gray);
                if (myGameHandler.getTotalClips() > 0) {
                    offGraphics.fillRect(x, y, (int) (w * myGameHandler.getLoadedClips() / myGameHandler.getTotalClips()), h);
                }
                offGraphics.setColor(Color.white);
                offGraphics.drawRect(x, y, w, h);
                offGraphics.drawString(s, x + 2 * fontWidth, y + fm.getMaxAscent());

            } else {
                s = "Game Over";
                offGraphics.drawString(s, (d.width - fm.stringWidth(s)) / 2, d.height / 4);
                s = "'S' to Start";
                offGraphics.drawString(s, (d.width - fm.stringWidth(s)) / 2, d.height / 4 + fontHeight);
            }
        } else if (myGameHandler.isGamePaused()) {
            s = "Game Paused";
            offGraphics.drawString(s, (d.width - fm.stringWidth(s)) / 2, d.height / 4);
        }

        // Copy the off screen buffer to the screen.

        g.drawImage(offImage, 0, 0, this);
    }

    public int getDELAY() {
        return DELAY;
    }

    public int getScrapCount() {
        return SCRAP_COUNT;
    }

    public int getHyperCount() {
        return HYPER_COUNT;
    }

    public int getMissileCount() {
        return MISSILE_COUNT;
    }

    public int getStormPause() {
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

    public void setUpBackGround(int width, int height) {

    }


    public double getMinRockSpeed() {
        return MIN_ROCK_SPEED;
    }

    public double getMaxRockSpeed() {
        return MAX_ROCK_SPEED;
    }

    public double getMaxRockSpin() {
        return MAX_ROCK_SPIN;
    }

    public double getShipAngleStep() {
        return SHIP_ANGLE_STEP;
    }

    public double getShipSpeedStep() {
        return SHIP_SPEED_STEP;
    }

    public double getMaxShipSpeed() {
        return MAX_SHIP_SPEED;
    }

    public double getMissileProbability() {
        return MISSILE_PROBABILITY;
    }

    public double getMaxRockSpeedTimesFPSPer2(){ return MAX_ROCK_SPEED * FPS / 2;}

}
