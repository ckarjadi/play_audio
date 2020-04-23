package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import util.AudioDuration;
import util.SetLabelText;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class CenterController {
    @FXML
    private HBox mediaTimeline;
    public VBox centerVBox;

    private MediaPlayer mp;
    private MediaView mediaView;
    private final boolean repeat = false;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;
    private Duration duration;
    private Slider timeSlider;
    private Label playTime;
    private Slider volumeSlider;

    private LeftPanelController leftPanelController;
    public void initializeController(LeftPanelController left) {
        leftPanelController = left;
        File defaultFile =  new File("file/wav/PinkPanther60.wav");
        SetLabelText.setFilenameLabel(leftPanelController.filenameLabel, defaultFile.getName());
        try {
            SetLabelText.setLengthLabel(leftPanelController.lengthLabel,
                    AudioDuration.getDurationString(defaultFile));
        } catch (IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }

        Media defaultMedia = new Media(defaultFile.toURI().toString());
        mp = new MediaPlayer(defaultMedia);
        mediaView = new MediaView(mp);
        mediaTimeline.setAlignment(Pos.CENTER);
        mediaTimeline.setPadding(new Insets(5, 10, 5, 10));
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
        mp.currentTimeProperty().addListener(ov -> updateValues());

        mp.setOnPlaying(() -> {
            if (stopRequested) {
                mp.pause();
                stopRequested = false;
            } else {
                playButton.setText("||");
            }
        });

        mp.setOnPaused(() -> {
            //System.out.println("onPaused");
            playButton.setText(">");
        });

        mp.setOnReady(() -> {
            duration = mp.getMedia().getDuration();
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
        mediaTimeline.getChildren().add(playButton);

        Label spacer = new Label("   ");
        mediaTimeline.getChildren().add(spacer);

        Label timeLabel = new Label("Time: ");
        mediaTimeline.getChildren().add(timeLabel);

        timeSlider = new Slider();
        HBox.setHgrow(timeSlider, Priority.ALWAYS);
        timeSlider.setMinWidth(50);
        timeSlider.setMaxWidth(Double.MAX_VALUE);
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
        mediaTimeline.getChildren().add(volumeSlider);
    }
    public void setMediaView(MediaPlayer mediaPlayer) {
        mp = mediaPlayer;
        mediaView = new MediaView(mediaPlayer);
    }

    @FXML
    private void initialize() {

    }

    private void updateValues() {
    }
}
