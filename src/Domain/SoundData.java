package src.Domain;

import javax.sound.sampled.Clip;

public class SoundData {

    // Sound clips.

    Clip crashSound;
    Clip explosionSound;
    Clip fireSound;
    Clip missleSound;
    Clip saucerSound;
    Clip thrustersSound;
    Clip warpSound;

    // Flags for looping sound clips.

    boolean thrustersPlaying;
    boolean saucerPlaying;
    boolean misslePlaying;

    // Counter and total used to track the loading of the sound clips.

    int clipTotal   = 0;
    int clipsLoaded = 0;
}
