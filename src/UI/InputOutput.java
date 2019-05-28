package src.UI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class InputOutput implements KeyListener {

    // Key flags.

    boolean left  = false;
    boolean right = false;
    boolean up    = false;
    boolean down  = false;

    public void setUpIO(){
        // Set up key event handling and set focus to applet window.
        addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
