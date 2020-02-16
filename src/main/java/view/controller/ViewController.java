package view.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import main.Main;
import tablemodel.DownloadTableModel;
import util.Download;

import java.io.File;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ViewController implements Initializable {

    @FXML
    private Button btnStart;

    @FXML
    private Button btnStartAll;

    @FXML
    private Button btnPause;

    @FXML
    private Button btnStop;

    @FXML
    private Button btnStopAll;

    @FXML
    private TextField txtUrl;

    @FXML
    private TextField txtHr;

    @FXML
    private TextField txtMn;

    @FXML
    private Button btnAdd;

  @FXML
  private TableView<DownloadTableModel> tbl;

  @FXML
  private TableColumn<DownloadTableModel, String> colFileName, colLoc, colSchTime, colUrl, colFileSize, colProg;

//  @FXML private TableColumn<DownloadTableModel, StringProperty> colProg;

  private File downloadFile;

  private ObservableList<DownloadTableModel> tblRows;

  private List<Download> downloads;

  @FXML
  private AnchorPane rootPane;


  public ViewController() {

  }

  @FXML
  private void stop(ActionEvent event) {
      DownloadTableModel model = tbl.getSelectionModel().getSelectedItem();
      Download download = new Download(model.getUrl(), new Date(model.getSchTime()), model.getLocation());
      Main.DOWNLOAD_MANAGER.stopDownload(download);
  }

  @FXML
  private void resume(ActionEvent event) {
      DownloadTableModel model = tbl.getSelectionModel().getSelectedItem();
      Download download = new Download(model.getUrl(), new Date(model.getSchTime()), model.getLocation());
      Main.DOWNLOAD_MANAGER.resumeDownload(download);
  }

  @FXML
  private void addDownload(ActionEvent event) {
//      System.out.println(txtHr.getText() + ":" + txtMn.getText());
      try {
          String location;
          if(!txtUrl.getText().isEmpty() && !txtMn.getText().isEmpty() && !txtHr.getText().isEmpty()) {
              final DirectoryChooser directoryChooser = new DirectoryChooser();
              Stage stage = (Stage) rootPane.getScene().getWindow();
              File selectedFile = directoryChooser.showDialog( stage );
              location = selectedFile.getAbsolutePath();
          } else {
              System.out.println("Fields Empty!");
              return;
          }

          int hour = Integer.parseInt(txtHr.getText());
          int min = Integer.parseInt(txtMn.getText());

          Calendar calendar = Calendar.getInstance();
          calendar.set(Calendar.HOUR_OF_DAY, hour);
          calendar.set(Calendar.MINUTE, min);
          calendar.set(Calendar.SECOND, 0);

          System.out.println(calendar.getTime());
          System.out.println(location);

//      System.out.println(calendar.getTime());

          Download download = new Download(txtUrl.getText(), calendar.getTime(), location);

          System.out.println(download);

          download = Main.DOWNLOAD_MANAGER.addNewDownload(download);
          System.out.println();
          System.out.println(download);

          DownloadTableModel row = new DownloadTableModel(download);

          tbl.getItems().add(row);


      } catch (Exception e) {
          e.printStackTrace();
//          System.out.println("Number format error");
      }
  }

  public void initialize(URL location, ResourceBundle resources) {
//    System.out.println(addTime.getValue().toString());

//      System.out.println("here");

      colFileName.setCellValueFactory(new PropertyValueFactory<>("fileName"));
      colFileSize.setCellValueFactory(new PropertyValueFactory<>("fileSize"));
      colLoc.setCellValueFactory(new PropertyValueFactory<>("location"));
      colSchTime.setCellValueFactory(new PropertyValueFactory<>("schTime"));
      colProg.setCellValueFactory(new PropertyValueFactory<>("progress"));
      colUrl.setCellValueFactory(new PropertyValueFactory<>("url"));

      tblRows = tbl.getItems();

//      System.out.println("init");
      for(Download download : Main.DOWNLOAD_MANAGER.getDownloads()) {
          DownloadTableModel model = new DownloadTableModel(download);
          tblRows.add(model);
      }

      btnAdd.setOnAction(this::addDownload);

      btnStop.setOnAction(this::stop);
      btnStart.setOnAction(this::resume);

  }


}
