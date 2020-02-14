package view.controller;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import tablemodel.DownloadTableModel;
import util.Download;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewController implements Initializable {

  @FXML
  private JFXTextField addURL;

  @FXML
  private JFXTimePicker addTime;

  @FXML
  private TableView<DownloadTableModel> tbl;

  @FXML
  private TableColumn<DownloadTableModel, String> colFileName, colLoc, colProg, colSchTime, colUrl, colFileSize;

  private File downloadFile;

  private ObservableList<DownloadTableModel> tblRows;

  private List<Download> downloads;

  @FXML
  private AnchorPane rootPane;



  @FXML
  private void addButtonAction(ActionEvent event) {

    if (!(addURL.getText().isEmpty())) {
      final DirectoryChooser directoryChooser = new DirectoryChooser();
      Stage stage = (Stage) rootPane.getScene().getWindow();
      File selectedFile = directoryChooser.showDialog( stage );
      String z = addTime.getValue().toString();
      downloads.add( new Download( addURL.getText(), addURL.getText(), addURL.getText(), z, selectedFile.getAbsolutePath() ) );
      addURL.clear();
    }
  }

  public ViewController() {

  }

  public void initialize(URL location, ResourceBundle resources) {
    try {
      downloadFile = new File(getClass().getResource("downloads.csv").toURI());
    } catch (URISyntaxException e) {
      System.out.println("Download CSV load failed!");
      e.printStackTrace();
    }

    tblRows = tbl.getItems();
    downloads = new ArrayList<>(  );
    List<DownloadTableModel> csvData = null;
    try {
      csvData = readCSV( downloadFile );
      tblRows.addAll( csvData );
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private List<DownloadTableModel> readCSV(File csvFile) throws Exception{
    List<DownloadTableModel> rows = new ArrayList<>(  );
    BufferedReader csvReader = new BufferedReader(new FileReader( csvFile ));
    String row;
    while ((row = csvReader.readLine()) != null) {
      String[] data = row.split(",");
      // do something with the data

      DownloadTableModel model = new DownloadTableModel(data[0], data[1], data[2], data[3], data[4]);

      Download download = new Download( data[0], data[1], data[2], data[3], data[4] );

      downloads.add( download );

      rows.add( model );
    }
    csvReader.close();

    return rows;
  }
}
