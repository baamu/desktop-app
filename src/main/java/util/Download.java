package util;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.Main;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimerTask;

public class Download  extends TimerTask implements Comparable<Download> {
    private int id;
    private String fileName;
    private String url;
    private long fileSize;
    private Date schTime;
    private String location;
    private long downloadedSize;

    private StringProperty progress = new SimpleStringProperty("0.0%");

    private File downloadFile;

    private boolean isExit = false;

    public Download(String url, Date schTime, String location) {
        this.url = url;
        this.schTime = schTime;
        this.location = location;

        init();
    }

    public Download(int id, String fileName, String url, long fileSize, Date schTime, String location) {
        this.id = id;
        this.fileName = fileName;
        this.url = url;
        this.fileSize = fileSize;
        this.schTime = schTime;
        this.location = location;

        downloadFile = new File(location, fileName);
    }

    public Download(Download download) {
        this.id = download.id;
        this.progress = download.progress;
        this.fileName = download.fileName;
        this.url = download.url;
        this.fileSize = download.fileSize;
        this.schTime = download.schTime;
        this.location = download.location;
        this.downloadFile = new File(download.downloadFile.getPath());
        this.downloadedSize = download.downloadedSize;

//        this.downloadedSize = downloadFile.;

        this.isExit = download.isExit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isExit() {
        return isExit;
    }

    public void setExit(boolean exit) {
        isExit = exit;
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

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public Date getSchTime() {
        return schTime;
    }

    public void setSchTime(Date schTime) {
        this.schTime = schTime;
    }

    public String getLocation() {
    return location;
  }

    public void setLocation(String location) {
    this.location = location;
  }

    public long getDownloadedSize() {
        return downloadedSize;
    }

    public void setDownloadedSize(long downloadedSize) {
        this.downloadedSize = downloadedSize;
    }

    public File getDownloadFile() {
    return downloadFile;
  }

    public void setDownloadFile(File downloadFile) {
    this.downloadFile = downloadFile;
  }

    public StringProperty progressProperty() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress.set(progress);
    }

    private void init() {

        String[] urlData = this.url.split( "/" );
        this.fileName = urlData[urlData.length-1];
        fileName = fileName.replaceAll("[\\\\/:*?\"<>|]", "");

        try {
            URL downURL = new URL( url );
            HttpURLConnection http = (HttpURLConnection) downURL.openConnection();

            this.downloadFile = new File(this.location, this.fileName);

            this.fileSize = http.getContentLengthLong();

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }

    public void stop() {
        cancel();
        this.isExit = true;
    }

    public void resume() {
        isExit = false;
    }

    @Override
    public void run() {

        try {
            URL url = new URL(this.url);

            long bytes = downloadFile.length();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String lastModified = "";
            try {
                BasicFileAttributes attr = Files.readAttributes(downloadFile.toPath(), BasicFileAttributes.class);
                lastModified = sdf.format(attr.creationTime().toMillis());
            }catch (NoSuchFileException ex) {
                System.out.println("New File");
            }

            System.out.println("Bytes : " + bytes);

            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.addRequestProperty("User-Agent", "Mozilla/4.76");

            //check whether the file was partially downloaded before
            if (bytes > 0) {
                downloadedSize = bytes;
                System.out.println("Downloaded size : " + downloadedSize);
                http.setRequestProperty("Range", "bytes=" + bytes + "-");
                System.out.println(lastModified);
                http.setRequestProperty("If-Range", lastModified);
            }

            BufferedInputStream inputStream = new BufferedInputStream(http.getInputStream());
            FileOutputStream fout = new FileOutputStream(downloadFile);
            BufferedOutputStream bout = new BufferedOutputStream(fout, 1024 * 64);
            byte[] buffer = new byte[1024];
            int read = 0;
            int readSize = 0;


            while (!isExit && (read = inputStream.read(buffer)) != -1) {
                bout.write(buffer, 0, read);
                downloadedSize += read;
                readSize += read;

                if (readSize >= 1024 * 64)     //flushes data on each 64KB
                {
                    readSize = 0;
                    bout.flush();
                }

                double downloadedPercent = (downloadedSize / (double)fileSize) * 100.0;
                progress.set(String.format("%.2f%%", downloadedPercent));
                System.out.println(String.format("Downloaded %.2f/%.2f (MB): %.2f%%", downloadedSize / (1024.0 * 1024.0), fileSize / (1024.0 * 1024.0), downloadedPercent));
            }

            bout.flush();
            bout.close();
            fout.close();
            inputStream.close();
            http.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(!isExit) {
            Main.DOWNLOAD_MANAGER.finishDownload(this);
        }
    }


    @Override
    public int compareTo(Download o) {
        return this.schTime.compareTo(o.schTime);   //returns -1 if this is earlier
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Download download = (Download) o;
        return Objects.equals(url, download.url) &&
                Objects.equals(schTime, download.schTime) &&
                Objects.equals(location, download.location);
    }

    @Override
    public int hashCode() {

        return Objects.hash(url, schTime, location);
    }

    @Override
    public String toString() {
        return "Download{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", url='" + url + '\'' +
                ", fileSize=" + fileSize +
                ", schTime=" + schTime +
                ", location='" + location + '\'' +
                ", downloadedSize=" + downloadedSize +
                ", downloadFile=" + downloadFile +
                ", isExit=" + isExit +
                '}';
    }
}
