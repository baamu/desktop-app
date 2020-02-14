package util;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Download extends Thread{
  private String fileName;
  private String url;
  private String fileSize;
  private String schTime;
  private String location;
  private String progress;

  private File downloadFile;

  public Download(String url, String schTime, String location) {
    this.url = url;
    this.schTime = schTime;
    this.location = location;

    init();
  }

  public Download(String fileName, String url, String fileSize, String schTime, String location) {
    this.fileName = fileName;
    this.url = url;
    this.fileSize = fileSize;
    this.schTime = schTime;
    this.location = location;

    downloadFile = new File(location, fileName);
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

  public File getDownloadFile() {
    return downloadFile;
  }

  public void setDownloadFile(File downloadFile) {
    this.downloadFile = downloadFile;
  }

  private void init() {
    String[] urlData = this.url.split( "/" );
    this.fileName = urlData[urlData.length-1];

    try {
      URL downURL = new URL( url );
      HttpURLConnection http = (HttpURLConnection) downURL.openConnection();

      this.downloadFile = new File(this.location, this.fileName);

      this.fileSize = Long.toString( http.getContentLengthLong() );

    } catch (Exception e) {
      e.printStackTrace();
      return;
    }


  }

  @Override
  public void run() {

  }
}
