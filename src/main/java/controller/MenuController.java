package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import util.AudioDuration;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class MenuController {
    @FXML
    private AnchorPane anchor;
    private File workingDir = null;
    private LeftPanelController leftPanelController;
    private CenterController centerController;
    public void initializeController(CenterController cenController,
                                     LeftPanelController leftController) {
        leftPanelController = leftController;
        centerController = cenController;
    }
    public void openAudioAction(ActionEvent actionEvent) {
        Window stage = anchor.getScene().getWindow();
        if (workingDir == null) {
            workingDir = new File("." + File.separator + "file");
        } else {
            while (!workingDir.exists()) {
                workingDir = new File(workingDir.getParent());
            }
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("audio files (wav, wma)",
                        "*.wav", "*.wma"));
        fileChooser.setInitialDirectory(workingDir);
        fileChooser.setTitle("Open Audio File");
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            leftPanelController.filenameLabel.setText("Filename: " + selectedFile.getName());
            try {
                leftPanelController.lengthLabel.setText("Length: " + AudioDuration.getDurationString(selectedFile));
            } catch (IOException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
            centerController.updateAudioPlayback(selectedFile);
        }
    }
    
    public void openSegmentAction(ActionEvent actionEvent) {
    }
}
