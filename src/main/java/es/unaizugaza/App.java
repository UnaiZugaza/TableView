package es.unaizugaza;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * JavaFX App
 */

public class App extends Application {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        logger.info("Iniciando el programa...");
        scene = new Scene(loadFXML("fxml/mostrarTabla"), 640, 480);
        stage.setMinWidth(400);
        stage.setMinHeight(500);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}