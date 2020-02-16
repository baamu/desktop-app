package tablemodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import util.Download;

public class DownloadTableModel {

    private String fileName;
    private String url;
    private String fileSize;
    private String schTime;
    private String location;
    private StringProperty progress = new SimpleStringProperty();

    private Download download;

    public DownloadTableModel(Download download) {
        this.download = download;
        this.fileName = download.getFileName();
        this.url = download.getUrl();
        this.fileSize = download.getFileSize()+"";
        this.schTime = download.getSchTime().toGMTString();
        this.location = download.getLocation();

        this.progress.bind(download.progressProperty());

//        this.progress = (download.getDownloadedSize() /(double) download.getFileSize()) * 100.00 + "";
    }

    public String getFileName() {
    return fileName;
  }

    public void setFileName(String fileName) {
    this.fileName = fileName;
  }

    public String getUrl() {
    return url;
  }

    public void setUrl(String url) {
    this.url = url;
  }

    public String getFileSize() {
    return fileSize;
  }

    public void setFileSize(String fileSize) {
    this.fileSize = fileSize;
  }

    public String getSchTime() {
    return schTime;
  }

    public void setSchTime(String schTime) {
    this.schTime = schTime;
  }

    public String getLocation() {
    return location;
  }

    public void setLocation(String location) {
    this.location = location;
  }

    public StringProperty progressProperty() {
        return progress;
    }

    public String getProgress() {
        return progressProperty().getValue();
    }

    public void setProgress(String progress) {
    this.progress.set(progress);
  }

}
