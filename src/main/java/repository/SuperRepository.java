package repository;

import entity.SuperEntity;

import java.util.List;

/**
 * @author oshan
 */
public interface SuperRepository<T extends SuperEntity> {
    boolean add(T t) throws Exception;
    T get(int id) throws Exception;
    boolean delete(int id) throws Exception;
    boolean update(T t) throws Exception;

    List<T> getAll() throws Exception;
}
