package entity;

import java.util.Date;

/**
 * @author oshan
 */
public class HistoryEntity extends SuperEntity {
    private Date finishedTime;

    public HistoryEntity(int id, String fileName, String url, long fileSize, Date schTime, String location) {
        super(id, fileName, url, fileSize, schTime, location);
    }

    public HistoryEntity(String fileName, String url, long fileSize, Date schTime, String location) {
        super(fileName, url, fileSize, schTime, location);
    }

    public HistoryEntity(int id, String fileName, String url, long fileSize, Date schTime, String location, Date finishedTime) {
        super(id, fileName, url, fileSize, schTime, location);
        this.finishedTime = finishedTime;
    }
}
