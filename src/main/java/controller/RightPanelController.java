package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.AudioModel;

public class RightPanelController {
    @FXML
    private ListView<String> speakerListView;
    @FXML
    private ListView<Float> startListView;
    @FXML
    private ListView<Float> endListView;

    public void initializeModel(AudioModel audioModel) {
        this.speakerListView.setItems(audioModel.menuController.getSpeakerList());
        this.startListView.setItems(audioModel.menuController.getStartList());
        this.endListView.setItems(audioModel.menuController.getEndList());
    }
}
