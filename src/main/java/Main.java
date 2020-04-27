import controller.CenterController;
import controller.LeftPanelController;
import controller.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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

        FXMLLoader centerLoader = new FXMLLoader(getClass().getResource("/fxml/center.fxml"));
        root.setCenter(centerLoader.load());

        FXMLLoader leftPanelLoader = new FXMLLoader(getClass().getResource("/fxml/leftPanel.fxml"));
        root.setLeft(leftPanelLoader.load());

        MenuController menuController = menuLoader.getController();
        CenterController centerController = centerLoader.getController();
        LeftPanelController leftPanelController = leftPanelLoader.getController();

        centerController.initializeController(leftPanelController);
        menuController.initializeController(centerController, leftPanelController);

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
