package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.db.Database;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Database.init();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/MainView.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("OCR Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
