package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import model.AudioModel;
import util.AudioDuration;
import util.CSVReader;
import util.ChooseFile;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MenuController {
    public MenuItem openAudioMI;
    public MenuItem openSegmentMI;
    public MenuItem clearSegmentMI;

    @FXML
    private AnchorPane anchor;
    private File workingDir = null;

    private AudioModel audioModel;
    private LeftPanelController leftPanelController;
    private CenterController centerController;

    private ObservableList<String> speakerList = FXCollections.observableArrayList();
    private ObservableList<Float> startList = FXCollections.observableArrayList();
    private ObservableList<Float> endList = FXCollections.observableArrayList();

    public void initializeModel(AudioModel model) {
        audioModel = model;
        leftPanelController = audioModel.leftPanelController;
        centerController = audioModel.centerController;
    }

    public void openAudioAction(ActionEvent actionEvent) {
        centerController.getMp().pause();
        File selectedFile = ChooseFile.chooseFile(anchor, workingDir, "audio files (.wav, .wma)",
                "Open Audio File", "*.wav", "*.wma");
        if (selectedFile != null) {
            leftPanelController.getFilenameLabel().setText("Filename: " + selectedFile.getName());
            try {
                leftPanelController.getLengthLabel().setText("Length: " + AudioDuration.getDurationString(selectedFile));
            } catch (IOException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
            centerController.updateAudioPlayback(selectedFile);
        }
    }

    public void openSegmentAction(ActionEvent actionEvent) throws IOException {
        File selectedFile = ChooseFile.chooseFile(anchor, workingDir, "segment files (.csv)",
                "Open Segment File", "*.csv");
        if (selectedFile != null) {
            clearObservableLists();
            updateObservableLists(CSVReader.readSegmentCSV(selectedFile.getAbsolutePath()));
        }
    }

    private void clearObservableLists() {
        speakerList.clear();
        startList.clear();
        endList.clear();
    }
    private void updateObservableLists(ArrayList<HashMap<String, String>> arrayList) {
        for (HashMap<String, String> hm: arrayList) {
            speakerList.add(hm.get("speaker"));
            startList.add(Float.parseFloat(hm.get("start")));
            endList.add(Float.parseFloat(hm.get("end")));
        }
    }

    public ObservableList<String> getSpeakerList() {
        return speakerList;
    }

    public ObservableList<Float> getStartList() {
        return startList;
    }

    public ObservableList<Float> getEndList() {
        return endList;
    }

    public void clearSegmentsAction(ActionEvent actionEvent) {
        clearObservableLists();
    }
}
