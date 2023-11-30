import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

public class PlaneWithMusic extends JFrame {

    private AdvancedPlayer player;


    private void playBackgroundMusic() {
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
            new Thread(() -> {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }).start();

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PlaneWithMusic planeWithMusic = new PlaneWithMusic();
            planeWithMusic.setVisible(true);
        });
    }
}
