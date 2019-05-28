package src.UI;

import src.Domain.Background;
import src.Domain.Sprite;

import java.awt.*;

public class StartScreen extends Screen {

    private Background myBackground;

    public StartScreen(Background background){
        this.myBackground = background;
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
