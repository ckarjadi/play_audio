package util;

import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class ChooseFile {
    public static File chooseFile(AnchorPane anchor, File workingDir, String chooserDescription,
                           String chooserTitle, String... extensions) {
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
                new FileChooser.ExtensionFilter(chooserDescription,
                        extensions));
        fileChooser.setInitialDirectory(workingDir);
        fileChooser.setTitle(chooserTitle);
        return fileChooser.showOpenDialog(stage);
    }
}
