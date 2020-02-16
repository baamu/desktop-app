package entity;

import java.util.Date;

/**
 * @author oshan
 */
public abstract class SuperEntity {
    protected int id;
    protected String fileName;
    protected String url;
    protected long fileSize;
    protected Date schTime;
    protected String location;

    public SuperEntity() {
    }

    public SuperEntity(String fileName, String url, long fileSize, Date schTime, String location) {
        this.fileName = fileName;
        this.url = url;
        this.fileSize = fileSize;
        this.schTime = schTime;
        this.location = location;
    }

    public SuperEntity(int id, String fileName, String url, long fileSize, Date schTime, String location) {
        this.id = id;
        this.fileName = fileName;
        this.url = url;
        this.fileSize = fileSize;
        this.schTime = schTime;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
