package ui.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import java.io.File;

// plays sound from button7.wav file in project folder
// CITATION: modelled from http://suavesnippets.blogspot.com/2011/06/add-sound-on-jbutton-click-in-java.html
public class Sound {

    // EFFECTS: plays sound from given file soundName if file exists.
    public void playSound(String soundName) {
        File musicPath = new File(soundName);

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicPath);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error");
        }
    }
}
