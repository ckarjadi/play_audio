package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import model.AudioModel;
import util.SpeakerListCell;

public class RightPanelController {
    @FXML
    private ListView<String> speakerListView;
    @FXML
    private ListView<Float> startListView;
    @FXML
    private ListView<Float> endListView;

    private ObservableList<String> speakerList;
    private ObservableList<Float> startList;
    private ObservableList<Float> endList;

    private Slider timeSlider;

    private AudioModel model;
    public void initializeModel(AudioModel audioModel) {
        model = audioModel;

        speakerList = audioModel.menuController.getSpeakerList();
        speakerListView.setItems(speakerList);

        startList = audioModel.menuController.getStartList();
        startListView.setItems(startList);

        endList = audioModel.menuController.getEndList();
        endListView.setItems(endList);

        timeSlider = audioModel.centerController.getTimeSlider();
        speakerListView.setCellFactory(param -> new SpeakerListCell(this, timeSlider));
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

    public AudioModel getModel() {
        return model;
    }
}
