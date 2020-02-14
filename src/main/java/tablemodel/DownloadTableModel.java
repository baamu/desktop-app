package tablemodel;

public class DownloadTableModel {
  private String fileName;
  private String url;
  private String fileSize;
  private String schTime;
  private String location;
  private String progress;

  public DownloadTableModel(String fileName, String url, String fileSize, String schTime, String location) {
    this.fileName = fileName;
    this.url = url;
    this.fileSize = fileSize;
    this.schTime = schTime;
    this.location = location;
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

  public String getProgress() {
    return progress;
  }

  public void setProgress(String progress) {
    this.progress = progress;
  }
}
