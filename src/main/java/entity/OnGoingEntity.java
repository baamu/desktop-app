package entity;

import java.util.Date;

/**
 * @author oshan
 */
public class OnGoingEntity extends SuperEntity {

    public OnGoingEntity() {
    }

    public OnGoingEntity(int id, String fileName, String url, long fileSize, Date schTime, String location) {
        super(id, fileName, url, fileSize, schTime, location);
    }
}
