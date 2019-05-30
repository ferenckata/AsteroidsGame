package src.Application;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.datatransfer.ClipboardOwner;
import java.io.File;
import java.io.IOException;

public class Sound {

    private static Sound myInstance;
    private int clipTotal;
    private int clipsLoaded;
    private Clip crashSound;
    private Clip explosionSound;
    private Clip fireSound;
    private Clip missileSound;
    private Clip saucerSound;
    private Clip thrustersSound;
    private Clip warpSound;

    // Flags for looping sound clips.

    private boolean thrustersPlaying;
    private boolean saucerPlaying;
    private boolean missilePlaying;

    private Sound(){
        clipTotal = 0;
        clipsLoaded = 0;
    }

    public static Sound getInstance(){
        if (myInstance == null){
            myInstance = new Sound();
        }
        return myInstance;
    }

    public void loadSound(String clip) throws LineUnavailableException, UnsupportedAudioFileException, IOException{
        Clip audioClip = AudioSystem.getClip();
        switch (clip) {
            case "Crash" :
                crashSound = audioClip;
                crashSound.open(AudioSystem.getAudioInputStream(new File("sounds/crash.wav")));
            case "Explosion" :
                explosionSound = audioClip;
                explosionSound.open(AudioSystem.getAudioInputStream(new File("sounds/explosion.wav")));
            case "Fire" :
                fireSound = audioClip;
                fireSound.open(AudioSystem.getAudioInputStream(new File("sounds/fire.wav")));
            case "Missile" :
                missileSound = audioClip;
                missileSound.open(AudioSystem.getAudioInputStream(new File("sounds/missile.wav")));
            case "Saucer" :
                saucerSound = audioClip;
                saucerSound.open(AudioSystem.getAudioInputStream(new File("sounds/saucer.wav")));
            case "Thrusters" :
                thrustersSound = audioClip;
                thrustersSound.open(AudioSystem.getAudioInputStream(new File("sounds/thrusters.wav")));
            case "Warp" :
                warpSound = audioClip;
                warpSound.open(AudioSystem.getAudioInputStream(new File("sounds/warp.wav")));
        }
        clipTotal++;
    }

    public void runSound(String clip) {
        switch (clip) {
            case "Crash" :
                crashSound.start();
                crashSound.stop();
            case "Explosion" :
                explosionSound.start();
                explosionSound.stop();
            case "Fire" :
                fireSound.start();
                fireSound.stop();
            case "Missile" :
                missileSound.start();
                missileSound.stop();
            case "Saucer" :
                saucerSound.start();
                saucerSound.stop();
            case "Thrusters" :
                thrustersSound.start();
                thrustersSound.stop();
            case "Warp" :
                warpSound.start();
                warpSound.stop();
        }
        clipsLoaded++;

    }


    public void initUfoSound(boolean sound) {

        mySoundData.setSaucerPlaying(true);

        if (sound) {
            saucerSound.setFramePosition(0);
            saucerSound.start();
            saucerSound.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void initCrashSound() {
        crashSound.setFramePosition(0);
        crashSound.start();


    public void stopThrustersSound() {

        if (isThrustersPlaying()){
            thrustersSound.stop();
            thrustersPlaying = false;
        }
    }


    public void setThrustersPlaying(boolean b) {
    }


    public boolean isThrustersPlaying() {
        return thrustersPlaying;
    }

    public boolean isSaucerPlaying() {
        return saucerPlaying;
    }

    public void setSaucerPlaying(boolean saucerPlaying) {
        this.saucerPlaying = saucerPlaying;
    }

    public boolean isMissilePlaying() {
        return missilePlaying;
    }

    public void setMissilePlaying(boolean missilePlaying) {
        this.missilePlaying = missilePlaying;
    }

    public Clip getCrashSound() {
        return crashSound;
    }

    public void setCrashSound(Clip crashSound) {
        this.crashSound = crashSound;
    }

    public Clip getExplosionSound() {
        return explosionSound;
    }

    public void setExplosionSound(Clip explosionSound) {
        this.explosionSound = explosionSound;
    }

    public Clip getFireSound() {
        return fireSound;
    }

    public void setFireSound(Clip fireSound) {
        this.fireSound = fireSound;
    }

    public Clip getMissileSound() {
        return missileSound;
    }

    public void setMissileSound(Clip missileSound) {
        this.missileSound = missileSound;
    }

    public Clip getSaucerSound() {
        return saucerSound;
    }

    public void setSaucerSound(Clip saucerSound) {
        this.saucerSound = saucerSound;
    }

    public Clip getThrustersSound() {
        return thrustersSound;
    }

    public void setThrustersSound(Clip thrustersSound) {
        this.thrustersSound = thrustersSound;
    }

    public Clip getWarpSound() {
        return warpSound;
    }

    public void setWarpSound(Clip warpSound) {
        this.warpSound = warpSound;
    }

    public void loopThrustersSound() {
        thrustersSound.setFramePosition(0);
        thrustersSound.stop();
        thrustersSound.loop(Clip.LOOP_CONTINUOUSLY);
        thrustersPlaying = true;
    }
    public void startFireSound(){
        fireSound.setFramePosition(0);
        fireSound.start();
    }

    public void startWarpSound() {
        warpSound.setFramePosition(0);
        warpSound.start();
    }

    public void loopMissileSound() {
        missileSound.setFramePosition(0);
        missileSound.start();
        missileSound.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void loopSaucerSound() {
        saucerSound.setFramePosition(0);
        saucerSound.start();
        saucerSound.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stopSound(String sound) {
        switch (sound) {
            case "Crash" :
                crashSound.stop();
            case "Explosion" :
                explosionSound.stop();
            case "Fire" :
                fireSound.stop();
            case "Missile" :
                missileSound.stop();
            case "Saucer" :
                saucerSound.stop();
            case "Thrusters" :
                thrustersSound.stop();
            case "Warp" :
                warpSound.stop();
            case "AllSounds" :
                crashSound.stop();
                explosionSound.stop();
                fireSound.stop();
                missileSound.stop();
                saucerSound.stop();
                thrustersSound.stop();
                warpSound.stop();
        }
    }
}
