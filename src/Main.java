package src;

import src.Application.Factory;
import src.Application.GameHandler;
import src.Domain.Background;
import src.Domain.Missile;
import src.Domain.Photon;
import src.Domain.Sprite;
import src.UI.InputOutput;
import src.UI.Screen;
import src.UI.StartScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Main implements Runnable{

    private static final long serialVersionUID = 256417214280211229L;

    // Copyright information.

    private String copyName = "src.Asteroids";
    private String copyVers = "Version 1.3";
    private String copyInfo = "Copyright 1998-2001 by Mike Hall";
    private String copyLink = "http://www.brainjar.com";
    private String copyText = copyName + '\n' + copyVers + '\n'
            + copyInfo + '\n' + copyLink;

    // Thread control variables.

    private Thread loadThread;
    private Thread loopThread;

    // creators
    private GameHandler myGameHandler = new GameHandler();
    private static Background myBackground = new Background();
    private static StartScreen myStartScreen = new StartScreen(myBackground);
    private static InputOutput myInputOutput = new InputOutput();


    public String getAppletInfo() {

        // Return copyright information.

        return(copyText);
    }

    public void init() {

        // Display copyright information.

        System.out.println(copyText);

        myStartScreen.setUpScreen();

        myInputOutput.setUpIO();


        // Create shape for the ship sprite. Including: // Create shapes for the ship thrusters.
        // Create shape for the flying saucer.
        // Create shape for the guided missle.
        // Create shape for each photon sprites.
        // Create asteroid sprites.
        // Create explosion sprites.

        myGameHandler.createGameObjects();

        // Initialize game data and put us in 'game over' mode.
        myGameHandler.createNewGame();

    }

    public void start() {

        if (loopThread == null) {
            loopThread = new Thread(this);
            loopThread.start();
        }

        if (!myGameHandler.isLoaded() && loadThread == null) {
            loadThread = new Thread(this);
            loadThread.start();
        }
    }

    public void stop() {

        if (loopThread != null) {
            try {
                loopThread.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            loopThread = null;
        }
        if (loadThread != null) {
            try {
                loadThread.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            loadThread = null;
        }
    }

    public static void main(String[] args)
    {
        Main main = new Main();
        main.init();

        JFrame mainFrame= new JFrame("src.Asteroids Game");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(myStartScreen);
        mainFrame.setSize(1200,800);
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);

        mainFrame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                myStartScreen.setBounds(0, 0, e.getComponent().getWidth(), e.getComponent().getHeight());
                myStartScreen.setSize(e.getComponent().getWidth(), e.getComponent().getHeight());
                myBackground.setWidth(e.getComponent().getWidth());
                myBackground.setHeight(e.getComponent().getHeight());
                myStartScreen.repaint();
            }
        });

        main.start();
    }


    @Override
    public void run() {
        long startTime;

        // Lower this thread's priority and get the current time.

        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        startTime = System.currentTimeMillis();

        // Run thread for loading sounds.

        if (!myGameHandler.isLoaded() && Thread.currentThread() == loadThread) {
            myGameHandler.loadSounds();
            myGameHandler.setLoaded(true);

            try {
                loadThread.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        // This is the main loop.

        while (Thread.currentThread() == loopThread) {

            myGameHandler.updateGame();

            // Update the screen and set the timer for the next loop.

            repaint();
            try {
                startTime += DELAY;
                Thread.sleep(Math.max(0, startTime - System.currentTimeMillis()));
            }
            catch (InterruptedException e) {
                break;
            }
        }


    }
}
