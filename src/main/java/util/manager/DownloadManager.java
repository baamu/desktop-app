package util.manager;

import entity.HistoryEntity;
import entity.OnGoingEntity;
import repository.impl.HistoryRepository;
import repository.impl.OnGoingRepository;
import util.Download;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * @author oshan
 */
public class DownloadManager {
    private final List<Download> downloads = new ArrayList<>();
    private final List<Download> inturrupted = new ArrayList<>();

    private final OnGoingRepository onGoingRepository = new OnGoingRepository();
    private final HistoryRepository historyRepository = new HistoryRepository();

    private Timer scheduler = new Timer();

    public DownloadManager() {
        init();
    }

    private void init() {
//        System.out.println("manager");
        try {
            List<OnGoingEntity> ds = onGoingRepository.getAll();

            for(OnGoingEntity entity : ds) {
                Download download = new Download(
                        entity.getId(),
                        entity.getFileName(),
                        entity.getUrl(),
                        entity.getFileSize(),
                        entity.getSchTime(),
                        entity.getLocation()
                );

                downloads.add(download);
                schedule(download);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void schedule(Download download) {
        scheduler.schedule(download, download.getSchTime());
    }

    public Download addNewDownload(Download download) {
        //add to repo and get id and set
        OnGoingEntity ent = new OnGoingEntity();
        ent.setFileName(download.getFileName());
        ent.setFileSize(download.getFileSize());
        ent.setLocation(download.getLocation());
        ent.setSchTime(download.getSchTime());
        ent.setUrl(download.getUrl());

        try {
            onGoingRepository.add(ent);

            ent = onGoingRepository.get(download.getUrl());
            download.setId(ent.getId());

            downloads.add(download);
            schedule(download);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return download;

    }

    public void removeDownload(Download download) {
        download = downloads.get(downloads.indexOf(download));

        stopDownload(download);

        downloads.remove(download);
        inturrupted.remove(download);

        try {
            onGoingRepository.delete(download.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopDownload(Download download) {
        Download d = downloads.get(downloads.indexOf(download));
        d.stop();

        downloads.remove(d);

        Download dlNew = new Download(d);
        downloads.add(dlNew);
        inturrupted.add(dlNew);
    }

    public void resumeDownload(Download download) {
        Download d = inturrupted.get(inturrupted.indexOf(download));
        d.resume();
        schedule(d);
        inturrupted.remove(d);
    }

    public List<Download> getDownloads() {
        return downloads;
    }

    //called by downloads when they finished downloading
    public void finishDownload(Download download) {
        System.out.println("Download Finished!");
        try {
            if(onGoingRepository.delete(download.getId())) {
                //add to history repo
                HistoryEntity hst = new HistoryEntity(
                        download.getFileName(),
                        download.getUrl(),
                        download.getFileSize(),
                        download.getSchTime(),
                        download.getLocation()
                );

                historyRepository.add(hst);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
