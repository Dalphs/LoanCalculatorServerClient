package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Server extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("test");
        Parent root = FXMLLoader.load(getClass().getResource("LoanCalculatorServer.fxml"));
        primaryStage.setTitle("Server");
        primaryStage.setScene(new Scene(root, 500, 400));
        System.out.println("test2");
        primaryStage.show();

    }
}
