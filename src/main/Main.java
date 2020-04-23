package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = new BorderPane();
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
        root.setTop(menuLoader.load());

        FXMLLoader rightPanelLoader = new FXMLLoader(getClass().getResource("/fxml/rightPanel.fxml"));
        root.setRight(rightPanelLoader.load());

        FXMLLoader canvasLoader = new FXMLLoader(getClass().getResource("/fxml/canvas.fxml"));
        root.setCenter(canvasLoader.load());

        FXMLLoader leftPanelLoader = new FXMLLoader(getClass().getResource("/fxml/leftPanel.fxml"));
        root.setLeft(leftPanelLoader.load());

        Scene scene = new Scene(root, 1200, 850, Color.WHITE);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Title");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
