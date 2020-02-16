package repository.impl;

import db.connection.DBConnection;
import entity.HistoryEntity;
import entity.OnGoingEntity;
import repository.SuperRepository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author oshan
 */
public class HistoryRepository implements SuperRepository<HistoryEntity> {
    private Connection connection;

    public HistoryRepository() {
        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean add(HistoryEntity historyEntity) throws Exception {
        String sql = "INSERT INTO history (fileName, url, fileSize, schTime, location, finishedTime) VALUES (?,?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setObject(1,historyEntity.getFileName());
        stm.setObject(2,historyEntity.getUrl());
        stm.setObject(3,historyEntity.getFileSize());
        stm.setObject(4,historyEntity.getSchTime());
        stm.setObject(5,historyEntity.getLocation());
        stm.setObject(6,new Date());

        return stm.executeUpdate() > 0;
    }

    @Override
    public HistoryEntity get(int id) throws Exception {
        String sql = "SELECT * FROM history WHERE id=?";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setObject(1,id);
        ResultSet rst = stm.executeQuery();

        rst.next();

        return new HistoryEntity(
                rst.getInt(1),
                rst.getString(2),
                rst.getString(3),
                rst.getLong(4),
                rst.getDate(5),
                rst.getString(6),
                rst.getDate(7)
        );
    }

    @Override
    public boolean delete(int id) throws Exception {
        String sql = "DELETE FROM history WHERE id=?";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setObject(1,id);

        return stm.executeUpdate()>0;
    }

    @Override
    public boolean update(HistoryEntity historyEntity) throws Exception {
        return false;
    }

    @Override
    public List<HistoryEntity> getAll() throws Exception {
        List<HistoryEntity> downs = new ArrayList<>();
        String sql = "SELECT * FROM history";
        PreparedStatement stm = connection.prepareStatement(sql);

        ResultSet rst = stm.executeQuery();

        while (rst.next())
            downs.add(
                    new HistoryEntity(
                            rst.getInt(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getLong(4),
                            rst.getDate(5),
                            rst.getString(6),
                            rst.getDate(7)
                    )
            );

        return downs;
    }
}
