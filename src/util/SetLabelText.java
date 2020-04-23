package util;

import javafx.scene.control.Label;

public class SetLabelText {
    public static void setFilenameLabel(Label label, String suffix) {
        label.setText("Filename: " + suffix);
    }
    public static void setLengthLabel(Label label, String suffix) {
        label.setText("Length: " + suffix);
    }
}
