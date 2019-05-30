package src;

import src.Tech.Runner;

public class Main{

    private static final long serialVersionUID = 256417214280211229L;

    // Copyright information.

    private static String copyName = "src.Asteroids";
    private static String copyVers = "Version 1.3";
    private static String copyInfo = "Copyright 1998-2001 by Mike Hall";
    private static String copyLink = "http://www.brainjar.com";
    private static String copyText = copyName + '\n' + copyVers + '\n'
            + copyInfo + '\n' + copyLink;

    public static String getAppletInfo() {

        // Return copyright information.

        return(copyText);
    }

    public static void main(String[] args)
    {
        Runner runner = Runner.getInstance();

        // Display copyright information.

        System.out.println(getAppletInfo());
        runner.init();
    }
}
