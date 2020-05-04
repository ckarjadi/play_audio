package util;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class AudioDuration {
    public static float getDurationSeconds(File file) throws IOException, UnsupportedAudioFileException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        AudioFormat format = audioInputStream.getFormat();
        long audioFileLength = file.length();
        int frameSize = format.getFrameSize();
        float frameRate = format.getFrameRate();
        return (audioFileLength / (frameSize * frameRate));
    }
    public static String getDurationString(File file) throws IOException, UnsupportedAudioFileException {
        float durationInSeconds = getDurationSeconds(file);
        int durationMinutes = (int) Math.floor(durationInSeconds / 60);
        int remainingSeconds = (int) (durationInSeconds - durationMinutes * 60);
        return String.format("%02d:%02d", durationMinutes, remainingSeconds);
    }
    public static String getDurationString(float durationInSeconds) {
        int durationMinutes = (int) Math.floor(durationInSeconds / 60);
        int remainingSeconds = (int) (durationInSeconds - durationMinutes * 60);
        return String.format("%02d:%02d", durationMinutes, remainingSeconds);
    }
}
