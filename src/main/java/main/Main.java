package main;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lk.vivoxalabs.customstage.CustomStage;
import util.manager.DownloadManager;

public class Main extends Application{

    public static final DownloadManager DOWNLOAD_MANAGER = new DownloadManager();

  @Override
  public void start(Stage primaryStage) throws Exception{
      CustomStage.getDefaultSceneManager().automate(getClass().getResource("/view/fxml/viewApp.fxml").toURI().toURL());

      Parent root = CustomStage.getDefaultSceneManager().getScene("viewApp");
      primaryStage.setTitle("Night Wolf");
      primaryStage.getIcons().add(new Image( "images/logo_blue.png"));
      primaryStage.setScene(new Scene(root, 800, 600));
      // primaryStage.setResizable(false);
      // primaryStage.setMaximized(true);
      primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
