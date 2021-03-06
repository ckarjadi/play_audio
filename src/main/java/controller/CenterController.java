package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import model.AudioModel;
import util.AudioDuration;
import util.GetLabelText;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class CenterController {

    @FXML
    private HBox mediaTimeline;
    @FXML
    private VBox centerVBox;

    private MediaPlayer mp;
    private MediaView mediaView;
    private final boolean repeat = false;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;
    private Duration duration;
    private Slider timeSlider;
    private Label playTime;
    private Slider volumeSlider;
    private AudioModel audioModel;
    private LeftPanelController leftPanelController;

    public void initializeModel(AudioModel model) {
        this.audioModel = model;
        this.leftPanelController = audioModel.leftPanelController;
        File defaultFile = new File("file/wav/PinkPanther60.wav");
        initMediaTimeline();
        updateAudioPlayback(defaultFile);
    }
    public Slider getTimeSlider() {
        return timeSlider;
    }

    public MediaPlayer getMp() {
        return mp;
    }

    private void initMediaTimeline() {
        mediaTimeline.setAlignment(Pos.CENTER);
        mediaTimeline.setPadding(new Insets(5, 10, 5, 10));
    }

    public void updateAudioPlayback(File file) {
        mediaTimeline.getChildren().clear();
        updateFilenameLength(file);
        createMedia(file);
    }

    private void updateFilenameLength(File file) {
        audioModel.setCurrentAudioFile(file);
        leftPanelController.setFilenameLabelText(GetLabelText.getFilenameLabel(file.getName()));
        float duration = Float.MIN_VALUE;
        try {
            duration = AudioDuration.getDurationSeconds(file);
            String durationString = AudioDuration.getDurationString(duration);
            leftPanelController.setLengthLabelText(GetLabelText.getLengthLabel(durationString));
        } catch (IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
        audioModel.setAudioDuration(duration);
    }

    private void createMedia(File file) {
        Media media = new Media(file.toURI().toString());
        mp = new MediaPlayer(media);
        mediaView = new MediaView(mp);

        Button playButton = createPlayButton(mp);
        setMediaPlayerBehavior(mp, playButton);

        mediaTimeline.getChildren().add(playButton);

        Label spacer = new Label("   ");
        mediaTimeline.getChildren().add(spacer);

        Label timeLabel = new Label("Time: ");
        mediaTimeline.getChildren().add(timeLabel);

        timeSlider = new Slider();
        HBox.setHgrow(timeSlider, Priority.ALWAYS);
        timeSlider.setMinWidth(50);
        timeSlider.setMaxWidth(Double.MAX_VALUE);
        timeSlider.valueProperty().addListener(ov -> {
            if (timeSlider.isValueChanging()) {
                double timestamp = timeSlider.getValue() / 100.0;
                System.out.println("seeking to: " + duration.multiply(timestamp));
                mp.seek(duration.multiply(timestamp));
                System.out.println("timeSlider value is now: " + timeSlider.getValue());
            }
        });
        System.out.println("timeslider: " + timeSlider);
        mediaTimeline.getChildren().add(timeSlider);

        playTime = new Label();
        playTime.setPrefWidth(130);
        playTime.setMinWidth(50);
        mediaTimeline.getChildren().add(playTime);

        Label volumeLabel = new Label("Vol: ");
        mediaTimeline.getChildren().add(volumeLabel);

        volumeSlider = new Slider();
        volumeSlider.setPrefWidth(70);
        volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
        volumeSlider.setMinWidth(30);
        volumeSlider.valueProperty().addListener(ov -> {
            if (volumeSlider.isValueChanging()) {
                mp.setVolume(volumeSlider.getValue() / 100.0);
            }
        });
        mediaTimeline.getChildren().add(volumeSlider);
    }

    private Button createPlayButton(MediaPlayer mp) {
        final Button playButton = new Button(">");
        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Status status = mp.getStatus();

                if (status == Status.UNKNOWN  || status == Status.HALTED)
                {
                    // don't do anything in these states
                    return;
                }

                if ( status == Status.PAUSED
                        || status == Status.READY
                        || status == Status.STOPPED)
                {
                    // rewind the movie if we're sitting at the end
                    if (atEndOfMedia) {
                        mp.seek(mp.getStartTime());
                        atEndOfMedia = false;
                    }
                    mp.play();
                } else {
                    mp.pause();
                }
            }
        });
        return playButton;
    }

    private void setMediaPlayerBehavior(MediaPlayer mp, Button playButton) {
        mp.currentTimeProperty().addListener(ov -> {
            System.out.println("updating time...");
            updateValues();
        });

        mp.setOnPlaying(() -> {
            if (stopRequested) {
                mp.pause();
                stopRequested = false;
            } else {
                playButton.setText("||");
            }
        });

        mp.setOnPaused(() -> {
            playButton.setText(">");
        });

        mp.setOnReady(() -> {
            duration = mp.getMedia().getDuration();
            System.out.println("mp duration: " + duration);
            updateValues();
        });

        mp.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
        mp.setOnEndOfMedia(() -> {
            if (!repeat) {
                playButton.setText(">");
                stopRequested = true;
                atEndOfMedia = true;
            }
        });

    }

    @FXML
    private void initialize() {

    }

    public void updateValues() {
        if (playTime != null && timeSlider != null && volumeSlider != null) {
            Platform.runLater(() -> {
                Duration currentTime = mp.getCurrentTime();
                playTime.setText(formatTime(currentTime, duration));
                timeSlider.setDisable(duration.isUnknown());
                if (!timeSlider.isDisabled()
                        && duration.greaterThan(Duration.ZERO)
                        && !timeSlider.isValueChanging()) {
                    timeSlider.setValue(currentTime.divide(duration).toMillis()
                            * 100.0);
                }
                if (!volumeSlider.isValueChanging()) {
                    volumeSlider.setValue((int)Math.round(mp.getVolume()
                            * 100));
                }
            });
        }
    }

    private static String formatTime(Duration elapsed, Duration duration) {
        int intElapsed = (int)Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
                - elapsedMinutes * 60;

        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int)Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60 -
                    durationMinutes * 60;
            if (durationHours > 0) {
                return String.format("%d:%02d:%02d/%d:%02d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds,
                        durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d/%02d:%02d",
                        elapsedMinutes, elapsedSeconds,durationMinutes,
                        durationSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%d:%02d:%02d", elapsedHours,
                        elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d",elapsedMinutes,
                        elapsedSeconds);
            }
        }
    }

}
