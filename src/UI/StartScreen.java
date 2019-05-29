package src.UI;

import src.Domain.GameObjects.Background;

import java.awt.*;

public class StartScreen extends Screen {

    private Background myBackground;
    private static StartScreen myInstance;

    private StartScreen(){
        this.myBackground = new Background();
    }

    public static StartScreen getInstance(){
        if(myInstance == null){
            myInstance = new StartScreen();
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

}
