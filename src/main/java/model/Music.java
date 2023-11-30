package model;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import javax.swing.*;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

public class Music extends JFrame {

    public AdvancedPlayer player;
    Thread t;

    public void playBackgroundMusic() {
        try {
            // Load audio file from the classpath
            URL url = new URL("https://cv.nguyenmanh.id.vn/1.mp3");
            BufferedInputStream bis = new BufferedInputStream(url.openStream());

            player = new AdvancedPlayer(bis);
            player.setPlayBackListener(new PlaybackListener() {
                @Override
                public void playbackFinished(PlaybackEvent evt) {
                    // Implement any additional logic after playback finishes
                }
            });

            // Start playing the audio
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        player.play();
                    } catch (JavaLayerException e) {
                        e.printStackTrace();
                    }
                }
            }); 
            t.start();
            
        } catch (IOException | JavaLayerException e) {
            e.printStackTrace();
        }
    }

    // Close the player when closing the application
    @Override
    public void dispose() {
        if (player != null) {
            player.close();
        }
        super.dispose();
    }

    public void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Music planeWithMusic = new Music();
            planeWithMusic.setVisible(true);
        });
    }

    public void play() {
        playBackgroundMusic();
    }
    
    public void stop(){
//        t.interrupt();
        
        }
    
}
