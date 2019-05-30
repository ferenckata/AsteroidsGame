package src.Tech;

import src.Application.GameHandler;
import src.Domain.Game;
import src.Domain.GameObjects.Background;
import src.UI.GameScreen;
import src.UI.InputOutput;
import src.UI.StartScreen;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Runner implements Runnable{

    private static Runner myInstance;
    private static final int DELAY = 20;

    // Thread control variables.

    private Thread loadThread;
    private Thread loopThread;

    private GameHandler myGameHandler;
    private Background myBackground;
    //private StartScreen myStartScreen;
    private GameScreen myGameScreen;
    private InputOutput myInputOutput;

    private Runner(){
    }

    public static Runner getInstance(){
        if (myInstance == null){
            myInstance = new Runner();
        }
        return myInstance;
    }

    public void init (){
        //Creators
        myGameHandler = GameHandler.getInstance();
        myBackground = new Background();
        //myStartScreen = StartScreen.getInstance(myBackground);
        myInputOutput = InputOutput.getInstance();
        myGameScreen = GameScreen.getInstance(myBackground);


       //myStartScreen.setUpScreen();
        myGameScreen.setUpScreen();
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

        setUpMainFrame();
        start();
    }


    public void start() {

        if (loopThread == null) {
            loopThread = new Thread(this);
            loopThread.start();
        }

        if (!myGameHandler.isSoundLoaded() && loadThread == null) {
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

    @Override
    public void run() {
        long startTime;

        // Lower this thread's priority and get the current time.

        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        startTime = System.currentTimeMillis();

        // Run thread for loading sounds.

        if (!myGameHandler.isSoundLoaded() && Thread.currentThread() == loadThread) {
            myGameHandler.loadSounds(DELAY);
            myGameHandler.setSoundLoaded(true);

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

            //myStartScreen.repaint();
            myGameScreen.repaint();
            try {
                startTime += DELAY;
                Thread.sleep(Math.max(0, startTime - System.currentTimeMillis()));
            }
            catch (InterruptedException e) {
                break;
            }
        }
    }

    private void setUpMainFrame(){
        JFrame mainFrame = new JFrame("src.Asteroids Game");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //mainFrame.add(myStartScreen);
        mainFrame.add(myGameScreen);
        mainFrame.setSize(1200,800);
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);

        mainFrame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                //myStartScreen.setBounds(0, 0, e.getComponent().getWidth(), e.getComponent().getHeight());
                //myStartScreen.setSize(e.getComponent().getWidth(), e.getComponent().getHeight());
                //myBackground.setWidth(e.getComponent().getWidth());
                //myBackground.setHeight(e.getComponent().getHeight());
                //myStartScreen.repaint();

                myGameScreen.setBounds(0, 0, e.getComponent().getWidth(), e.getComponent().getHeight());
                myGameScreen.setSize(e.getComponent().getWidth(), e.getComponent().getHeight());
                myBackground.setWidth(e.getComponent().getWidth());
                myBackground.setHeight(e.getComponent().getHeight());
                myGameScreen.repaint();
            }
        });
    }
}