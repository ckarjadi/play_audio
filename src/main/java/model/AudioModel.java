package model;

import controller.CenterController;
import controller.LeftPanelController;
import controller.MenuController;
import controller.RightPanelController;

public class AudioModel {
    public MenuController menuController;
    public LeftPanelController leftPanelController;
    public CenterController centerController;
    public RightPanelController rightPanelController;


    public AudioModel(MenuController menuController, LeftPanelController leftPanelController, CenterController centerController,
                      RightPanelController rightPanelController) {
        this.menuController = menuController;
        this.leftPanelController = leftPanelController;
        this.centerController = centerController;
        this.rightPanelController = rightPanelController;
    }


}
