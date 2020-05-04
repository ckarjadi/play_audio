package util;

import controller.RightPanelController;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.Slider;

public class SpeakerListCell extends ListCell<String> {
    public SpeakerListCell(RightPanelController rightPanelController, Slider timeSlider) {
        ObservableList<String> speakerList = rightPanelController.getSpeakerList();
        ObservableList<Float> startList = rightPanelController.getStartList();
        ObservableList<Float> endList = rightPanelController.getEndList();
        float duration = rightPanelController.getModel().getAudioDuration();
        setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                int idx = getIndex();
                String speaker = speakerList.get(idx);
                Float start = startList.get(idx);
                Float end = endList.get(idx);
                System.out.println(speaker);
                System.out.println(start);
                System.out.println(end);
                double newPosition = (start / duration) * timeSlider.getMax();
                System.out.println("start: " + start);
                System.out.println("duration: " + duration);
                System.out.println("timeSlider max: " + timeSlider.getMax());
                System.out.println("newPosition: " + newPosition);
                timeSlider.setValue(newPosition);
                timeSlider.setValueChanging(true);
            }

        });
    }
    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(item);
        }
    }
}
