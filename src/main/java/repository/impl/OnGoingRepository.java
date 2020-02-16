package repository.impl;

import db.connection.DBConnection;
import entity.OnGoingEntity;
import repository.SuperRepository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author oshan
 */
public class OnGoingRepository implements SuperRepository<OnGoingEntity> {

    private Connection connection;

    public OnGoingRepository() {
        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean add(OnGoingEntity onGoingEntity) throws Exception {
        String sql = "INSERT INTO ongoing (fileName, url, fileSize, schTime, location) VALUES (?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setObject(1,onGoingEntity.getFileName());
        stm.setObject(2,onGoingEntity.getUrl());
        stm.setObject(3,onGoingEntity.getFileSize());
        stm.setObject(4,onGoingEntity.getSchTime());
        stm.setObject(5,onGoingEntity.getLocation());

        return stm.executeUpdate() > 0;
    }

    @Override
    public OnGoingEntity get(int id) throws Exception {

        String sql = "SELECT * FROM ongoing WHERE id=?";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setObject(1,id);
        ResultSet rst = stm.executeQuery();

        rst.next();

        return new OnGoingEntity(
                rst.getInt(1),
                rst.getString(2),
                rst.getString(3),
                rst.getLong(4),
                rst.getDate(5),
                rst.getString(6)
        );
    }

    @Override
    public boolean delete(int id) throws Exception {
        String sql = "DELETE FROM ongoing WHERE id=?";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setObject(1,id);

        return stm.executeUpdate()>0;
    }

    @Override
    public boolean update(OnGoingEntity onGoingEntity) throws Exception {
        return false;
    }

    @Override
    public List<OnGoingEntity> getAll() throws Exception {
        List<OnGoingEntity> downs = new ArrayList<>();
        String sql = "SELECT * FROM ongoing";
        PreparedStatement stm = connection.prepareStatement(sql);

        ResultSet rst = stm.executeQuery();

        while (rst.next())
            downs.add(
                    new OnGoingEntity(
                            rst.getInt(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getLong(4),
                            rst.getDate(5),
                            rst.getString(6)
                    )
            );

        return downs;
    }

    public OnGoingEntity get(String url) throws Exception {
        String sql = "SELECT * FROM ongoing WHERE url=?";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setObject(1,url);
        ResultSet rst = stm.executeQuery();

        rst.next();

        return new OnGoingEntity(
                rst.getInt(1),
                rst.getString(2),
                rst.getString(3),
                rst.getLong(4),
                rst.getDate(5),
                rst.getString(6)
        );
    }
}
