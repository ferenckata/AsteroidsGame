package src.Tech;

import src.Application.GameHandler;

import src.Domain.Game;
import src.UI.GameScreen;
import src.UI.InputOutput;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Runner implements Runnable{

    private static Runner myInstance = null;
    private static final int DELAY = 20;

    // Thread control variables.
    private Thread loadThread;
    private Thread loopThread;
    private GameScreen myGameScreen = GameScreen.getInstance();
    private GameHandler myGameHandler = GameHandler.getInstance();
    private InputOutput myInputOutput = InputOutput.getInstance();

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

        myInputOutput.setUpIO();
        myGameScreen.setUpScreen();

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
                if (!myGameHandler.isGamePaused()){
                    System.out.println("BEFORE UPDATEGAME");
                    myGameHandler.updateGame();
                }


                System.out.println("BEFORE REPAINT");

                //working version -> repaint();
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
        JFrame mainFrame = new JFrame("src.Main Game");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(myGameScreen);
        mainFrame.setSize(1200,800);
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);

        mainFrame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {

                myGameScreen.setBounds(0, 0, e.getComponent().getWidth(), e.getComponent().getHeight());
                myGameScreen.setSize(e.getComponent().getWidth(), e.getComponent().getHeight());
                myGameScreen.setUpBackGround(e.getComponent().getWidth(),e.getComponent().getHeight());
                System.out.println("BeforeRepaintComponent");
                myGameScreen.repaint();
            }
        });
    }
}
