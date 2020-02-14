package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class Main extends Application{


  @Override
  public void start(Stage primaryStage) throws Exception{

    System.out.println(new File("src/main/resources/fxml/viewApp.fxml").getAbsolutePath());

    URL url = getClass().getClassLoader().getResource( "src/main/resources/fxml/viewApp.fxml" );
    Parent root = FXMLLoader.load(url);
    primaryStage.setTitle("Night Wolf");
    primaryStage.getIcons().add(new Image( "sample/logo_blue.png"));
    primaryStage.setScene(new Scene(root, 700, 500));
    // primaryStage.setResizable(false);
    // primaryStage.setMaximized(true);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
