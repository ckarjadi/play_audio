package model;

import controller.CenterController;
import controller.LeftPanelController;
import controller.MenuController;
import controller.RightPanelController;

import java.io.File;

public class AudioModel {
    public MenuController menuController;
    public LeftPanelController leftPanelController;
    public CenterController centerController;
    public RightPanelController rightPanelController;
    private File currentAudioFile;
    private float audioDuration;

    public AudioModel(MenuController menuController, LeftPanelController leftPanelController, CenterController centerController,
                      RightPanelController rightPanelController) {
        this.menuController = menuController;
        this.leftPanelController = leftPanelController;
        this.centerController = centerController;
        this.rightPanelController = rightPanelController;
    }

    public File getCurrentAudioFile() {
        return currentAudioFile;
    }

    public void setCurrentAudioFile(File currentAudioFile) {
        this.currentAudioFile = currentAudioFile;
    }

    public float getAudioDuration() {
        return audioDuration;
    }

    public void setAudioDuration(float audioDuration) {
        this.audioDuration = audioDuration;
    }
}
