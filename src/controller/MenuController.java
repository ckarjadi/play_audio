package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class MenuController {
    @FXML
    private AnchorPane anchor;
    @FXML
    private MenuItem openMI;
    private File workingDir = null;

    public void openAction(ActionEvent actionEvent) {
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
        System.out.println(selectedFile);
    }
}
