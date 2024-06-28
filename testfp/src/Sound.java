import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Sound {
    private Clip clip;

    // Konstruktor untuk membuat objek Sound
    public Sound(String filePath) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Metode untuk memainkan suara
    public void play() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }
}
