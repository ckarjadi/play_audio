package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.AudioModel;

public class LeftPanelController {
    private AudioModel audioModel;
    @FXML
    private Label filenameLabel;
    @FXML
    private Label lengthLabel;

    public void initializeModel(AudioModel audioModel) {
        this.audioModel = audioModel;
    }
    public Label getFilenameLabel() {
        return filenameLabel;
    }

    public Label getLengthLabel() {
        return lengthLabel;
    }


    public void setFilenameLabelText(String text) {
        this.filenameLabel.setText(text);
    }

    public void setLengthLabelText(String text) {
        this.lengthLabel.setText(text);
    }
}
