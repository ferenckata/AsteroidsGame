package src.UI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class InputOutput implements KeyListener {

    // Key flags.

    private boolean left  = false;
    private boolean right = false;
    private boolean up    = false;
    private boolean down  = false;

    private static InputOutput myInstance;

    private InputOutput(){

    }

    public static InputOutput getInstance() {
        if (myInstance == null){
            myInstance = new InputOutput();
        }
        return myInstance;
    }

    public void setUpIO(){
        // Set up key event handling and set focus to applet window.
        addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Check if any cursor keys have been pressed and set flags.

        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            setLeft(true);
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            setRight(true);
        if (e.getKeyCode() == KeyEvent.VK_UP)
            setUp(true);
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            setDown(true);

        //ToDo: remaining elements in Asteroids godclass
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Check if any cursor keys where released and set flags.

        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            setLeft(false);
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            setRight(false);
        if (e.getKeyCode() == KeyEvent.VK_UP)
            setUp(false);
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            setDown(false);

        //ToDo: thruster sound spearation, remaining in Asteroids godclass
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
}
