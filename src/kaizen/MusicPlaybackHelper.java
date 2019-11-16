/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaizen;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author blair
 */
public class MusicPlaybackHelper {

    public static MediaPlayer globalMediaPlayer;

    public static boolean playMusic(String filename) {
        boolean isSuccessful = false;
        try {
            Media media = new Media(new File(filename).toURI().toString());
            MusicPlaybackHelper.globalMediaPlayer = new MediaPlayer(media);
            MusicPlaybackHelper.globalMediaPlayer.play();
            //Loop
            isSuccessful = true;
        } catch (Exception e) {
            isSuccessful = false;
        }

        return isSuccessful;
    }
    
    //Write new method to stop music when playing:
    public static void stopMusic() {
        boolean isSuccessful = false;
        try {
            MusicPlaybackHelper.globalMediaPlayer.stop();
            isSuccessful = true;
        } catch (Exception e) {
            isSuccessful = false;
        }
        System.out.println(isSuccessful);
    }
}
